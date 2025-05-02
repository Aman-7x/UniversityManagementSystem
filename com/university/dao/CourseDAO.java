package com.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.university.connection.Connect;
import com.university.model.Branch;
import com.university.model.Course;

public class CourseDAO {

	

	//view Course
	public List<Course> viewAllCourses() throws ClassNotFoundException {
	    List<Course> list = new ArrayList<>();
	    try {
	        
	        PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT * FROM Course");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	Course c = new Course();
	          c.setCourseId(rs.getInt("course_id"));
	          c.setCourseName(rs.getString("course_name"));
	          c.setDuration(rs.getInt("duration"));
	            list.add(c);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	//get course id by roll no 
	// Get course_id of student by roll number
	public int getCourseIdByRollNumber(String rollNumber) throws SQLException, ClassNotFoundException {
	    int courseId = -1;
	    String sql = "SELECT b.course_id FROM student s JOIN branch b ON s.branch_id = b.branch_id WHERE s.roll_number = ?";
	    
	    	PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
	        ps.setString(1, rollNumber);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                courseId = rs.getInt("course_id");
	            }
	        }
	    
	    return courseId;
	}

	 
	//Add Course 
	public void addCourse(String courseName, int duration) {
	    String sql = "INSERT INTO course (course_name, duration) VALUES (?, ?)";

	    try {
	    	PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
	    	
	        ps.setString(1, courseName);
	        ps.setInt(2, duration);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("✅ Course added successfully!");
	        } else {
	            System.out.println("❌ Failed to add course.");
	        }

	    } catch (SQLIntegrityConstraintViolationException e) {
	        System.out.println("❗ Course already exists.");
	    } catch (SQLException | ClassNotFoundException e) {
	        System.out.println("Error adding course: " + e.getMessage());
	    }
	}

	

	//course //admin
	public  List<Course> getAllCourses() {
	    List<Course> list = new ArrayList<>();
	    String sql = "SELECT * FROM course";

	    try  {
	         PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
	         ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Course c = new Course();
	            c.setCourseId(rs.getInt("course_id"));
	            c.setCourseName(rs.getString("course_name"));
	            c.setDuration(rs.getInt("duration"));

	            list.add(c);
	        }

	    } catch (Exception e) {
	        e.getMessage();
	    }

	    return list;
	}
	

	
	//view Branch and course 
		public List<Branch> getAllBranchesWithCourseName() {
		    List<Branch> list = new ArrayList<>();

		    String sql = "SELECT b.branch_id, b.name AS branch_name, c.course_name " +
		                 "FROM branch as b JOIN course  as c ON b.course_id = c.course_id";

		    try  {
		         PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
		         ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            Branch b = new Branch();
		            b.setBranchId(rs.getInt("branch_id"));
		            b.setName(rs.getString("branch_name"));
		            b.setCourseName(rs.getString("course_name")); // custom field for display
		            list.add(b);
		        }

		    } catch (Exception e) {
		        e.getMessage();
		    }

		    return list;
		}
		
		
		
		
}
