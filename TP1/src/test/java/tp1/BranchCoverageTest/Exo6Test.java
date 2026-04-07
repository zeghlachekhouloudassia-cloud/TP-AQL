package tp1.BranchCoverageTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.FizzBuzz;

public class Exo6Test {

    // ===========================================
    // BRANCH COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        FizzBuzz p = new FizzBuzz();
        assertNotNull(p);
    }
    
    // Branche: validation (n <= 1)
    @Test
    void testBranch_Validation_True() {
        // Branche: n <= 1 -> true (exception)
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(0));
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(1));
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(-5));
    }
    
    @Test
    void testBranch_Validation_False() {
        // Branche: n <= 1 -> false (continue)
        FizzBuzz.fizzBuzz(2);
    }
    
    // Branche: FizzBuzz (n % 15 == 0)
    @Test
    void testBranch_FizzBuzz_True() {
        // Branche: n % 15 == 0 -> true
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(45));
    }
    
    @Test
    void testBranch_FizzBuzz_False() {
        // Branche: n % 15 == 0 -> false (continue)
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
    }
    
    // Branche: Fizz (n % 3 == 0)
    @Test
    void testBranch_Fizz_True() {
        // Branche: n % 3 == 0 -> true (quand n % 15 != 0)
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(12));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(18));
    }
    
    @Test
    void testBranch_Fizz_False() {
        // Branche: n % 3 == 0 -> false (continue vers Buzz)
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
    }
    
    // Branche: Buzz (n % 5 == 0)
    @Test
    void testBranch_Buzz_True() {
        // Branche: n % 5 == 0 -> true (quand n % 15 != 0 et n % 3 != 0)
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(20));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(25));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(35));
    }
    
    @Test
    void testBranch_Buzz_False() {
        // Branche: n % 5 == 0 -> false (retourne le nombre)
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
    }
    
    // Branche: return number as string
    @Test
    void testBranch_ReturnNumber() {
        // Branche: retourne String.valueOf(n)
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("11", FizzBuzz.fizzBuzz(11));
        assertEquals("13", FizzBuzz.fizzBuzz(13));
        assertEquals("14", FizzBuzz.fizzBuzz(14));
    }
    
    // ===========================================
    // TESTS POUR LES BRANCHES AUX FRONTIÈRES
    // ===========================================
    
    @Test
    void testBranch_Boundary_Near3() {
        assertEquals("2", FizzBuzz.fizzBuzz(2));   // avant Fizz
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3)); // Fizz
        assertEquals("4", FizzBuzz.fizzBuzz(4));   // après Fizz
    }
    
    @Test
    void testBranch_Boundary_Near5() {
        assertEquals("4", FizzBuzz.fizzBuzz(4));   // avant Buzz
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5)); // Buzz
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6)); // après Buzz (Fizz)
    }
    
    @Test
    void testBranch_Boundary_Near15() {
        assertEquals("14", FizzBuzz.fizzBuzz(14));      // avant FizzBuzz
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15)); // FizzBuzz
        assertEquals("16", FizzBuzz.fizzBuzz(16));      // après FizzBuzz
    }
    
    @Test
    void testBranch_Boundary_Near30() {
        assertEquals("29", FizzBuzz.fizzBuzz(29));      // avant FizzBuzz
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30)); // FizzBuzz
        assertEquals("31", FizzBuzz.fizzBuzz(31));      // après FizzBuzz
    }
    
    // ===========================================
    // SÉQUENCES POUR COUVRIR TOUTES LES BRANCHES
    // ===========================================
    
    @Test
    void testBranch_Sequence_2to10() {
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
    }
    
    @Test
    void testBranch_Sequence_11to20() {
        assertEquals("11", FizzBuzz.fizzBuzz(11));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(12));
        assertEquals("13", FizzBuzz.fizzBuzz(13));
        assertEquals("14", FizzBuzz.fizzBuzz(14));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
        assertEquals("16", FizzBuzz.fizzBuzz(16));
        assertEquals("17", FizzBuzz.fizzBuzz(17));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(18));
        assertEquals("19", FizzBuzz.fizzBuzz(19));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(20));
    }
}