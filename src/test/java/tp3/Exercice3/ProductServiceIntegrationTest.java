package tp3.Exercice3;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TP3 – Partie 2 – Exercice 1 (Testcontainers) appliqué à Exercice 3
 *
 * On remplace le mock de ProductApiClient par une vraie API simulée
 * grâce à MockServer démarré dans un conteneur Docker via Testcontainers.
 *
 * Chaîne testée : ProductService → ProductApiClientHttp → MockServer (Docker)
 *
 * MockServer (mockserver/mockserver:5.15.0) est un serveur HTTP léger
 * que l'on configure via son API REST pour retourner des réponses définies.
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceIntegrationTest {

    private static final int MOCK_PORT = 1080;

    @Container
    static final GenericContainer<?> mockServer =
        new GenericContainer<>(DockerImageName.parse("mockserver/mockserver:5.15.0"))
            .withExposedPorts(MOCK_PORT);

    private static String       baseUrl;
    private static ProductService service;

    // ── JSON responses ────────────────────────────────────────────────────────
    private static final String LAPTOP_JSON =
        "{\"id\":\"P001\",\"name\":\"Laptop\",\"price\":999.99,\"category\":\"Electronics\"}";
    private static final String MOUSE_JSON =
        "{\"id\":\"P002\",\"name\":\"Mouse\",\"price\":25.50,\"category\":\"Accessories\"}";

    @BeforeAll
    static void setUp() throws Exception {
        baseUrl = "http://" + mockServer.getHost() + ":" + mockServer.getMappedPort(MOCK_PORT);
        ProductApiClientHttp client = new ProductApiClientHttp(baseUrl);
        service = new ProductService(client);

        // ── Configurer les expectations MockServer ────────────────────────────
        // P001 → 200 + JSON Laptop
        addExpectation("/products/P001", 200, LAPTOP_JSON);
        // P002 → 200 + JSON Mouse
        addExpectation("/products/P002", 200, MOUSE_JSON);
        // P999 → 404 (not found)
        addExpectation("/products/P999", 404, "");
        // P_ERR → 500 (server error)
        addExpectation("/products/P_ERR", 500, "Internal Server Error");
        // P_BAD → 200 + JSON invalide (format incompatible)
        addExpectation("/products/P_BAD", 200, "{\"broken\":true}");
    }

    /**
     * Envoie une expectation à MockServer via son API REST PUT /mockserver/expectation.
     */
    static void addExpectation(String path, int statusCode, String body) throws Exception {
        String payload = "{"
            + "\"httpRequest\":{\"path\":\"" + path + "\"},"
            + "\"httpResponse\":{"
            +   "\"statusCode\":" + statusCode + ","
            +   "\"body\":\"" + body.replace("\"", "\\\"") + "\""
            + "}"
            + "}";

        URL url = new URL(baseUrl + "/mockserver/expectation");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }
        conn.getResponseCode(); // déclenche la requête
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 1 : récupération réussie – le produit est bien désérialisé
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(1)
    void testGetProduct_RecuperationReussie() throws ApiException {
        Product p = service.getProduct("P001");

        assertNotNull(p);
        assertEquals("P001",        p.getId());
        assertEquals("Laptop",      p.getName());
        assertEquals(999.99,        p.getPrice(), 0.001);
        assertEquals("Electronics", p.getCategory());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 2 : produit non trouvé (404) → ApiException "Product not found"
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    void testGetProduct_ProduitInexistant_LanceApiException() {
        ApiException ex = assertThrows(ApiException.class,
            () -> service.getProduct("P999"));

        assertTrue(ex.getMessage().contains("Product not found"),
            "Le message doit contenir 'Product not found'");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 3 : erreur serveur (500) → ApiException propagée
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    void testGetProduct_ErreurServeur500_LanceApiException() {
        ApiException ex = assertThrows(ApiException.class,
            () -> service.getProduct("P_ERR"));

        assertTrue(ex.getMessage().contains("HTTP 500") || ex.getMessage().contains("API"),
            "Une erreur 500 doit lever une ApiException");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 4 : format JSON incompatible → ApiException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    void testGetProduct_FormatIncompatible_LanceApiException() {
        assertThrows(ApiException.class,
            () -> service.getProduct("P_BAD"),
            "Un JSON malformé doit lever une ApiException");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 5 : ID null ou vide → IllegalArgumentException (validation côté service)
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    void testGetProduct_IdNull_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.getProduct(null));
    }

    @Test
    @Order(6)
    void testGetProduct_IdVide_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.getProduct(""));
    }

    @Test
    @Order(7)
    void testGetProduct_IdEspaces_LanceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.getProduct("   "));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 6 : deux produits différents avec des IDs différents
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(8)
    void testGetProduct_DeuxProduitsDistincts() throws ApiException {
        Product laptop = service.getProduct("P001");
        Product mouse  = service.getProduct("P002");

        assertAll(
            () -> assertEquals("Laptop", laptop.getName()),
            () -> assertEquals("Mouse",  mouse.getName()),
            () -> assertNotEquals(laptop.getPrice(), mouse.getPrice())
        );
    }
}
