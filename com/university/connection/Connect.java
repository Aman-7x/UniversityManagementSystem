package com.university.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
			public static void main(String[] args) throws ClassNotFoundException {
//				Connection con = 
						getConnection();
//		        if (con != null) {
//		            System.out.println("✅ Connection Successful");
//		        } else {
//		            System.out.println("❌ Connection Failed");
//		        }
				}
		
		 
	private static final String URL = "jdbc:mysql://localhost:3306/universityms";
	 
	private static Connection con = null;
	
	public static Connection getConnection() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if(con==null || con.isClosed()) {
				con = DriverManager.getConnection(URL,"root","root");
			}
			
		}catch(SQLException e) {
			System.out.println("Connection Failed: " + e.getMessage());
		}
		
		return con;
	}
	
}
