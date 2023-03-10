package util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Demo of a recursive method for finding all subsets of a specified size.
 */
public class SubsetFinder
{
  public static void main(String[] args)
  {
	  
    // Try finding the 5-element subsets of {0, 1, 2, 3, 4, 5, 6}
    ArrayList<int[]> subsets = findSubsets(9, 2);
    
    // Print results
    System.out.println("Found " + subsets.size() + " subsets");
    for (int i = 0; i < subsets.size(); ++i)
    {
      int[] subset = subsets.get(i);
      System.out.println(Arrays.toString(subset));
    }
    System.out.println();
    
    // If you want subsets of some other set of items, just
    // use the returned values as indices.  E.g. here
    // are the 3-character strings you can create from the 
    // items {"A", "B", "C", "D", "E"}.
    String[] items = {"A", "B", "C", "D", "E"};
    subsets = findSubsets(5, 3);
    for (int i = 0; i < subsets.size(); ++i)
    {
      int[] subset = subsets.get(i);
      
      // create an array with just the letters chosen by the subset
      String[] result = new String[3];
      int resultIndex = 0;
      for (int j = 0; j < subset.length; ++j)
      {
        // each element of the subset can be used as an index
        int index = subset[j];
        String letter = items[index];
        
        // then put it in the result array
        result[resultIndex] = letter;
        resultIndex += 1;
      }
      System.out.println(Arrays.toString(result));
    }
  }
  
  /**
   * Finds all k-element subsets of the values 0 through n - 1.  Each subset is 
   * represented as an int array with distinct values ordered smallest to largest.
   * The results are ordered lexicographically in the returned list.
   * @param n
   *   size of universe
   * @param k
   *   size of subsets to select
   * @return
   *   list of all possible k-element subsets of n elements 
   */
  public static ArrayList<int[]> findSubsets(int n, int k)
  {
    if (n < 0 || k < 0 || n < k) 
    {
      return new ArrayList<int[]>();
    }
    ArrayList<Integer> givenSet = new ArrayList<Integer>();
    for (int i = 0; i < n; ++i)
    {
      givenSet.add(i);
    }
    
    return findSubsets(givenSet, k);
  }
  
  
  public static ArrayList<int[]> findSubsets(ArrayList<Integer> givenSet, int size)
  {
    // Empty array list in which to put the results
    ArrayList<int []> results = new ArrayList<int[]>();
    
    // Initially no values are chosen, start with empty set
    ArrayList<Integer> chosen = new ArrayList<Integer>();
    
    // Start the recursive search using helper method
    findSubsets(chosen, givenSet, size, results);
   
    // Put resulting list in lexicographic order
    Collections.sort(results, new ArrayComparator());
    return results;
  }
  
  /**
   * Recursive helper method finds all subsets that can be obtained by selecting 
   * 'howMany' elements from 'available' and adding them to 'chosen'.  
   * Results are added to the given array list 'results'.
   * 
   * @param chosen
   *   list containing elements already selected
   * @param available
   *   list containing elements available to be selected
   * @param howMany
   *   number of additional elements to select
   * @param results
   *   all subsets found so far
   */
  private static void findSubsets(ArrayList<Integer> chosen, ArrayList<Integer> available, int howMany, ArrayList<int[]> results)
  {
    if (howMany == 0)
    {
      // Base case (success): we've selected enough elements, add chosen elements to list
      int[] set = new int[chosen.size()];
      for (int i = 0; i < chosen.size(); ++i)
      {
        set[i] = chosen.get(i);
      }
      Arrays.sort(set);
      results.add(set);
    }
    else if (available.size() == 0)
    {
      // Base case (failure): there are no more elements available to select, so 
      // this is a dead end
      return;
    }
    else
    {
      // Recursive case: take one available element (in this case we take
      // the last element of the list).  Possible
      // subsets either include it, or they don't.
      Integer x = available.remove(available.size() - 1);
      
      // First, find all subsets that DO include x, by selecting
      // howMany - 1 more elements from remaining...
      chosen.add(x);
      findSubsets(chosen, available, howMany - 1, results);
      chosen.remove(chosen.size() - 1);
      
      // Then find subsets that DON'T include x, by selecting howMany more
      // elements from remaining
      findSubsets(chosen, available, howMany, results);
      
      // restore the available list before returning
      available.add(x);
    }
  }

  /**
   * Comparator for ordering int arrays lexicographically.
   */
  private static class ArrayComparator implements Comparator<int[]>
  {
    @Override
    public int compare(int[] lhs, int[] rhs)
    {
      int size = Math.min(lhs.length,  rhs.length);
      for (int i = 0; i < size; ++i)
      {
        if (lhs[i] != rhs[i])
        {
          return lhs[i] - rhs[i];
        }
      }
      return lhs.length - rhs.length;
    }
    
  }
  
}
