package GoBoard;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class TextPane {
	// variables
	JFrame frame;
	static GoBoard t;
	static Graphics g;
	static JTextField nameText;
	static boolean changed = false;
	static JTextPane answerField =  new JTextPane();
	static JTextField messageSent;
	static JMenuBar mb; 
	static JMenu x;  
	static JMenuItem m1, m2, m3, m4, m5, m6; 
	static String defaultMessage="Message";
	static String message = defaultMessage;
	public static int currentTurn = 0;
	
	/**
	 * Create the application.
	 */
	public TextPane(Game newGame) {
		initialize(newGame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(Game newGame) {
		frame = new JFrame();
		frame.setBounds(410, 0, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JToggleButton firstButton = new JToggleButton("Record New Turn");
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextPane.message = TextPane.nameText.getText();
				System.out.println(TextPane.message);
				firstButton.setSelected(false);
				System.out.println("Total turns: "+newGame.getAllTurns());
				if (GoBoard.turnChambered==true) {
					if (TextPane.message.equals(defaultMessage)) {
						System.out.println(GoBoard.chambered.toString()); 
						answerField.setText(GoBoard.chambered.toString());
			    		System.out.println(newGame.toString());
						newGame.addTurn(GoBoard.chambered);
						GoBoard.turnChambered = false;
		    		}
		    		else {
		    			GoBoard.chambered.setMessage(TextPane.message);
			    		TextPane.answerField.setText(GoBoard.chambered.toString());
			    		System.out.println(GoBoard.chambered.toString());
			    		newGame.addTurn(GoBoard.chambered);
			    		GoBoard.turnChambered = false;
			    		TextPane.nameText.setText(defaultMessage);
			    		TextPane.message=defaultMessage;;
		    		}
				}
	    		else {
	    		    System.out.println("No turn chambered.");
	    		    answerField.setText("No turn chambered.");
	    		}
			}
		});
		
		firstButton.setBounds(46, 83, 300, 29);
		frame.getContentPane().add(firstButton);
		nameText = new JTextField();
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		message=defaultMessage;
		nameText.setText(message);
		nameText.setBounds(46, 16, 300, 66);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);
		
		answerField.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(answerField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		answerField.setText("Left-Click to place a turn, Right-Click to remove it.  Shift+Right-Click to take captures.  Shift+Left-Click to chamber a turn.  Submit to add turn to Game.");
		answerField.setEditable(false);
		answerField.setBounds(46, 140, 300, 150);
		frame.getContentPane().add(answerField);
		frame.add(scrollPane);
		
		JButton prev=new JButton("Prev");  
		prev.setBounds(46,300,100,25);  
	    frame.add(prev); 
	    prev.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTurn--;
	    		GoBoard.prevBoard(newGame, t);
				}  
			}   
	    ); 
		
		JButton next=new JButton("Next");  
		next.setBounds(150,300,100,25);  
	    frame.add(next); 
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTurn++;
	    		GoBoard.nextBoard(newGame, t);
			}   
		}); 
		

		JButton repaint=new JButton("Repaint");  
		repaint.setBounds(253,300,100,25);  
	    frame.add(repaint); 
	    repaint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
	    		GoBoard.clearBoard(newGame, t);
			}  
	    }); 
	    
	    
		// Label
		JLabel lblYourName = new JLabel("Turn Submitted");
		lblYourName.setBounds(46, 120, 300, 16);
		frame.getContentPane().add(lblYourName);
		
		 // https://www.geeksforgeeks.org/java-swing-jmenubar/
	     mb = new JMenuBar(); 
	     x = new JMenu("Menu"); 
	     m1 = new JMenuItem("Load Teaching Game"); 
	     m2 = new JMenuItem("Load Full Game"); 
	     m3 = new JMenuItem("Save Current Game"); 
	     m4 = new JMenuItem("Display Current Game"); 
	     m5 = new JMenuItem("Clear Current Game"); 
	     m6 = new JMenuItem("Refresh Board"); 
	     
	     x.add(m1); 
	     x.add(m2); 
	     x.add(m3); 
	     x.add(m4); 
	     x.add(m5); 
	     x.add(m6);
	     mb.add(x); 
	     frame.setJMenuBar(mb); 
	     
	     //m1 = new JMenuItem("Load Teaching Game"); 
	     m1.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
	    	    		for (int i = 0; i < GoBoard.boardSize; i++) {
	    				    for (int j = 0; j < GoBoard.boardSize; j++) {
	    				    	GoBoard.board[i][j] = 0;
	    					}
	    				}
	    	    		Game fileGame = GameFiles.readTurns("teachingGame.txt");
	    	    		System.out.println("\nTurns read: "+ fileGame.allTurns.size());
	    	    		if (newGame.allTurns.size()>0) {
	    	    			newGame.allTurns.clear();
	    	    		}
	    	    		newGame.allTurns.addAll(fileGame.allTurns);
	    	    		System.out.println("Current newGame is: \n"+newGame.toString());
	    	    		GoBoard.loadBoard(newGame, t);
	    	    		System.out.println("NewGame is now "+newGame.toString());
	    	    		System.out.println("Current turn: "+newGame.allTurns.size());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	     
	     // m2 = new JMenuItem("Load Full Game"); 
	     m2.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
						//GameFiles.loadGame("fullGame.txt");
	    	    		System.out.println(newGame.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	     
	     //m3 = new JMenuItem("Save Current Game"); 
	     m3.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
	    	    		GameFiles.saveTurns("savedGame.txt");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	     
	     // m4 = new JMenuItem("Display Current Game"); 
	     m4.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
	    	    		TextPane.answerField.setText(newGame.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	    
	    	});
	     
	     // m5 = new JMenuItem("Clear Current Game"); 
	     m5.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
	    	    		newGame.allTurns.clear();
	    	    		TextPane.answerField.setText("Game cleared.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    		System.out.println("NewGame is now "+newGame.toString());
    	    		System.out.println("Current turn: "+newGame.allTurns.size());
	    	    }
	    	});
	     

	     // m6 = new JMenuItem("Refresh Board"); 
	     m6.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	for (int i = 0; i < GoBoard.boardSize; i++) {
    				    for (int j = 0; j < GoBoard.boardSize; j++) {
    				    	GoBoard.board[i][j] = 0;
    					}
    				}
	    	    	System.out.println("Board is now "+GoBoard.board.toString());
	    	    	try {
	    	    		GoBoard.refreshBoard(newGame, t);
	    	    		TextPane.answerField.setText("Board Refreshed.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	    
	    	});
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		Game aGame= new Game();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPane textWindow = new TextPane(aGame);
					textWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

