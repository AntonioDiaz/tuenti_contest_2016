package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Challenge07 {

	private static final String INPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/input/";
	//private static final String OUTPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/output/";

	private static final String INPUT_FILE_NAME = INPUT_PATH + "challenge07_testInput_small.txt";
	//private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "testOuput04_huge.txt";
	
	public static void main(String[] args) {
		
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			scanner.nextLine();
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				Integer arrayHeight = new Integer (line.split(" ")[0]);
				Integer arrayWidth = new Integer (line.split(" ")[0]);
				char[][] myArray = new char[arrayHeight][arrayWidth];
				for (int i = 0; i < arrayHeight; i++) {
					line = scanner.nextLine();
					for (int j = 0; j < arrayWidth; j++) {
						myArray[i][j] = line.charAt(j);
					}
				}
				System.out.println("init char array:");
				printArrayChar(myArray);
				int[][] integerArray = charToIntegerArray(myArray);
				System.out.println("init char integer:");				
				printArrayInteger(integerArray);
				calculateMaxRectangleSum(integerArray);
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		System.out.println("termina challenge07");
	}

	public static int calculateMaxRectangleSum(int[][] myArray) {
		Integer arrayHeight = myArray.length;
		Integer arrayWidth = myArray[1].length;
		Integer maxRectangleSum = 0;
		for (int h = 0; h < myArray.length; h++) {
			for (int w = 0; w < myArray[h].length; w++) {
				System.out.println("h " + h + " w " + w);
				int[][] rectangles = new int[myArray.length][myArray[h].length];
				/* calculate maxrectangle for this coordenate. */
				for (int hSub = h; hSub < h + arrayHeight; hSub++) {
					for (int wSub = w; wSub < w + arrayWidth; wSub++) {
						int myH = hSub - h;
						int myW = wSub - w;
						if (myW==0) {
							rectangles[hSub][wSub] = myArray[hSub][wSub-w];
						} else {
							rectangles[myH][myW] = rectangles[myH][myW-1] + myArray[hSub][wSub];
						}
					}
				}
				System.out.println("After rows sumatory");
				printArrayInteger(rectangles);
				for (int wSub = w; wSub < w + myArray[h].length; wSub++) {
					for (int hSub = h; hSub < h + myArray.length; hSub++) {
						int myH = hSub - h;
						int myW = wSub - w;
						if (myH>0) {
							rectangles[myH][myW] = rectangles[myH -1][myW] + rectangles[myH][myW];
						}
					}
				}
				System.out.println("After column sumatory");
				printArrayInteger(rectangles);
			}
		}
		return maxRectangleSum;
	}
	
	public static int[][] charToIntegerArray(char[][] myArray) {
		int[][] myArrayInteger = new int[myArray.length][myArray[0].length];
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray[i].length; j++) {
				//Integer myValue = calculateValue (myArray[i][j]);
				 myArrayInteger[i][j] = calculateValue( myArray[i][j]);
			}
		}
		return myArrayInteger;
	}
	public static void printArrayChar(char[][] myArray) {
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray[i].length; j++) {
				//Integer myValue = calculateValue (myArray[i][j]);
				System.out.print("\t" + myArray[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void printArrayInteger(int[][] myArray) {
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray[i].length; j++) {
				//Integer myValue = calculateValue (myArray[i][j]);
				System.out.print("\t" + myArray[i][j]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * An uppercase character indicates a positive value A = 1, B = 2 ... Z = 26, 
	 * a lowercase character indicates a negative value a = -1, b = -2 ... z = -26 
	 * a dot indicates 0.
	 * @param c
	 * @return
	 */
	public static Integer calculateValue(Character c){
		Integer myValue = 0;
		if (Character.isUpperCase(c)) {
			myValue = (int)c - 64;
		} else if (Character.isLowerCase(c)) {
			myValue = ((int)c - 96)* -1;
		}
		return myValue; 
	}
}
