package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Challenge07 {
	private static final String INPUT_PATH = "C:/Users/A549232/workspaces/workspace_test/contest/src/input_files/";
	private static final String OUTPUT_PATH = "C:/Users/A549232/workspaces/workspace_test/contest/src/output_files/";

	private static final String INPUT_FILE_NAME = INPUT_PATH + "challenge07_testInput_small.txt";
	private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "challenge07_testOutput_small.txt";
	
	public static void main(String[] args) {
		
		try {
			FileOutputStream fos = new FileOutputStream(new File(OUTPUT_FILE_NAME));		 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			scanner.nextLine();
			int cont = 0;
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				Integer arrayHeight = new Integer (line.split(" ")[0]);
				Integer arrayWidth = new Integer (line.split(" ")[1]);
				char[][] myArray = new char[arrayHeight][arrayWidth];
				System.out.println(line);
				for (int i = 0; i < arrayHeight; i++) {
					line = scanner.nextLine();
					System.out.println(line);
					for (int j = 0; j < arrayWidth; j++) {
						myArray[i][j] = line.charAt(j);
					}
				}
				//System.out.println("init char array:");
				//printArrayChar(myArray);
				int[][] integerArray = charToIntegerArray(myArray);
				//System.out.println("init char integer:");				
				//printArrayInteger(integerArray);
				int calculateMaxRectangleSum = calculateMaxRectangleSum(integerArray);
				cont++;
				System.out.println(cont + " el resultado es: " + calculateMaxRectangleSum);
				String result = calculateMaxRectangleSum==Integer.MAX_VALUE?"INFINITY":Integer.toString(calculateMaxRectangleSum);
				bw.write(String.format("Case #%d: %s", cont, result));
				bw.newLine();
			}
			bw.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println("termina challenge07");
	}

	public static int calculateMaxRectangleSum(int[][] myArray) {
		Integer arrayHeight = myArray.length;
		Integer arrayWidth = myArray[0].length;
		Integer maxRectangleSum = 0;
		for (int h = 0; h < arrayHeight; h++) {
			for (int w = 0; w < arrayWidth; w++) {
				//System.out.println("h " + h + " w " + w);
				int[][] subArray = new int[arrayHeight][arrayWidth];
				for (int hSub = 0; hSub < arrayHeight; hSub++) {
					for (int wSub = 0; wSub < arrayWidth; wSub++) {
						subArray[hSub][wSub] = myArray[(hSub+h) % arrayHeight][(wSub+w) % arrayWidth];
					}
				}
				//printArrayInteger(subArray);
				int[][] calculateRectangleMaxArray = calculateRectangleMaxArray(subArray);
				int[] maxValue = maxValue(calculateRectangleMaxArray);
				if (maxValue[0] > maxRectangleSum) {					
					maxRectangleSum = maxValue[0];
						System.out.println(maxValue + " h " + h + " w " + w);
						System.out.println("sub array");
						printArrayInteger(subArray);
						System.out.println("rectangles");
						printArrayInteger(calculateRectangleMaxArray);
						if (maxValue[1]==arrayHeight -1 && maxValue[2]==arrayWidth -1) {
							maxRectangleSum = Integer.MAX_VALUE;
						}
//						for (int hAux = 0; h < arrayHeight; h++) {
//							for (int wAux = 0; w < arrayWidth; w++) {
//						
						
				}
			}
		}
		return maxRectangleSum;
	}

	public static int[] maxValue (int[][] myArray) {
		/* calculate maxrectangle for this coordenate. */
		int[] maxValue = new int[3];
		maxValue[0] = Integer.MIN_VALUE;
		for (int h = 0; h < myArray.length; h++) {
			for (int w = 0; w < myArray[0].length; w++) {
				if (myArray[h][w]>maxValue[0]) {
					maxValue[0] =  myArray[h][w];
					maxValue[1] =  h;
					maxValue[2] =  w;
				}
			}
		}
		return maxValue;
	}
	
	public static int[][] calculateRectangleMaxArray(int[][] myArray) {
		int[][] rectangles = new int[myArray.length][myArray[0].length];
		/* calculate maxrectangle for this coordenate. */
		for (int h = 0; h < myArray.length; h++) {
			for (int w = 0; w < myArray[0].length; w++) {
				if (w==0) {
					rectangles[h][w] = myArray[h][w];
				} else {
					rectangles[h][w] = rectangles[h][w-1] + myArray[h][w];
				}
			}
		}
		//System.out.println("After rows sumatory");
		//printArrayInteger(rectangles);
		for (int wSub = 0; wSub < myArray[0].length; wSub++) {
			for (int hSub = 0; hSub < myArray.length; hSub++) {
				if (hSub>0) {
					rectangles[hSub][wSub] = rectangles[hSub -1][wSub] + rectangles[hSub][wSub];
				}
			}
		}		
		return rectangles;		
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
