import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javafx.scene.control.ComboBox;

import javax.swing.JComboBox;

public class User_Frame {


	private JFrame frame;
	private static String USER;
	private JLabel lblNewLabel;
	private static String LOGINTIME;

	/**
	 * Launch the application.
	 */
	public static void User_Frame(String CatchVal, String loginTime) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					USER = CatchVal;
					LOGINTIME = loginTime;
					User_Frame window = new User_Frame();
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
	public User_Frame() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Welcome, "+USER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(29, 33, 288, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.addItem("Select Register");
		comboBox.addItem("Register_1");
		comboBox.addItem("Register_2");
		comboBox.addItem("Register_3");
		comboBox.setBounds(456, 42, 288, 29);
		frame.getContentPane().add(comboBox);
		
		JLabel lblSelectRegister = new JLabel("Register:");
		lblSelectRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectRegister.setBounds(271, 40, 175, 35);
		frame.getContentPane().add(lblSelectRegister);
		
		JLabel lblLoginTime = new JLabel("Login Time:");
		lblLoginTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLoginTime.setBounds(29, 103, 135, 33);
		frame.getContentPane().add(lblLoginTime);
		
		JLabel lblNewLabel_1 = new JLabel(LOGINTIME);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(145, 107, 301, 25);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Start New Sale");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Select Register") {
					JOptionPane.showMessageDialog(null, 
	                          "Kindly Select a Register before starting a new sale", 
	                          "Error", 
	                          JOptionPane.WARNING_MESSAGE);
				}
				else {
				String SelectedRegister = (String) comboBox.getSelectedItem();
				SaleGUI newsale =new SaleGUI();
				newsale.SaleGUI(USER, LOGINTIME, SelectedRegister);
				}
			}
		});
		btnNewButton.setBounds(271, 189, 473, 132);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnReturnSale = new JButton("Return Sale");
		btnReturnSale.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReturnSale.setBounds(271, 343, 473, 132);
		frame.getContentPane().add(btnReturnSale);
		
		
		
		
	}
}
