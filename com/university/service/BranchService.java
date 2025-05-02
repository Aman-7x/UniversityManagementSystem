package com.university.service;

import java.util.List;
import java.util.Scanner;

import com.university.dao.BranchDAO;
import com.university.dao.CourseDAO;
import com.university.model.Branch;
import com.university.model.Course;

public class BranchService {

	BranchDAO branchDAO;
	CourseDAO courseDAO;
	Scanner sc;
	public BranchService() {
		this.branchDAO = new BranchDAO();
		this.courseDAO = new CourseDAO();
		this.sc= new Scanner(System.in);
		
	}
	
	
	

	
	//View All Branches 
	public void viewAllBranches() throws ClassNotFoundException {
		List<Branch> list = branchDAO.getAllBranches();
		if (list.isEmpty()) {
	        System.out.println("No branches found.");
	        return;
	    }

	    System.out.println("\n=== List of Branches ===");
	    int i = 1;
	    for (Branch b : list) {
	        System.out.printf("%d. Branch: %s\n", i++, b.getName());
	    }
	}
	
	
	
	//add branch
		public void addBranch() {
		    

		    // 1. Show available courses
		    List<Course> courseList = null;
			try {
				courseList = courseDAO.getAllCourses();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    if (courseList.isEmpty()) {
		        System.out.println("No courses found. Please add courses first.");
		        return;
		    }

		    System.out.println("\n=== Available Courses ===");
		    for (int i = 0; i < courseList.size(); i++) {
		        Course c = courseList.get(i);
		        System.out.printf("%d. %s (%d semesters)\n", i + 1, c.getCourseName(), c.getDuration());
		    }

		    // 2. Handle course selection input
		    int choice = -1;
		    while (true) {
		        System.out.print("Choose course number to add branch under: ");
		        try {
		            choice = Integer.parseInt(sc.nextLine());
		            if (choice >= 1 && choice <= courseList.size()) {
		                break;
		            } else {
		                System.out.println("Please enter a valid course number from the list.");
		            }
		        } catch (NumberFormatException e) {
		            System.out.println("Please enter a valid number.");
		        }
		    }

		    Course selectedCourse = courseList.get(choice - 1);

		    // 3. Enter branch name with empty check
		    String branchName = "";
		    while (true) {
		        System.out.print("Enter branch name: ");
		        branchName = sc.nextLine().trim();
		        if (!branchName.isEmpty()) {
		            break;
		        } else {
		            System.out.println("Branch name cannot be empty.");
		        }
		    }

		    // 4. Save to DB
		    boolean result = branchDAO.addBranch(selectedCourse.getCourseId(), branchName);

		    // 5. Final message
		    if (result) {
		        System.out.println("✅ Branch added successfully.");
		    } else {
		        System.out.println("❌ Failed to add branch.");
		    }
		}
		
}
