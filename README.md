# TP3 – Tests d'Intégration 


## Partie 2 – Tests d'intégration avec Testcontainers

### Philosophie
Au lieu de mocker les dépendances, on démarre de vraies instances en Docker :
| Exercice | Dépendance mockée avant | Remplacée par |
|----------|------------------------|---------------|
| Exercice 1 | `UserRepository` (mock Mockito) | `UserRepositoryJdbc` + **MySQL dans Docker** |
| Exercice 2 | `OrderDao` (mock Mockito) | `OrderDaoJdbc` + **MySQL dans Docker** |
| Exercice 3 | `ProductApiClient` (mock Mockito) | `ProductApiClientHttp` + **MockServer dans Docker** |



### Avantages vs Mockito pour les tests d'intégration
| Critère | Mockito (mocks) | Testcontainers |
|---------|----------------|----------------|
| Vitesse d'exécution | ⚡ Très rapide | 🐢 Plus lent (démarrage Docker) |
| Fidélité au comportement réel | ❌ Simulé | ✅ Réel |
| Détection des bugs SQL | ❌ Non | ✅ Oui |
| Détection des bugs réseau/HTTP | ❌ Non | ✅ Oui |
| Reproductibilité | ✅ Parfaite | ✅ Parfaite |
| Dépendance externe | ❌ Aucune | ✅ Docker requis |


---

## Partie 3 – Exercice 2 : Projet task-manager (rengreen)

### Lien GitHub cloné

https://github.com/rengreen/task-manager

### 2. Analyse des tests existants

Le projet `task-manager` utilise **Spring Boot** avec une base de données **H2 en mémoire** pour les tests.

**Approches utilisées dans les tests existants :**
- `@SpringBootTest` avec H2 en mémoire (`spring.datasource.url=jdbc:h2:mem:testdb`)
- Tests de repository avec `@DataJpaTest`
- Tests de service avec `@MockBean` pour les repositories

**Limitations des tests existants :**
1. **H2 ≠ MySQL en production** : les tests utilisent H2 (base en mémoire) alors que l'application cible MySQL. Des requêtes SQL spécifiques à MySQL peuvent passer en H2 mais échouer en prod.
2. **Pas de test de la couche réseau** : les tests ne valident pas le comportement HTTP réel (headers, status codes, sérialisation JSON).
3. **Données perdues entre tests** : la base H2 est recréée à chaque test, ce qui est réaliste mais ne teste pas la persistance.

### 3. Tests réécrits avec Testcontainers

Les tests `@DataJpaTest` avec H2 sont remplacés par des tests utilisant **MySQL via Testcontainers**.

**Exemple de réécriture :**
```java
// AVANT (H2 en mémoire)
@DataJpaTest
class TaskRepositoryTest {
    @Autowired TaskRepository repo;
    // ...
}

// APRÈS (MySQL réel via Testcontainers)
@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("taskdb")
        .withUsername("root")
        .withPassword("password");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",      mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired TaskRepository repo;

    @Test
    void testSaveAndFindTask() {
        Task task = new Task("Test task", "Description");
        Task saved = repo.save(task);
        Optional<Task> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test task", found.get().getName());
    }
}
```

### 4. Comparaison finale

| Aspect | Tests H2 originaux | Tests Testcontainers réécrits |
|--------|-------------------|-------------------------------|
| Couverture | Bonne pour la logique métier | Meilleure : inclut le comportement SQL réel |
| Lisibilité | Simple (`@DataJpaTest`) | Un peu plus verbeux (config MySQL) |
| Maintenabilité | Facile | Nécessite Docker dans l'équipe |
| Fiabilité | Risque de divergence H2/MySQL | Fidèle à la prod |
| Scénarios ajoutés | — | Test de persistance après redémarrage du service |


---


