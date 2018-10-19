
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Inventory {
 private int productId;
 private String productName;
 private double productPrice;
 private int productCount;
 private String productSupplier;
 
 public Inventory() {
	 
 }

 public int getproductId() {
	  return productId;
	 }
 
 public String getproductName() {
	  return productName;
	 }
 
 public int getproductCount() {
	  return productCount;
	 }
 

 public double getproductPrice() {
	  return productPrice;
	 }

 public String getproductSupplier() {
	  return productSupplier;
	 }

 public void setproductId(int newproductId) {
	  this.productId =  newproductId;
	 }
 
 public void setproductCount(int newval) {
  this.productCount = newval;
 }
 
 public void setproductName(String newproductName) {
	  this.productName = newproductName;
	 }

 public void setproductPrice(double newproductPrice) {
	  this.productPrice = newproductPrice;
	 }
 
 public void setproductSupplier(String newproductSupplier) {
	  this.productSupplier = newproductSupplier;
	 }
 
 public List<String> productDetails(String filepath, String pId) throws IOException {
		//Creating a dictionary to map the productsID with its equivalentproduct Values
		  Map<String, List<String>> productDict = new HashMap<String, List<String>>();
		  
		  List<Inventory> InventoryValues = getInventory(filepath);
		  
		  for(Inventory u: InventoryValues) {
			  List<String> values = new ArrayList<String>();
			  values.add(Integer.toString(u.getproductId()));
			  values.add(Integer.toString(u.getproductCount()));
			  values.add(Double.toString(u.getproductPrice()));
			  values.add(u.getproductSupplier());
			  productDict.put(u.getproductName(), values);
		   }
		  
		  return productDict.get(pId);
		  //System.out.println(productDict.get(pId));
		  //System.out.println(productDict.values());
		  //System.out.println(productDict.keySet());
		  
		}

	
	
	//Takes the input from file, updates the product count value and writes the same in the file
	public void updateProductCount(String filePath, int productId, int newCount) throws IOException {
		  BufferedReader reader = null;
		   List<Inventory> Products = new ArrayList<Inventory>();
		   String line = "";
		   reader = new BufferedReader(new FileReader(filePath));
		   reader.readLine();
		   
		   while((line = reader.readLine()) != null) {
		    String[] fields = line.split(",");
		    
		    if(fields.length > 0) {
		     Inventory product = new Inventory();
		     product.setproductId(Integer.parseInt(fields[0]));
		     product.setproductName(fields[1]);
		     product.setproductCount(Integer.parseInt(fields[2]));
		     product.setproductPrice(Double.parseDouble(fields[3]));
		     product.setproductSupplier(fields[4]);
		     Products.add(product);
		    }
		   }
		   
		   for(Inventory u: Products) {
			   if(u.getproductId() == productId) {
				   u.setproductCount(newCount);
			   }
			   reader.close();
			   
			   FileWriter fileWriter = new FileWriter(filePath);
			   
			   fileWriter.append("ProductId,ProductName,ProductCount,ProductPrice,ProductSupplier\n");
			   for(Inventory u1: Products) {
				fileWriter.append(Integer.toString(u1.getproductId()));
				fileWriter.append(",");
			    fileWriter.append(u1.getproductName());
			    fileWriter.append(",");
			    fileWriter.append(Integer.toString(u1.getproductCount()));
			    fileWriter.append(",");
			    fileWriter.append(Double.toString(u1.getproductPrice()));
			    fileWriter.append(",");
			    fileWriter.append(u1.getproductSupplier());
			    fileWriter.append("\n");
			   }
			   fileWriter.close();
			   
		    //System.out.printf("[ProductId=%d, ProductName=%s]\n", u.getproductId(), u.getproductName());
		   }
	}

	//Read Inventory values on to the screen
	public List<Inventory> getInventory(String filepath) throws IOException {
			  BufferedReader reader = null;
			   List<Inventory> Products = new ArrayList<Inventory>();
			   String line = "";
			   reader = new BufferedReader(new FileReader(filepath));
			   reader.readLine();
			   
			   while((line = reader.readLine()) != null) {
			    String[] fields = line.split(",");
			    
			    if(fields.length > 0) {
			     Inventory product = new Inventory();
			     product.setproductId(Integer.parseInt(fields[0]));
			     product.setproductName(fields[1]);
			     product.setproductCount(Integer.parseInt(fields[2]));
			     product.setproductPrice(Double.parseDouble(fields[3]));
			     product.setproductSupplier(fields[4]);
			     Products.add(product);
			    }
			   }
			   reader.close();
			   
			   return Products;
			   
			  /* for(Inventory u: Products) {
					  System.out.printf("[ProductId=%d,ProductName=%s, ProductCount=%d, ProductPrice=%f, ProductSupplier=%s]\n",u.getproductId(), u.getproductName(),u.getproductCount(),u.getproductPrice(),u.getproductSupplier());
				   }
				*/
				}
 
 
}
