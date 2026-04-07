package tp1.ConditionCoverageTest;



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

   
    @Test
    void testCondition_ArrayNull_True() {
        // Condition: array == null -> true
        assertThrows(NullPointerException.class, 
            () -> BinarySearch.binarySearch(null, 5));
    }
    
    @Test
    void testCondition_ArrayNull_False() {
        // Condition: array == null -> false
        int[] array = {1, 2, 3};
        BinarySearch.binarySearch(array, 2);
    }
    
    @Test
    void testCondition_LowLessThanHigh_True() {
        // Condition: low < high -> true (entre dans la boucle)
        int[] array = {1, 2, 3};
        BinarySearch.binarySearch(array, 2);
    }
    
    @Test
    void testCondition_LowLessThanHigh_False() {
        // Condition: low < high -> false (sortie de boucle)
        int[] array = {1};
        BinarySearch.binarySearch(array, 1);
    }
    
    @Test
    void testCondition_ArrayMidEqualsElement_True() {
        // Condition: array[mid] == element -> true
        int[] array = {1, 2, 3};
        assertEquals(1, BinarySearch.binarySearch(array, 2));
    }
    
    @Test
    void testCondition_ArrayMidEqualsElement_False() {
        // Condition: array[mid] == element -> false
        int[] array = {1, 2, 3, 4, 5};
        BinarySearch.binarySearch(array, 5);
    }
    
    @Test
    void testCondition_ArrayMidLessThanElement_True() {
        // Condition: array[mid] < element -> true (déplace low)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(6, BinarySearch.binarySearch(array, 13));
    }
    
    @Test
    void testCondition_ArrayMidLessThanElement_False() {
        // Condition: array[mid] < element -> false (déplace high)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(0, BinarySearch.binarySearch(array, 1));
    }
    
    @Test
    void testCondition_ArrayMidGreaterThanElement_True() {
        // Condition: array[mid] > element -> true (déplace high)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(1, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testCondition_ArrayMidGreaterThanElement_False() {
        // Condition: array[mid] > element -> false (déplace low)
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(5, BinarySearch.binarySearch(array, 11));
    }
    
   
    
    @Test
    void testCondition_LowEqualsHigh_ElementFound() {
        // Condition spéciale: low == high et élément trouvé
        int[] array = {5};
        assertEquals(0, BinarySearch.binarySearch(array, 5));
    }
    
    @Test
    void testCondition_LowEqualsHigh_ElementNotFound() {
        // Condition spéciale: low == high et élément non trouvé
        int[] array = {5};
        assertEquals(-1, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testCondition_ArrayMidEqualsElement_AtFirstIteration() {
        // Condition: trouvé à la première itération
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(2, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testCondition_ArrayMidEqualsElement_AtLastIteration() {
        // Condition: trouvé à la dernière itération
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(4, BinarySearch.binarySearch(array, 5));
    }
    
    @Test
    void testCondition_MultipleIterations_WithLowUpdate() {
        // Condition: multiples itérations avec mise à jour de low
        int[] array = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        assertEquals(7, BinarySearch.binarySearch(array, 15));
    }
    
    @Test
    void testCondition_MultipleIterations_WithHighUpdate() {
        // Condition: multiples itérations avec mise à jour de high
        int[] array = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        assertEquals(1, BinarySearch.binarySearch(array, 3));
    }
    
    @Test
    void testCondition_TwoElements_FirstIterationMidCalculation() {
        // Condition: tableau de 2 éléments - calcul du mid
        int[] array = {10, 20};
        assertEquals(0, BinarySearch.binarySearch(array, 10));
        assertEquals(1, BinarySearch.binarySearch(array, 20));
    }
}