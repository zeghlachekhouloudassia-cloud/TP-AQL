package tp1.LineCoverageTest;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tp1.BinarySearch;

public class Exo3Test {
    
    @Test
    public void testConstructor() {
        BinarySearch p = new BinarySearch();
        assertNotNull(p);
    }

    // ===========================================
    // LINE COVERAGE - Tests
    // ===========================================
    
    @Test
    void testLineCoverage_NullCheck() {
        // Ligne: if (array == null)
        assertThrows(NullPointerException.class, 
            () -> BinarySearch.binarySearch(null, 5));
    }
    
    @Test
    void testLineCoverage_Initialization() {
        // Lignes: int low = 0; int high = array.length - 1;
        int[] array = {1, 2, 3};
        BinarySearch.binarySearch(array, 2);
    }
    
    @Test
    void testLineCoverage_WhileLoop_Entry() {
        // Ligne: while (low < high)
        int[] array = {1, 2, 3};
        BinarySearch.binarySearch(array, 2);
    }
    
    @Test
    void testLineCoverage_WhileLoop_Exit() {
        // Ligne: sortie de la boucle while
        int[] array = {1};
        BinarySearch.binarySearch(array, 1);
    }
    
    @Test
    void testLineCoverage_MidCalculation() {
        // Ligne: int mid = (low + high) / 2;
        int[] array = {1, 2, 3, 4, 5};
        BinarySearch.binarySearch(array, 3);
    }
    
    @Test
    void testLineCoverage_ElementFound_True() {
        // Ligne: if (array[mid] == element)
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(2, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testLineCoverage_ElementFound_False_LowUpdate() {
        // Ligne: else if (array[mid] <= element) -> low = mid + 1
        int[] array = {1, 3, 5, 7, 9};
        assertEquals(4, BinarySearch.binarySearch(array, 9));
    }
    
    @Test
    void testLineCoverage_ElementFound_False_HighUpdate() {
        // Ligne: else -> high = mid - 1
        int[] array = {1, 3, 5, 7, 9};
        assertEquals(1, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testLineCoverage_ReturnMinusOne() {
        // Ligne: return -1
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(-1, BinarySearch.binarySearch(array, 6));
    }
    
    @Test
    void testLineCoverage_ReturnIndex() {
        // Ligne: return mid
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(2, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testLineCoverage_EmptyArray() {
        // Cas limite: array.length - 1 = -1
        int[] array = {};
        assertEquals(-1, BinarySearch.binarySearch(array, 5));
    }
    
    @Test
    void testLineCoverage_SingleElement() {
        // Cas limite: low = 0, high = 0
        int[] array = {5};
        assertEquals(0, BinarySearch.binarySearch(array, 5));
    }
}