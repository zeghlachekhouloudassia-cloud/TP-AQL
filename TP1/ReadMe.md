# TP1 - Tests Unitaires et Couverture de Code

## Résumé des bugs et corrections

| Exercice | Bug | Correction |
|----------|-----|------------|
| Palindrome | `j++` et `i--` au lieu de `i++` et `j--` | Remplacer par `i++` et `j--` || Anagram | `i <= s1.length()` | `i < s1.length()` |
| BinarySearch | `while (low < high)` et `array[mid] <= element` | `while (low <= high)` et `array[mid] < element` |
| QuadraticEquation | Aucun | - |
| RomanNumeral | `;` manquant, `i <= symbols.length`, `n > values[i]` | Ajouter `;`, `i < symbols.length`, `n >= values[i]` |
| FizzBuzz | `if (n <= 1)` | `if (n < 1)` |

## Redondance entre critères de couverture

| Exercice | Redondance |
|----------|------------|
| Palindrome | Line = Branch = Condition (mêmes tests) |
| Anagram | Line & Branch partagent tests null/longueurs; Condition nécessite plus de tests |
| BinarySearch | Line & Branch partagent test null/tableau vide; Condition nécessite plus de tests |
| QuadraticEquation | Line = Branch = Condition (mêmes tests) |
| RomanNumeral | Line & Branch partagent symboles de base; Condition nécessite plus de tests |
| FizzBuzz | Line & Branch partagent validation/Fizz/Buzz; Condition nécessite plus de tests |

## Explication par exercice

### Exercice 1: Palindrome
- **Bug:** `j++` et `i--` au lieu de `i++` et `j--`
- **Conséquence:** La boucle ne se termine jamais (boucle infinie) car i diminue et j augmente
- **Correction:** 
  ```java
  i++;
  j--;
### Exercice 2: Anagram
- **Branch = Condition ?** NON
- **Pourquoi :** La condition `if (s1 == null || s2 == null)` contient un OU logique (||). Branch coverage teste juste "true/false" de l'ensemble, mais Condition coverage doit tester chaque partie séparément.

### Exercice 3: BinarySearch
- **Branch = Condition ?** NON
- **Pourquoi :** La condition `else if (array[mid] <= element)` contient un opérateur de comparaison. Condition coverage doit tester `<` et `==` séparément.

### Exercice 4: QuadraticEquation
- **Branch = Condition ?** OUI
- **Pourquoi :** Toutes les conditions sont simples : `a == 0`, `delta < 0`, `delta == 0`. Pas de &&, pas de ||.

### Exercice 5: RomanNumeral
- **Branch = Condition ?** NON
- **Pourquoi :** La condition `if (n < 1 || n > 3999)` contient un OU logique (||). Condition coverage doit tester `n < 1` et `n > 3999` séparément.

### Exercice 6: FizzBuzz
- **Branch = Condition ?** NON
- **Pourquoi :** Plusieurs conditions en cascade. Condition coverage doit tester chaque condition atomique : `n <= 1`, `n % 15 == 0`, `n % 3 == 0`, `n % 5 == 0`.
### REMARQUE:Le dernier TP est dan sune autre branche (dans la brache tp3 et pas la branche main)
