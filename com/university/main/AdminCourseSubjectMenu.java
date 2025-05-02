package com.university.main;

import java.util.Scanner;

import com.university.dao.adminDAO;
import com.university.service.BranchService;
import com.university.service.CourseService;
//import com.university.service.CourseService;
import com.university.service.SubjectService;
import com.university.service.adminService;

public class AdminCourseSubjectMenu {

    static Scanner sc = new Scanner(System.in);
    static CourseService courseSer = new CourseService();
    static SubjectService subjectSer = new SubjectService();
    static adminService adSer = new adminService();
    static BranchService branchSer = new BranchService();
    public static void showCourseSubjectMenu() throws ClassNotFoundException {
        while (true) {
            System.out.println("\n========= Course & Subject Management =========");
            System.out.println("1. Branch Management");
            System.out.println("2. Course Management");
            System.out.println("3. Subject Management");
            System.out.println("0. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    branchMenu();
                    break;
                case "2":
                    courseMenu();
                    break;
                case "3":
                    subjectMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }
    }

    private static void branchMenu() throws ClassNotFoundException {
        while (true) {
            System.out.println("\n========= Branch Management =========");
            System.out.println("1. Add New Branch");
            System.out.println("2. View All Branches");
            System.out.println("3. View Branches with Course Name");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    branchSer.addBranch();
                    break;
                case "2":
                    branchSer.viewAllBranches();
                    break;
                case "3":
                    courseSer.viewAllBranchesWithCourse();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }
    }

    private static void courseMenu() {
        while (true) {
            System.out.println("\n========= Course Management =========");
            System.out.println("1. View All Courses");
            System.out.println("2. Add Courses");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    courseSer.viewAllCourses();
                    break;
                case "2":
                    courseSer.addCourse();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }
    }

    private static void subjectMenu() throws ClassNotFoundException {
        while (true) {
            System.out.println("\n========= Subject Management =========");
            System.out.println("1. Add New Subject");
            System.out.println("2. View Subjects by Course and Semester"); // to be implemented
            System.out.println("3. Assign Subject to Faculty"); // to be implemented
            System.out.println("4. View Subjects Assigned to Faculty"); // to be implemented
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    subjectSer.addSubject();
                    break;
                case "2":
                    subjectSer.viewSubjectsByCourseSemester(); // you’ll implement
                    break;
                case "3":
                    subjectSer.assignSubjectToFaculty(); // you’ll implement
                    break;
                case "4":
                    subjectSer.viewSubjectsAssignedToFaculty(); // you’ll implement
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }
    }
}
