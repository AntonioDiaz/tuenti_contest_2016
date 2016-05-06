package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Challenge02 {

	private static final String INPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/input/";
	private static final String OUTPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/output/";
	private static final String INPUT_FILE_CORPUS = INPUT_PATH + "challenge02_corpus_huge.txt";
	private static final String INPUT_FILE_NAME = INPUT_PATH + "challenge02_testInput_huge.txt";
	private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "testOuput_02.txt";
	public static void main(String[] args) throws FileNotFoundException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(OUTPUT_FILE_NAME));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			Integer cont = 0;
			List<String> wordsList = new ArrayList<String>();
			Scanner scannerCorpus = new Scanner(new File(INPUT_FILE_CORPUS));
			scannerCorpus.useDelimiter("\\s");
			while (scannerCorpus.hasNext()) {
				String word = scannerCorpus.next();
				wordsList.add(word);
			}
			scannerCorpus.close();
			Scanner scannerInput = new Scanner(new File(INPUT_FILE_NAME));
			scannerInput.nextLine();
			while (scannerInput.hasNext()) {
				String line = scannerInput.nextLine();
				String[] split = line.split(" ");
				Integer from = Integer.parseInt(split[0]);
				Integer to = Integer.parseInt(split[1]);
				Map<String, Integer> intervalRepetitions = new HashMap<String, Integer>();
				for (int i = from; i <= to; i++) {
					String myWord = wordsList.get(i - 1);
					if (intervalRepetitions.containsKey(myWord)) {
						intervalRepetitions.put(myWord, intervalRepetitions.get(myWord) + 1);
					} else {
						intervalRepetitions.put(myWord, 1);
					}
				}
				Set<String> wordsKeys = intervalRepetitions.keySet();
				List<String> listSelected = new ArrayList<String>(3);
				for(int i=0; i<3; i++) {
					String m0 = null;
					for (Iterator<String> iterator = wordsKeys.iterator(); iterator.hasNext();) {
						String wordRepetaed = (String) iterator.next();
						if ((m0==null || intervalRepetitions.get(wordRepetaed)> intervalRepetitions.get(m0)) && !listSelected.contains(wordRepetaed)) {
							m0 = wordRepetaed;
						}					
					}
					listSelected.add(i, m0);
				}
				String str = "Case #" + (++cont) + ": ";
				for(int i=0; i<3; i++){
					str += String.format("%s %d", listSelected.get(i), intervalRepetitions.get(listSelected.get(i)));
					if (i<3-1) {
						str += ",";
					}
				}
				bw.write(str);
				bw.newLine();
				if (cont%100==0){
					System.out.println("cont " + cont);
				}
			}
			bw.close();
			scannerInput.close();
			System.out.println(wordsList.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finished");
	}
}
