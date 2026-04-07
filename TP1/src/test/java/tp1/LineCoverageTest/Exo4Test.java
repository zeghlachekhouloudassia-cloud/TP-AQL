package tp1.LineCoverageTest;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.QuadraticEquation ; 
public class Exo4Test {

    
    @Test
    public void testConstructor() {
        QuadraticEquation p = new QuadraticEquation();
        assertNotNull(p);
    }
    
    @Test
    void testLineCoverage_AIsZero() {
        // Ligne: if (a == 0)
        assertThrows(IllegalArgumentException.class, 
            () -> QuadraticEquation.solve(0, 2, 3));
    }
    
    @Test
    void testLineCoverage_DeltaCalculation() {
        // Ligne: double delta = (b * b) - (4 * a * c)
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testLineCoverage_DeltaNegative() {
        // Ligne: if (delta < 0) -> return null
        assertNull(QuadraticEquation.solve(1, 1, 1));
    }
    
    @Test
    void testLineCoverage_DeltaZero() {
        // Ligne: if (delta == 0) -> return new double[]{-b / (2 * a)}
        double[] result = QuadraticEquation.solve(1, -4, 4);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(2.0, result[0], 0.0001);
    }
    
    @Test
    void testLineCoverage_DeltaPositive() {
        // Ligne: return new double[]{(-b + sqrt(delta))/(2*a), (-b - sqrt(delta))/(2*a)}
        double[] result = QuadraticEquation.solve(1, -5, 6);
        assertNotNull(result);
        assertEquals(2, result.length);
        double[] sorted = result.clone();
        java.util.Arrays.sort(sorted);
        assertEquals(2.0, sorted[0], 0.0001);
        assertEquals(3.0, sorted[1], 0.0001);
    }
    
    @Test
    void testLineCoverage_WithNegativeA() {
        // Test avec a négatif
        double[] result = QuadraticEquation.solve(-1, 5, -6);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testLineCoverage_WithDecimalResults() {
        // Test avec résultats décimaux
        double[] result = QuadraticEquation.solve(2, -7, 3);
        assertNotNull(result);
        assertEquals(2, result.length);
        double[] sorted = result.clone();
        java.util.Arrays.sort(sorted);
        assertEquals(0.5, sorted[0], 0.0001);
        assertEquals(3.0, sorted[1], 0.0001);
    }
    
    @Test
    void testLineCoverage_BIsZero() {
        // Test avec b = 0
        double[] result = QuadraticEquation.solve(1, 0, -4);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
    
    @Test
    void testLineCoverage_CIsZero() {
        // Test avec c = 0
        double[] result = QuadraticEquation.solve(1, -5, 0);
        assertNotNull(result);
        assertEquals(2, result.length);
    }
}