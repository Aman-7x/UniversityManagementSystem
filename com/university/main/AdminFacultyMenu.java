package com.university.main;




import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import com.university.service.FacultyService;
import com.university.service.adminService;
import com.university.util.ValidInput;

public class AdminFacultyMenu {

    static Scanner sc = new Scanner(System.in);
    static FacultyService facultyService = new FacultyService();
    static adminService aSer = new adminService();

    public static void showFacultyMenu() throws IOException, ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n========= Faculty Management Dashboard =========");
            System.out.println("1. Add Faculty");
            System.out.println("2. View Faculty");
            System.out.println("3. Update Faculty Details");
            System.out.println("4. Delete Faculty");
            System.out.println("5. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    aSer.addFaculty();
                    break;

                case "2":
                    viewFacultySubMenu();
                    break;

                case "3":
                    updateFacultySubMenu();
                    break;

                case "4":
                    facultyService.deleteFacultyByEmployeeId();
                    break;

                case "5":
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Faculty Submenu
    private static void viewFacultySubMenu() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n----- View Faculty Options -----");
            System.out.println("1. View All Faculty");
            System.out.println("2. View Faculty by Employee ID");
            System.out.println("3. View Faculty by Branch and Course");
            System.out.println("4. View Assigned Subjects by Employee ID");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    facultyService.viewAllFaculties();
                    break;
                case "2":
                    facultyService.viewFacultyByEmployeeId();
                    break;
                case "3":
                    facultyService.viewFacultyByBranchAndCourse();
                    break;
                case "4":
                	String empId;
                	while(true) {
                    System.out.print("Enter Faculty ID: ");
                     empId = sc.nextLine().trim();
                    if(ValidInput.isValidFacNo(empId)) {
                    	break;
                     }else {
                    	 System.out.println("Enter Valid Faculty Id");
                     }
                	}
                    facultyService.printAssignedSubjects(empId);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Update Faculty Submenu
    private static void updateFacultySubMenu() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n----- Update Faculty Details -----");
            System.out.println("1. Update Email");
            System.out.println("2. Update Contact");
            System.out.println("3. Update Gender");
            System.out.println("4. Update Password");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                	String empId;
                	while(true) {
                    System.out.print("Enter Faculty ID: ");
                     empId = sc.nextLine().trim();
                    if(ValidInput.isValidFacNo(empId)) {
                    	break;
                     }else {
                    	 System.out.println("Enter Valid Faculty Id");
                     }
                	}
                    facultyService.updateEmail(empId);
                    break;
                case "2":
                	String empId1;
                	while(true) {
                    System.out.print("Enter Faculty ID: ");
                     empId1 = sc.nextLine().trim();
                    if(ValidInput.isValidFacNo(empId1)) {
                    	break;
                     }else {
                    	 System.out.println("Enter Valid Faculty Id");
                     }
                	}
                    facultyService.updateContact(empId1);
                    break;
                case "3":
                	String empId2;
                	while(true) {
                    System.out.print("Enter Faculty ID: ");
                     empId2 = sc.nextLine().trim();
                    if(ValidInput.isValidFacNo(empId2)) {
                    	break;
                     }else {
                    	 System.out.println("Enter Valid Faculty Id");
                     }
                	}
                    facultyService.updateGender(empId2);
                    break;
                case "4":
                	String empId3;
                	while(true) {
                    System.out.print("Enter Faculty ID: ");
                     empId3 = sc.nextLine().trim();
                    if(ValidInput.isValidFacNo(empId3)) {
                    	break;
                     }else {
                    	 System.out.println("Enter Valid Faculty Id");
                     }
                	}
                    facultyService.updatePassword(empId3);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
