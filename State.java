package csci3236;
/**
 * 
 * @author Jireh Bethely and Keagan Galbraith
 *
 */
public class State {

	private char state;
	private char transitionTbl[][];
	private int curr = 0; //number of states that have been entered
	/**
	 * Creates a new state
	 * @param c The name of the state
	 * @param lenOfAlpha The length of the alphabet
	 */
	public State (char c, int lenOfAlpha) {
		state = c;
		transitionTbl = new char[lenOfAlpha][2];
	}
	/**
	 * Gives you the name of the next state given a member of the alphabet. 
	 * @param alpha The member of the alphabet
	 * @return The name of the next state. Returns the null character if the next state cannot be found.
	 */
	public char traverse(char alpha) { 
		char resultState = '\u0000'; //null character
		for (int i = 0; i < transitionTbl.length; i++) {
			if (alpha == transitionTbl[i][0]) {
				resultState = transitionTbl[i][1];
			}
		}
		return resultState;
	}
	/**
	 * Stores the name of next state to go to given the next state's name and the corresponding member of the alphabet.
	 * @param alpha The member of the alphabet that causes the transition
	 * @param nextState The name of the next state
	 */
	public void addTransition(char alpha, char nextState) {
		transitionTbl[curr][0] = alpha;
		transitionTbl[curr][1] = nextState;
		curr++;
	}
	/**
	 * Returns the name of the State
	 * @return The name of the state
	 */
	public char getChar() {
		return state;
	}
	/**
	 * Tests if two states are equal
	 * @param s The second state being tested
	 * @return Returns true if the current state and the state in the parameter are the same.
	 */
	public boolean equals(State s) {
		boolean result = false;
		if (state == s.getChar()) {
			return true;
		}
		return result;
	}
}