package com.university.main;

 
import java.util.Scanner;
 

public class AdminMarksMenu {

    static Scanner sc = new Scanner(System.in);
    

    public static void showMarksMenu() {
        while (true) {
            System.out.println("\n========= Marks Management =========");
            System.out.println("1. View Marks by Branch, Course & Semester");
            System.out.println("2. View Marks of a Specific Student");
            System.out.println("0. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                  //  marksService.viewMarksByBranchCourseSemester();
                    break;
                case "2":
                   // marksService.viewMarksByRollNumber();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please enter a valid option.");
            }
        }
    }
}
