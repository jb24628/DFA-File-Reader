package csci3236;
import java.util.Scanner;
import java.io.File;
/**
 * 
 * @author Jireh Bethely and Keagan Galbraith
 *
 */
public class Runner {
	private static DFA dfa;
	private static char alphabet[];
	/**
	 * Reads a file and creates a corresponding DFA
	 * @param fileName The name of the file the DFA instructions are stored in
	 */
	public Runner() {

	}
	public static void readFile(String fileName) {
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));
		}
		catch(Exception e) {
			System.out.println("There was a problem opening the file.");
			System.exit(0);
		}

		try {
			String line1 = input.nextLine(); //Line containing the alphabet            
			alphabet = new char[line1.length()/2];
			int index = 0;
			for (int i = 1; i < line1.length(); i+=2) {
				alphabet[index++] = line1.charAt(i);
			}

			String line2 = input.nextLine(); //Line containing the states
			dfa = new DFA(line2.length()/2);
			for (int i = 1; i < line2.length(); i+=2) {
				dfa.addState( new State(line2.charAt(i), alphabet.length) );
			}

			String line3 = input.nextLine(); //Line containing the start state
			dfa.setStartState(dfa.getState(line3.charAt(0)));

			String line4 = input.nextLine(); //Line contain the accepting states
			int numOfAcceptStates = line4.length()/2;
			dfa.setNumOfAcceptStates(numOfAcceptStates);
			for (int i = 1; i <line4.length(); i += 2) {
				dfa.addAcceptState(dfa.getState(line4.charAt(i)));
			}

			int transCount=0;
			while (input.hasNextLine()) {
				if( transCount < alphabet.length * dfa.getNumOfState()){
					String s=input.nextLine();
					State tempState= dfa.getState(s.charAt(1));
					tempState.addTransition(s.charAt(3),s.charAt(7));
					transCount++;
				}
				else{
					System.out.println("The file input does not represent a DFA");
					System.exit(0);
				}
			}
		}
		catch(Exception e1) {
			System.out.println("Please make sure the file is formatted correctly. Example input:");
			System.out.println("{0,1}\r\n" + "{a,b,c,d}\r\n" +     "a\r\n" + "{d}\r\n" + "(a,0)->b\r\n" +     "(a,1)->a\r\n" +
					"(b,0)->c\r\n" + "(b,1)->a\r\n" +     "(c,0)->c\r\n" + "(c,1)->d\r\n" + "(d,0)->d\r\n" +     "(d,1)->d");
			System.exit(0);
		}
	}
	/**
	 * Takes the command line arguments and decides what to do based on the number of them
	 * @param args The arguments from the commandline
	 */
	public static void fileOrPrompt(String args[]) {
		switch(args.length) {
		case 2: //Is executed when there are two command line arguments. One being the DFA file instructions name and one being a file of test cases
			readFile(args[0]);

			Scanner input = null;
			try {
				input = new Scanner(new File(args[1]));
			}
			catch(Exception e) {
				System.out.println("There was a problem opening the file of strings.");
				System.exit(0);
			}

			while (input.hasNextLine()) { //tests each test case of the second file
				String temp = input.nextLine();
				System.out.print(temp + " is");
				if (!isValidString(temp)) {
					System.out.print(" not");
				}
				System.out.println(" accepted in the current DFA.");
			}
			return;

		case 1: //File as argument
			System.out.println("File argument is valid.\n");
			readFile(args[0]);
			return;

		case 0: // No command line arguments no file input
			Scanner kb = new Scanner(System.in);
			System.out.println("Enter in a file name:");
			String fName = kb.nextLine();
			readFile(fName);
		}
	}
	/**
	 * Tests if a String is valid given the current DFA
	 * @param str The String being tested
	 * @return Returns true if the String is valid in the given DFA. Returns false if it is not.
	 */
	public static boolean isValidString (String str) {
		State temp = dfa.getStartState();


		for (int i = 0; i < str.length(); i ++) {
			boolean letterIsInAlpha = false;
			for (int j = 0; j < alphabet.length && !letterIsInAlpha; j++) {
				if (alphabet[j] == str.charAt(i)) {
					letterIsInAlpha = true;
				}
			}
			if (!letterIsInAlpha) {
				System.out.println("There is is/are (a) letter(s) in the string that is not in the alphabet of the DFA");
				return false;
			}
			temp = dfa.getState(temp.traverse(str.charAt(i)));
		}

		if (dfa.isAcceptState(temp)) {
			return true;
		}
		return false;
	}

	public static void areValidStrings(String[] s) {
		for (int i = 0; i < s.length; i++) {
			System.out.print(s[i] + " is");
			if (!isValidString(s[i])) {
				System.out.print(" not ");
			}
			System.out.println(" a valid string");
		}
	}

	/**
	 * Shows each state visited given as String
	 * @param str The String to test
	 */
	public static void showStateTraversal(String str) {
		State temp = dfa.getStartState();
		System.out.print(temp.getChar() + "-->");
		for (int i = 0; i < str.length(); i ++) {
			boolean letterIsInAlpha = false;
			for (int j = 0; j < alphabet.length && !letterIsInAlpha; j++) {
				if (alphabet[j] == str.charAt(i)) {
					letterIsInAlpha = true;
				}
			}
			if (!letterIsInAlpha) {
				System.out.println("There is is/are (a) letter(s) in the string that is not in the alphabet of the DFA");
				return;
			}
			temp = dfa.getState(temp.traverse(str.charAt(i)));
			System.out.print(temp.getChar());
			if (i != str.length()-1) {
				System.out.print("-->");
			}

		}
		System.out.println();
	}

	public static void main(String args[]) {
		fileOrPrompt(args); //Generates the DFA based on the command line arguments

		Scanner kb = new Scanner(System.in); //Creates way to take user input

		System.out.println("Enter in a string to see if it is valid in the DFA:");
		String input = kb.nextLine(); //User input on String to test

		while (!input.equals("/quit")) { //Runs infinitely until the user types "/quit"
			boolean skip = false;
			try {
				if (input.substring(0,6).equals("/show ")) { //Sees if the user wanted a visual display of the states visited
					showStateTraversal(input.substring(6));
					input = input.substring(6);
				}

				if (input.charAt(0) == '/' && input.charAt(2) == ' ') {
					areValidStrings(input.substring(3).split("" + input.charAt(1)));
					break;
				}

			}
			catch(Exception e) {};
			if (!skip) {
				System.out.print(input);
				if (!isValidString(input)) {
					System.out.print(" is not valid in the given DFA");
				}
				else {
					System.out.println(" is valid in the given DFA.");
				}

				input = kb.nextLine();
			}
		}
		kb.close();
	}
}