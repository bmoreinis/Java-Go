package GoBoard;
import java.util.Deque;
import java.util.LinkedList;

/** 
 * Represents a Go Game
 * @author Bram
 *
 */

public class Game {
	
	/* this Deque is for all played turns */
	Deque<Turn> allTurns;
	
	/* this Deque is for moving played turns into a queue when navigating forward & back. */
	Deque<Turn> hanoiTurns;
	
	
	private void repOK() {
		// Turns Deque may not be null
		assert(allTurns !=null);
		// No null Turns in Turns Deque
		for (Turn t : allTurns) {
			assert(t != null);
		}
	}
	
	/** Constructor 
	 * Creates a new Game with an empty Deque of Turns
	 * and an empty Deque for swapping turns
	 */
	
	public Game() {
		this.allTurns = new LinkedList<Turn>();
		this.hanoiTurns = new LinkedList<Turn>();
		repOK();
	}
	
	
	/**
	 * Checks if specified Game has no turns.  
	 * @return true if there are zero Turns in the game.
	 */
	
	public boolean isEmpty() {
		return this.allTurns.isEmpty();
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
				this.allTurns.push(newTurn);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Returns the number of turns in a game
	 * @return in 
	 * @throws NullPointerException
	 */
	public int size() throws NullPointerException {
		if (this.allTurns==null) {
			throw new NullPointerException("Can't size a null game.");
		}
		else {
			return this.allTurns.size();
		}
	}
	
	/**
	 * Adds turns from a saved game to an existing game, removing existing turns first.
	 */
	public void addAllTurns(Game toAdd) {
		if (!this.isEmpty()) {
			this.allTurns.clear();
		}
		this.allTurns.addAll(toAdd.allTurns);
	}
	
	
	/**
	 * Shows the last turn without modifying deck contents.
	 * @return the last turn 
	 * @throws IllegalOperationException if the game is empty.
	 */
	public Turn getLastTurn() throws NullPointerException {
		if (this.isEmpty()) {
			throw new NullPointerException("No turns yet.");
		}
		else return this.allTurns.peek();
	}
	

	
	/**
	 * Go back one turn
	 * @throws NullPointerException if the deck is empty.
	 */
	public Turn previousTurn() throws NullPointerException {
		if (this.isEmpty()) {
			throw new NullPointerException("Empty game: no turns to go back from.");
		}
		else {
			this.hanoiTurns.push(this.allTurns.peek());
			System.out.println("Staged on Hanoi: "+this.hanoiTurns.toString());
			return this.allTurns.pop();
		}
	}
	
	
	/**
	 * Pull turn from hanoi Turns and stage back into game
	 * @param none
	 * @throws NullPointerException if c is null
	 * 
	 */
	public Turn nextTurn() throws NullPointerException {
		if (this.hanoiTurns.size()==0) {
			System.out.println("All caught up!");
			return this.allTurns.peek();
		}
		else {
			try {
				this.allTurns.push(this.hanoiTurns.peek());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.hanoiTurns.pop();
	}
	
	public int getAllTurns() {
		return this.allTurns.size()+1;
	}
	
	public void setAllTurns(Deque<Turn> allTurns) {
		this.allTurns = allTurns;
	}
	
	@Override
	public String toString() {
		return allTurns.toString();
	}

}
