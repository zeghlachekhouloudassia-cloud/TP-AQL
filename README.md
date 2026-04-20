# TP3 – Tests d'Intégration Java avec JUnit 5, Mockito et Testcontainers

## Structure du projet

```
tp3-integration/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/tp3/
    │   ├── Exercice1/
    │   │   ├── User.java
    │   │   ├── UserRepository.java        (interface)
    │   │   ├── UserRepositoryJdbc.java    (implémentation JDBC – Partie 2)
    │   │   └── UserService.java
    │   ├── Exercice2/
    │   │   ├── Order.java
    │   │   ├── OrderController.java
    │   │   ├── OrderDao.java              (interface)
    │   │   ├── OrderDaoJdbc.java          (implémentation JDBC – Partie 2)
    │   │   └── OrderService.java
    │   └── Exercice3/
    │       ├── ApiException.java
    │       ├── Product.java
    │       ├── ProductApiClient.java      (interface)
    │       ├── ProductApiClientHttp.java  (implémentation HTTP – Partie 2)
    │       └── ProductService.java
    └── test/java/tp3/
        ├── Exercice1/
        │   ├── Exercice1Test.java              ← Partie 1 (Mockito)
        │   └── UserServiceIntegrationTest.java ← Partie 2 (Testcontainers + MySQL)
        ├── Exercice2/
        │   ├── Exercice2Test.java              ← Partie 1 (Mockito)
        │   └── OrderIntegrationTest.java       ← Partie 2 (Testcontainers + MySQL)
        └── Exercice3/
            ├── Exercice3Test.java              ← Partie 1 (Mockito)
            └── ProductServiceIntegrationTest.java ← Partie 2 (Testcontainers + MockServer)
```

---

## Prérequis et installation de l'environnement (VS Code + Maven + JaCoCo)

### 1. Java 11+
```bash
# Vérifier la version
java -version
```
Si Java n'est pas installé, télécharger depuis https://adoptium.net

### 2. Maven
```bash
mvn -version
```
Si absent, télécharger depuis https://maven.apache.org/download.cgi et ajouter au PATH.

### 3. Docker (sans WSL2)
Vous utilisez Docker Desktop sans WSL2 (mode Hyper-V).  
Vérifier que Docker tourne :
```bash
docker info
```
Si Docker Desktop est installé mais que les conteneurs ne démarrent pas :
- Ouvrir Docker Desktop → Settings → General → cocher **"Use Hyper-V instead of WSL 2"**
- Redémarrer Docker Desktop

Testcontainers utilise Docker pour démarrer les conteneurs MySQL et MockServer automatiquement.

### 4. VS Code – Extensions recommandées
- **Extension Pack for Java** (Microsoft) – inclut Maven, débogueur, etc.
- **Test Runner for Java** (Microsoft) – pour lancer/visualiser les tests JUnit
- **Docker** (Microsoft) – pour visualiser les conteneurs

### 5. Variable d'environnement (si Testcontainers ne trouve pas Docker)
Si les tests Testcontainers échouent avec "Cannot connect to Docker", ajouter dans votre shell :
```bash
# Windows (PowerShell)
$env:DOCKER_HOST = "tcp://localhost:2375"
# ou
$env:TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE = "//./pipe/docker_engine"
```
Dans Docker Desktop → Settings → General → cocher **"Expose daemon on tcp://localhost:2375 without TLS"**.

---

## Lancer les tests

### Tous les tests (Partie 1 + Partie 2)
```bash
mvn test
```

### Seulement les tests Testcontainers (Partie 2)
```bash
mvn test -Dtest="UserServiceIntegrationTest,OrderIntegrationTest,ProductServiceIntegrationTest"
```

### Seulement les tests Mockito (Partie 1)
```bash
mvn test -Dtest="Exercice1Test,Exercice2Test,Exercice3Test"
```

### Rapport de couverture JaCoCo
```bash
mvn test
# Le rapport HTML est généré dans :
# target/site/jacoco/index.html
```
Ouvrir ce fichier dans un navigateur pour visualiser la couverture par classe/méthode/ligne.

---

## Partie 1 – Tests unitaires avec Mockito (déjà réalisée)

Les tests de la Partie 1 utilisent Mockito pour simuler (`mock`) les dépendances :
- `UserRepository` est mocké → `UserService` est testée en isolation
- `OrderDao` est mocké → chaîne `OrderController → OrderService` testée avec Mockito
- `ProductApiClient` est mocké → `ProductService` testée en isolation

**Point clé :** les mocks permettent de tester chaque couche sans dépendance réelle (pas de base de données, pas d'API externe).

---

## Partie 2 – Tests d'intégration avec Testcontainers

### Philosophie
Au lieu de mocker les dépendances, on démarre de vraies instances en Docker :
| Exercice | Dépendance mockée avant | Remplacée par |
|----------|------------------------|---------------|
| Exercice 1 | `UserRepository` (mock Mockito) | `UserRepositoryJdbc` + **MySQL dans Docker** |
| Exercice 2 | `OrderDao` (mock Mockito) | `OrderDaoJdbc` + **MySQL dans Docker** |
| Exercice 3 | `ProductApiClient` (mock Mockito) | `ProductApiClientHttp` + **MockServer dans Docker** |

### Comment Testcontainers fonctionne
```java
@Container
static final MySQLContainer<?> mysql =
    new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("tp3db")
        .withUsername("tp3user")
        .withPassword("tp3pass");
```
- Au démarrage du test, Testcontainers **pull l'image MySQL 8.0** (si pas encore présente) et démarre un conteneur.
- L'URL JDBC (`mysql.getJdbcUrl()`) contient le port mappé dynamiquement.
- À la fin des tests, le conteneur est **arrêté et supprimé automatiquement**.

### Avantages vs Mockito pour les tests d'intégration
| Critère | Mockito (mocks) | Testcontainers |
|---------|----------------|----------------|
| Vitesse d'exécution | ⚡ Très rapide | 🐢 Plus lent (démarrage Docker) |
| Fidélité au comportement réel | ❌ Simulé | ✅ Réel |
| Détection des bugs SQL | ❌ Non | ✅ Oui |
| Détection des bugs réseau/HTTP | ❌ Non | ✅ Oui |
| Reproductibilité | ✅ Parfaite | ✅ Parfaite |
| Dépendance externe | ❌ Aucune | ✅ Docker requis |

### Inconvénients de Testcontainers
- Requiert Docker en cours d'exécution
- Plus lent (pull d'image + démarrage du conteneur : 5-30s)
- Plus complexe à configurer dans les pipelines CI (besoin de Docker dans le runner)

---

## Partie 3 – Exercice 2 : Projet task-manager (rengreen)

### Lien GitHub cloné
```
https://github.com/rengreen/task-manager
```

### 1. Cloner le projet
```bash
git clone https://github.com/rengreen/task-manager.git
cd task-manager
```

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

**Scénario supplémentaire ajouté :**  
Test de cohérence des données après plusieurs insertions et suppressions successives — ce type de test révèle des problèmes de transactions que H2 peut masquer.

---

## Pousser sur GitHub

```bash
# Initialiser le dépôt (si pas encore fait)
git init
git add .
git commit -m "feat: TP3 - tests unitaires (Partie 1) + intégration Testcontainers (Partie 2+3)"

# Lier à votre dépôt GitHub
git remote add origin https://github.com/VOTRE_USERNAME/tp3-integration.git
git branch -M main
git push -u origin main
```

**Structure du commit recommandée :**
```
feat: TP3 Partie 1 - tests unitaires Mockito (Exercice1/2/3)
feat: TP3 Partie 2 - Exercice1 Testcontainers MySQL (UserService)
feat: TP3 Partie 2 - Exercice2 Testcontainers MySQL (OrderController chain)
feat: TP3 Partie 2 - Exercice3 Testcontainers MockServer (ProductService HTTP)
docs: README avec analyse task-manager (Partie 3 Exercice2)
```
