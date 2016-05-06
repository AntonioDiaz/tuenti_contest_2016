package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Challenge01 {
	
	private static final String INPUT_FILE_NAME = "C:/Users/xxx/Desktop/submitInput.txt";
	private static final String OUTPUT_FILE_NAME = "C:/Users/xxx/Desktop/testOuput_01.txt";

	public static void main(String[] args) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(new File(OUTPUT_FILE_NAME));		 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		int cont = 0;
		try (Scanner scanner = new Scanner(new File(INPUT_FILE_NAME))) {
			scanner.nextLine();
			while (scanner.hasNext()){
				int members = scanner.nextInt();
				//System.out.println(scanner.nextLine());
				int tables;
				if (members==0){
					tables = 0;
				}else if (members<=4) {
					tables = 1;
				} else {
					tables = Math.abs((members - 3)) /2 + 1;					
				}
				bw.write(String.format("Case #%d: %d", ++cont, tables));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finished");
	}
}
