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
	
	private static int startPull;
	
	public static int countTurns(String fileName) throws Exception {
		System.out.println(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		int turns = 0;
		while (reader.readLine() != null) turns++;
		reader.close();
		return turns;
	}
	
	public static Game readTurns(String fileName) throws Exception {
		Game newGame = new Game();
		Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
		int turns = countTurns(fileName);
		for (int i=0; i<turns; i++) {
			String[] line = sc.nextLine().trim().split(",");
			newGame.addTurn(readTurn(line));
		}
		sc.close();
		return newGame;
	}

	public static Turn readTurn(String[] line) {
		int x = Integer.parseInt(line[0]);
		int y = Integer.parseInt(line[1]);
		int color = Integer.parseInt(line[2]);
		int code = Integer.parseInt(line[3]);
		int captures = Integer.parseInt(line[4]);
		if (captures > 0) {
			Location [] capStones = new Location [captures];
			startPull = 5;
			for (int cap = 0; cap < captures;cap++) {
				capStones[cap]= new Location(Integer.parseInt(line[startPull]),Integer.parseInt(line[startPull+1]));
				startPull +=2;
			}
			Turn newTurn = new Turn(x, y, color, code, captures, capStones);
			System.out.print(newTurn.toString());
			return newTurn;
		}
		else {
			Turn newTurn = new Turn(x, y, color, code);
			System.out.print(newTurn.toString());
			return newTurn;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Game newGame = readTurns("teachingGame.txt");
		System.out.println("\nTurns read: "+ newGame.size());
	}
}