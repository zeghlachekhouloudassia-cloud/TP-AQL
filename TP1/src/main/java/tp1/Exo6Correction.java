package tp1;

public class Exo6Correction {
    
    public static String fizzBuzz(int n) {
        // BUG CORRIGÉ: l'original avait n <= 1 → rejette 1 qui est valide (retourne "1")
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (n % 15 == 0) {
            return "FizzBuzz";
        }
        if (n % 3 == 0) {
            return "Fizz";
        }
        if (n % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(n);
    }
}