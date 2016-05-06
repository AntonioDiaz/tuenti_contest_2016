package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge04 {

	private static final String INPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/input/";
	private static final String OUTPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/output/";

	private static final String INPUT_FILE_NAME = INPUT_PATH + "challenge04_testInput_huge.txt";
	private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "testOuput04_huge.txt";

	public static void main(String[] args) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(new File(OUTPUT_FILE_NAME));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		int cont = 0;
		try (Scanner scanner = new Scanner(new File(INPUT_FILE_NAME))) {
			scanner.nextLine();
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				//System.out.println(cont + "-" + line.length());
				Integer incomplete = calculateIncompletes("-" + line + "-");
				bw.write(String.format("Case #%d: %d", ++cont, incomplete));
				bw.newLine();
			}
			bw.close();
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("challenge04 - finished");
	}

	/* combos without las movement. */
	private static final String COMBO_01			= "\\-L\\-LD\\-D\\-RD\\-R\\-P\\-";
	private static final String COMBO_01_INCOMPLETE = "\\-L\\-LD\\-D\\-RD\\-R\\-";
	private static final String COMBO_02 			=	"\\-D\\-RD\\-R\\-P\\-";
	private static final String COMBO_02_INCOMPLETE =	"\\-D\\-RD\\-R\\-";
	private static final String COMBO_03 			= "\\-R\\-D\\-RD\\-P\\-";
	private static final String COMBO_03_INCOMPLETE = "\\-R\\-D\\-RD\\-";
	private static final String COMBO_04 			= "\\-R\\-RD\\-D\\-LD\\-L\\-K\\-";
	private static final String COMBO_04_INCOMPLETE = "\\-R\\-RD\\-D\\-LD\\-L\\-";
	private static final String COMBO_05 			= "\\-D\\-LD\\-L\\-K\\-";
	private static final String COMBO_05_INCOMPLETE = "\\-D\\-LD\\-L\\-";

	private static Integer calculateIncompletes(String input) {
		Integer count01 = repetitions(input, COMBO_01_INCOMPLETE) - repetitions(input, COMBO_01);
		Integer count02 = repetitions(input, COMBO_02_INCOMPLETE) - repetitions(input, COMBO_02) - count01;
		Integer count03 = repetitions(input, COMBO_03_INCOMPLETE) - repetitions(input, COMBO_03);
		Integer count04 = repetitions(input, COMBO_04_INCOMPLETE) - repetitions(input, COMBO_04);
		Integer count05 = repetitions(input, COMBO_05_INCOMPLETE) - repetitions(input, COMBO_05) - count04;
		return count01 + count02 + count03 + count04 + count05;
	}
	
	private static Integer repetitions(String myInput, String myPattern) {
		Integer cont = 0;
		Integer position = 0;
		Pattern pattern = Pattern.compile(myPattern);
		Matcher matcher = pattern.matcher(myInput);
		while (matcher.find(position)) {
			position = matcher.start() + 1; 
			cont++;
		}
		return cont;
	}
}
