package tp1.ConditionCoverageTest;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tp1.Palindrome;

public class Exo1Test {

 @Test
 void testNull() {

  assertThrows(NullPointerException.class, () -> {
   Palindrome.isPalindrome(null);
  });

 }

 @Test
 void testSingleLetter() {

  assertTrue(Palindrome.isPalindrome("a"));

 }

 @Test
 void testPalindromeWithSpaces() {

  assertTrue(Palindrome.isPalindrome("Esope reste ici et se repose"));

 }

 @Test
 void testNotPalindrome() {

  assertFalse(Palindrome.isPalindrome("java"));

 }

}