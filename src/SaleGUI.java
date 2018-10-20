

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JComboBox;

public class SaleGUI {

	private JFrame frame;
	private JTextField quantity;
	private static String USER;
	private static String LOGINTIME;
	private static String REGISTER;

	/**
	 * Launch the application.
	 */
	public static void SaleGUI(String name, String loginTime, String register) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					USER = name;
					LOGINTIME = loginTime;
					REGISTER = register;
					SaleGUI window = new SaleGUI();
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
	public SaleGUI() {
		initialize();
	}
	

	

	//Function to validate the Quantity of the product
	//Creating a sale item list(receipt) in a dictionary format
	public Map<String, Object> validateProduct(String inventoryPath, String item, JTextArea saleArea, Map<String, Object> dict) throws IOException {
		//Getting the quantity from the inventory and validating if the sale can be made
		
		 Inventory Sale = new Inventory();
		 List<String> product;
			int Quantity = Integer.parseInt(quantity.getText());
			product = Sale.productDetails(inventoryPath,item);
			int productCount = Integer.parseInt(product.get(1));
			double productPrice = Double.parseDouble(product.get(2));
			double finalPrice = (productPrice*Quantity);
			if(productCount >= Quantity && Quantity != 0 && Quantity > 0) {
				
				//saleArea.setText(item+"   " + finalPrice     +"    "+Quantity);
				List<Object> values = new ArrayList<Object>();
				  values.add(Quantity);
				  values.add(productPrice);
				  values.add(finalPrice);
				  dict.put(item, values);
				//dict.put(item, finalPrice);
				
			}
			else {
				JOptionPane.showMessageDialog(null, 
                          "Only " + productCount + " In Stock\n"+"Select Quantity within Range", 
                          "Error", 
                          JOptionPane.WARNING_MESSAGE);
			}
			return dict;
	}
	
	public void removeDictItem(Map<String, Object> sale, String key) {
		sale.remove(key);
		
	}
	
	public long generateId() {
	    long leftLimit = 1000000L;
	    long rightLimit = 10000000L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}

	
	public void completeSale(Map<String, Object> dict) throws IOException {
		
		long receiptId = generateId();
		FileWriter pw = new FileWriter("database\\completedSale.csv");
		
		
        
            pw.flush();
            pw.close();
	}
	
	public double totalSaleAmount(Map<String, Object> dict) {
		
		double total = 0.0;
		for (String key : dict.keySet()){
			List<Object> keyVal = new ArrayList<Object>();
			keyVal = (List<Object>) dict.get(key);
			double fPrice = (double) keyVal.get(2);
			total += fPrice;
		}
		return total;
		}
	
	
	
	//Display the item name, quantity, individual price and final price of each product
	public void displayDict(Map<String, Object> dict, String dictKeys, JTextArea saleText) {
		
		List<Object> keyVal = new ArrayList<Object>();
		keyVal = (List<Object>) dict.get(dictKeys);
		int pQuantity = (int) keyVal.get(0);
		double pPrice = (double) keyVal.get(1);
		double fPrice = (double) keyVal.get(2);

		
		
		saleText.append(dictKeys + "\t   "+ pQuantity + "\t   "+ pPrice + "\t   "+ round(fPrice,2) + "\n");
		
		//String result = dictKeys + "   "+ pQuantity + "   "+ pPrice + "   "+ fPrice + "\n";
		//return result;
		
	}

	
	public double round(double doublePrice, int places) {
		double scale = Math.pow(10, places);
	    return Math.round(doublePrice * scale) / scale;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Declaring the values of the required variables
		final String SOURCE = "database\\Inventory.csv";
		
		Map<String,Object> sale = new HashMap<String, Object>();
		
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Items");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(33, 190, 90, 60);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea totalSaleArea = new JTextArea();
		totalSaleArea.setFont(new Font("Arial", Font.PLAIN, 20));
		totalSaleArea.setBounds(686, 573, 501, 73);
		frame.getContentPane().add(totalSaleArea);
		
		quantity = new JTextField();
		quantity.setBounds(448, 275, 160, 60);
		frame.getContentPane().add(quantity);
		quantity.setColumns(10);
		

		JTextArea saleArea = new JTextArea();
		saleArea.setFont(new Font("Arial", Font.PLAIN, 20));
		saleArea.setBounds(686, 238, 501, 325);
		frame.getContentPane().add(saleArea);
		
		JButton btnTotal = new JButton("Total");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				totalSaleArea.setText("\n"+"Total Amount: "+"\t"+"\t"+round(totalSaleAmount(sale),2)+"$");
				
			}
		});
		btnTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTotal.setBounds(860, 656, 160, 60);
		frame.getContentPane().add(btnTotal);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.addItem("Add Item");
		comboBox.addItem("Remove Item");
		comboBox.setBounds(448, 372, 160, 32);
		frame.getContentPane().add(comboBox);
		
		
		JButton btnSmartWatch = new JButton("SmartWatch");
		btnSmartWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "SmartWatch", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "SmartWatch");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
			
			}
		});
		btnSmartWatch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSmartWatch.setBounds(33, 622, 160, 60);
		frame.getContentPane().add(btnSmartWatch);
		
		JButton btnKeyboard = new JButton("Keyboard");
		btnKeyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Keyboard", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Keyboard");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnKeyboard.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnKeyboard.setBounds(214, 622, 160, 60);
		frame.getContentPane().add(btnKeyboard);
		
		JButton btnDeodrant = new JButton("Deodrant");
		btnDeodrant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Deodrant", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Deodrant");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnDeodrant.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDeodrant.setBounds(33, 552, 160, 60);
		frame.getContentPane().add(btnDeodrant);
		
		JButton btnJeans = new JButton("Jeans");
		btnJeans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Jeans", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Jeans");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnJeans.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnJeans.setBounds(214, 552, 160, 60);
		frame.getContentPane().add(btnJeans);
		
		JButton btnHandbag = new JButton("Handbag");
		btnHandbag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Handbag", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Handbag");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnHandbag.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnHandbag.setBounds(214, 482, 160, 60);
		frame.getContentPane().add(btnHandbag);
		
		JButton btnToaster = new JButton("Toaster");
		btnToaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Toaster", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Toaster");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnToaster.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnToaster.setBounds(33, 482, 160, 60);
		frame.getContentPane().add(btnToaster);
		
		JButton btnUmbrella = new JButton("Umbrella");
		btnUmbrella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Umbrella", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Umbrella");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnUmbrella.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUmbrella.setBounds(214, 412, 160, 60);
		frame.getContentPane().add(btnUmbrella);
		
		JButton btnCereals = new JButton("Cereals");
		btnCereals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Cereals", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Cereals");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnCereals.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCereals.setBounds(33, 412, 160, 60);
		frame.getContentPane().add(btnCereals);
		
		JButton btnShampoo = new JButton("Shampoo");
		btnShampoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Shampoo", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Shampoo");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		
		JButton btnRamen = new JButton("Ramen");
		btnRamen.setBounds(214, 272, 160, 60);
		frame.getContentPane().add(btnRamen);
		btnRamen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Ramen", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Ramen");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnRamen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShampoo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShampoo.setBounds(33, 342, 160, 60);
		frame.getContentPane().add(btnShampoo);
		
		JButton btnTshirt = new JButton("Tshirt");
		btnTshirt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Tshirt", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Tshirt");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				
			
			}
		});
		btnTshirt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTshirt.setBounds(214, 342, 160, 60);
		frame.getContentPane().add(btnTshirt);
		
		
		JButton btnHairspray = new JButton("Hairspray");
		btnHairspray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Add Item")
				{
					try {
						validateProduct(SOURCE, "Hairspray", saleArea, sale); 
						Set<String> keysCount = sale.keySet();
						if(keysCount.size() > 0) {
							saleArea.setText(null);
						}
						
						for (String key : sale.keySet()){
							displayDict(sale,key, saleArea);
						}
						//saleArea.setText(Arrays.toString(sale.entrySet().toArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else if(comboBox.getSelectedItem() == "Remove Item"){
					removeDictItem(sale, "Hairspray");
					Set<String> keysCount = sale.keySet();
					if(keysCount.size() >= 0) {
						saleArea.setText(null);
					}
					
					for (String key : sale.keySet()){
						displayDict(sale,key, saleArea);
					}
				}
				}
				
				
		});
		btnHairspray.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnHairspray.setBounds(33, 272, 160, 60);
		frame.getContentPane().add(btnHairspray);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantity.setBounds(448, 190, 160, 60);
		frame.getContentPane().add(lblQuantity);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProductName.setBounds(686, 204, 137, 32);
		frame.getContentPane().add(lblProductName);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantity_1.setBounds(834, 204, 137, 32);
		frame.getContentPane().add(lblQuantity_1);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrice.setBounds(958, 204, 137, 32);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblFinalPrice = new JLabel("Final Price");
		lblFinalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFinalPrice.setBounds(1068, 204, 137, 32);
		frame.getContentPane().add(lblFinalPrice);

		
		JButton btnRemoveAll = new JButton("Cancel Sale");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Set<String> keysCount = sale.keySet();
				if(keysCount.size() >= 1) {
					saleArea.setText(null);
				}
				
				for (String key : sale.keySet()){
					sale.clear();
				}
			}
		});
		btnRemoveAll.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRemoveAll.setBounds(686, 656, 160, 60);
		frame.getContentPane().add(btnRemoveAll);
		
		JButton btnCompleteSale = new JButton("Complete Sale");
		btnCompleteSale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCompleteSale.setBounds(1037, 656, 150, 60);
		frame.getContentPane().add(btnCompleteSale);
		
		JLabel lblCashierName = new JLabel("Cashier Name:");
		lblCashierName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCashierName.setBounds(33, 75, 160, 60);
		frame.getContentPane().add(lblCashierName);
		
		JLabel label = new JLabel(USER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(181, 75, 160, 60);
		frame.getContentPane().add(label);
		
		JLabel lblLoginTime = new JLabel("Login Time:");
		lblLoginTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLoginTime.setBounds(448, 75, 160, 60);
		frame.getContentPane().add(lblLoginTime);
		
		JLabel lblLoginTime_1 = new JLabel(LOGINTIME);
		lblLoginTime_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLoginTime_1.setBounds(559, 75, 264, 60);
		frame.getContentPane().add(lblLoginTime_1);
		
		JLabel lblRegister = new JLabel("Register:");
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegister.setBounds(916, 75, 90, 60);
		frame.getContentPane().add(lblRegister);
		
		JLabel lblSelectedRegister = new JLabel(REGISTER);
		lblSelectedRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectedRegister.setBounds(1027, 75, 160, 60);
		frame.getContentPane().add(lblSelectedRegister);
		
		
		
	}
}
