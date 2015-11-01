package imageprocessing;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import edu.princeton.cs.introcs.Picture;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

import java.awt.TextArea;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.ScrollPane;
import java.io.File;
import javax.swing.JSlider;

public class GUI {

	private JFrame frame;
	private ConnectedComponentImage test;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		test = new ConnectedComponentImage("images/bacteria.bmp");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 785, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCountTheNumber = new JButton("Count The Number of Components");
		btnCountTheNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "The number of Components is " + test.countComponents());
			}
		});
		btnCountTheNumber.setBounds(32, 401, 228, 63);
		frame.getContentPane().add(btnCountTheNumber);
		
		JButton btnNewButton = new JButton("Show Random Colour");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test.colourComponentImage().show();
			}
		});
		btnNewButton.setBounds(435, 401, 308, 63);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Binary Image");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test.getBinary().show();
			}
		});
		btnNewButton_1.setBounds(32, 311, 228, 63);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Show Border around Components");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Picture pic = test.getOriginalWithBorders();
				pic.show();
			}
		});
		btnNewButton_2.setBounds(435, 311, 308, 63);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblConnectedComponentLabel = new JLabel("Connected Component Label");
		lblConnectedComponentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedComponentLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblConnectedComponentLabel.setForeground(Color.RED);
		lblConnectedComponentLabel.setBounds(195, 82, 331, 167);
		frame.getContentPane().add(lblConnectedComponentLabel);
		
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JFileChooser c = new JFileChooser();
			c.showOpenDialog(null);
			file = c.getSelectedFile();
			test = new ConnectedComponentImage("images/" + file.getName());
			}
		});
		btnFile.setBounds(0, 0, 62, 29);
		frame.getContentPane().add(btnFile);
		final JLabel jLab = new JLabel();

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
