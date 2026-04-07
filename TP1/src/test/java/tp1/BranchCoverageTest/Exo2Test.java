package tp1.BranchCoverageTest;
import tp1.Anagram;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Exo2Test {

    @Test
    public void testConstructor() {
        Anagram p = new Anagram();
        assertNotNull(p);
    }
    
    // ===========================================
    // BRANCH COVERAGE - Tests
    // ===========================================
    
    @Test
    void testBranch_Exception_S1Null() {
        // Branche: s1 == null -> true (exception)
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram(null, "a"));
    }
    
    @Test
    void testBranch_Exception_S2Null() {
        // Branche: s2 == null -> true (exception)
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram("a", null));
    }
    
    @Test
    void testBranch_NoException() {
        // Branche: s1 == null || s2 == null -> false
        assertTrue(Anagram.isAnagram("a", "a"));
    }
    
    @Test
    void testBranch_RetourFalse_LongueursDifferentes() {
        // Branche: s1.length() != s2.length() -> true (return false)
        assertFalse(Anagram.isAnagram("ab", "abc"));
    }
    
    @Test
    void testBranch_RetourFalse_MemeLongueurPasAnagramme() {
        // Branche: même longueur mais pas anagramme -> return false
        assertFalse(Anagram.isAnagram("aab", "abb"));
    }
    
    @Test
    void testBranch_RetourTrue_AnagrammeValide() {
        // Branche: anagramme valide -> return true
        assertTrue(Anagram.isAnagram("abc", "cba"));
    }
    
    @Test
    void testBranch_CasParticulier_ChainesVides() {
        // Branche spéciale: chaînes vides
        assertTrue(Anagram.isAnagram("", ""));
    }
    
    @Test
    void testBranch_CasParticulier_UnCaractere() {
        // Branche spéciale: un seul caractère
        assertTrue(Anagram.isAnagram("a", "a"));
    }
    
    @Test
    void testBranch_AvecEspaces() {
        // Branche: avec espaces multiples
        assertTrue(Anagram.isAnagram("  abc  ", "cba"));
    }
}