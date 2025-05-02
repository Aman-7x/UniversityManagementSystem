package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionCheck {
    public static void main(String[] args) {

        // Step 1: Database credentials
        String url = "jdbc:mysql://localhost:3306/universityms"; // Change your DB name
        String username = "root"; // Your MySQL username
        String password = "root"; // Your MySQL password

        // Step 2: Load MySQL JDBC driver (optional for newer versions)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8 and above
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Step 3: Create connection
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to the database successfully!");
            con.close();
        } catch (SQLException e) {
            System.out.println("❌ Connection Failed!");
            e.printStackTrace();
        }
    }
}
