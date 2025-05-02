package com.university.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.university.service.StudentService;
 

public class AdminStudentMenu {

    private StudentService studentService;
    private Scanner sc;

    public AdminStudentMenu() {
        studentService = new StudentService();
        sc = new Scanner(System.in);
    }

    public void showStudentMenu() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n====== Student Management ======");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. View Student");
            System.out.println("4. Back to Admin Dashboard");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    studentService.addStudent();
                    break;
                case 2:
                    updateStudentMenu();
                    break;
                case 3:
                    viewStudentMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private void updateStudentMenu() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n-- Update Student --");
            System.out.println("1. Update Contact");
            System.out.println("2. Update Gender");
            System.out.println("3. Update Branch");
            System.out.println("4. Update Semester");
            System.out.println("5. Update Email");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    studentService.updateStudentContact();
                    break;
                case 2:
                    studentService.updateStudentGender();
                    break;
                case 3:
                    studentService.updateStudentBranch();
                    break;
                case 4:
                    studentService.updateStudentSemester();
                    break;
                case 5:
                	studentService.updateStudentEmail();
                	break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewStudentMenu() throws ClassNotFoundException {
        while (true) {
            System.out.println("\n-- View Student --");
            System.out.println("1. By Roll Number");
            System.out.println("2. By Name");
            System.out.println("3. By Branch");
            System.out.println("4. By Semester");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    studentService.filterByRollNumber();
                    break;
                case 2:
                    studentService.filterByName();
                    break;
                case 3:
                    studentService.filterByBranch();
                    break;
                case 4:
                    studentService.filterBySemester();
                    break;
                 
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
