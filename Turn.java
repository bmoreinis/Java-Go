package GoBoard;

/** 
 * Represents a turn in Go
 * @author Bram
 *
 */

public class Turn implements Cloneable {
	public static final String [] colors = {"e","b","w"};
	private Location coordinates;
	private String stone;
	private Location [] capStones;
	private int code;
	private int captures;
	private String message;
	int turn;
	static int nextTurn = 0;
	private void repOK() {
		// Location must be inside board
		assert(coordinates.x <= 9 && coordinates.y <= 9);
		// stone must be one of the allowed colors
		assert(stone=="w" || stone=="b");
	}

	public Turn(int x, int y, int color) {
		this(x,y,color,0,0,null);
	}
	
	public Turn(int x, int y, int color, int code) {
		this(x,y,color,code,0,null);
	}
	
	public Object clone() throws CloneNotSupportedException 
	{ 
		 return super.clone(); 
	} 

	public Location getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Location coordinates) {
		this.coordinates = coordinates;
	}

	public String getMessage() {
		return message;
	}

	public void setStone(String stone) {
		this.stone = stone;
	}
	
	public String setColor(int color) {
		return colors[color];
	}
	public void setCapStones(Location[] capStones) {
		this.capStones = capStones;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setCaptures(int captures) {
		this.captures = captures;
	}

	public Turn(int x, int y, int color, int code, int captures) {
		this(x,y,color,code,0,null);
	}
	
	public Turn(int x, int y, int color, int code, int captures, Location [] capStones) {
		this(x,y,color,code,captures,capStones,captures+" stones captured.");
	}
	
	public Turn(int x, int y, int color, int code, int captures, Location [] capStones, String message) {
		this.coordinates = new Location(x,y);
		this.stone = colors[color];
		this.code = code;
		this.captures = captures;
		this.capStones = capStones;
		this.message = message;
		repOK();
		this.turn=nextTurn;
		repOK();
		nextTurn++;
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
		String turnRep = this.coordinates.toString()+",";
		turnRep+="\""+this.stone+"\", ";
		turnRep+=this.code+", ";
		turnRep+=this.captures+", ";
		if (this.captures>0) {
			for (int cap=0;cap<captures;cap++) {
				turnRep+=this.capStones[cap].toString();
			}
		}
		if (this.code>0) turnRep+=" "+message+" ";
		turnRep+="\n";
		return turnRep;
	}

	public void setMessage(String string) {
		this.message=string;
	}
}