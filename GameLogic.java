package GoBoard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
/*
 * https://www.tutorialspoint.com/How-to-read-a-2d-array-from-a-file-in-java
 * A 2d array is an array of one dimensional arrays to read the contents of a file to a 2d array –
 * Instantiate Scanner or other relevant class to read data from a file.
 * Create an array to store the contents.
 * To copy contents, you need two loops one nested within the other. the outer loop is to traverse through 
 * the array of one dimensional arrays and, the inner loop is to traverse through the elements of a particular 
 * one dimensional array.
 * Create an outer loop starting from 0 up to the length of the array. Within this loop read each line trim 
 * and split it using nextLine(), trim(), and split() methods respectively.
 * Create the second loop starting from 0 up to the length of the line. Within this loop 
 * convert each element of the string array to integer and assign to the array created in the previous step.
 */

public class GameLogic {

	public static void readTurns(String fileName) throws Exception {
		System.out.println(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		int turns = 0;
		while (reader.readLine() != null) {
			turns++;
			System.out.println(turns);
		}
		reader.close();
		Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
		int [][] turnsArray = new int[turns][6];
		for (int i=0; i<turns; i++) {
			String[] line = sc.nextLine().trim().split(",");
			for (int j=0; j<5; j++) {
				turnsArray[i][j] = Integer.parseInt(line[j]);
				if (turnsArray[i][4]>0) {
					System.out.println("Turn "+i+" has "+turnsArray[i][4]+" capture(s).");
				}
			}
			
		}
		sc.close();
		System.out.println(Arrays.deepToString(turnsArray));
	}

	public static void main(String[] args) throws Exception {
		readTurns("teachingGame.txt");
	}
}