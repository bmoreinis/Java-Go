package GoBoard;
import java.util.Deque;
import java.util.LinkedList;
public class Game {

	Deque<Turn> allTurns;
	Deque<Turn> hanoiTurns;
	
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

	public void setAllTurns(Deque<Turn> allTurns) {
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
		this.allTurns = new LinkedList<Turn>();
		this.hanoiTurns = new LinkedList<Turn>();
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
	 * Shows the last turn without modifying deck contents.
	 * @return the last turn 
	 * @throws IllegalOperationException if the game is empty.
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
		else {
			hanoiTurns.push(allTurns.peek());
			System.out.println("Staged on Hanoi: "+hanoiTurns.toString());
			return allTurns.pop();
		}
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
		else {
			try {
				Turn newTurn = (Turn) t.clone();
				allTurns.push(newTurn);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Pull turn from hanoi Turns and stage back into game
	 * @param none
	 * @throws NullPointerException if c is null
	 * 
	 */
	public Turn nextTurn() throws NullPointerException {
		if (hanoiTurns.isEmpty()) {
			System.out.println("All caught up!");
			return allTurns.peek();
		}
		else {
			try {
				allTurns.push(hanoiTurns.peek());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hanoiTurns.pop();
	}
	
	
	public static void main(String[] args) {
	}

	public String length() {
		// TODO Auto-generated method stub
		return null;
	}

}
