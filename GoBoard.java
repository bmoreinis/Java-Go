package GoBoard;
//NOTE TO BRAM: https://codereview.stackexchange.com/questions/93901/go-board-game-in-java
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.InputEvent;

public class GoBoard extends JPanel {
	// constants
	private static final long serialVersionUID = 1L;
    static final int boardPixelSize = 400;
    static final int boardSize = 9;
    static final Dimension preferredSize = new Dimension(420,440);
    
    // internal variables
    int board[][]; // 9x9 board
    // black always goes first.
    public static char lastMove = 'w';
    static int moveCount = 0; 
    int color=0;
    int captures=0;

    // --- Computed values ---
    // Everything is square, but this is computed so that we know where
    // to place stones so that they fall on the intersections of the 
    // board.
    int squareSize;
    // How big, in pixels are the stones?
    int stoneSize;
    // Number of pixels to make it so that the circle is centered.
    int  centerOffset;
    // The borderOffset is what you must add to all values to get the right 
    // position on the board, because of the ghost column that is the border.
    int borderOffset;

    
    // OOP Integration
    // Signify and store new captures
    boolean newCaptures = false;
    // must use ArrayList because variable length
	ArrayList<Location> newCapStones = new ArrayList<Location>();
	// must reference a game to add turns to
    Game newGame = new Game();
    
    public GoBoard(JFrame frame) {
	board = new int[boardSize][boardSize];
	// Just so everything is clear, ratio is basically the number of
	// pixels per square.
	// The number of pixels per square is the board's entire pixel size
	// divided by the number of columns, right?  What about the border?
	// given the border, we deal with an extra two squares, one on each 
	// side.  So, remember, whenever using this to locate a square, 
	// we multiply the column number, plus one for the border, by the 
	// squareSize to get the right location.
	squareSize = (int)(boardPixelSize/(boardSize+1));
	borderOffset = squareSize;
	centerOffset = (int)(squareSize/2);
	// The stone should be just big enough to almost fill the square.  
	stoneSize = squareSize;
	stoneSize -= squareSize/10;
	
	// build blank board.
	// 0 = empty, 1 = black, 2 = white. 
	for (int i = 0; i < boardSize; i++) {
	    for (int j = 0; j < boardSize; j++) {
		board[i][j] = 0;
	    }
	}
	
	// Respond to mouse events
	frame.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			int modifiersEx = e.getModifiersEx();
		    int x = (int)((e.getX()-centerOffset)/
				  squareSize);
		    int y = (int)((e.getY()-centerOffset-borderOffset)/
				  squareSize);
		    if (x < 0 || x > boardSize || y < 0 || y > boardSize) 
		    	return;
		    // Right Click
		    if(e.getButton() == MouseEvent.BUTTON3) {
		    	// Shift: remove a stone, replace with red, add to captures and to newCapStones
		    	if (modifiersEx==64) {
		    		board[y][x]=-1;
		    		Location newLoc = new Location(x,y);
		    		newCapStones.add(newLoc);
		    		//System.out.println(newCapStones.toString());
		    		captures++;
		    	}
		    	// Remove a stone, take a different turn
		    	else {
		    		board[y][x]=0;
			    	moveCount--;
		    	}
		    }
		   // Left-Click + Shift: Save a turn
		    else if (modifiersEx==64) {
		    	// there are no captures
		    	if (captures==0) {
			    	Turn newTurn = new Turn(x,y,color);
			    	newGame.addTurn(newTurn);
			    	System.out.println(newTurn.toString());
		    	}
		    	// there are captures
		    	else {
		    		//System.out.println(captures+" captures: "+newCapStones.toString());
		    		Location [] theseCapStones = new Location [captures];
		    		theseCapStones = newCapStones.toArray(theseCapStones);
		    		if (TextPane.nameText.getText() == "Message") {
			    		Turn newTurn = new Turn(x,y,color,1,captures,theseCapStones);
			    		newGame.addTurn(newTurn);
			    		System.out.println(newTurn.toString());
		    		}
		    		else {
		    			String message=TextPane.nameText.getText();
			    		Turn newTurn = new Turn(x,y,color,1,captures,theseCapStones,message);
			    		newGame.addTurn(newTurn);
			    		System.out.println(newTurn.toString());
			    		TextPane.answerField.setText(newTurn.toString());
		    		}
		    	}
		    	System.out.println("Game: \n"+newGame.toString());
		    }
		    //Left-Click: Set up a Turn
		    else if (board[y][x] == 0) {
		    	board[y][x] = ++moveCount;
			    color=(moveCount-1)%2+1;
		    }
		    repaint();
		}});
    }

    public Dimension getPrefferedSize() {
	return preferredSize;
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.setColor(new Color(200,170,30));
	// Create the gold/tan background
	g.fillRect(0,0,boardPixelSize,boardPixelSize);
	// draw the board.
	g.setColor(Color.black);
	for (int i = 0; i < boardSize; i++) {
	    g.drawLine(borderOffset, borderOffset+i*squareSize,
		       borderOffset+(boardSize-1)*squareSize,
		       borderOffset+i*squareSize);
	    g.drawLine(borderOffset+i*squareSize,borderOffset,
		       borderOffset+i*squareSize,
		       borderOffset+(boardSize-1)*squareSize);
	}
	// this repaints the board from the array, placing stones based on the turn sequence, with black first.      
	//System.out.print(Arrays.deepToString(board));
	//System.out.println();
	//System.out.println("================");
	//if (captures>0) System.out.println(newCapStones.toString());
	for (int i = 0; i < boardSize; i++) {
	    for (int j = 0; j < boardSize; j++) {
			if (board[i][j] != 0) {
			    if (board[i][j]%2==1) {
			    	g.setColor(Color.black);
			    } else if (board[i][j]%2==0) {
			    	g.setColor(Color.white);
			    } else if (board[i][j]==-1) {
					g.setColor(Color.red);
					board[i][j]=0;
			    }
			    g.fillOval(borderOffset+j*squareSize-centerOffset,
				       borderOffset+i*squareSize-centerOffset, 
				       stoneSize, stoneSize);
				} 
		    }
		}
    }
    
    public static void main(String args[]) {
		JFrame frame = new JFrame("Go Board");
		GoBoard t = new GoBoard(frame);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    System.exit(0);
			}});
		frame.getContentPane().add(t);
		frame.setSize(preferredSize);
		frame.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPane window = new TextPane();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}