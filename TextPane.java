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
	private String message = null;
	static JTextPane answerField =  new JTextPane();
	static JTextField messageSent;
	static JMenuBar mb; 
	static JMenu x;  
	static JMenuItem m1, m2, m3; 
	
	/**
	 * Create the application.
	 */
	public TextPane() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(410, 0, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JToggleButton firstButton = new JToggleButton("Submit");
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				message = setMessage(nameText.getText());
				System.out.println(message);
				firstButton.setSelected(false);
				
				answerField.setContentType("text/html");
				answerField.setText(message);
			}
		});
		
		firstButton.setBounds(46, 83, 300, 29);
		frame.getContentPane().add(firstButton);
		nameText = new JTextField();
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		nameText.setText("Message");
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
	     x.add(m1); 
	     x.add(m2); 
	     x.add(m3); 
	     mb.add(x); 
	     frame.setJMenuBar(mb); 
	     
	     m1.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent arg0) {
	    	    	try {
						GameLogic.loadGame("teachingGame.txt");
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
						GameLogic.loadGame("fullGame.txt");
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
	    	    		GameLogic.saveTurns("savedGame.txt");
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

	public String setMessage(String message) {
		this.message = message;
		return message;
	}
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPane textWindow = new TextPane();
					textWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

}

