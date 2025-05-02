package com.university.main;
 
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.university.service.LoginService;
import com.university.service.StudentService;
import com.university.util.*;
public class MainApp {
	  
	    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
	    	   LoginService loginService = new LoginService();
	    	   StudentService stuSer = new StudentService();
	        //   loginService.studentLogin();  // abhi sirf student login test kar rahe
	           
	        //   stuSer.displayMarksByRoll("452020");
	    	   
	    	 //  stuSer.displayResultByRoll("452020");

	    	// stuSer.viewStudentByRollNo("452020");
	    	   
	    	 //  stuSer.viewAttendanceByRollNo("452020");
	    	   
	    	   
	    	// loginService.adminLogin();
	    	   
	    	   
	           Scanner sc = new Scanner(System.in);
	           boolean run = true;

	           while (run) {
	               System.out.println("\n====== UNIVERSITY MANAGEMENT SYSTEM ======");
	               System.out.println("1. Student Login");
	               System.out.println("2. Faculty Login");
	               System.out.println("3. Admin Login");
	               System.out.println("4. Exit");
	               System.out.print("Enter your choice: ");

	               String choice = sc.nextLine();

	               switch (choice) {
	                   case "1":
	                	   loginService.studentLogin();
	                       break;
	                   case "2":
	                      loginService.facultyLogin();
	                       break;
	                   case "3":
	                        loginService.adminLogin();
	                       break;
	                   case "4":
	                       System.out.println("Exiting... Thank you!");
	                       run = false;
	                       break;
	                   default:
	                       System.out.println("Invalid choice. Please try again.");
	               }
	           }

	           sc.close();
	       }
  
	    }
	


