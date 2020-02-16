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
import javax.swing.JScrollPane;

public class TextPane {

	JFrame frame;
	static JTextField nameText;
	static JTextField answerField;
	static JTextField messageSent;
	private String message = null;

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

	/**
	 * Create the application.
	 */
	public TextPane() {
		initialize();
		   // Save a turn
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
				message=nameText.getText();
				messageSent.setText(message);
				firstButton.setSelected(false);
				answerField.setText(message);
				System.out.println(answerField.getText());
				if (message == "Message") {
					GoBoard.newGame.addTurn(GoBoard.newTurn);
	    		}
	    		else if (TextPane.nameText.getText() == "clear") {
		    		GoBoard.newGame.allTurns.clear();
	    			GoBoard.newTurn.setMessage("Game cleared.");
		    		System.out.println("Game cleared.");
	    		}
	    		else {
	    			String message=TextPane.nameText.getText();
	    			GoBoard.newTurn.setMessage(message);
		    		TextPane.answerField.setText(GoBoard.newTurn.toString());
	    		}
				GoBoard.newGame.addTurn(GoBoard.newTurn);
				System.out.println("Game turns: "+GoBoard.newTurn.toString());
			}
		});
		
		
		firstButton.setBounds(46, 83, 300, 29);
		frame.getContentPane().add(firstButton);
		
		messageSent = new JTextField();
		messageSent.setHorizontalAlignment(SwingConstants.CENTER);
		messageSent.setText("Message submitted");
		messageSent.setBounds(46, 220, 300, 24);
		frame.getContentPane().add(messageSent);
		messageSent.setColumns(10);
		messageSent.setEditable(false);
		
		nameText = new JTextField();
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		nameText.setText("Message");
		nameText.setBounds(46, 16, 300, 66);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);
		
		JTextPane answerField =  new JTextPane();
		answerField.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(answerField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		answerField.setText("Left-Click to place a turn, Right-Click to remove it.  Shift+Right-Click to take captures.  Shift+Left-Click to chamber a turn.  Submit to add turn to Game.");
		answerField.setEditable(false);
		answerField.setBounds(46, 140, 300, 75);
		frame.getContentPane().add(answerField);
		frame.add(scrollPane);
		
		JLabel lblYourName = new JLabel("Turn Submitted");
		lblYourName.setBounds(46, 120, 300, 16);
		frame.getContentPane().add(lblYourName);
		
	}
}

