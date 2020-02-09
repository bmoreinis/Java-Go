package GoBoard;

public class Location {
	int x;   // Cartesian
    int y;   // coordinates

    // creates and initializes a point with given (x, y)
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
	
    // return string representation of this point
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }
}
