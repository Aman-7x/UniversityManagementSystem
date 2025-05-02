package com.university.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.university.connection.Connect;
import com.university.dao.adminDAO;
import com.university.model.Admin;
import com.university.model.Branch;
import com.university.model.Course;
import com.university.model.Faculty;
import com.university.util.ValidInput;

public class adminService {

	adminDAO adDAO;
	Scanner sc ;
	public adminService() {
		adDAO = new adminDAO();
		sc = new Scanner(System.in);
	}
	
	
	
	//login
	public Admin adminlogin(String email , String password) throws ClassNotFoundException {
		
		return adDAO.getAdminByEmailAndPassword(email, password);
	}
	
	
	
	
	
	
	//View All Courses
	
	
	//Add Faculty
	
    public void addFaculty() throws IOException {
         
        Faculty faculty = new Faculty();

        
         
    		String name;
    		while(true) {
    			System.out.print("Enter Faculty Name : ");
    			name = sc.nextLine();
    			if(Pattern.matches("^[a-zA-Z\\s]+$", name)) {
    				faculty.setName(name);
    			break;}
    			else
    				System.out.println("Invalid Name! Please enter a valid name (only letters and spaces).");			
    		}
    		 
    	
       
        

       // System.out.println("Enter Faculty Email:");
    		String email = ValidInput.getValidEmail();
        faculty.setEmail(email);

       // System.out.println("Enter Contact Number:");
	        String phone;
	        while (true) {
	            System.out.print("Enter Phone Number: ");
	            phone = sc.nextLine().trim();
	            if (phone.matches("\\d{10}")) {
	            	faculty.setContactNumber(phone);
	                break;
	            }
	            System.out.println("Invalid Phone Number! Enter a 10-digit number.");
	        }
	      
        

	        String gender = "";
	        while (true) {
	            System.out.println("Enter Gender (M/F/O):");
	            gender = sc.nextLine().trim().toUpperCase();

	            if (gender.equals("M") || gender.equals("F") || gender.equals("O")) {
	                faculty.setGender(gender);
	                break;
	            } else {
	                System.out.println("❌ Invalid input! Please enter only M, F, or O.");
	            }
	        }


       // System.out.println("Enter Employee ID:");
        faculty.setEmployeeId(ValidInput.employee_id());
        
        
        

       // System.out.println("Enter Password:");
        String plainPassword = ValidInput.hidepassword(); 
        String encrypted = ValidInput.encryptPassword(plainPassword);
        faculty.setPassword(encrypted);

        boolean success = adDAO.addFaculty(faculty);

        if (success) {
            System.out.println("✅ Faculty added successfully!");
        } else {
            System.out.println("❌ Failed to add faculty. Please try again.");
        }
    }


}
