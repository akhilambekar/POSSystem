import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
public class PosSystem {
	public static String employeeDatabase = "database/employee.csv";
	private static Object out;
	
	public List<Employee> employees = new ArrayList<Employee>();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	String username="";
	String password="";
	String name="";
	int index=-1;
	Calendar calendar=null; 
	
	private void readFile() throws IOException{
		String line="";
		String[] lineResult;
		try{
			FileReader file=new FileReader(employeeDatabase);
			BufferedReader textReader = new BufferedReader(file);
			while((line=textReader.readLine())!=null){
				lineResult = line.split(",");
				String name=lineResult[2];
		        employees.add(new Employee(lineResult[0],name,lineResult[1],lineResult[3]));
			}
			textReader.close();
		}catch(FileNotFoundException e){
			//System.out.println("Unable to open the file employeeDatabase!");
			e.printStackTrace();
		}catch(IOException e) {
		    e.printStackTrace();
		}		
	}
	public int logIn(String usernameField,String passwordField){
		try {
			readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		username=usernameField;
		password=passwordField;
		boolean find=false;
		for(int i=0;i<employees.size();i++){
			if(username.equals(employees.get(i).getUsername())){
				find=true;
				index=i;
				break;
			}
		}
		if(find==true){
			if(password.equals(employees.get(index).getPassword())){
				calendar=Calendar.getInstance();
				name=(employees.get(index)).getName();
				if(((employees.get(index)).getDesignation()).equals("Cashier")){
			        return 2;
			    }else if(((employees.get(index)).getDesignation()).equals("Admin")){
			    	return 1;
			    }  
			}else{
				return 0;
			}
		}
		return 0;
	}
	public boolean checkTemp()
	  {
	   String temp = "Database/temp.txt";
	   File f=new File(temp);
	     if(f.exists() && !f.isDirectory())
	      return true;
	     return false;
	  }
}
