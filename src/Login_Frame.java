import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Login_Frame extends JFrame implements ActionListener{
	
	private JTextField username;
	private JPasswordField password;
	private JButton loginButton;
	private JButton exitButton;
	private String usernameField="";
	private String passwordField="";
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	PosSystem system=new PosSystem();
	
	public String DateUtils() {
		  final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		  
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		    return sdf.format(cal.getTime());

	}
	
	public Login_Frame(){
		setSize(826,499);
		setLocation(500,280);
		
		usernameLabel=new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(510, 130, 101, 21);
		passwordLabel=new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(510, 169, 101, 21);
		getContentPane().setLayout(null);
		getContentPane().add(usernameLabel);
		getContentPane().add(passwordLabel);
		
		username = new JTextField(15);
		username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		username.setBounds(645, 132, 150, 20);
		password=new JPasswordField(15);
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setBounds(645, 169, 150, 20);
		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loginButton.setBounds(510, 235, 131, 44);
		exitButton=new JButton("Exit");
		exitButton.setBounds(0, 0, 0, 0);
		getContentPane().add(username);
		getContentPane().add(password);
		getContentPane().add(loginButton);
		getContentPane().add(exitButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\akhil\\Desktop\\POS System\\images\\logo.jpg"));
		lblNewLabel.setBounds(0, 0, 500, 458);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(123, 169, 161, 182);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(664, 235, 131, 44);
		getContentPane().add(btnCancel);
		
		loginButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==loginButton){
			usernameField=username.getText();
			passwordField=password.getText();
			
			
			
			if(system.logIn(usernameField,passwordField)==1){
				Admin_Frame admin = new Admin_Frame(system);
				admin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				admin.setVisible(true);
				this.setVisible(false);
				dispose();
			}else if(system.logIn(usernameField,passwordField)==2){
				//Open Cashier Class
				User_Frame user = new User_Frame();
				String currentDateTime = DateUtils();
				user.User_Frame(usernameField, currentDateTime);
				dispose();
			}else{
				JOptionPane.showMessageDialog(null,"User does not exists");
				username.setText("");
				password.setText("");
			}
		}
		if (e.getSource() == exitButton){ //Exits the program
			dispose();
			
		}
	}
}
