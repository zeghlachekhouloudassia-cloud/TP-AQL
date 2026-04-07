package tp1.LineCoverageTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.Anagram;
public class Exo2Test {

    @Test
    public void testConstructor() {
        Anagram p = new Anagram();
        assertNotNull(p);
    }
    
 
    @Test
    void testLineCoverage_NullS1() {
        // Ligne: if (s1 == null || s2 == null)
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram(null, "test"));
    }
    
    @Test
    void testLineCoverage_NullS2() {
        // Ligne: if (s1 == null || s2 == null)
        assertThrows(NullPointerException.class, 
            () -> Anagram.isAnagram("test", null));
    }
    
    @Test
    void testLineCoverage_Normalization() {
        // Lignes: s1.toLowerCase().replaceAll() et s2.toLowerCase().replaceAll()
        assertTrue(Anagram.isAnagram("Chien  ", "  NICHE"));
    }
    
    @Test
    void testLineCoverage_DifferentLengths() {
        // Ligne: if (s1.length() != s2.length())
        assertFalse(Anagram.isAnagram("abc", "abcd"));
    }
    
    @Test
    void testLineCoverage_ValidAnagram() {
        // Lignes: boucle for et incrémentation/décrémentation
        assertTrue(Anagram.isAnagram("abc", "cba"));
    }
    
    @Test
    void testLineCoverage_InvalidAnagram() {
        // Lignes: for (int c : count) et if (c != 0)
        assertFalse(Anagram.isAnagram("abc", "abd"));
    }
    
    @Test
    void testLineCoverage_EmptyStrings() {
        // Cas limite pour la couverture
        assertTrue(Anagram.isAnagram("", ""));
    }
}