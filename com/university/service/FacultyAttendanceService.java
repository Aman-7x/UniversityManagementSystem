package com.university.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.university.connection.Connect;
import com.university.dao.FacultyAttendanceDAO;
import com.university.util.ValidInput;

public class FacultyAttendanceService {

	Scanner sc;
	FacultyAttendanceDAO facAttDAO;
	FacultyService facSer;
	public FacultyAttendanceService() {
		sc = new Scanner(System.in);
		facAttDAO = new FacultyAttendanceDAO();
		facSer = new FacultyService();
	}
	
	 public void uploadAttendanceForFaculty() throws ClassNotFoundException, SQLException {
	        

	        // Prompt for Employee ID and Attendance Status
		// 1. Get valid Employee ID
		 String empId;
		 while (true) {
		     System.out.print("Enter Faculty Employee ID: ");
		     empId = sc.nextLine().trim();

		     if (ValidInput.isValidFacNo(empId) && !empId.isEmpty()) {
		         break;
		     } else {
		         System.out.println("Faculty ID is Invalid. Please enter again.");
		     }
		 }

		 // 2. Get valid Attendance Status
		 String status;
		 while (true) {
		     System.out.print("Enter Attendance Status (PRESENT/ABSENT/HALF-DAY/LEAVE): ");
		     status = sc.nextLine().trim().toUpperCase();

		     if (status.equals("PRESENT") || status.equals("ABSENT") || status.equals("HALF-DAY") || status.equals("LEAVE")) {
		         break;
		     } else {
		         System.out.println("Invalid status! Please enter only 'PRESENT' / 'ABSENT' / 'Half-Day'.");
		     }
		 }


	               

	        // Call the service method to upload attendance
	          facAttDAO.uploadAttendance(empId, status);
	    }
	 
	 
	 //view attandace via empid and date
	   public void viewAttendanceForFaculty() throws ClassNotFoundException {
	         
	        // Prompt for Employee ID and Date
		   String empId;
			 while (true) {
			     System.out.print("Enter Faculty Employee ID: ");
			     empId = sc.nextLine().trim();

			     if (!empId.isEmpty()) {
			         break;
			     } else {
			         System.out.println("Employee ID cannot be empty. Please enter again.");
			     }
			 }

	        
	        LocalDate date  =  facSer.getValidDateFromUser();



	        // Call the service method to view attendance
	         
	        facAttDAO.viewAttendance(empId, date);
	    }
}
