package tp1.BranchCoverageTest;
import tp1.QuadraticEquation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Exo4Test {

    // ===========================================
    // BRANCH COVERAGE - Tests
    // ===========================================
    
    @Test
    public void testConstructor() {
        QuadraticEquation p = new QuadraticEquation();
        assertNotNull(p);
    }
    
    // Branche: a == 0
    @Test
    void testBranch_AEqualsZero_True() {
        // Branche: a == 0 -> true (exception)
        assertThrows(IllegalArgumentException.class, 
            () -> QuadraticEquation.solve(0, 2, 3));
    }
    
    @Test
    void testBranch_AEqualsZero_False() {
        // Branche: a == 0 -> false (continue)
        assertNotNull(QuadraticEquation.solve(1, 2, 3));
    }
    
    // Branche: delta < 0
    @Test
    void testBranch_DeltaNegative_True() {
        // Branche: delta < 0 -> true (return null)
        assertNull(QuadraticEquation.solve(1, 1, 1));
    }
    
    @Test
    void testBranch_DeltaNegative_False() {
        // Branche: delta < 0 -> false (continue vers delta == 0 ou deux solutions)
        double[] result = QuadraticEquation.solve(1, -4, 4);
        assertNotNull(result);
    }
    
    // Branche: delta == 0
    @Test
    void testBranch_DeltaEqualsZero_True() {
        // Branche: delta == 0 -> true (retourne une solution)
        double[] result = QuadraticEquation.solve(1, -4, 4);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(2.0, result[0], 0.0001);
    }
    
    @Test
    void testBranch_DeltaEqualsZero_False() {
        // Branche: delta == 0 -> false (retourne deux solutions)
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    // Tests supplémentaires pour couvrir toutes les branches
    @Test
    void testBranch_DeltaNegative_WithDifferentCoefficients() {
        // delta < 0 avec différents coefficients
        assertNull(QuadraticEquation.solve(2, 3, 5));
        assertNull(QuadraticEquation.solve(-1, 2, -3));
    }
    
    @Test
    void testBranch_DeltaZero_WithNegativeA() {
        // delta == 0 avec a négatif
        double[] result = QuadraticEquation.solve(-1, 4, -4);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(2.0, result[0], 0.0001);
    }
    
    @Test
    void testBranch_DeltaZero_WithFraction() {
        // delta == 0 avec solution fractionnaire
        double[] result = QuadraticEquation.solve(4, -4, 1);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(0.5, result[0], 0.0001);
    }
    
    @Test
    void testBranch_DeltaPositive_IntegerRoots() {
        // delta > 0 avec racines entières
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testBranch_DeltaPositive_FractionalRoots() {
        // delta > 0 avec racines fractionnaires
        double[] result = QuadraticEquation.solve(2, -7, 3);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testBranch_DeltaPositive_IrrationalRoots() {
        // delta > 0 avec racines irrationnelles
        double[] result = QuadraticEquation.solve(1, 0, -2);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testBranch_SpecialCase_BIsZero() {
        // Cas spécial: b = 0
        double[] result = QuadraticEquation.solve(1, 0, -4);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testBranch_SpecialCase_CIsZero() {
        // Cas spécial: c = 0
        double[] result = QuadraticEquation.solve(1, -5, 0);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
}