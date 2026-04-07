package tp1.LineCoverageTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.RomanNumeral;

public class Exo5Test {

    // ===========================================
    // LINE COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        RomanNumeral p = new RomanNumeral();
        assertNotNull(p);
    }
    
    @Test
    void testLineCoverage_Validation_LowerBound() {
        // Ligne: if (n < 1 || n > 3999)
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(0));
    }
    
    @Test
    void testLineCoverage_Validation_UpperBound() {
        // Ligne: if (n < 1 || n > 3999)
        assertThrows(IllegalArgumentException.class, 
            () -> RomanNumeral.toRoman(4000));
    }
    
    @Test
    void testLineCoverage_Validation_Valid() {
        // Ligne: validation passe
        assertEquals("I", RomanNumeral.toRoman(1));
    }
    
    @Test
    void testLineCoverage_Initialization() {
        // Lignes: String[] symbols, int[] values, StringBuilder sb
        RomanNumeral.toRoman(1);
    }
    
    @Test
    void testLineCoverage_ForLoop() {
        // Ligne: for (int i = 0; i < symbols.length; i++)
        RomanNumeral.toRoman(1);
    }
    
    @Test
    void testLineCoverage_WhileLoop_Entry() {
        // Ligne: while (n >= values[i])
        assertEquals("III", RomanNumeral.toRoman(3));
    }
    
    @Test
    void testLineCoverage_WhileLoop_Body() {
        // Lignes: sb.append(symbols[i]); n -= values[i];
        assertEquals("VI", RomanNumeral.toRoman(6));
    }
    
    @Test
    void testLineCoverage_WhileLoop_Exit() {
        // Ligne: sortie de la boucle while
        RomanNumeral.toRoman(1);
    }
    
    @Test
    void testLineCoverage_Return() {
        // Ligne: return sb.toString()
        String result = RomanNumeral.toRoman(1);
        assertEquals("I", result);
    }
    
    @Test
    void testLineCoverage_BasicSymbols() {
        // Couverture des symboles de base
        assertEquals("V", RomanNumeral.toRoman(5));
        assertEquals("X", RomanNumeral.toRoman(10));
        assertEquals("L", RomanNumeral.toRoman(50));
        assertEquals("C", RomanNumeral.toRoman(100));
        assertEquals("D", RomanNumeral.toRoman(500));
        assertEquals("M", RomanNumeral.toRoman(1000));
    }
    
    @Test
    void testLineCoverage_SubtractiveNotations() {
        // Couverture des notations soustractives
        assertEquals("IV", RomanNumeral.toRoman(4));
        assertEquals("IX", RomanNumeral.toRoman(9));
        assertEquals("XL", RomanNumeral.toRoman(40));
        assertEquals("XC", RomanNumeral.toRoman(90));
        assertEquals("CD", RomanNumeral.toRoman(400));
        assertEquals("CM", RomanNumeral.toRoman(900));
    }
    
    @Test
    void testLineCoverage_MultipleIterations() {
        // Couverture des multiples itérations
        assertEquals("MMM", RomanNumeral.toRoman(3000));
        assertEquals("XXX", RomanNumeral.toRoman(30));
        assertEquals("CCC", RomanNumeral.toRoman(300));
    }
    
    @Test
    void testLineCoverage_ComplexNumber() {
        // Couverture des nombres complexes
        assertEquals("MMMDCCCLXXXVIII", RomanNumeral.toRoman(3888));
    }
    
    @Test
    void testLineCoverage_EdgeCases() {
        // Cas limites
        assertEquals("I", RomanNumeral.toRoman(1));
        assertEquals("MMMCMXCIX", RomanNumeral.toRoman(3999));
    }
} 
