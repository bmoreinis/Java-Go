package GoBoard;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class TextPane {

	JFrame frame;
	static JTextField nameText;
	private String message = null;
	static JTextField answerField;

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
				firstButton.setSelected(false);
				answerField.setText(message);
				System.out.println(answerField.getText());
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
		
		answerField = new JTextField();
		answerField.setHorizontalAlignment(SwingConstants.CENTER);
		answerField.setEditable(false);
		answerField.setBounds(46, 140, 300, 75);
		frame.getContentPane().add(answerField);
		answerField.setColumns(10);
		
		JLabel lblYourName = new JLabel("Turn Submitted");
		lblYourName.setBounds(46, 120, 300, 16);
		frame.getContentPane().add(lblYourName);
		
		
	}
}

