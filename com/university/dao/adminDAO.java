package com.university.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.connection.Connect;
import com.university.model.Admin;
import com.university.model.Branch;
import com.university.model.Course;
import com.university.model.Faculty;

public class adminDAO {


	public Admin getAdminByEmailAndPassword(String email, String password) throws ClassNotFoundException {
        Admin admin = null;

        try {
            String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);  // If encrypted, hash it first

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password")); // optional
                admin.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
            }

        } catch (SQLException e) {
            System.out.println("Invalid Admin Email or Password");
        }

        return admin;
    }

	
	
	
	
	
	
	
	
	
	
	
	//Add faculty
	 public boolean addFaculty(Faculty faculty) {
	        boolean success = false;

	        try {
	            String sql = "INSERT INTO faculty (name, email, contact_number, gender, password, employee_id) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
	            ps.setString(1, faculty.getName());
	            ps.setString(2, faculty.getEmail());
	            ps.setString(3, faculty.getContactNumber());
	            ps.setString(4, faculty.getGender());
	            ps.setString(5, faculty.getPassword()); // Password should be encrypted before calling this
	            ps.setString(6, faculty.getEmployeeId());

	            int rows = ps.executeUpdate();
	            if (rows > 0) {
	                success = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return success;
	    }
}
