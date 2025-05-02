package com.university.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.university.connection.Connect;

public class MarksheetDAO {
	
	 
	
	//Is Hosted By Admin or not
	 public boolean isMarksheetHosted() {
	        boolean hosted = false;
	        try {
	        	
	        	String sql = "SELECT host_marksheet FROM marksheet_hosted LIMIT 1";
	             PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
	             
	             ResultSet rs = pst.executeQuery();
	             
	            if (rs.next()) {
	                hosted = rs.getString("host_marksheet").equalsIgnoreCase("YES");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return hosted;
	    }
}
