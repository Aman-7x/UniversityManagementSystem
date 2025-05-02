package com.university.main;

import java.sql.SQLException;
import java.util.Scanner;

import com.university.service.FacultyAttendanceService;

public class AdminAttendanceMenu {

    static Scanner sc = new Scanner(System.in);
    static FacultyAttendanceService attSer = new FacultyAttendanceService();

    public static void showAttendanceMenu() throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n========= Attendance Management =========");
            System.out.println("1. Upload Faculty Attendance");
            System.out.println("2. View Attendance");
            System.out.println("0. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    attSer.uploadAttendanceForFaculty();
                    break;
                case "2":
                   attSer.viewAttendanceForFaculty();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input! Please enter a valid option.");
            }
        }
        }
    }