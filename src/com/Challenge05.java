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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Challenge05 {

	private static final String INPUT_PATH = "C:/Users/toni/git/tuenti_contest_2016/input/";

	private static final String INPUT_WORDS = INPUT_PATH + "challenge05_words.txt";

	private static final String HOST_IP = "52.49.91.111";
	private static final int PORT_NUMBER = 9988;
	private static final int INIT_WORD_SIZE = 4;
	
	public static void main(String[] args) throws FileNotFoundException {
		/* reading words */
		List<LetterFrequency> lettersFrequencyList = null;
		try {
			Scanner scanner = new Scanner(new File(INPUT_WORDS));
			scanner.close();
			Boolean finished = false;
			Integer level = 1; 
			while (!finished) {
				List<String> words = readWords(INIT_WORD_SIZE);
				lettersFrequencyList = calculateFrecuency (words);
				Socket echoSocket = new Socket(HOST_IP, PORT_NUMBER);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				System.out.println(in.readLine());
				out.println(" ");
				Boolean gameOver = false;
				Set<LetterFrequency> lettersUsed = new HashSet<LetterFrequency>();
				while (!gameOver) {
					for (int i=0; i<10; i++) {
						String line = in.readLine();
						System.out.println(i + "-" + line);
						if (line.contains("GAME OVER")) {
							gameOver = true;
						}
					}
					if (!gameOver) {
						String gaps = in.readLine();
						System.out.println(gaps);
						if (gaps.contains("_")) {
							if (!gameOver) {
								gaps = gaps.replaceAll(" ", "");
								gaps = gaps.replaceAll("_", "\\\\w");
								List<String> filterList = filterList(words, gaps);
								System.out.println("filterList -->" + filterList);
								lettersFrequencyList = calculateFrecuency (filterList);
								lettersFrequencyList.removeAll(lettersUsed);
								LetterFrequency letterFrequency = lettersFrequencyList.get(0);
								lettersUsed.add(letterFrequency);
								System.out.println("Testing with...." + letterFrequency.getLetter());					
								out.println(letterFrequency.getLetter().toString());
							}
						} else {
							if (level.equals(1)) {
								for (int i=0; i<3; i++) {				
									String line = in.readLine();
									System.out.println(i + " " + line);
								}
							}
							for (int i=0; i<2; i++) {				
								String line = in.readLine();
								System.out.println(i + " " + line);
							}
								//								if (i==3) {
								//									String string = (line.split(": "))[1];
								//									System.out.println(level + "solution..." + string);
								//								}
							lettersUsed = new HashSet<LetterFrequency>();
							words = readWords(INIT_WORD_SIZE + level);
							out.println("a");
							level++;
						}
					}
				}
				echoSocket.close();
				out.close();
				in.close();					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("challenge05 - finished");
	}
	
	public static List<String> readWords(Integer wordSize) throws FileNotFoundException {
		List<String> wordsList = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(INPUT_WORDS));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.length()==wordSize) {
				wordsList.add(line);
			}
		}
		scanner.close();
		return wordsList;
	}
	
	
	/**
	 * Filter a list of words.
	 * @param inputList
	 * @param charArray
	 * @return
	 */
	public static List<String> filterList(List<String>inputList, String patternStr) {
		List<String> outputList = new ArrayList<String>();
		Pattern pattern = Pattern.compile(patternStr);
		for (String myWord : inputList) {
			if (pattern.matcher(myWord).matches()) {
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
		public boolean equals(Object other){
		    if (other == null) return false;
		    if (other == this) return true;
		    if (!(other instanceof LetterFrequency))return false;
		    return ((LetterFrequency)other).getLetter().equals(this.getLetter());
		}		
		
		@Override
		public String toString() {
			return "LetterFrequency [letter=" + letter + ", repetitions=" + repetitions + "]";
		}
	 }	
}
