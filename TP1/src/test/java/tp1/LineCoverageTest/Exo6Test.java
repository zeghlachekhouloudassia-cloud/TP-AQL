package tp1.LineCoverageTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.FizzBuzz;

public class Exo6Test {

    // ===========================================
    // LINE COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        FizzBuzz p = new FizzBuzz();
        assertNotNull(p);
    }
    
    @Test
    void testLineCoverage_Validation() {
        // Ligne: if (n <= 1)
        assertThrows(IllegalArgumentException.class, 
            () -> FizzBuzz.fizzBuzz(0));
    }
    
    @Test
    void testLineCoverage_FizzBuzz() {
        // Ligne: if (n % 15 == 0) return "FizzBuzz"
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
    }
    
    @Test
    void testLineCoverage_Fizz() {
        // Ligne: if (n % 3 == 0) return "Fizz"
        assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
    }
    
    @Test
    void testLineCoverage_Buzz() {
        // Ligne: if (n % 5 == 0) return "Buzz"
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
    }
    
    @Test
    void testLineCoverage_Neither() {
        // Ligne: return String.valueOf(n)
        assertEquals("2", FizzBuzz.fizzBuzz(2));
    }
    
    @Test
    void testLineCoverage_MultipleOf15() {
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(45));
    }
    
    @Test
    void testLineCoverage_MultipleOf3Only() {
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(12));
    }
    
    @Test
    void testLineCoverage_MultipleOf5Only() {
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(20));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(25));
    }
    
    @Test
    void testLineCoverage_NormalNumbers() {
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("4", FizzBuzz.fizzBuzz(4));
        assertEquals("7", FizzBuzz.fizzBuzz(7));
        assertEquals("8", FizzBuzz.fizzBuzz(8));
        assertEquals("11", FizzBuzz.fizzBuzz(11));
    }
    
    @Test
    void testLineCoverage_EdgeCase_2() {
        // Plus petit nombre valide (après correction)
        assertEquals("2", FizzBuzz.fizzBuzz(2));
    }
    
    @Test
    void testLineCoverage_LargeNumbers() {
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9999));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5000));
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(1500));
        assertEquals("1999", FizzBuzz.fizzBuzz(1999));
    }
}