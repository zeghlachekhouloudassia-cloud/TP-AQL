package tp1.BranchCoverageTest;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.RomanNumeral;

public class Exo5Test {

    @Test
    public void testConstructor() {
        RomanNumeral p = new RomanNumeral();
        assertNotNull(p);
    }
    
    // Branche: validation (n < 1 || n > 3999)
    @Test
    void testBranch_Validation_True_Lower() {
        // Branche: n < 1 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(0));
    }
    
    @Test
    void testBranch_Validation_True_Upper() {
        // Branche: n > 3999 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(4000));
    }
    
    @Test
    void testBranch_Validation_False() {
        // Branche: condition validation -> false
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    // Branche: boucle for
    @Test
    void testBranch_ForLoop_Entry() {
        // Branche: i < symbols.length -> true (entre dans la boucle)
        RomanNumeral.toRoman(1);
    }
    
    @Test
    void testBranch_ForLoop_Exit() {
        // Branche: i < symbols.length -> false (sortie de boucle)
        RomanNumeral.toRoman(3999);
    }
    
    // Branche: boucle while pour chaque symbole
    @Test
    void testBranch_WhileLoop_Entry_M() {
        // while (n >= 1000) -> true
        assertEquals("M", RomanNumeral.toRoman(1000));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_CM() {
        // while (n >= 900) -> true
        assertEquals("CM", RomanNumeral.toRoman(900));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_D() {
        // while (n >= 500) -> true
        assertEquals("D", RomanNumeral.toRoman(500));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_CD() {
        // while (n >= 400) -> true
        assertEquals("CD", RomanNumeral.toRoman(400));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_C() {
        // while (n >= 100) -> true
        assertEquals("C", RomanNumeral.toRoman(100));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_XC() {
        // while (n >= 90) -> true
        assertEquals("XC", RomanNumeral.toRoman(90));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_L() {
        // while (n >= 50) -> true
        assertEquals("L", RomanNumeral.toRoman(50));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_XL() {
        // while (n >= 40) -> true
        assertEquals("XL", RomanNumeral.toRoman(40));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_X() {
        // while (n >= 10) -> true
        assertEquals("X", RomanNumeral.toRoman(10));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_IX() {
        // while (n >= 9) -> true
        assertEquals("IX", RomanNumeral.toRoman(9));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_V() {
        // while (n >= 5) -> true
        assertEquals("V", RomanNumeral.toRoman(5));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_IV() {
        // while (n >= 4) -> true
        assertEquals("IV", RomanNumeral.toRoman(4));
    }
    
    @Test
    void testBranch_WhileLoop_Entry_I() {
        // while (n >= 1) -> true
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    // Branche: sortie de la boucle while (n < values[i])
    @Test
    void testBranch_WhileLoop_Exit_M() {
        // n < 1000 (après avoir ajouté M)
        assertEquals("CM", RomanNumeral.toRoman(900));
    }
    
    @Test
    void testBranch_WhileLoop_Exit_WithMultipleIterations() {
        // Test avec multiples itérations
        assertEquals("III", RomanNumeral.toRoman(3));
        assertEquals("XXX", RomanNumeral.toRoman(30));
        assertEquals("CCC", RomanNumeral.toRoman(300));
        assertEquals("MMM", RomanNumeral.toRoman(3000));
    }
    
    // Branche: cas particuliers
    @Test
    void testBranch_EdgeCase_Minimum() {
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    @Test
    void testBranch_EdgeCase_Maximum() {
        assertEquals("MMMCMXCIX", RomanNumeral.toRoman(3999));
    }
    
    @Test
    void testBranch_ComplexNumber() {
        assertEquals("MMMDCCCLXXXVIII", RomanNumeral.toRoman(3888));
    }
}