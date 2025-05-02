package com.university.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.university.service.FacultyService;

public class FacultyDashboard {

    static Scanner sc = new Scanner(System.in);
    static FacultyService facSer = new FacultyService();

    public static void showFacultyMenu(String empId) throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n========== Faculty Dashboard ==========");
            System.out.println("1. View Profile");
            System.out.println("2. View Subject Assigned");
            System.out.println("3. View Students Assigned");
            System.out.println("4. Upload Attendance");
            System.out.println("5. View Attendance");
            System.out.println("6. Upload Marks");
            System.out.println("7. View Marks");
            System.out.println("8. View Top 10 Toppers");
            System.out.println("9. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    facSer.viewFacultyDetails(empId);
                    break;
                case "2":
                    facSer.printAssignedSubjects(empId);
                    break;
                case "3":
                   facSer.showStudentsUnderFaculty(empId);
                    break;
                case "4":
                    facSer.uploadAttendance(empId);
                    break;
                case "5":
                   facSer.viewAttendance(empId);
                    break;
                case "6":
                   facSer.uploadMarks(empId);
                    break;
                case "7":
                   facSer.viewMarks(empId);
                    break;
                case "8":
                	facSer.viewTop10Toppers(empId);
                	break;
                case "9":
                	updateProfileMenu(empId);
                  	break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Please enter a valid option.");
            }
        }
    }
    
    
    
    private static void updateProfileMenu(String empId) throws ClassNotFoundException {
        while (true) {
            System.out.println("\n----- Update Profile -----");
            System.out.println("1. Update Contact");
            System.out.println("2. Update Gmail");
            System.out.println("3. Update Gender");
            System.out.println("4. Change Password");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                  facSer.updateContact(empId);
                    break;
                case "2":
                  facSer.updateEmail(empId);
                    break;
                case "3":
                 facSer.updateGender(empId);
                    break;
                case "4":
                   facSer.updatePassword(empId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }
}
