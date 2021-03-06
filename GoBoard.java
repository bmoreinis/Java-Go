package GoBoard;

// NOTES TO BRAM: 
// https://youtu.be/Db3cC5iPrOM
// https://codereview.stackexchange.com/questions/93901/go-board-game-in-java
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.EventQueue;

public class GoBoard extends JPanel {

	// constants
	private static final long serialVersionUID = 1L;
	static GoBoard t;
	static final int boardPixelSize = 400;
	static final int boardSize = 9;
	static final Dimension preferredSize = new Dimension(420, 440);
	static int board[][]; // 9x9 board
	// black always goes first.
	public static char lastMove = 'w';
	static int moveCount = 0;

	// --- Computed values ---
	// Everything is square, but this is computed so that we know where
	// to place stones so that they fall on the intersections of the
	// board.
	int squareSize;
	// How big, in pixels are the stones?
	int stoneSize;
	// Number of pixels to make it so that the circle is centered.
	int centerOffset;
	// The borderOffset is what you must add to all values to get the right
	// position on the board, because of the ghost column that is the border.
	int borderOffset;

	/* OOP Integration */
	// Signify and store new captures
	boolean newCaptures = false;
	// must use ArrayList because variable length
	ArrayList<Location> newCapStones = new ArrayList<Location>();
	// must reference a Turn to add coordinates to
	public static Turn chambered = new Turn(0, 0, 0);
	public static boolean turnChambered = false;
	int color = 0;
	int captures = 0;

	public static void click(int x, int y) throws AWTException {
		Robot bot = new Robot();
		bot.mouseMove(x, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public GoBoard(JFrame frame) {
		board = new int[boardSize][boardSize];
		squareSize = (int) (boardPixelSize / (boardSize + 1));
		borderOffset = squareSize;
		centerOffset = (int) (squareSize / 2);
		stoneSize = squareSize;
		stoneSize -= squareSize / 10;

		// build blank board.
		// 0 = empty, 1 = black, 2 = white.
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				GoBoard.board[i][j] = 0;
			}
		}

		// Respond to mouse events
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int modifiersEx = e.getModifiersEx();
				int x = (int) ((e.getX() - centerOffset) / squareSize);
				int y = (int) ((e.getY() - centerOffset - borderOffset) / squareSize);
				// out of bounds
				if (x < 0 || x > boardSize || y < 0 || y > boardSize) {
					System.out.println("Out of bounds");
				}
				// Left click to place
				else if (board[y][x] == 0 && e.getButton() == MouseEvent.BUTTON1) {
					board[y][x] = ++moveCount;
					color = (moveCount - 1) % 2 + 1;
					System.out.println("Placed " + color + " at location " + x + ", " + y);
				}
				// Right click to remove
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Remove a stone, take a different turn
					if (modifiersEx != 64) {
						board[y][x] = 0;
						moveCount--;
					}
					// Shift Added: remove a stone, replace with red, add to captures and to
					// newCapStones
					else if (modifiersEx == 64) {
						board[y][x] = -1;
						Location newLoc = new Location(x, y);
						newCapStones.add(newLoc);
						captures++;
					}
				}
				// Left-Click + Shift: Chamber a turn
				else if (modifiersEx == 64) {
					Location chamberedCoords = new Location(x, y);
					chambered.setCoordinates(chamberedCoords);
					chambered.setColor(color);
					String message = TextPane.nameText.getText();
					// there are captures
					if (captures > 0) {
						chambered.setCaptures(captures);
						Location[] theseCapStones = new Location[captures];
						theseCapStones = newCapStones.toArray(theseCapStones);
						chambered.setCapStones(theseCapStones);
					}
					// there is a message
					if (!message.equals(TextPane.defaultMessage)) {
						chambered.setCode(1);
						chambered.setMessage(message);
						System.out.println("Chambered " + color + " at location " + x + ", " + y + " with " + captures
								+ " captures and " + message + " message.");
					} else {
						chambered.setCode(0);
						chambered.setMessage("");
						System.out.println("Chambered " + color + " at location " + x + ", " + y + " with " + captures
								+ " captures.");
					}
					turnChambered = true;
				}
				// Left-Click: Set up a Turn
				else if (board[y][x] == 0) {
					board[y][x] = ++moveCount;
					color = (moveCount - 1) % 2 + 1;
				}
				repaint();
			}

		});
	}

	public Dimension getPrefferedSize() {
		return preferredSize;
	}

	public static void refreshBoard(Game newGame, Component g) {
		try {
			click(50, 50);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void loadBoard(Game newGame, Component g) {
		java.util.Iterator<Turn> gameTurns = newGame.allTurns.iterator();
		int thisTurn = 0;
		while (gameTurns.hasNext()) {
			Turn popTurn = newGame.allTurns.remove();
			newGame.hanoiTurns.add(popTurn);
			Location xy = popTurn.getCoordinates();
			int x = (int) xy.x;
			int y = (int) xy.y;
			GoBoard.board[y][x] = thisTurn;
			thisTurn++;
			if (popTurn.getCaptures() > 0) {
				Location[] capturedStones = popTurn.getCapStones();
				for (int s = 0; s < capturedStones.length; s++) {
					x = capturedStones[s].x;
					y = capturedStones[s].y;
					System.out.println("Capture: " + x + "," + y);
					GoBoard.board[y][x] = -1;
				}
			}
			System.out.println(
					"Hanoi size = " + newGame.hanoiTurns.size() + "And allTurns size = " + newGame.allTurns.size());
		}
		java.util.Iterator<Turn> hanoiTurns = newGame.hanoiTurns.iterator();
		while (hanoiTurns.hasNext()) {
			Turn pushTurn = newGame.hanoiTurns.remove();
			newGame.allTurns.add(pushTurn);
		}
		try {
			click(50, 50);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void prevBoard(Game newGame, Component g) {
		int gameSize = newGame.allTurns.size();
		if (gameSize > 0) {
			Turn popTurn = newGame.allTurns.remove();
			newGame.hanoiTurns.add(popTurn);
			Location xy = popTurn.getCoordinates();
			int x = (int) xy.x;
			int y = (int) xy.y;
			GoBoard.board[y][x] = 0;
			Turn hanoiTurn = newGame.hanoiTurns.peek();
			if (hanoiTurn.getCaptures() > 0) {
				Location[] capturedStones = hanoiTurn.getCapStones();
				for (int s = 0; s < capturedStones.length; s++) {
					x = capturedStones[s].x;
					y = capturedStones[s].y;
					System.out.println("Previous Capture: " + x + "," + y);
					GoBoard.board[y][x] = 0;
				}
			}
			if (popTurn.getCaptures() > 0) {
				Location[] capturedStones = popTurn.getCapStones();
				for (int s = 0; s < capturedStones.length; s++) {
					x = capturedStones[s].x;
					y = capturedStones[s].y;
					System.out.println("Capture: " + x + "," + y);
					GoBoard.board[y][x] = -1;
				}
			}
		}
		else {
			System.out.println("All turns reversed.");
		}
		try {
			click(50, 50);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clearBoard(Game newGame, Component g) {
		try {
			click(50, 50);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void nextBoard(Game newGame, Component g) {
		int gameSize = newGame.allTurns.size();
		Turn popTurn = newGame.hanoiTurns.remove();
		newGame.allTurns.add(popTurn);
		Location xy = popTurn.getCoordinates();
		int x = (int) xy.x;
		int y = (int) xy.y;
		GoBoard.board[y][x] = gameSize;
		if (popTurn.getCaptures() > 0) {
			Location[] capturedStones = popTurn.getCapStones();
			for (int s = 0; s < capturedStones.length; s++) {
				x = capturedStones[s].x;
				y = capturedStones[s].y;
				System.out.println("Capture: " + x + "," + y);
				GoBoard.board[y][x] = -1;
			}
		}
		try {
			click(50, 50);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(200, 170, 30));
		// Create the gold/tan background
		g.fillRect(0, 0, boardPixelSize, boardPixelSize);
		// draw the board.
		g.setColor(Color.black);
		for (int i = 0; i < boardSize; i++) {
			g.drawLine(borderOffset, borderOffset + i * squareSize, borderOffset + (boardSize - 1) * squareSize,
					borderOffset + i * squareSize);
			g.drawLine(borderOffset + i * squareSize, borderOffset, borderOffset + i * squareSize,
					borderOffset + (boardSize - 1) * squareSize);
		}
		// this repaints the board from the array, placing stones based on the turn
		// sequence, with black first.
		// System.out.print(Arrays.deepToString(board));
		// System.out.println();
		// System.out.println("================");
		// if (captures>0) System.out.println(newCapStones.toString());
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] != 0) {
					if (board[i][j] % 2 == 1) {
						g.setColor(Color.black);
					} else if (board[i][j] % 2 == 0) {
						g.setColor(Color.white);
					} else if (board[i][j] == -1) {
						g.setColor(Color.red);
						board[i][j] = 0;
					}
					g.fillOval(borderOffset + j * squareSize - centerOffset,
							borderOffset + i * squareSize - centerOffset, stoneSize, stoneSize);
				}
			}
		}
	}

	public static void initiallize(Game newGame) {
		System.out.println("Accessing in GoBoard ...." + newGame.getAllTurns());
		JFrame frame = new JFrame("Go Board");
		GoBoard t = new GoBoard(frame);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(t);
		frame.setSize(preferredSize);
		frame.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPane window = new TextPane(newGame);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String toString() {
		String boardString = "Current Board is: \n";
		for (int i = 0; i < boardSize; i++) {
			boardString += Arrays.toString(GoBoard.board[i]);
		}
		boardString += "\n";
		return boardString;
	}

}