package GoBoard;

import java.awt.EventQueue;
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
	static JTextField nameText;
	static boolean changed = false;
	static JTextPane answerField =  new JTextPane();
	static JTextField messageSent;
	static JMenuBar mb; 
	static JMenu x;  
	static JMenuItem m1, m2, m3, m4, m5; 
	static String defaultMessage="Message";
	static String message = defaultMessage;
	
	/**
	 * Create the application.
	 */
	public TextPane(Game newGame) {
		initialize(newGame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Game newGame) {
		System.out.println("Accessing in TextPane ...."+newGame.getAllTurns());
		frame = new JFrame();
		frame.setBounds(410, 0, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JToggleButton firstButton = new JToggleButton("Submit");
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
			    		TextPane.message=defaultMessage;
			    		System.out.println("Just set message to "+defaultMessage);
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
		answerField.setBounds(46, 140, 300, 75);
		frame.getContentPane().add(answerField);
		frame.add(scrollPane);
		
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
	     x.add(m1); 
	     x.add(m2); 
	     x.add(m3); 
	     x.add(m4); 
	     x.add(m5); 
	     mb.add(x); 
	     frame.setJMenuBar(mb); 
	     
	     m1.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
						GameFiles.loadGame("teachingGame.txt");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	    
	     m2.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
						GameFiles.loadGame("fullGame.txt");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    }
	    	});
	     
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

