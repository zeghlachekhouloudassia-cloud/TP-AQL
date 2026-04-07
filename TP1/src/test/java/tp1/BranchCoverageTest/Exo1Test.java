package tp1.BranchCoverageTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tp1.Palindrome;

public class Exo1Test {

 @Test
 void testPalindrome() {
  assertTrue(Palindrome.isPalindrome("kayak"));
 }

 @Test
 void testNotPalindrome() {
  assertFalse(Palindrome.isPalindrome("hello"));
 }

 @Test
 void testSentencePalindrome() {
  assertTrue(Palindrome.isPalindrome("Esope reste ici et se repose"));
 }

}