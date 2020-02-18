package GoBoard;
import java.util.Stack;
public class Game {

	Stack<Turn> allTurns;
	
	private void repOK() {
		// Cards may not be null
		assert(allTurns !=null);
		
		// No null Cards in cards
		for (Turn t : allTurns) {
			assert(t != null);
		}
		
	}
	
	public int getAllTurns() {
		return allTurns.size()+1;
	}

	public void setAllTurns(Stack<Turn> allTurns) {
		this.allTurns = allTurns;
	}

	@Override
	public String toString() {
		return allTurns.toString();
	}
	
	/* Constructor
	 * Creates an empty deck of cards
	 */
	public Game() {
		this.allTurns = new Stack<Turn>();
		repOK();
	}
	
	
	/**
	 * Checks if specified deck is empty.  
	 * @return true if there are zero cards in the deck.
	 */
	
	public boolean isEmpty() {
		return allTurns.isEmpty();
	}
	
	/**
	 * Returns the turns in a game
	 * @return in 
	 * @throws NullPointerException
	 */
	public int size() throws NullPointerException {
		if (allTurns==null) {
			throw new NullPointerException("Can't size a null game.");
		}
		else {
			return allTurns.size();
		}
	}
	
	
	/**
	 * Shows the top card without modifying deck contents.
	 * @return the Card to the top of the deck
	 * @throws IllegalOperationException if the deck is empty.
	 */
	public Turn getLastTurn() throws NullPointerException {
		if (allTurns.isEmpty()) {
			throw new NullPointerException("No turns yet.");
		}
		else return allTurns.peek();
	}
	
	
	/**
	 * Go back one turn
	 * @throws NullPointerException if the deck is empty.
	 */
	public Turn previousTurn() throws NullPointerException {
		if (allTurns.isEmpty()) {
			throw new NullPointerException("Empty game: no turns to go back from.");
		}
		else return allTurns.pop();
	}
	
	/**
	 * Adds a card to the top of the deck.
	 * @param c - the card added to the deck, never null
	 * @throws NullPointerException if c is null
	 * 
	 */
	public void addTurn(Turn t) throws NullPointerException {
		if (t==null) {
			throw new NullPointerException("Can't add a null turn.");
		}
		else allTurns.push(t);
	}
	
	public static void main(String[] args) {
	}

	public String length() {
		// TODO Auto-generated method stub
		return null;
	}

}
