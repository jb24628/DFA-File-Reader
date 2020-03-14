package csci3236;
/**
 * 
 * @author Jireh Bethely and Keagan Galbraith
 *
 */
public class DFA {
	private State startState = null, acceptStates[], states[];
	private int numStates = 0, numAS = 0; //keeps track of no. of states added and no. of accepted states added
	/**
	 * Creates a DFA
	 * @param size the number of states in the DFA
	 */
	public DFA(int size) {
		states = new State[size];
	}
	/**
	 * Adds a state to the DFA
	 * @param s The state to add to the DFA
	 */
	public void addState(State s) {
		states[numStates] = s;
		numStates++;
	}
	/**
	 * Returns the number of states
	 * @return The int value of the number of states
	 */
	public int getNumOfState() {
		return states.length;
	}
	/**
	 * Sets the start state of the DFA
	 * @param s The state to be made the start state
	 */
	public void setStartState(State s) {
		startState = s;
	}
	/**
	 * Returns the start state
	 * @return The start state
	 */
	public State getStartState() {
		return startState;
	}
	/**
	 * Sets the number of accepting states
	 * @param num The number of accepting states
	 */
	public void setNumOfAcceptStates(int num) {
		acceptStates = new State[num];
	}
	/**
	 * Adds an accepting state to the DFA
	 * @param s One of the accepting states
	 */
	public void addAcceptState(State s) {
		acceptStates[numAS] = s;
		numAS++;
	}
	/**
	 * Sees if a state is an accept state or not
	 * @param s The sate being tested
	 * @return True if the state is an accepting state. False if it is not.
	 */
	public boolean isAcceptState(State s) {
		boolean result = false;
		for (int i = 0; i < acceptStates.length; i ++) {
			if (s.equals(acceptStates[i])) {
				return true;
			}
		}
		return result;
	}
	/**
	 * Returns a State given its name.
	 * @param c The name of the State
	 * @return The State itself Returns null if the state is not found.
	 */
	public State getState(char c) {
		State result = null;
		for (int i = 0; i < numStates; i++) {
			if (states[i].getChar() == c) {
				result = states[i];
			}
		}
		return result;
	}
}