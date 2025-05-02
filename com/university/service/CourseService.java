package com.university.service;

import java.util.List;
import java.util.Scanner;

import com.university.dao.CourseDAO;
import com.university.model.Branch;
import com.university.model.Course;

public class CourseService {

	Scanner sc;
	CourseDAO cDAO;
	public CourseService() {
		sc =new  Scanner(System.in);
		cDAO=new CourseDAO();
	}
	
	public void addCourse() {
        String courseName = "";
        int duration = -1;

        // Course Name Input + Validation
        while (true) {
            System.out.print("Enter Course Name: ");
            courseName = sc.nextLine().trim();

            if (courseName.isEmpty()) {
                System.out.println("❌ Course name cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        // Duration Input + Validation
        while (true) {
            System.out.print("Enter Duration (in Semester): ");
            String durationInput = sc.nextLine().trim();

            try {
                duration = Integer.parseInt(durationInput);
                if (duration <= 0) {
                    System.out.println("❌ Duration must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number format. Please enter a valid number.");
            }
        }

        // Call DAO to insert course
        cDAO.addCourse(courseName, duration);
    }
	
	
	
	//View All Courses
		public void viewAllCourses() {
		    List<Course> list = cDAO.getAllCourses();
		    
		    if (list.isEmpty()) {
		        System.out.println("No courses available.");
		    } else {
		        System.out.println("--------- All Available Courses ---------");
		        for (Course course : list) {
		            System.out.println("Course Name : " + course.getCourseName());
		            System.out.println("Duration    : " + course.getDuration() + " Semesters");
		            System.out.println("----------------------------------------");
		        }
		    }
		}

		//View All branch With Course
		public void viewAllBranchesWithCourse() {
		    List<Branch> list = cDAO.getAllBranchesWithCourseName();

		    if (list.isEmpty()) {
		        System.out.println("No branches found.");
		        return;
		    }

		    System.out.println("\n=== List of Branches ===");
		    int i = 1;
		    for (Branch b : list) {
		        System.out.printf("%d. Branch: %s, Course: %s\n", i++, b.getName(), b.getCourseName());
		    }
		}
		
		
		
}
