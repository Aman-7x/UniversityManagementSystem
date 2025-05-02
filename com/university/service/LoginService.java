package com.university.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.university.dao.StudentDAO;
import com.university.main.AdminDashboard;
import com.university.main.FacultyDashboard;
import com.university.main.StudentDashboard;
import com.university.model.Admin;
import com.university.model.Faculty;
import com.university.model.Student;
import com.university.util.ValidInput;
public class LoginService {

	Scanner sc = new Scanner(System.in);
	ValidInput is=new ValidInput();
	
	//Student Login
	 public boolean studentLogin() throws IOException, ClassNotFoundException, SQLException {
		 boolean loggedIn = false;
		 
		 while(!loggedIn) {
		 
		  System.out.println("===== Student Login =====");
	        
		 
	       // System.out.print("Enter Email: ");
	        String email =  is.getValidEmail();

	        String rawPassword =  ValidInput.hidepassword();
	        String encrypted =  ValidInput.encryptPassword(rawPassword);

	        //System.out.println("\nEncrypted Password: " + encrypted);
	        
	        
	        StudentService studentSer = new StudentService();
	        Student student = studentSer.studentLoginByEmailPass(email, encrypted);
	        
	        
	         
	        if (student != null) {
	        	  loggedIn = true;
	        	
	            System.out.println("Login Successful! Redirecting to Student Dashboard...");
	           
	            	String rollNo = student.getRollno();
	            	StudentDashboard.showDashboard(rollNo);

	             
	        } else {
	        	 System.out.println("Invalid Credentials! Try Again or Type 'B' to go back");
	                String retry = sc.nextLine().trim();

	                if (retry.equalsIgnoreCase("B")) {
	                    System.out.println("Going back to main menu...");
	                    // Add back functionality or return to main menu method
	                    break; // Or redirect back
	                }
	        }
	        
		 }
		 return loggedIn;
	    }
	 
	 
	 //Admin Login
	 
	 public boolean adminLogin() throws IOException, ClassNotFoundException, SQLException {
	        
		 boolean loggedIn = false;
		 
		 while(!loggedIn) {
		 
		  System.out.println("===== Admin Login =====");
	        
		 
	       // System.out.print("Enter Email: ");
	        String email =  is.getValidEmail();

	        String rawPassword =  ValidInput.hidepassword();
	        String encrypted =  ValidInput.encryptPassword(rawPassword);

	     //   System.out.println("\nEncrypted Password: " + encrypted);
	        
	        
	        adminService adSER = new adminService();
		       Admin  admin = adSER.adminlogin(email, encrypted); 
	         
	        if (admin != null) {
	           // System.out.println("Login Successful!");
	           
	            System.out.println("Login successful. Welcome, " + admin.getUsername() + "!");
	            loggedIn = true;
	            AdminDashboard.showAdminDashboard(admin);
	            // yahan student menu dikha sakte ho
	        } else {
	        	 System.out.println("Invalid Credentials! Try Again or Type 'B' to go back");
	                String retry = sc.nextLine().trim();

	                if (retry.equalsIgnoreCase("B")) {
	                    System.out.println("Going back to main menu...");
	                    // Add back functionality or return to main menu method
	                    break; // Or redirect back
	                }
	        }
	        
		 }
		 return loggedIn;
	 }
	 
	 
	 
	 //Faculty Login 
	 public boolean facultyLogin() throws IOException, ClassNotFoundException, SQLException {
	        
		 boolean loggedIn = false;
		 
		 while(!loggedIn) {
		 
		  System.out.println("===== Faculty Login =====");
	        
		 
	       // System.out.print("Enter Email: ");
	        String email =  is.getValidEmail();

	        String rawPassword =  ValidInput.hidepassword();
	        String encrypted =  ValidInput.encryptPassword(rawPassword);

	     //   System.out.println("\nEncrypted Password: " + encrypted);
	        
	        
	        FacultyService facSER = new FacultyService();
		       Faculty  faculty = facSER.facultyLogIn(email, encrypted); 
	         
	        if (faculty != null) {
	           // System.out.println("Login Successful!");
	           
	            System.out.println("Login successful. Welcome, " + faculty.getName() + "!");
	            loggedIn = true;
	            FacultyDashboard facDash = new FacultyDashboard();
	            String employeeId = faculty.getEmployeeId();
	             
	            facDash.showFacultyMenu(employeeId);
	             
	        } else {
	        	 System.out.println("Invalid Credentials! Try Again or Type 'B' to go back");
	                String retry = sc.nextLine().trim();

	                if (retry.equalsIgnoreCase("B")) {
	                    System.out.println("Going back to main menu...");
	                    // Add back functionality or return to main menu method
	                    break; // Or redirect back
	                }
	        }
	        
		 }
		 return loggedIn;
	 }
}
