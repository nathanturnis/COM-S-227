package mini2;

import java.util.Arrays;

public class Mini2Test {

	public static void main(String[] args) {
		
		int[][] arr = {{6, 2, 5, 5}, {5, 5, 4, 4}, {5, 2, 2, 2}};
		
		ArrayAdventures.swapRows(arr, 0, 2);
		
		System.out.println(Arrays.deepToString(arr));
		

	}

}
