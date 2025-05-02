package com.university.service;

import java.util.List;
import java.util.Scanner;

import com.university.dao.BranchDAO;
import com.university.dao.CourseDAO;
import com.university.dao.FacultyDAO;
import com.university.dao.SubjectDAO;
import com.university.dao.adminDAO;
import com.university.model.Branch;
import com.university.model.Course;
import com.university.model.Faculty;
import com.university.model.Subject;

public class SubjectService {

	SubjectDAO subDAO;
	Scanner sc;
	BranchDAO branchDAO;
	FacultyDAO facDAO;
	CourseDAO courseDAO;
	public SubjectService(){
		subDAO = new SubjectDAO();
		sc = new Scanner(System.in);
		branchDAO = new BranchDAO();
		facDAO = new FacultyDAO();
		courseDAO = new CourseDAO();
	}
	
	
	
	//add sub
	public void addSubject() throws ClassNotFoundException {
        // Step 1: Fetch branch list from DB
        List<Branch> branchList = branchDAO.getAllBranches();

        if (branchList.isEmpty()) {
            System.out.println("❌ No branches found. Please add branches first.");
            return;
        }

        System.out.println("\n=== Available Branches ===");
        for (int i = 0; i < branchList.size(); i++) {
            Branch b = branchList.get(i);
            System.out.printf("%d. %s (Course ID: %d)\n", i + 1, b.getName(), b.getCourseId());
        }

        int branchChoice = -1;
        while (true) {
            try {
                System.out.print("Choose branch number to assign subject: ");
                branchChoice = Integer.parseInt(sc.nextLine());
                if (branchChoice >= 1 && branchChoice <= branchList.size()) break;
                else System.out.println("Please select a valid option.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        int branchId = branchList.get(branchChoice - 1).getBranchId();
        System.out.println("Branch I.D : ->>> "+branchId);
        // Step 2: Enter subject details
        String name = "";
        while (true) {
            System.out.print("Enter Subject Name: ");
            name = sc.nextLine().trim();
            if (!name.isEmpty()) break;
            else System.out.println("Subject name cannot be empty.");
        }

        int semester = -1;
        while (true) {
            try {
                System.out.print("Enter Semester: ");
                semester = Integer.parseInt(sc.nextLine());
                if (semester > 0 && semester <= 12) break;
                else System.out.println("Enter valid semester between 1 and 12.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        String type = "";
        while (true) {
            System.out.print("Enter Subject Type (CORE / DEPARTMENT_ELECTIVE / OPEN_ELECTIVE): ");
            type = sc.nextLine().trim().toUpperCase();
            if (type.equals("CORE") || type.equals("DEPARTMENT_ELECTIVE") || type.equals("OPEN_ELECTIVE")) {
                break;
            } else {
                System.out.println("Invalid subject type. Please enter one of: CORE, DEPARTMENT_ELECTIVE, OPEN_ELECTIVE.");
            }
        }

        // Step 3: Save subject
        Subject subject = new Subject();
        subject.setBranch_id(branchId);
        subject.setSubject_name(name);
        subject.setSemester(semester);
        subject.setSubjectType(type);
        boolean added = subDAO.addSubject(subject);
        if (added) {
            System.out.println("✅ Subject added successfully!");
        } else {
            System.out.println("❌ Failed to add subject.");
        }
    }
	
	
	//Assign Subject to faculty
	public void assignSubjectToFaculty() {
	    Scanner sc = new Scanner(System.in);

	    // Show all faculty
	    List<Faculty> facultyList = facDAO.getAllFaculties();
	    System.out.println(" Available Faculty:");
	    for (int i = 0; i < facultyList.size(); i++) {
	        Faculty f = facultyList.get(i);
	        System.out.println((i + 1) + ". " + f.getName() + " (Employee ID: " + f.getEmployeeId() + ")");
	    }

	    System.out.print("👉 Choose Faculty (number): ");
	    int fIndex = Integer.parseInt(sc.nextLine());
	    if (fIndex < 1 || fIndex > facultyList.size()) {
	        System.out.println("❌ Invalid choice.");
	        return;
	    }
	    String selectedEmpId = facultyList.get(fIndex - 1).getEmployeeId();

	    // Show all subjects
	    List<Subject> subjectList = subDAO.getAllSubjects();
	    System.out.println("\n Available Subjects:");
	    for (int i = 0; i < subjectList.size(); i++) {
	        Subject s = subjectList.get(i);
	        System.out.println((i + 1) + ". " + s.getName() + " | Sem: " + s.getSemester() + " | Type: " + s.getSubjectType());
	    }

	    System.out.print("👉 Choose Subject (number): ");
	    int sIndex = Integer.parseInt(sc.nextLine());
	    if (sIndex < 1 || sIndex > subjectList.size()) {
	        System.out.println("❌ Invalid choice.");
	        return;
	    }
	    int selectedSubjectId = subjectList.get(sIndex - 1).getSubject_id();

	    // Assign the selected subject to selected faculty
	    subDAO.assignSubjectToFaculty(selectedEmpId, selectedSubjectId);
	}

	
	
	//view subject by course and sem
	public void viewSubjectsByCourseSemester() {
	    Scanner sc = new Scanner(System.in);

	    // Step 1: Get all available courses
	    List<Course> courseList = courseDAO.getAllCourses();

	    if (courseList.isEmpty()) {
	        System.out.println("❌ No courses available.");
	        return;
	    }

	    System.out.println("📚 Available Courses:");
	    for (int i = 0; i < courseList.size(); i++) {
	        System.out.println((i + 1) + ". " + courseList.get(i).getCourseName());
	    }
	    System.out.print("Please select a course (1 - " + courseList.size() + "): ");
	    int courseChoice = sc.nextInt();
	    Course selectedCourse = courseList.get(courseChoice - 1);
	    int duration = selectedCourse.getDuration();
	    // Step 2: Get all available semesters for the selected course along with their duration
	   // List<Course> courseDurationList = courseDAO.getCourseDurationByCourseId(selectedCourse.getCourseId());

	    System.out.println("📅 Available Semesters and Duration for " + selectedCourse.getCourseName() + ":");
//	    for (Course cd : courseDurationList) {
//	        System.out.println("Semester " + cd.getSemester() + ": " + cd.getDuration() + " months");
//	    }
	    for(int i=0; i<duration;i++) {
	    	  System.out.println("Semester " + (i+1));
	    }

	    System.out.println("Please select a semester (1 - " + duration + "): ");
	    int semesterChoice = sc.nextInt();

	    // Step 3: Fetch subjects based on selected course and semester
	    List<Subject> subjectList = subDAO.viewSubjectsByCourseSemester(selectedCourse.getCourseId(), semesterChoice);

	    if (subjectList.isEmpty()) {
	        System.out.println("❌ No subjects found for the selected course and semester.");
	    } else {
	        System.out.println("📚 Subjects for Course: " + selectedCourse.getCourseName() + " and Semester " + semesterChoice + ":");
	        int index = 1;
	        for (Subject s : subjectList) {
	            System.out.println(index++ + ". " + s.getName() + " | Branch: " + s.getBranchName() +
	                               " | Type: " + s.getSubjectType() + " | Semester: " + s.getSemester());
	        }
	    }
	}
	
	
	//View Subject Assign to which faculty
	public void viewSubjectsAssignedToFaculty() {
	    List<Faculty> assignedList = subDAO.getAllFacultySubjectAssignments();

	    if (assignedList.isEmpty()) {
	        System.out.println("❌ No subjects assigned to any faculty.");
	        return;
	    }

	    System.out.println("📋 Subjects Assigned to Faculty:\n");

	    int count = 1;
	    for (Faculty dto : assignedList) {
	        System.out.println(count++ + ". Faculty: " + dto.getName() + " (" + dto.getEmployeeId() + ")");
	        System.out.println("   ➤ Subject: " + dto.getSubjectName());
	        System.out.println("   ➤ Course: " + dto.getCourseName());
	        System.out.println("   ➤ Semester: " + dto.getSemester());
	        System.out.println("   ➤ Branch: " + dto.getBranchName());
	        System.out.println("   ➤ Type: " + dto.getSubjectType());
	        System.out.println();
	    }
	}


	}

	

