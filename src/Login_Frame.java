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
		setLayout(null);
		setSize(520,200);
		setLocation(500,280);
		
		usernameLabel=new JLabel("username");
		passwordLabel=new JLabel("password");
		usernameLabel.setBounds(90,30,150,20);
		passwordLabel.setBounds(90,65,150,20);
		add(usernameLabel);
		add(passwordLabel);
		
		username = new JTextField(15);
		password=new JPasswordField(15);
		loginButton = new JButton("Login");
		exitButton=new JButton("Exit");
		username.setBounds(180,30,150,20);
		password.setBounds(180,65,150,20);
		loginButton.setBounds(170,100,80,20);
		loginButton.setBounds(170,100,80,20);
		add(username);
		add(password);
		add(loginButton);
		add(exitButton);
		
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
