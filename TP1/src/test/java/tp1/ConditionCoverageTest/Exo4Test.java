package tp1.ConditionCoverageTest;

import tp1.QuadraticEquation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Exo4Test {

    // ===========================================
    // CONDITION COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        QuadraticEquation p = new QuadraticEquation();
        assertNotNull(p);
    }
    
    // Condition: a == 0
    @Test
    void testCondition_AEqualsZero_True() {
        // Condition: a == 0 -> true
        assertThrows(IllegalArgumentException.class, 
            () -> QuadraticEquation.solve(0, 2, 3));
    }
    
    @Test
    void testCondition_AEqualsZero_False() {
        // Condition: a == 0 -> false
        QuadraticEquation.solve(1, 2, 3);
    }
    
    // Condition: delta < 0
    @Test
    void testCondition_DeltaLessThanZero_True() {
        // Condition: delta < 0 -> true
        assertNull(QuadraticEquation.solve(1, 1, 1));
    }
    
    @Test
    void testCondition_DeltaLessThanZero_False_WithDeltaZero() {
        // Condition: delta < 0 -> false (delta = 0)
        double[] result = QuadraticEquation.solve(1, -4, 4);
        assertNotNull(result);
        assertEquals(1, result.length);
    }
    
    @Test
    void testCondition_DeltaLessThanZero_False_WithDeltaPositive() {
        // Condition: delta < 0 -> false (delta > 0)
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    // Condition: delta == 0
    @Test
    void testCondition_DeltaEqualsZero_True() {
        // Condition: delta == 0 -> true
        double[] result = QuadraticEquation.solve(1, -4, 4);
        assertEquals(1, result.length);
    }
    
    @Test
    void testCondition_DeltaEqualsZero_False() {
        // Condition: delta == 0 -> false
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertEquals(2, result.length);
    }
    
    // ===========================================
    // TESTS POUR LES DIFFÉRENTS TYPES DE DELTA
    // ===========================================
    
    @Test
    void testDeltaNegative_VariousForms() {
        // delta < 0 avec différentes formes
        assertNull(QuadraticEquation.solve(1, 1, 1));     // x² + x + 1
        assertNull(QuadraticEquation.solve(2, 3, 5));     // 2x² + 3x + 5
        assertNull(QuadraticEquation.solve(-1, 2, -3));   // -x² + 2x - 3
        assertNull(QuadraticEquation.solve(1, 0, 4));     // x² + 4
    }
    
    @Test
    void testDeltaZero_VariousForms() {
        // delta == 0 avec différentes formes
        double[] result1 = QuadraticEquation.solve(1, -4, 4);   // (x-2)²
        assertEquals(2.0, result1[0], 0.0001);
        
        double[] result2 = QuadraticEquation.solve(1, 6, 9);    // (x+3)²
        assertEquals(-3.0, result2[0], 0.0001);
        
        double[] result3 = QuadraticEquation.solve(4, -4, 1);   // (2x-1)²
        assertEquals(0.5, result3[0], 0.0001);
        
        double[] result4 = QuadraticEquation.solve(-1, 4, -4);  // -(x-2)²
        assertEquals(2.0, result4[0], 0.0001);
        
        double[] result5 = QuadraticEquation.solve(1, 0, 0);    // x²
        assertEquals(0.0, result5[0], 0.0001);
    }
    
    @Test
    void testDeltaPositive_VariousForms() {
        // delta > 0 avec différentes formes
        double[] result1 = QuadraticEquation.solve(1, -5, 6);   // x² - 5x + 6
        double[] sorted1 = result1.clone();
        java.util.Arrays.sort(sorted1);
        assertEquals(2.0, sorted1[0], 0.0001);
        assertEquals(3.0, sorted1[1], 0.0001);
        
        double[] result2 = QuadraticEquation.solve(1, 5, 6);    // x² + 5x + 6
        double[] sorted2 = result2.clone();
        java.util.Arrays.sort(sorted2);
        assertEquals(-3.0, sorted2[0], 0.0001);
        assertEquals(-2.0, sorted2[1], 0.0001);
        
        double[] result3 = QuadraticEquation.solve(1, -1, -6);  // x² - x - 6
        double[] sorted3 = result3.clone();
        java.util.Arrays.sort(sorted3);
        assertEquals(-2.0, sorted3[0], 0.0001);
        assertEquals(3.0, sorted3[1], 0.0001);
        
        double[] result4 = QuadraticEquation.solve(1, 0, -4);   // x² - 4
        double[] sorted4 = result4.clone();
        java.util.Arrays.sort(sorted4);
        assertEquals(-2.0, sorted4[0], 0.0001);
        assertEquals(2.0, sorted4[1], 0.0001);
        
        double[] result5 = QuadraticEquation.solve(2, -7, 3);   // 2x² - 7x + 3
        double[] sorted5 = result5.clone();
        java.util.Arrays.sort(sorted5);
        assertEquals(0.5, sorted5[0], 0.0001);
        assertEquals(3.0, sorted5[1], 0.0001);
    }
    
    // ===========================================
    // TESTS POUR LA VÉRIFICATION DES PROPRIÉTÉS
    // ===========================================
    
    @Test
    void testVerifySumOfRoots() {
        // Vérification de la somme des racines (-b/a)
        double[] result = QuadraticEquation.solve(2, -8, 6);
        assertEquals(4.0, result[0] + result[1], 0.0001);
        
        result = QuadraticEquation.solve(1, -5, 6);
        assertEquals(5.0, result[0] + result[1], 0.0001);
        
        result = QuadraticEquation.solve(1, 5, 6);
        assertEquals(-5.0, result[0] + result[1], 0.0001);
    }
    
    @Test
    void testVerifyProductOfRoots() {
        // Vérification du produit des racines (c/a)
        double[] result = QuadraticEquation.solve(2, -8, 6);
        assertEquals(3.0, result[0] * result[1], 0.0001);
        
        result = QuadraticEquation.solve(1, -5, 6);
        assertEquals(6.0, result[0] * result[1], 0.0001);
        
        result = QuadraticEquation.solve(1, 5, 6);
        assertEquals(6.0, result[0] * result[1], 0.0001);
    }
    
    // ===========================================
    // TESTS POUR LES CAS LIMITES
    // ===========================================
    
    @Test
    void testExtremeValues_LargeCoefficients() {
        double[] result = QuadraticEquation.solve(1000000, -2000000, 1000000);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1.0, result[0], 0.0001);
    }
    
    @Test
    void testExtremeValues_VerySmallCoefficients() {
        double[] result = QuadraticEquation.solve(1e-10, -2e-10, 1e-10);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1.0, result[0], 0.1);
    }
    
    @Test
    void testDeltaCloseToZero_Positive() {
        double[] result = QuadraticEquation.solve(1, -2, 0.9999999);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testSymmetricalRoots() {
        double[] result = QuadraticEquation.solve(1, 0, -4);
        assertEquals(0.0, result[0] + result[1], 0.0001);
    }
}