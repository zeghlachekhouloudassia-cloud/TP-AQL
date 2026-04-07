package tp1.LineCoverageTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.Palindrome;

public class Exo1Test {
@Test
public void testConstructor() {
        Palindrome p = new Palindrome();  // Pour couvrir le constructeur
        assertNotNull(p);
    }
 @Test
 void testPalindromeSimple() {

  assertTrue(Palindrome.isPalindrome("kayak"));

 }

}