package tp3.Exercice2;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TP3 – Partie 2 – Exercice 1 (Testcontainers) appliqué à Exercice 2
 *
 * On remplace le mock de OrderDao par une vraie base MySQL dans Docker.
 * La chaîne complète OrderController → OrderService → OrderDaoJdbc → MySQL
 * est testée en intégration réelle.
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderIntegrationTest {

    @Container
    static final MySQLContainer<?> mysql =
        new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("ordersdb")
            .withUsername("tp3user")
            .withPassword("tp3pass");

    private static OrderDaoJdbc     dao;
    private static OrderService     service;
    private static OrderController  controller;

    @BeforeAll
    static void setUp() throws Exception {
        dao = new OrderDaoJdbc(
            mysql.getJdbcUrl(),
            mysql.getUsername(),
            mysql.getPassword()
        );
        dao.initSchema();

        service    = new OrderService(dao);
        controller = new OrderController(service);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 1 : création réussie – la commande est persistée en base
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(1)
    void testCreateOrder_ChaineComplete_CommandePersisteeEnBase() throws Exception {
        Commande order = new Commande(0L, "Laptop", 2, 999.99);

        Commande saved = controller.createOrder(order);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0, "L'ID auto-généré doit être > 0");
        assertEquals("Laptop", saved.getProductName());
        assertEquals(2,        saved.getQuantity());
        assertEquals(999.99,   saved.getPrice(), 0.001);

        // Vérification en base directement
        Commande fromDb = dao.findById(saved.getId());
        assertNotNull(fromDb);
        assertEquals("Laptop", fromDb.getProductName());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 2 : commande null → IllegalArgumentException, rien en base
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    void testCreateOrder_OrderNull_LanceException() {
        assertThrows(IllegalArgumentException.class,
            () -> controller.createOrder(null));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 3 : quantité négative → IllegalArgumentException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    void testCreateOrder_QuantityNegative_LanceException() {
        Commande bad = new Commande(0L, "Product", -1, 10.0);
        assertThrows(IllegalArgumentException.class,
            () -> controller.createOrder(bad));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 4 : prix négatif → IllegalArgumentException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    void testCreateOrder_PriceNegative_LanceException() {
        Commande bad = new Commande(0L, "Product", 1, -5.0);
        assertThrows(IllegalArgumentException.class,
            () -> controller.createOrder(bad));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 5 : plusieurs commandes successives ont des IDs distincts
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    void testCreateOrder_PlusieursCommandes_IDsDistincts() {
        Commande o1 = controller.createOrder(new Commande(0L, "Mouse",    1,  25.0));
        Commande o2 = controller.createOrder(new Commande(0L, "Keyboard", 1,  75.0));
        Commande o3 = controller.createOrder(new Commande(0L, "Monitor",  1, 350.0));

        assertNotEquals(o1.getId(), o2.getId());
        assertNotEquals(o2.getId(), o3.getId());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 6 : le prix total calculé est cohérent avec qty × price
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(6)
    void testCreateOrder_PrixTotalCoherent() {
        Commande order = new Commande(0L, "Webcam", 3, 49.99);
        Commande saved = controller.createOrder(order);

        assertEquals(3 * 49.99, saved.getTotalPrice(), 0.001);
    }
}
