================================================================================
TP3 - TESTS D'INTEGRATION AVEC STUBS
================================================================================

OBJECTIF DU TP
================================================================================
Apprendre à écrire des tests d'intégration en utilisant la technique de 
stubbing avec Mockito pour simuler les dépendances externes (base de données, 
API, etc.).




Dans ce TP :
  -> Les classes metier (UserService, OrderService, ProductService) sont 
     testees reellement
  -> Seules les dependances externes (UserRepository, OrderDao, 
     ProductApiClient) sont stubbees/mockees





RESUME DES EXERCICES
================================================================================

EXERCICE 1 : Interaction Simple entre Modules
--------------------------------------------------------------------------------
Classe testee : UserService
Dependance stubee : UserRepository

Ce qu'on a teste :
  - Recuperation reussie d'un utilisateur
  - Utilisateur inexistant (retour null)
  - Propagation d'exception (panne DB)
  - Regle "dernier stub ecrase le precedent"
  - Regle "retourne toujours la meme valeur"

Logique :
  UserService (teste) -> UserRepository (stubbe)
  On controle ce que retourne le repository pour voir comment le service reagit


EXERCICE 2 : Interaction avec une Base de Donnees
--------------------------------------------------------------------------------
Classes testees : OrderController ET OrderService (toute la chaine)
Dependance stubee : OrderDao (acces base de donnees)

Ce qu'on a teste :
  - Chaine complete Controller -> Service -> Dao
  - Validation des donnees (commande null, quantite negative, prix negatif)
  - Gestion des erreurs du Dao (exception propagee)
  - Chaine thenThrow() puis thenReturn()
  - Ordre des appels (verification avec InOrder)

Logique :
  OrderController (teste) -> OrderService (teste) -> OrderDao (stubbe)
  On ne mock PAS OrderService car on veut tester son vrai comportement
  On stubb seulement OrderDao qui est la dependance externe


EXERCICE 3 : Integration d'API avec Mocking
--------------------------------------------------------------------------------
Classe testee : ProductService
Dependance stubee : ProductApiClient (appel API externe)

Ce qu'on a teste :
  - Recuperation reussie d'un produit
  - Produit non trouve (API retourne null)
  - Echec reseau (API lance une exception)
  - Validation des IDs (null, vide, espaces) -> pas d'appel API
  - Chaine timeout puis succes
  - Reponses differentes selon l'ID
  - Retourne toujours la meme instance
  - Dernier stub ecrase les precedents

Logique :
  ProductService (teste) -> ProductApiClient (stubbe)
  On simule les reponses de l'API (succes, echec, timeout, produit inexistant)


DIFFERENCES ENTRE LES EXERCICES
================================================================================

Exercice 1 : Test simple d'une seule dependance
Exercice 2 : Test d'une chaine de 2 classes (Controller + Service) avec DAO stubbee
Exercice 3 : Test d'integration API avec gestion d'exceptions


COMMENT ON A CONSTRUIT LES TESTS
================================================================================

Pour chaque test, on suit la meme structure en 3 etapes :

1. ARRANGE (Configuration)
   - Creer les objets necessaires
   - Configurer le stub (when(...).thenReturn(...))
   - Preparer les donnees de test

2. ACT (Action)
   - Appeler la methode qu'on teste

3. ASSERT (Verification)
   - Verifier le resultat (assertEquals, assertNotNull, etc.)
   - Verifier les appels au stub (verify(...))



  start target/site/jacoco/index.html





================================================================================
