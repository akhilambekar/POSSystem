
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
import java.io.Writer;
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
				  values.add(round(productPrice,2));
				  values.add(round(finalPrice, 2));
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
	
public String completeSale(Map<String, Map<String, Object>>receipt, Map<String, Object> dict, String filepath) throws IOException {
		
		String Id = Long.toString(generateId());
		receipt.put(Id, dict);
		
		String eol = System.getProperty("line.separator");
		try (Writer writer = new FileWriter(filepath, true)) {
		
		    writer.append(Id)
		          .append(',')
		          .append(""+receipt.get(Id))
		          .append(',')
		          .append(USER)
		          .append(',')
		          .append(""+totalSaleAmount(dict))
		          .append(',')
		          .append(LOGINTIME)
		          .append(',')
		          .append(REGISTER)
		          .append(eol);
		    writer.flush();
		    writer.close();
		
		    
		}
		catch (IOException ex) {
			  ex.printStackTrace(System.err);
			}
		    
		
		return Id;
		
		
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
		final String SOURCE = "C:\\Users\\akhil\\Desktop\\POS System\\database\\Inventory.csv";
		final String COMPLETESALE = "C:\\Users\\akhil\\Desktop\\POS System\\database\\completedSale.csv";
		
		Map<String,Object> sale = new HashMap<String, Object>();
		Map<String,Map<String,Object>> receiptdict = new HashMap<String, Map<String,Object>>();
		
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Items");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(33, 36, 90, 60);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea totalSaleArea = new JTextArea();
		totalSaleArea.setFont(new Font("Arial", Font.PLAIN, 20));
		totalSaleArea.setBounds(686, 448, 489, 73);
		frame.getContentPane().add(totalSaleArea);
		
		quantity = new JTextField();
		quantity.setBounds(431, 107, 160, 60);
		frame.getContentPane().add(quantity);
		quantity.setColumns(10);
		

		JTextArea saleArea = new JTextArea();
		saleArea.setFont(new Font("Arial", Font.PLAIN, 20));
		saleArea.setBounds(686, 107, 489, 325);
		frame.getContentPane().add(saleArea);
		
		JButton btnTotal = new JButton("Total");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				totalSaleArea.setText("\n"+"Total Amount: "+"\t"+"\t"+round(totalSaleAmount(sale),2)+"$");
				
			}
		});
		btnTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTotal.setBounds(856, 558, 160, 60);
		frame.getContentPane().add(btnTotal);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.addItem("Add Item");
		comboBox.addItem("Remove Item");
		comboBox.setBounds(431, 196, 160, 32);
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
		btnSmartWatch.setBounds(33, 558, 160, 60);
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
		btnKeyboard.setBounds(214, 558, 160, 60);
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
		btnDeodrant.setBounds(33, 461, 160, 60);
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
		btnJeans.setBounds(214, 461, 160, 60);
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
		btnHandbag.setBounds(214, 372, 160, 60);
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
		btnToaster.setBounds(33, 372, 160, 60);
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
		btnUmbrella.setBounds(214, 281, 160, 60);
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
		btnCereals.setBounds(33, 281, 160, 60);
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
		btnRamen.setBounds(214, 107, 160, 60);
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
		btnShampoo.setBounds(33, 196, 160, 60);
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
		btnTshirt.setBounds(214, 196, 160, 60);
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
		btnHairspray.setBounds(33, 106, 160, 60);
		frame.getContentPane().add(btnHairspray);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantity.setBounds(431, 36, 160, 60);
		frame.getContentPane().add(lblQuantity);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProductName.setBounds(686, 64, 137, 32);
		frame.getContentPane().add(lblProductName);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantity_1.setBounds(833, 64, 137, 32);
		frame.getContentPane().add(lblQuantity_1);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrice.setBounds(961, 64, 137, 32);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblFinalPrice = new JLabel("Final Price");
		lblFinalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFinalPrice.setBounds(1066, 64, 137, 32);
		frame.getContentPane().add(lblFinalPrice);

		
		JButton btnRemoveAll = new JButton("Cancel Sale");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Set<String> keysCount = sale.keySet();
				if(keysCount.size() >= 1) {
					saleArea.setText(null);
					totalSaleArea.setText(null);
				}
				
				for (String key : sale.keySet()){
					sale.clear();
				}
			}
		});
		btnRemoveAll.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRemoveAll.setBounds(686, 558, 160, 60);
		frame.getContentPane().add(btnRemoveAll);
		
		//Finishing a sale and generating receipt along with the sale details being saved in completedSale.csv
		JButton btnCompleteSale = new JButton("Complete Sale");
		btnCompleteSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double PaidAmount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Payment Amount:", "Payment", JOptionPane.INFORMATION_MESSAGE));
				double SaleAmount = totalSaleAmount(sale);
				Inventory inventory = new Inventory();
				if(PaidAmount > SaleAmount) {
				try {
					String ReceiptId = completeSale(receiptdict, sale, COMPLETESALE);
					//Updating Value in the inventory
					for(Object key: sale.keySet()) {
						List<Object> CompleteValues = (List<Object>) sale.get(key);
						int SaleCount = (int) CompleteValues.get(0);
						List<String> product = inventory.productDetails(SOURCE,key.toString());
						int productCount = Integer.parseInt(product.get(1));
						inventory.updateProductCount(SOURCE, key.toString(), (productCount - SaleCount));
						
					}
					double returnedAmount = PaidAmount-SaleAmount; 
					JOptionPane.showMessageDialog(null, "Sale Completed\n"+"Returned Amount: "+round(returnedAmount,2)+"$\n"+"Generated Receipt Id: "+ReceiptId, "Transaction Successfull", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sale.clear();
				receiptdict.clear();
				saleArea.setText(null);
				totalSaleArea.setText(null);
				}
				else if(PaidAmount == SaleAmount) {
					try {
						String ReceiptId = completeSale(receiptdict, sale, COMPLETESALE);
						//Updating Value in the inventory
						for(Object key: sale.keySet()) {
							List<Object> CompleteValues = (List<Object>) sale.get(key);
							int SaleCount = (int) CompleteValues.get(0);
							List<String> product = inventory.productDetails(SOURCE,key.toString());
							int productCount = Integer.parseInt(product.get(1));
							inventory.updateProductCount(SOURCE, key.toString(), (productCount - SaleCount));
							
						}
					
						JOptionPane.showMessageDialog(null, "Sale Completed\n"+"Generated Receipt Id: "+ReceiptId, "Transaction Successfull", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
					}
					sale.clear();
					receiptdict.clear();
					saleArea.setText(null);
					totalSaleArea.setText(null);
						
				}
				else {
					JOptionPane.showMessageDialog(null, "Insufficient Funds", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCompleteSale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCompleteSale.setBounds(1025, 558, 150, 60);
		frame.getContentPane().add(btnCompleteSale);
		
		JLabel lblCashierName = new JLabel("Cashier Name:");
		lblCashierName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCashierName.setBounds(1185, 116, 137, 32);
		frame.getContentPane().add(lblCashierName);
		
		JLabel lblLogin = new JLabel("Login Time:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLogin.setBounds(33, 10, 137, 32);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblRegisterDetail = new JLabel("Register Detail:");
		lblRegisterDetail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegisterDetail.setBounds(1185, 177, 164, 32);
		frame.getContentPane().add(lblRegisterDetail);
		
		JLabel lblNewLabel_1 = new JLabel(USER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(1320, 116, 210, 32);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel label = new JLabel(LOGINTIME);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(147, 10, 283, 32);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel(REGISTER);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(1327, 177, 150, 32);
		frame.getContentPane().add(label_1);
		
		
		
	}
}
