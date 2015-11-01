package imageprocessing;

import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import edu.princeton.cs.introcs.Picture;

public class ConnectedComponentImageGUI {

	private JFrame frame;
	private ConnectedComponentImage test;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectedComponentImageGUI window = new ConnectedComponentImageGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectedComponentImageGUI() {
		test = new ConnectedComponentImage("images/bacteria.bmp");
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 734, 448);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Show Binary Image");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test.binaryComponentImage().show();
			}
		});
		btnNewButton_1.setBounds(30, 208, 239, 62);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnCountTheNumber = new JButton("Count the Number of Components ");
		btnCountTheNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "The number of Components is " + test.countComponents());
			}
		});
		btnCountTheNumber.setBounds(30, 305, 239, 52);
		frame.getContentPane().add(btnCountTheNumber);
		
		JButton btnNewButton = new JButton("Show Image with "
				+ "Random Colours");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test.colourComponentImage().show();
				
			}
		});
		btnNewButton.setBounds(438, 208, 239, 62);
		frame.getContentPane().add(btnNewButton);
		
		
	
		
		JButton btnShowImageWith = new JButton("Show Image with borders");
		btnShowImageWith.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				test.colourComponentImage().show();
			}
			
		});
		btnShowImageWith.setBounds(438, 305, 239, 52);
		frame.getContentPane().add(btnShowImageWith);
		
	}
}
