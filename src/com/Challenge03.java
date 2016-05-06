package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Challenge03 {
	private static final String INPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/input/";
	private static final String OUTPUT_PATH = "C:/Users/toni/workspaces/workspace_testing/tuenti_contest_2016/output/";
	private static final String INPUT_FILE_NAME = INPUT_PATH + "challenge03_testInput_huge.txt";
	private static final String OUTPUT_FILE_NAME = OUTPUT_PATH + "testOuput03_huge.txt";

	public static void main(String[] args) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(OUTPUT_FILE_NAME)); 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			List<String> linesCode = new ArrayList<String>();
			boolean startCode = false;
			boolean startTapes = false;
			Integer tapesCount = 0;
			/* selecting all lines of the input related to the machine definition */
			while (scanner.hasNext()){
				String line = scanner.nextLine();
				if (line.equals("tapes:")) {
					startTapes = true;
					line = scanner.nextLine();
					startCode = false;
					
				}
				if (startTapes && !line.equals("...")) {
					String myInput = line.substring(line.indexOf("'")+1, line.lastIndexOf("'"));
					Map<String, Map<Character, Action>> interpreter = buildStatesSet(linesCode);
					String outPut = tapeInterperter(myInput.toCharArray(), interpreter);
					String outputLine = String.format("Tape #%d: %s", ++tapesCount, outPut);
					System.out.println(outputLine);
					bw.write(outputLine);
					bw.newLine();
				}
				if (startCode) {
					linesCode.add(line);
				}
				if (line.equals("code:")) {
					startCode = true;
				}
			}
			bw.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static String tapeInterperter(char[] input, Map<String, Map<Character, Action>> interpreter) {
		System.out.println("input -->" + new String(input));
		String state = "start";
		Integer i = 0;
		while(!state.equals("end")) {
			Character myChar = null;
			if (i<input.length) {
				myChar = input[i];
			} else {
				myChar = " ".charAt(0);
			}
			Action action = interpreter.get(state).get(myChar);
			if (action==null) {
				state = "end";
			} else {
				if (action.getKey()!=null){
					if (i<input.length) {
						input[i] = action.getKey();
					} else {
						String outputNew= new String(input) + action.getKey();
						input = outputNew.toCharArray();
					}
				}
				if (action.getMove()==EnumMovements.RIGHT) {
					i++;
				}
				if (action.getMove()==EnumMovements.LEFT) {
					i--;
				}
				if (action.getState()!=null) {
					state = action.getState();
				}
				if (input.length%100==0) {
					System.out.println(input.length);
				}
			}
		}
		return new String(input);
	}
	private static Map<String, Map<Character, Action>> buildStatesSet(List<String> linesCode) {
		Pattern patternState = Pattern.compile("\\s\\s[\\w]*:");
		Pattern patternKey = Pattern.compile("\\s\\s\\s\\s'(.)*':");
		Pattern patternAction = Pattern.compile("\\s\\s\\s\\s\\s\\s(.)*");
		Map<String, Map<Character, Action>> mapStates = new HashMap<String, Map<Character, Action>>();
		Map<Character, Action> setKeys = new HashMap<Character, Action>();
		
		Action action = new Challenge03.Action();
		for(int i=linesCode.size(); i>0; i--) {
			String myLine = linesCode.get(i-1);
			if (patternState.matcher(myLine).matches()) {
				mapStates.put(myLine.trim().replace(":", ""), setKeys);
				setKeys = new HashMap<Character, Action>();
			}
			if (patternKey.matcher(myLine).matches()) {
				String myKey = myLine.substring(myLine.indexOf("'")+1, myLine.lastIndexOf("'"));
				setKeys.put(myKey.charAt(0), action);
				action = new Challenge03.Action();
				
			}
			if (patternAction.matcher(myLine).matches()) {
				if (myLine.contains("state")) {
					String myState = myLine.substring(myLine.indexOf(":") + 2, myLine.length());
					action.setState(myState);
				}
				if (myLine.contains("write")) {
					String myKey = myLine.substring(myLine.indexOf("'") + 1, myLine.lastIndexOf("'"));
					action.setKey(myKey.charAt(0));
				}
				if (myLine.contains("move")) {
					if (myLine.contains("right")) {
						action.setMove(EnumMovements.RIGHT);
					}
					if (myLine.contains("left")) {
						action.setMove(EnumMovements.LEFT);
					}
				}
			}
		}
		return mapStates;
	}
	
	public enum EnumKey {
		ONE, CERO, SPACE;
	};
	public enum EnumMovements {LEFT, RIGHT};
	
	static class Action {
		private Character key;
		private String state;
		private EnumMovements move;

		public Character getKey() {
			return key;
		}
		public void setKey(Character key) {
			this.key = key;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public EnumMovements getMove() {
			return move;
		}
		public void setMove(EnumMovements move) {
			this.move = move;
		}
		@Override
		public String toString() {
			return "Action [write=" + key + ", state=" + state + ", move=" + move + "]";
		}
		
	}
}
