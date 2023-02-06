package mini1;

import java.util.Scanner;

/**
 * Utility class with a bunch of static methods for loop practice.
 * @author
 */
public class LoopsInfinityAndBeyond {

	// disable instantiation
	private LoopsInfinityAndBeyond() { }
	
	/**
	 * Returns a new string in which every character in the given string that
	 * is not already repeated consecutively is doubled.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * "attribute1" -> "aattrriibbuuttee11"
	 * "AAA Bonds" -> "AAA  BBoonnddss"
	 * }</pre>
	 * 
	 * @param text given starting string
	 * @return string with characters doubled
	 */
	public static String doubleChars(String text)
	{
		
		if(text.length() < 1) {
			return text;
		} else if(text.length() == 1) {
			return text + text;
		}
		
		String finalText = "";
		
		finalText += text.charAt(0);
		
		if(text.charAt(0) != text.charAt(1)) {
			finalText += text.charAt(0);
		}
		
		for(int i = 1; i < text.length(); i++) {
			
			finalText += text.charAt(i);
			
			if(i < text.length() - 1) {
				if(text.charAt(i) != text.charAt(i - 1) && text.charAt(i) != text.charAt(i + 1)) {
					finalText += text.charAt(i);
				}
			}
					
		}
		
		if(text.charAt(text.length() - 1) != text.charAt(text.length() - 2)) {
			finalText += text.charAt(text.length() - 1);
		}
		
		return finalText;
	}

	/**
	 * Returns a year with the highest value, given a string containing pairs
	 * of years and values (doubles). If there are no pairs, the method returns
	 * -1. In the case of a tie, the first year with the highest value is
	 * returned. Assumes the given string follows the correct format.
	 * <p>
	 * For example, given the following string, the year 1995 is returned.
	 * <pre>
	 * 1990 75.6 1991 110.6 1995 143.6 1997 62.3
	 * </pre>
	 * 
	 * @param data given string containing year-value pairs
	 * @return first year associated with the highest value, or -1 if no pair
	 *         given
	 */
	public static int maxYear(String data)
	{
		
		if(data.equals("")) {
			return -1;
		}
		
		Scanner inSS = new Scanner(data);
		
		int largestYear = 0;
		int currentYear;
		double largestValue = 0;
		double currentValue;
		
		while(inSS.hasNext()) {
			
			currentYear = inSS.nextInt();
			currentValue = inSS.nextDouble();
			
			if(currentValue > largestValue) {
				largestYear = currentYear;
				largestValue = currentValue;
			}
			
		}
		
		return largestYear;
	}
	
	/**
	 * Returns the number of iterations required until n is equal to 1,
	 * where each iteration updates n in the following way:
	 * 
	 * <pre>
	 *     if n is even
	 *         divide it in half
	 *     else
	 *         multiply n by three and add 1
	 * </pre>
	 * 
	 * For example, given n == 6, the successive values of
	 * n would be 3, 10, 5, 16, 8, 4, 2, 1, so the method returns 8. If
	 * n is less than 1, the method returns -1.
	 * <p>
	 * (Remark: How do we know this won't be an infinite loop for some
	 * values of n? In general, we don't: in fact this is a well-known
	 * open problem in mathematics. However, it has been checked for numbers up to
	 * 10 billion, which covers the range of possible values of the Java
	 * int type.)
	 * 
	 * @param n given starting number
	 * @return number of iterations required to reach n == 1, or -1 if
	 *         n is not positive
	 */
	public static int collatzCounter(int n)
	{
		
		int iterations = 0;
		
		if(n < 1) {
			return - 1;
		}
		
		while(n != 1) {
			
			if(n % 2 == 0) {
				n /= 2;
			} else {
				n = (n * 3) + 1;
			}
			
			iterations++;
		}
		
		return iterations;
	}
	
	/**
	 * Returns a new string in which every word in the given string is doubled. A
	 * word is defined as a contiguous group of non-space (i.e., ' ') characters
	 * that starts with an alphabetic letter and are surrounded by spaces and/or
	 * the start or end of the given string. Assumes the given string does not
	 * contain more than one consecutive white-space.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * "the time time" -> "the the time time time time"
	 * "The answer is 42." -> "The The answer answer is is 42."
	 * "A. runtime = 10ms" -> "A. A. runtime runtime = 10ms"
	 * }</pre>
	 * 
	 * @param text given starting string
	 * @return new string in which words are doubled
	 */
	public static String doubleWords(String text)
	{
		
		String finalText = "";
		
		Scanner inSS = new Scanner(text);
		
		while(inSS.hasNext()) {
			
			String nextToken = inSS.next();
			char firstChar = nextToken.charAt(0);
			
			if(Character.isAlphabetic(firstChar)) {
				finalText += nextToken;
				finalText += " ";
				finalText += nextToken;
				finalText += " ";
			} else {
				finalText += nextToken;
				finalText += " ";
			}
			
		}
		
		finalText = finalText.trim();
		
		return finalText;
	}

	/**
	 * Returns true if string t can be obtained from string s by removing exactly
	 * one vowel character. The vowels are the letters 'a', 'e', 'i', 'o'
	 * and 'u'. Vowels can be matched in either upper or lower case, for example,
	 * 'A' is treated the same as 'a'. If s contains no vowels the method returns
	 * false.
	 * <p>
	 * For example:
	 * <pre>{@code
	 * "banana" and "banna" returns true
	 * "Apple" and "ppl" returns false
	 * "Apple" and "pple" returns true
	 * }</pre>
	 * 
	 * @param s longer string
	 * @param t shorter string
	 * @return true if removing one vowel character from s produces the string t
	 */
	public static boolean oneVowelRemoved(String s, String t)
	{
		
		String newString = "";
		String charToCheck;
		
		for(int i = 0; i < s.length(); i++) {
			
			newString = s;
			charToCheck = "";
			charToCheck += newString.charAt(i);
			
			if("aeiouAEIOU".contains(charToCheck)) {
				
				newString = s.substring(0, i) + s.substring(i + 1);
				
				if(newString.equals(t)) {
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	/**
	 * Returns a new string in which a UFO pattern in the given string is
	 * shifted one character to the right. The UFO pattern starts with a
	 * {@code '<'}, has one or more {@code '='} and ends with a {@code '>'}. The pattern may wrap
	 * around from the end to the beginning of the string, for example
	 * {@code ">  <="}. Any other characters from the given string remain in place.
	 * If the UFO moves over top of another character, that character is
	 * removed. If there are multiple UFO patterns, only the one that starts
	 * farthest to the left is moved.
	 * <p> 
	 * For example:
	 * <pre>{@code
	 * " <=>  *   . <=> " ->
	 * "  <=> *   . <=> "
	 * 
	 * "   <=>*   .     " ->
	 * "    <=>   .     "
	 * 
	 * ">.   x     .  <=" ->
	 * "=>   x     .   <"
	 * 
	 * " <= <===>   .   " ->
	 * " <=  <===>  .   "
	 * }</pre>
	 * 
	 * @param space given string
	 * @return a new string with a UFO pattern moved one to the right
	 */
	public static String ufo(String space)
	{
		
		//could not figure this one out
		
		return null;
	}
	
	/**
	 * Prints a pattern of <code>2*n</code> rows containing slashes, dashes and backslashes
	 * as illustrated below.
	 * <p>
	 * When {@code n <= 0 }, prints nothing.
	 * <p> 
	 * <code>n = 1</code>
	 * <pre>
	 * \/
	 * /\
	 * </pre>
	 * <p> 
	 * <code>n = 2</code>
	 * <pre>
	 * \--/
	 * -\/
	 * -/\
	 * /--\
	 * </pre>
	 * <p> 
	 * <code>n = 3</code>
	 * <pre>
	 * \----/
	 * -\--/
	 * --\/
	 * --/\
	 * -/--\
	 * /----\
	 * </pre>
	 * 
	 * @param n number of rows in the output
	 */
	public static void printX(int n)
	{
		
		int numPreDash = 0;
		int numInsideDash = (n * 2) - 2;
		
		if(n <= 0) {
			return;
		}
		
		for(int i = 0; i < n; i++) {
			
			for(int m = 0; m < numPreDash; m++) {
				System.out.print("-");
			}
			
			System.out.print("\\");
			
			for(int k = numInsideDash; k > 0; k--) {
				System.out.print("-");
			}
				
			System.out.println("/");
				
			numPreDash++;
			numInsideDash -= 2;
			
		}
		
		numInsideDash = 0;
		
		for(int i = 0; i < n; i++) {
			
			numPreDash--;
			
			for(int m = 0; m < numPreDash; m++) {
				System.out.print("-");
			}
			
			System.out.print("/");
			
			for(int k = numInsideDash; k > 0; k--) {
				System.out.print("-");
			}
			
			System.out.println("\\");		
			numInsideDash += 2;
		}

				
	}

	
}
