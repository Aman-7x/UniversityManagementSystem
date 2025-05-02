package com.university.main;

import java.sql.SQLException;
import java.util.Scanner;
import com.university.service.StudentService;

public class StudentDashboard {
 
	 
	    static Scanner sc = new Scanner(System.in);
	    static StudentService stuSer = new StudentService();
	    
	    public static void showDashboard(String rollNumber) throws ClassNotFoundException, SQLException {
	        while (true) {
	            System.out.println("\n========== Student Dashboard ==========");
	            System.out.println("Welcome Dear");
	            System.out.println("1. View Profile");
	            System.out.println("2. Academic Details");
	            System.out.println("3. Update Contact Info");
	            System.out.println("4. Change Password");
	            System.out.println("5. Logout / Back to Main Menu");
	            System.out.print("Enter your choice: ");
	            String choice = sc.nextLine();

	            switch (choice) {
	                case "1":
	                    stuSer.viewStudentByRollNo(rollNumber);
	                    break;
	                case "2":
	                    showAcademicMenu(rollNumber);
	                    break;
	                case "3":
	                    stuSer.updateStudentContact();
	                    break;
	                case "4":
	                   // StudentService.changePassword(rollNumber); // implement later
	                    break;
	                case "5":
	                    System.out.println("Logging out...");
	                    return;
	                default:
	                    System.out.println("❌ Invalid choice. Try again.");
	            }
	        }
	    }

	    private static void showAcademicMenu(String rollNumber) throws ClassNotFoundException {
	        while (true) {
	            System.out.println("\n--- Academic Details ---");
	            System.out.println("1. View Attendance");
	            System.out.println("2. View Marks");
	            System.out.println("3. View Result / Percentage");
	            System.out.println("4. Back");
	            System.out.print("Enter your choice: ");
	            String choice = sc.nextLine();

	            switch (choice) {
	                case "1":
	                   stuSer.viewAttendanceByRollNo(rollNumber);
	                    break;
	                case "2":
	                    stuSer.displayMarksByRoll(rollNumber);
	                    break;
	                case "3":
	                    stuSer.displayResultByRoll(rollNumber);
	                    break;
	                case "4":
	                    return;
	                default:
	                    System.out.println("❌ Invalid choice. Try again.");
	            }
	        }
	    }
	
}
