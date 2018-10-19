import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Admin_Frame extends JFrame implements ActionListener{
	
	private JButton addCashierButton;
	private JButton addAdminButton;
	private JButton deleteButton;
	private JButton updateButton;
	private JButton logOutButton;
	private JTextArea textShow;
	private JScrollPane scroll;
	
	PosSystem system=new PosSystem();
	
	private List<Employee> employees;
	
	public Admin_Frame(PosSystem system2){
		setLayout(null);
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		int xSize = ((int) tk.getScreenSize().getWidth());
//		int ySize = ((int) tk.getScreenSize().getHeight());
		
		setSize(800,600);
		addCashierButton = new JButton("Add Cashier");
		addCashierButton.setBounds(800*4/5,600/8,100,50);
		add(addCashierButton);
		
		addAdminButton = new JButton("Add Admin");
		addAdminButton.setBounds(800*4/5,600*2/8,100,50);
		add(addAdminButton);
		
		deleteButton = new JButton("Remove Employee");
		deleteButton.setBounds(800*4/5,600*3/8,100,50);
		add(deleteButton);
		
		updateButton = new JButton("Update Employee");
		updateButton.setBounds(800*4/5,600*4/8,100,50);
		add(updateButton);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setBounds(800*4/5,600*6/8,100,50);
		add(logOutButton);
		
		textShow=new JTextArea();  
		textShow.setBackground(Color.white);  
		textShow.setForeground(Color.black);  
		textShow.setEditable(false);
		Font font = textShow.getFont();
		float size = font.getSize() + 5.0f;
		textShow.setFont( font.deriveFont(size) );
		scroll = new JScrollPane (textShow, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(800/16,600/16,3*800/5,4*600/5);  
		add(scroll);
		
		addCashierButton.addActionListener(this);
		addAdminButton.addActionListener(this);
		deleteButton.addActionListener(this);
		updateButton.addActionListener(this);
		logOutButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==logOutButton){
			Login_Frame login = new Login_Frame();
			login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			login.setVisible(true);
			
			this.setVisible(false);
			dispose();
		}
	}
	
}
