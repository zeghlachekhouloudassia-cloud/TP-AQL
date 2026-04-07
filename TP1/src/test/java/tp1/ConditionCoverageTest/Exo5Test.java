package tp1.ConditionCoverageTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    
    // Condition: n < 1
    @Test
    void testCondition_NLessThan1_True() {
        // Condition: n < 1 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(0));
    }
    
    @Test
    void testCondition_NLessThan1_False() {
        // Condition: n < 1 -> false
        RomanNumeral.toRoman(1);
    }
    
    // Condition: n > 3999
    @Test
    void testCondition_NGreaterThan3999_True() {
        // Condition: n > 3999 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(4000));
    }
    
    @Test
    void testCondition_NGreaterThan3999_False() {
        // Condition: n > 3999 -> false
        RomanNumeral.toRoman(3999);
    }
    
    // Condition: i < symbols.length
    @Test
    void testCondition_ForLoopCounter_LessThanLength_True() {
        // Condition: i < symbols.length -> true
        RomanNumeral.toRoman(1);
    }
    
    @Test
    void testCondition_ForLoopCounter_LessThanLength_False() {
        // Condition: i < symbols.length -> false
        RomanNumeral.toRoman(3999);
    }
    
    // Condition: n >= values[i] pour chaque symbole
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_M() {
        assertEquals("M", RomanNumeral.toRoman(1000));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_CM() {
        assertEquals("CM", RomanNumeral.toRoman(900));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_D() {
        assertEquals("D", RomanNumeral.toRoman(500));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_CD() {
        assertEquals("CD", RomanNumeral.toRoman(400));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_C() {
        assertEquals("C", RomanNumeral.toRoman(100));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_XC() {
        assertEquals("XC", RomanNumeral.toRoman(90));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_L() {
        assertEquals("L", RomanNumeral.toRoman(50));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_XL() {
        assertEquals("XL", RomanNumeral.toRoman(40));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_X() {
        assertEquals("X", RomanNumeral.toRoman(10));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_IX() {
        assertEquals("IX", RomanNumeral.toRoman(9));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_V() {
        assertEquals("V", RomanNumeral.toRoman(5));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_IV() {
        assertEquals("IV", RomanNumeral.toRoman(4));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_True_I() {
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    // Condition: n >= values[i] -> false (sortie de boucle)
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_False_M() {
        // n < 1000 après ajout
        assertEquals("CM", RomanNumeral.toRoman(900));
    }
    
    @Test
    void testCondition_WhileLoop_NGreaterOrEqual_False_WithMultipleIterations() {
        // Test pour les symboles répétés
        assertEquals("IV", RomanNumeral.toRoman(4));  // 4 >= 4? oui, puis 0 >= 4? non
        assertEquals("IX", RomanNumeral.toRoman(9));  // 9 >= 9? oui, puis 0 >= 9? non
        assertEquals("XL", RomanNumeral.toRoman(40)); // 40 >= 40? oui, puis 0 >= 40? non
        assertEquals("XC", RomanNumeral.toRoman(90)); // 90 >= 90? oui, puis 0 >= 90? non
        assertEquals("CD", RomanNumeral.toRoman(400)); // 400 >= 400? oui, puis 0 >= 400? non
        assertEquals("CM", RomanNumeral.toRoman(900)); // 900 >= 900? oui, puis 0 >= 900? non
    }
    
    // ===========================================
    // TESTS POUR LES CONDITIONS COMBINÉES
    // ===========================================
    
    @Test
    void testCondition_Combined_ValidInput() {
        // n < 1 = false, n > 3999 = false
        assertDoesNotThrow(() -> RomanNumeral.toRoman(500));
    }
    
    @Test
    void testCondition_Combined_InvalidLower() {
        // n < 1 = true, n > 3999 = false
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(0));
    }
    
    @Test
    void testCondition_Combined_InvalidUpper() {
        // n < 1 = false, n > 3999 = true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(4000));
    }
    
    // ===========================================
    // TESTS POUR LES CAS LIMITES DES CONDITIONS
    // ===========================================
    
    @Test
    void testCondition_Boundary_1() {
        // n = 1: n < 1 = false, n > 3999 = false
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    @Test
    void testCondition_Boundary_3999() {
        // n = 3999: n < 1 = false, n > 3999 = false
        assertEquals("MMMCMXCIX", RomanNumeral.toRoman(3999));
    }
    
    @Test
    void testCondition_Boundary_0() {
        // n = 0: n < 1 = true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(0));
    }
    
    @Test
    void testCondition_Boundary_4000() {
        // n = 4000: n > 3999 = true
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(4000));
    }
    
    @Test
    void testCondition_WhileLoop_ExactValues() {
        // Test des valeurs exactes pour chaque symbole
        assertEquals("M", RomanNumeral.toRoman(1000));
        assertEquals("CM", RomanNumeral.toRoman(900));
        assertEquals("D", RomanNumeral.toRoman(500));
        assertEquals("CD", RomanNumeral.toRoman(400));
        assertEquals("C", RomanNumeral.toRoman(100));
        assertEquals("XC", RomanNumeral.toRoman(90));
        assertEquals("L", RomanNumeral.toRoman(50));
        assertEquals("XL", RomanNumeral.toRoman(40));
        assertEquals("X", RomanNumeral.toRoman(10));
        assertEquals("IX", RomanNumeral.toRoman(9));
        assertEquals("V", RomanNumeral.toRoman(5));
        assertEquals("IV", RomanNumeral.toRoman(4));
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    @Test
    void testCondition_WhileLoop_JustBelowValues() {
        // Test des valeurs juste en dessous de chaque symbole
        assertEquals("CMXCIX", RomanNumeral.toRoman(999));   // juste en dessous de 1000
        assertEquals("DCCCXCIX", RomanNumeral.toRoman(899)); // juste en dessous de 900
        assertEquals("CDXCIX", RomanNumeral.toRoman(499));   // juste en dessous de 500
        assertEquals("CCCXCIX", RomanNumeral.toRoman(399));  // juste en dessous de 400
        assertEquals("XCIX", RomanNumeral.toRoman(99));      // juste en dessous de 100
        assertEquals("LXXXIX", RomanNumeral.toRoman(89));    // juste en dessous de 90
        assertEquals("XLIX", RomanNumeral.toRoman(49));      // juste en dessous de 50
        assertEquals("XXXIX", RomanNumeral.toRoman(39));     // juste en dessous de 40
        assertEquals("IX", RomanNumeral.toRoman(9));         // juste en dessous de 10
        assertEquals("VIII", RomanNumeral.toRoman(8));       // juste en dessous de 9
        assertEquals("IV", RomanNumeral.toRoman(4));         // juste en dessous de 5
        assertEquals("III", RomanNumeral.toRoman(3));        // juste en dessous de 4
    }
}