package tp1.ConditionCoverageTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.FizzBuzz;

public class Exo6Test {

    // ===========================================
    // CONDITION COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        FizzBuzz p = new FizzBuzz();
        assertNotNull(p);
    }
    
    // Condition: n <= 1
    @Test
    void testCondition_NLessThanOrEqual1_True() {
        // Condition: n <= 1 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(0));
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(1));
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(-1));
    }
    
    @Test
    void testCondition_NLessThanOrEqual1_False() {
        // Condition: n <= 1 -> false
        FizzBuzz.fizzBuzz(2);
        FizzBuzz.fizzBuzz(3);
        FizzBuzz.fizzBuzz(100);
    }
    
    // Condition: n % 15 == 0
    @Test
    void testCondition_NMod15Equals0_True() {
        // Condition: n % 15 == 0 -> true
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(45));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(60));
    }
    
    @Test
    void testCondition_NMod15Equals0_False() {
        // Condition: n % 15 == 0 -> false
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("2", FizzBuzz.fizzBuzz(2));
    }
    
    // Condition: n % 3 == 0
    @Test
    void testCondition_NMod3Equals0_True() {
        // Condition: n % 3 == 0 -> true (et n % 15 != 0)
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(12));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(18));
    }
    
    @Test
    void testCondition_NMod3Equals0_False() {
        // Condition: n % 3 == 0 -> false
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
    }
    
    // Condition: n % 5 == 0
    @Test
    void testCondition_NMod5Equals0_True() {
        // Condition: n % 5 == 0 -> true (et n % 15 != 0, n % 3 != 0)
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(20));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(25));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(35));
    }
    
    @Test
    void testCondition_NMod5Equals0_False() {
        // Condition: n % 5 == 0 -> false
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
    }
    
    // ===========================================
    // TESTS POUR LES CONDITIONS COMBINÉES
    // ===========================================
    
    @Test
    void testCondition_Combined_ValidNumber() {
        // n <= 1 = false
        FizzBuzz.fizzBuzz(2);
    }
    
    @Test
    void testCondition_Combined_InvalidNumber() {
        // n <= 1 = true
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(0));
    }
    
    @Test
    void testCondition_Combined_FizzBuzzCase() {
        // n % 15 == 0 = true, les autres non évaluées
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
    }
    
    @Test
    void testCondition_Combined_FizzCase() {
        // n % 15 == 0 = false, n % 3 == 0 = true
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
    }
    
    @Test
    void testCondition_Combined_BuzzCase() {
        // n % 15 == 0 = false, n % 3 == 0 = false, n % 5 == 0 = true
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
    }
    
    @Test
    void testCondition_Combined_NeitherCase() {
        // n % 15 == 0 = false, n % 3 == 0 = false, n % 5 == 0 = false
        assertEquals("2", FizzBuzz.fizzBuzz(2));
    }
    
    // ===========================================
    // TESTS POUR LES VALEURS FRONTIÈRES
    // ===========================================
    
    @Test
    void testCondition_Boundary_2() {
        // Plus petit nombre valide (après correction du bug)
        assertEquals("2", FizzBuzz.fizzBuzz(2));
    }
    
    @Test
    void testCondition_Boundary_3() {
        // Premier multiple de 3
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
    }
    
    @Test
    void testCondition_Boundary_4() {
        // Juste après un multiple de 3
        assertEquals("4", FizzBuzz.fizzBuzz(4));
    }
    
    @Test
    void testCondition_Boundary_5() {
        // Premier multiple de 5
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
    }
    
    @Test
    void testCondition_Boundary_6() {
        // Multiple de 3 et 5? Non, juste 3
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
    }
    
    @Test
    void testCondition_Boundary_10() {
        // Multiple de 5 seulement
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
    }
    
    @Test
    void testCondition_Boundary_15() {
        // Premier multiple de 15
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
    }
    
    @Test
    void testCondition_Boundary_30() {
        // Multiple de 15
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30));
    }
    
    // ===========================================
    // TESTS DE SÉQUENCE POUR LES CONDITIONS
    // ===========================================
    
    @Test
    void testCondition_Sequence_1to5() {
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(1));
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
    }
    
    @Test
    void testCondition_Sequence_6to10() {
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
    }
    
    @Test
    void testCondition_Sequence_11to15() {
        assertEquals("11", FizzBuzz.fizzBuzz(11));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(12));
        assertEquals("13", FizzBuzz.fizzBuzz(13));
        assertEquals("14", FizzBuzz.fizzBuzz(14));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
    }
    
    // ===========================================
    // TESTS POUR LES GRANDES VALEURS
    // ===========================================
    
    @Test
    void testCondition_LargeNumbers() {
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9999));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5000));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(1500));
        assertEquals("1999", FizzBuzz.fizzBuzz(1999));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3003));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5005));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(3000));
    }
}