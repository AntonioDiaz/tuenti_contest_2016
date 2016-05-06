package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Challenge05 {

	private static final String INPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/input/";
	//private static final String OUTPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/output/";

	private static final String INPUT_WORDS = INPUT_PATH + "challenge05_words.txt";
	//private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "testOuput04_huge.txt";

	private static final String hostName = "52.49.91.111";
	private static final int portNumber = 9988;
	
	public static void main(String[] args) throws FileNotFoundException {
		/* reading words */
		List<String> wordsList = new ArrayList<String>(30);
		List<LetterFrequency> lettersFrequencyList = null;
		try {
			Scanner scanner = new Scanner(new File(INPUT_WORDS));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.length()==4) {
					wordsList.add(line);
				}
			}
			lettersFrequencyList = calculateFrecuency (wordsList);
			scanner.close();
			Boolean solved;
			do {
				solved = play(lettersFrequencyList);	
			} while (!solved);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("challenge04 - finished");
	}
	
	private static Boolean play(List<LetterFrequency> lettersFrequencyList) throws Exception {
		Socket echoSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(in.readLine());
		out.println(" ");
		Boolean solved = false;
		Boolean exit = false;
		//Set<Character> usedCharacters = new HashSet<Character>();
		Integer j = 0;
		while (!exit) {
			for (int i=0; i<10; i++) {
				String line = in.readLine();
				System.out.println(line);
				if (line.contains("GAME OVER")) {
					exit = true;
				}
			}
			String gaps = in.readLine();
			System.out.println(gaps);
			if (gaps.contains("_")) {
				//gaps = gaps.trim();
				//gaps.replace("_", )
				if (!exit) {
					//String letter = stdIn.readLine();
					LetterFrequency letterFrequency = lettersFrequencyList.get(j++);
					System.out.println("Testing with...." + letterFrequency.getLetter());					
					out.println(letterFrequency.getLetter().toString());
				}
			} else {
				solved = true;
			}
		}
		echoSocket.close();
		out.close();
		in.close();		
		return solved;
	}

	/**
	 * Filter a list of words.
	 * @param inputList
	 * @param charArray
	 * @return
	 */
	public static List<String> filterList(List<String>inputList, char[] charArray) {
		List<String> outputList = new ArrayList<String>();
		for (String myWord : inputList) {
			boolean valid = true;
			for (int i = 0; i < charArray.length; i++) {
				valid &= myWord.charAt(i)==charArray[i];
			}
			if (valid) {
				outputList.add(myWord);
			}
		}
		return outputList;
	}
	
	private static List<LetterFrequency> calculateFrecuency (List<String> wordsList) {
		Map<Character, Integer> mapFrequency = new HashMap<Character, Integer>();
		for (String word : wordsList) {
			char[] charArray = word.toCharArray();
			for (char c : charArray) {
				if (mapFrequency.containsKey(c)) {
					mapFrequency.put(c, mapFrequency.get(c) + 1);
				} else {
					mapFrequency.put(c, 1);
				}
			}
		}
		System.out.println(mapFrequency);
		Set<Character> keySet = mapFrequency.keySet();
		List<LetterFrequency> list = new ArrayList<LetterFrequency>();
		for (Character character : keySet) {
			LetterFrequency letterFrequency = new LetterFrequency();
			letterFrequency.setLetter(character);
			letterFrequency.setRepetitions(mapFrequency.get(character));
			list.add(letterFrequency);
		}
		Collections.sort(list, new LetterFrequencyComparator());
		System.out.println("sort list--->" + list);
		return list;
	}

	static class LetterFrequencyComparator implements Comparator<LetterFrequency> {
		@Override
		public int compare(LetterFrequency o1, LetterFrequency o2) {
			return o2.getRepetitions().compareTo(o1.repetitions);
		}	
	}
	
	static class LetterFrequency {
		private Character letter;
		private Integer repetitions;
		
		public Character getLetter() {
			return letter;
		}
		public void setLetter(Character letter) {
			this.letter = letter;
		}
		public Integer getRepetitions() {
			return repetitions;
		}
		public void setRepetitions(Integer repetitions) {
			this.repetitions = repetitions;
		}
		@Override
		public int hashCode() {
			return letter.hashCode();
		}
		@Override
		public String toString() {
			return "LetterFrequency [letter=" + letter + ", repetitions=" + repetitions + "]";
		}
	 }	
}
