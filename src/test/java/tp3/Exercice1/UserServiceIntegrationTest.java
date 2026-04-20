package tp3.Exercice1;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TP3 – Partie 2 – Exercice 1 (Testcontainers)
 *
 * On remplace le mock de UserRepository par une vraie base MySQL
 * démarrée automatiquement dans un conteneur Docker via Testcontainers.
 *
 * Ce test vérifie l'intégration réelle entre UserService et la base de données.
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceIntegrationTest {

    // ── Conteneur MySQL partagé sur toute la classe (démarré une seule fois) ──
    @Container
    static final MySQLContainer<?> mysql =
        new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("tp3db")
            .withUsername("tp3user")
            .withPassword("tp3pass");

    private static UserRepositoryJdbc repo;
    private static UserService        service;

    // ── Initialisation unique après démarrage du conteneur ────────────────────
    @BeforeAll
    static void setUpDatabase() throws Exception {
        repo = new UserRepositoryJdbc(
            mysql.getJdbcUrl(),
            mysql.getUsername(),
            mysql.getPassword()
        );
        repo.initSchema();

        // Insérer des données de test
        repo.save(new User(1L, "Alice Martin",  "alice@email.com"));
        repo.save(new User(2L, "Bob Durand",    "bob@email.com"));
        repo.save(new User(3L, "Charlie Lopez", "charlie@email.com"));

        service = new UserService(repo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 1 : récupération réussie d'un utilisateur existant
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(1)
    void testGetUserById_UtilisateurExistant_RetourneUser() {
        User user = service.getUserById(1L);

        assertNotNull(user, "L'utilisateur doit être trouvé en base");
        assertEquals(1L,              user.getId());
        assertEquals("Alice Martin",  user.getName());
        assertEquals("alice@email.com", user.getEmail());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 2 : utilisateur inexistant → retourne null
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    void testGetUserById_UtilisateurInexistant_RetourneNull() {
        User user = service.getUserById(999L);

        assertNull(user, "Un ID inexistant doit renvoyer null");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 3 : plusieurs utilisateurs sont récupérables indépendamment
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    void testGetUserById_PlusieursUtilisateurs_ChacunRecuperableIndependamment() {
        User alice   = service.getUserById(1L);
        User bob     = service.getUserById(2L);
        User charlie = service.getUserById(3L);

        assertAll(
            () -> assertEquals("Alice Martin",  alice.getName()),
            () -> assertEquals("Bob Durand",    bob.getName()),
            () -> assertEquals("Charlie Lopez", charlie.getName())
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 4 : les appels successifs retournent les mêmes données
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    void testGetUserById_AppelsSuccessifs_MemeResultat() {
        User first  = service.getUserById(2L);
        User second = service.getUserById(2L);

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(first.getId(),    second.getId());
        assertEquals(first.getName(),  second.getName());
        assertEquals(first.getEmail(), second.getEmail());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario 5 : données cohérentes (email et nom cohérents pour chaque user)
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    void testGetUserById_DonneesCoherentes() {
        User user = service.getUserById(3L);

        assertNotNull(user);
        assertTrue(user.getEmail().contains("charlie"),
            "L'email doit correspondre à l'utilisateur Charlie");
    }
}
