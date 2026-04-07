package tp1.BranchCoverageTest;

import tp1.BinarySearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Exo3Test {
    
    @Test
    public void testConstructor() {
        BinarySearch p = new BinarySearch();
        assertNotNull(p);
    }

   
    @Test
    void testBranch_NullCheck_True() {
        // Branche: array == null -> true (exception)
        assertThrows(NullPointerException.class, 
            () -> BinarySearch.binarySearch(null, 5));
    }
    
    @Test
    void testBranch_NullCheck_False() {
        // Branche: array == null -> false (continue)
        int[] array = {1, 2, 3};
        BinarySearch.binarySearch(array, 2);
    }
    
    @Test
    void testBranch_WhileLoop_Entry() {
        // Branche: while (low < high) -> true (entre dans la boucle)
        int[] array = {1, 2, 3, 4, 5};
        BinarySearch.binarySearch(array, 3);
    }
    
    @Test
    void testBranch_WhileLoop_Exit() {
        // Branche: while (low < high) -> false (sortie de boucle)
        int[] array = {1};
        BinarySearch.binarySearch(array, 1);
    }
    
    @Test
    void testBranch_If_ElementFound_True() {
        // Branche: array[mid] == element -> true (return mid)
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(2, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testBranch_If_ElementFound_False() {
        // Branche: array[mid] == element -> false (continue)
        int[] array = {1, 2, 3, 4, 5};
        BinarySearch.binarySearch(array, 5);
    }
    
    @Test
    void testBranch_ElseIf_LowUpdated_True() {
        // Branche: else if (array[mid] < element) -> true (low = mid + 1)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(6, BinarySearch.binarySearch(array, 13));
    }
    
    @Test
    void testBranch_ElseIf_LowUpdated_False() {
        // Branche: else if (array[mid] < element) -> false (exécute else)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(0, BinarySearch.binarySearch(array, 1));
    }
    
    @Test
    void testBranch_Else_HighUpdated() {
        // Branche: else -> true (high = mid - 1)
        int[] array = {1, 3, 5, 7, 9};
        assertEquals(1, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testBranch_ReturnMinusOne_NotFound() {
        // Branche: return -1 (élément non trouvé)
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(-1, BinarySearch.binarySearch(array, 6));
    }
    
    @Test
    void testBranch_ReturnIndex_Found() {
        // Branche: return mid (élément trouvé)
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(2, BinarySearch.binarySearch(array, 3));
    }
    
    // ===========================================
    // Cas spéciaux pour les branches
    // ===========================================
    
    @Test
    void testBranch_TwoElements_Found_First() {
        // Branche: recherche dans tableau à 2 éléments - premier élément
        int[] array = {3, 7};
        assertEquals(0, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testBranch_TwoElements_Found_Second() {
        // Branche: recherche dans tableau à 2 éléments - deuxième élément
        int[] array = {3, 7};
        assertEquals(1, BinarySearch.binarySearch(array, 7));
    }
    
    @Test
    void testBranch_EvenNumberOfElements() {
        // Branche: tableau avec nombre pair d'éléments
        int[] array = {2, 4, 6, 8, 10, 12};
        assertEquals(3, BinarySearch.binarySearch(array, 8));
    }
    
    @Test
    void testBranch_WithNegativeNumbers() {
        // Branche: nombres négatifs
        int[] array = {-10, -5, -3, 0, 2, 4};
        assertEquals(1, BinarySearch.binarySearch(array, -5));
    }
}
