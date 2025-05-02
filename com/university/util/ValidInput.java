package com.university.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.university.connection.Connect;

public class ValidInput {

	
	//EmailVerification
	 public static String getValidEmail() {
		 Scanner sc=new Scanner(System.in);
		    while(true) {
		    	System.out.print("Enter Email : ");
		    	 
				String email=sc.nextLine();
				 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	        
	        // Create Pattern object
	        Pattern pattern = Pattern.compile(emailRegex);
	        
	        // Create matcher object
	        Matcher matcher = pattern.matcher(email);
	        
	        // Return true if the email matches the regex, otherwise false
	        if(matcher.matches())  
	        	return email;
	        else
	        	System.out.println("Invalid Email! Please enter a valid Email (only Standard Formate).");
		    }
	    }
	 
	 
	 //Encript password
	 public static String encryptPassword(String password) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hash = md.digest(password.getBytes("UTF-8"));

	            StringBuilder hexString = new StringBuilder();
	            for (byte b : hash) {
	                hexString.append(String.format("%02x", b));
	            }

	            return hexString.toString();

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 
	 
	 
	 //password reader
	 

	 public static String hidepassword() throws IOException  {
			 
			 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		        

		        // Start masking thread
		        final boolean[] stop = {false};
		        Thread maskThread = new Thread(() -> {
		            while (!stop[0]) {
		                System.out.print("\b*");
		                try {
		                    Thread.sleep(2);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }
		        });

		        System.out.print("Enter Password: ");
		        maskThread.start();
		        String password = reader.readLine();
		        stop[0] = true;
				return password;

	    }

	 
	 //Faculty Id;
	 public static String employee_id() {
	     Set<String> set=new HashSet<>();
	 Random ran = new Random();

		String empNo;
		   do {
	           int roll = 10000 + ran.nextInt(90000); // Generate a 5-digit number (10000-99999)
	           empNo = "FAC" + String.format("%05d", roll); // Ensure 5-digit formatting
	       } while (set.contains(empNo)); // Keep generating until unique

	       set.add(empNo); // Add the unique roll number to the set
	       
	       return empNo;
 } 
	 
	 
	 //Student Id;
	 public static String generateRollNumber(String branchCode) {
		    Set<String> generatedRollNumbers = new HashSet<>();
		    Random random = new Random();

		    String rollNo;
		    do {
		        // Get current year (last two digits)
		        int year = LocalDate.now().getYear() % 100;

		        // Generate 3-digit random number
		        int rand = 100 + random.nextInt(900); // 100 to 999

		        rollNo = "0704" + branchCode.toUpperCase() + year + rand;

		    } while (generatedRollNumbers.contains(rollNo));

		    generatedRollNumbers.add(rollNo);
		    return rollNo;
		}

	 
	//Method To Check Existing Roll No.
			public static boolean isValidRollNo(String roll) throws SQLException, ClassNotFoundException {
				String sql="Select * from  student where roll_number = ?";
		
				PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
				
				pst.setString(1, roll);
				
				ResultSet rs = pst.executeQuery();
				String avail="";
				boolean status=false;
				while(rs.next()) {
					avail=rs.getString("roll_number");
				}
				if(!avail.isEmpty())
					status = true;
				
				return status;
			}
			
			
			
			//Method To Check Existing Faculty No.
			public static boolean isValidFacNo(String facNo) throws SQLException, ClassNotFoundException {
				String sql="Select * from  faculty where employee_id = ?";
		
				PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
				
				pst.setString(1, facNo);
				
				ResultSet rs = pst.executeQuery();
				String avail="";
				boolean status=false;
				while(rs.next()) {
					avail=rs.getString("employee_id");
				}
				if(!avail.isEmpty())
					status = true;
				
				return status;
			}
	 
}
