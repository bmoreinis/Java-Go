package GoBoard;
/** 
 * Represents a turn in Go
 * @author Bram
 *
 */

public class Turn {
	public static final String [] colors = {"e","b","w"};
	private Location coordinates;
	private String stone;
	private Location [] capStones;
	private int code;
	private int captures;
	
	private void repOK() {
		// Location must be inside board
		assert(coordinates.x <= 9 && coordinates.y <= 9);
		// stone must be one of the allowed colors
		assert(stone=="w" || stone=="b");
	}

	public Turn(int x, int y, int color, int code) {
		this(x,y,color,code,0,null);
	}

	public Turn(int x, int y, int color, int code, int captures) {
		this(x,y,color,code,0,null);
	}
	
	public Turn(int x, int y, int color, int code, int captures, Location [] capStones) {
		this.coordinates = new Location(x,y);
		this.stone = colors[color];
		this.code = code;
		this.captures = captures;
		this.capStones = capStones;
	}
	
	public Location getCoords() {
		return coordinates;
	}
	
	public String getStone() {
		return stone;
	}
	
	public int getCode() {
		return code;
	}
	
	public int getCaptures() {
		return captures; 
	}
	
	public Location [] getCapStones() {
		return capStones; 
	}
	public String toString() {
		String cardRep = this.coordinates.toString()+",";
		cardRep+="\""+this.stone+"\", ";
		cardRep+=this.code+", ";
		cardRep+=this.captures+", ";
		if (this.captures>0) {
			for (int cap=0;cap<captures;cap++) {
			cardRep+=this.capStones[cap].toString();
			}
		}
		cardRep+="\n";
		return cardRep;
	}
}