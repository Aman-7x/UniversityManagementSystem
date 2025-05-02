package com.university.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.university.connection.Connect;

public class FacultyAttendanceDAO{

	
	//upload attendance
	 public void uploadAttendance(String empId, String status) throws ClassNotFoundException {
	        
	       
		 PreparedStatement  stmt =null;
	        try {
	            String query = "INSERT INTO faculty_attendance (employee_id, date, status) VALUES (?, ?, ?)";
	             stmt = Connect.getConnection().prepareStatement(query);

	            // Get Faculty ID using empId
	            String getFacultyIdQuery = "SELECT faculty_id FROM faculty WHERE employee_id = ?";
	            PreparedStatement pstmt = Connect.getConnection().prepareStatement(getFacultyIdQuery);
	            pstmt.setString(1, empId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                int facultyId = rs.getInt("faculty_id");

	                // Prepare and set parameters for inserting attendance
	                stmt.setString(1, empId);
	                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Today's date
	                stmt.setString(3, status.toUpperCase()); // 'PRESENT' or 'ABSENT'

	                int rowsAffected = stmt.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Attendance uploaded successfully!");
	                } else {
	                    System.out.println("Error uploading attendance. Please try again.");
	                }
	            } else {
	                System.out.println("Invalid Faculty Employee ID.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 
	 
	 //view attandance by emp id and date 
	//View Attendace via emp id and date
	  
	    // Method to view attendance for a faculty member by employee ID
	    public void viewAttendance(String employee_id, LocalDate date) throws ClassNotFoundException {
	       
	        PreparedStatement stmt = null;

	        try {
	            // Fetch the faculty ID based on the employee ID
//	            String getFacultyIdQuery = "SELECT faculty_id FROM faculty WHERE employee_id = ?";
//	            PreparedStatement pstmt = Connect.getConnection().prepareStatement(getFacultyIdQuery);
//	            pstmt.setString(1, empId);
//	            ResultSet rs = pstmt.executeQuery();
//
//	            if (rs.next()) {
//	                int facultyId = rs.getInt("faculty_id");

	                // Retrieve attendance records for the given date
	                String query = "SELECT * FROM faculty_attendance WHERE employee_id = ? AND date = ?";
	                stmt = Connect.getConnection().prepareStatement(query);
	                stmt.setString(1, employee_id);
	                stmt.setDate(2, java.sql.Date.valueOf(date));

	                ResultSet resultSet = stmt.executeQuery();
	                if (resultSet.next()) {
	                    String status = resultSet.getString("status");
	                    System.out.println("Attendance for Faculty ID " + employee_id + " on " + date + ": " + status);
	                } else {
	                    System.out.println("No attendance record found for the given date.");
	                }
//	            } else {
//	                System.out.println("Invalid Faculty Employee ID.");
//	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
