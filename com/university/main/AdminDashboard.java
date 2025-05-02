package com.university.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.university.model.Admin;

public class AdminDashboard {

    private static Scanner sc = new Scanner(System.in);

    private static AdminStudentMenu stuMenu = new AdminStudentMenu();
    private static AdminFacultyMenu adfacMenu = new AdminFacultyMenu();
    private static AdminCourseSubjectMenu adCouMenu = new AdminCourseSubjectMenu();
    private static AdminAttendanceMenu adAttMenu = new AdminAttendanceMenu();
    
    public static void showAdminDashboard(Admin admin) throws ClassNotFoundException, SQLException, IOException {
        while (true) {
            System.out.println("\n======= Admin Dashboard =======");
            System.out.println("Logged in as: " + admin.getUsername());
            System.out.println("1. Student Management");
            System.out.println("2. Faculty Management");
            System.out.println("3. Course & Subject Management");
            System.out.println("4. Attendance Management");
           // System.out.println("5. Marks Management");
            
            System.out.println("5. Logout");

            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    stuMenu.showStudentMenu();
                    break;
                case "2":
                    adfacMenu.showFacultyMenu();
                    break;
                case "3":
                    adCouMenu.showCourseSubjectMenu();
                    break;
                case "4":
                    adAttMenu.showAttendanceMenu();
                    break;
//                case "5":
//                   // AdminMarksMenu.show();
//                    break;
//                case "6":
//                   // ChangePasswordService.changeAdminPassword(adminEmail);
//                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }
    
    	
    
    
}


