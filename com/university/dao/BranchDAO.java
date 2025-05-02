package com.university.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.connection.Connect;
import com.university.model.Branch;

public class BranchDAO {

	

	//view Branches
//	public List<Branch> getAllBranches() throws ClassNotFoundException {
//	    List<Branch> list = new ArrayList<>();
//	    try {
//	        
//	        PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT * FROM branch");
//	        ResultSet rs = ps.executeQuery();
//	        while (rs.next()) {
//	            Branch b = new Branch();
//	            b.setBranchId(rs.getInt("branch_id"));
//	            b.setName(rs.getString("branch_name"));
//	          //  b.setCourseId(rs.getInt("course_id"));
//	            list.add(b);
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return list;
//	}
	
	public List<Branch> getAllBranchesByCourseId(int course_id) throws ClassNotFoundException {
	    List<Branch> list = new ArrayList<>();
	    try {
	        
	        
	    	PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT * FROM branch where course_id = ?");
	    	ps.setInt(1, course_id);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Branch b = new Branch();
	            b.setBranchId(rs.getInt("branch_id"));
	            b.setName(rs.getString("name"));
	          //  b.setCourseId(rs.getInt("course_id"));
	            list.add(b);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	
	public List<Branch> getAllBranchesWithCourses() throws ClassNotFoundException {
	    List<Branch> list = new ArrayList<>();
	    String query = "SELECT b.branch_id, b.name AS branch_name, c.course_name " +
	                   "FROM branch as b JOIN course as c ON b.course_id = c.course_id";

	    try {
	         PreparedStatement ps = Connect.getConnection().prepareStatement(query);
	         ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	  Branch b = new Branch();
		            b.setBranchId(rs.getInt("branch_id"));
		            b.setName(rs.getString("branch_name"));
	                b.setCourseName(rs.getString("course_name"));

	           
	            list.add(b);
	        }

	    } catch (SQLException e) {
	       e.getMessage();
	    }

	    return list;
	}

	
	

	//view Branches
	public List<Branch> getAllBranches() throws ClassNotFoundException {
	    List<Branch> list = new ArrayList<>();
	    try {
	        
	        PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT * FROM branch");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Branch b = new Branch();
	            b.setBranchId(rs.getInt("branch_id"));
	            b.setName(rs.getString("name"));
	            b.setCourseId(rs.getInt("course_id"));
	            list.add(b);
	        }
	    } catch (SQLException e) {
	        e.getMessage();
	    }
	    return list;
	}
	

	//add branch
	public boolean addBranch(int courseId, String branchName) {
	    String sql = "INSERT INTO branch (course_id, name) VALUES (?, ?)";

	    try {
	         PreparedStatement ps = Connect.getConnection().prepareStatement(sql);

	        ps.setInt(1, courseId);
	        ps.setString(2, branchName);

	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
