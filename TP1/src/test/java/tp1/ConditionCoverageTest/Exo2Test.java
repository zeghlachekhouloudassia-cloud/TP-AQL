package tp1.ConditionCoverageTest;
import tp1.Anagram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class Exo2Test {

    @Test
    public void testConstructor() {
        Anagram p = new Anagram();
        assertNotNull(p);
    }
    
    // ===========================================
    // CONDITION COVERAGE - Tests
    // ===========================================
    
    @Test
    void testCondition_S1Null_True() {
        // Condition: s1 == null -> true
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram(null, "a"));
    }
    
    @Test
    void testCondition_S1Null_False() {
        // Condition: s1 == null -> false
        assertTrue(Anagram.isAnagram("a", "a"));
    }
    
    @Test
    void testCondition_S2Null_True() {
        // Condition: s2 == null -> true
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram("a", null));
    }
    
    @Test
    void testCondition_S2Null_False() {
        // Condition: s2 == null -> false
        assertTrue(Anagram.isAnagram("a", "a"));
    }
    
    @Test
    void testCondition_LongueursDifferentes_True() {
        // Condition: s1.length() != s2.length() -> true
        assertFalse(Anagram.isAnagram("ab", "abc"));
    }
    
    @Test
    void testCondition_LongueursDifferentes_False() {
        // Condition: s1.length() != s2.length() -> false
        assertTrue(Anagram.isAnagram("ab", "ba"));
    }
    
    @Test
    void testCondition_CNonNul_True_First() {
        // Condition: c != 0 -> true (premier élément non nul)
        assertFalse(Anagram.isAnagram("abc", "abd"));
    }
    
    @Test
    void testCondition_CNonNul_True_Middle() {
        // Condition: c != 0 -> true (élément du milieu non nul)
        assertFalse(Anagram.isAnagram("aaz", "zza"));
    }
    
    @Test
    void testCondition_CNonNul_True_Last() {
        // Condition: c != 0 -> true (dernier élément non nul)
        assertFalse(Anagram.isAnagram("abz", "aby"));
    }
    
    @Test
    void testCondition_CNonNul_False() {
        // Condition: c != 0 -> false (tous les éléments sont nuls)
        assertTrue(Anagram.isAnagram("abc", "cba"));
    }
    
    @Test
    void testCondition_Combinaison_S1NullFalse_S2NullFalse() {
        // Combinaison: s1 == null false, s2 == null false
        assertTrue(Anagram.isAnagram("a", "a"));
    }
}