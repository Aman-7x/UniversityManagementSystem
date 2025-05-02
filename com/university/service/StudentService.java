package com.university.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.university.dao.BranchDAO;
import com.university.dao.CourseDAO;
import com.university.dao.StudentDAO;
import com.university.model.Branch;
import com.university.model.Course;
import com.university.model.Marks;
import com.university.model.Student;
import com.university.util.ValidInput;

public class StudentService {

	StudentDAO stuDAO;
	Scanner sc;
	CourseDAO course;
	BranchDAO branch;
	public StudentService() {
		 this.stuDAO = new StudentDAO();
		 this.sc = new Scanner(System.in);
		 this.course = new CourseDAO();
		 this.branch = new BranchDAO();
	}
	
	
	public Student studentLoginByEmailPass(String email , String password) throws ClassNotFoundException {
		return stuDAO.getStudentByEmailAndPassword(email, password);
	}
	
	
	
	
	
	public void viewStudentByRollNo(String rollno) throws ClassNotFoundException {
		stuDAO.getStudentByRoll(rollno);
	}
	
	
	public void viewAttendanceByRollNo(String rollNo) throws ClassNotFoundException {
		stuDAO.getAttendanceByRollNo(rollNo);
	}
	
	  public void displayMarksByRoll(String rollNumber) {
	       List<Marks> marks = stuDAO.getMarksByRollNumber(rollNumber);
	    //    List<Marks> marks = stuDAO.getMarksByStudentRollNo(rollNumber);

	        if (marks.isEmpty()) {
	            System.out.println("No marks found for Roll Number: " + rollNumber);
	            return;
	        }

	        System.out.println("\n--- Student Marks ---");
	        for ( Marks m : marks) {
	            System.out.println("Subject: " + m.getSubjectName());
	            System.out.println("Exam Type: " + m.getExamType());
	            System.out.println("Marks: " + m.getMarksObtained() + "/" + m.getMaxMarks());
	            System.out.println("Status: " + m.getResultStatus());
	            System.out.println("Remarks: " + (m.getRemarks() == null ? "-" : m.getRemarks()));
	            System.out.println("----------------------");
	        }
	    }
	
	  public void displayResultByRoll(String rollNumber) {
	        List<Marks> resultList = stuDAO.getResultByRollNumber(rollNumber);

	        if (resultList.isEmpty()) {
	            System.out.println("No result found for Roll Number: " + rollNumber);
	            return;
	        }

	        System.out.println("\n================== STUDENT RESULT ==================");
	        System.out.printf("%-20s %-10s %-10s %-10s %-10s\n", "Subject", "MID", "INTERNAL", "END", "PRACTICAL");
	        System.out.println("------------------------------------------------------");

	        String currentSubject = "";
	        int mid = 0, internal = 0, end = 0, practical = 0;
	        for (Marks m : resultList) {
	            if (!m.getSubjectName().equals(currentSubject)) {
	                if (!currentSubject.isEmpty()) {
	                    System.out.printf("%-20s %-10d %-10d %-10d %-10d\n", currentSubject, mid, internal, end, practical);
	                }
	                currentSubject = m.getSubjectName();
	                mid = internal = end = practical = -1;
	            }
	            switch (m.getExamType()) {
	                case "MID": mid = m.getMarksObtained(); break;
	                case "INTERNAL": internal = m.getMarksObtained(); break;
	                case "END": end = m.getMarksObtained(); break;
	                case "PRACTICAL": practical = m.getMarksObtained(); break;
	            }
	        }
	        System.out.printf("%-20s %-10d %-10d %-10d %-10d\n", currentSubject, mid, internal, end, practical);
	        System.out.println("=====================================================");
	        System.out.println("\n\n\n\nNote : In case if you got -1 in any Subject ,\n\tPlease Contact to the Faculty");
	    }
	  
	  public void addStudent() {
		    try {
		        Student student = new Student();

		        // ==== Name ====
		        String stuName;
		        while (true) {
		            System.out.print("Enter Student Name: ");
		            stuName = sc.nextLine().trim();
		            if (Pattern.matches("^[a-zA-Z\\s]+$", stuName)) {
		                student.setName(stuName);
		                break;
		            } else {
		                System.out.println("❌ Invalid name! Please use only letters and spaces.");
		            }
		        }

		        // ==== Email ====
		        student.setEmail(ValidInput.getValidEmail());

		        // ==== Contact ====
		        String phone;
		        while (true) {
		            System.out.print("Enter Phone Number: ");
		            phone = sc.nextLine().trim();
		            if (phone.matches("\\d{10}")) {
		                student.setContactNumber(phone);
		                break;
		            } else {
		                System.out.println("❌ Invalid phone number! Enter a 10-digit number.");
		            }
		        }

		        // ==== Gender ====
		        while (true) {
		            System.out.print("Enter Gender (M/F/O): ");
		            String gender = sc.nextLine().trim().toUpperCase();
		            if (gender.equals("M") || gender.equals("F") || gender.equals("O")) {
		                student.setGender(gender);
		                break;
		            } else {
		                System.out.println("❌ Invalid input! Enter only M, F, or O.");
		            }
		        }

		        // ==== Course Selection ====
		        Course selectedCourse;
		        while (true) {
		            System.out.println("\nAvailable Courses:");
		            List<Course> courseList = course.viewAllCourses();

		            for (int i = 0; i < courseList.size(); i++) {
		                Course c = courseList.get(i);
		                System.out.println("(" + (i + 1) + ") " + c.getCourseName() + " | Duration: " + c.getDuration() + " semesters");
		            }

		            System.out.print("Enter Course Serial Number: ");
		            int courseChoice = -1;

		            try {
		                courseChoice = Integer.parseInt(sc.nextLine());
		            } catch (NumberFormatException e) {
		                System.out.println("❌ Enter a valid number.");
		                continue;
		            }

		            if (courseChoice < 1 || courseChoice > courseList.size()) {
		                System.out.println("❌ Invalid course selection.");
		                continue;
		            }

		            selectedCourse = courseList.get(courseChoice - 1);
		            student.setCourse_id(selectedCourse.getCourseId());
		            break;
		        }
		        
		        System.out.println(student.getCourse_id());
		        // ==== Branch Selection ====
		        while (true) {
		            System.out.println("\nAvailable Branches for selected course:");
		            List<Branch> branchList = branch.getAllBranchesByCourseId(student.getCourse_id());

		            if (branchList.isEmpty()) {
		                System.out.println("❌ No branches found for selected course.");
		                return;
		            }

		            for (int i = 0; i < branchList.size(); i++) {
		                Branch b = branchList.get(i);
		                System.out.println("(" + (i + 1) + ") Branch Name: " + b.getName());
		            }

		            System.out.print("Enter Branch Serial Number: ");
		            int branchChoice = -1;

		            try {
		                branchChoice = Integer.parseInt(sc.nextLine());
		            } catch (NumberFormatException e) {
		                System.out.println("❌ Enter a valid number.");
		                continue;
		            }

		            if (branchChoice < 1 || branchChoice > branchList.size()) {
		                System.out.println("❌ Invalid branch selection.");
		                continue;
		            }

		            Branch selectedBranch = branchList.get(branchChoice - 1);
		            student.setBranchId(selectedBranch.getBranchId());
		            break;
		        }

		        // ==== Semester ====
		        while (true) {
		            System.out.print("Enter Semester: ");
		            try {
		                int sem = Integer.parseInt(sc.nextLine());
		                if (sem > 0 && sem <= selectedCourse.getDuration()) {
		                    student.setSemester(sem);
		                    break;
		                } else {
		                    System.out.println("❌ Semester must be between 1 and " + selectedCourse.getDuration());
		                }
		            } catch (NumberFormatException e) {
		                System.out.println("❌ Enter a valid number.");
		            }
		        }

		        // ==== Roll Number ====
		         //System.out.println("Enter Branch Code \n i.e\n Computer Science -> CS  Information Technology -> IT");
		         String branchCode = "";
		         String roll="";
		            while (true) {
		                System.out.println("Enter Branch Code \ni.e\n Computer Science -> CS \n Information Technology -> IT");
		                branchCode = sc.nextLine().trim().toUpperCase();
		                if(branchCode.length()<=3 && !branchCode.isEmpty()) {
		                	roll=ValidInput.generateRollNumber(branchCode);
		                	break;
		                }else {
		                	System.out.println("Entere Valid Code");
		                }
		            }

		            student.setRollno(roll);

		        // ==== Password ====
		         // System.out.println("Enter Password:");
		            String plainPassword = ValidInput.hidepassword(); 
		            String encrypted = ValidInput.encryptPassword(plainPassword);
		            student.setPassword(encrypted);


		        // ==== Final Save ====
		        boolean isAdded = stuDAO.addStudent(student);
		        if (isAdded) {
		            System.out.println("✅ Student added successfully!");
		        } else {
		            System.out.println("❌ Failed to add student. Please try again.");
		        }

		    } catch (Exception e) {
		        System.out.println("⚠️ Error: " + e.getMessage());
		    }
		}
	  
	  
	  
	  
	  // Show all students Filter by branch (with branch )
	    public void filterByBranch() throws ClassNotFoundException {
	        Scanner sc = new Scanner(System.in);

	        // Show available branches with course names
	        List<Branch> branches = branch.getAllBranchesWithCourses();
	        System.out.println("Select Branch:");
	        for (int i = 0; i < branches.size(); i++) {
	            System.out.println((i + 1) + ". " + branches.get(i).getName() + " (" + branches.get(i).getCourseName() + ")");
	        }

	        int choice = -1;
	        while (true) {
	            System.out.print("Enter option: ");
	            try {
	                choice = Integer.parseInt(sc.nextLine());
	                if (choice >= 1 && choice <= branches.size()) break;
	                else System.out.println("Invalid choice. Try again.");
	            } catch (NumberFormatException e) {
	                System.out.println("Please enter a valid number.");
	            }
	        }

	        String selectedBranchName = branches.get(choice - 1).getName();
	        List<Student> students = stuDAO.filterByBranch(selectedBranchName);
	        displayStudents(students);
	    }

	    // show all students Filter by semester with input validation
	    public void filterBySemester() throws ClassNotFoundException {
	        Scanner sc = new Scanner(System.in);
	        int semester = -1;

	        while (true) {
	            System.out.print("Enter Semester (number): ");
	            try {
	                semester = Integer.parseInt(sc.nextLine());
	                break;
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a valid semester number.");
	            }
	        }

	        List<Student> students = stuDAO.filterBySemester(semester);
	        displayStudents(students);
	    }

	    // Show students 
	    public void filterByRollNumber() throws ClassNotFoundException {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("Enter Roll Number: ");
	        String rollNumber = sc.nextLine();
	        List<Student> students = stuDAO.filterByRollNumber(rollNumber);
	        displayStudents(students);
	    }

	    //show student by name
	    public void filterByName() throws ClassNotFoundException {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("Enter Name: ");
	        String name = sc.nextLine();
	        List<Student> students = stuDAO.filterByName(name);
	        displayStudents(students);
	    }

	    // Display list of students
	    private void displayStudents(List<Student> students) {
	        if (students.isEmpty()) {
	            System.out.println("No students found.");
	        } else {
	            for (Student student : students) {
	            //    System.out.println("Student ID   : " + student.getStudentId());
	                System.out.println("Name         : " + student.getName());
	                System.out.println("Email        : " + student.getEmail());
	                System.out.println("Course     : " + student.getCourseName());
	                System.out.println("Branch     : " + student.getBranchName());
	                System.out.println("Semester     : " + student.getSemester());
	                System.out.println("Roll Number  : " + student.getRollno());
	                System.out.println("Admission Date : " + student.getCreated_at());
	                System.out.println("---------------------------");
	            }
	        }
	    }
	    
	    
	    //Delete Student By admin (Roll num)
	    
	    public void deleteStudent() throws ClassNotFoundException {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("Enter Roll Number of Student to delete: ");
	        String roll = sc.nextLine().trim();

	        // Confirmation
	        System.out.print("Are you sure you want to delete this student? (yes/no): ");
	        String confirm = sc.nextLine().trim().toLowerCase();

	        if (confirm.equals("yes")) {
	            boolean deleted = stuDAO.deleteStudentByRoll(roll);
	            if (deleted) {
	                System.out.println("✅ Student deleted successfully.");
	            } else {
	                System.out.println("❌ Student with Roll Number '" + roll + "' not found.");
	            }
	        } else {
	            System.out.println("❌ Deletion cancelled.");
	        }
	    }
	    
	    
	    
	    //update Student Email by Roll no
	    public void updateStudentEmail() throws ClassNotFoundException, SQLException {
	         
	    	String roll;
	    	while(true) {
	    	
	        System.out.print("Enter Roll Number: ");
	         roll = sc.nextLine().trim();
	        if(!ValidInput.isValidRollNo(roll)) {
	        	System.out.println("Invalid Roll Number\n");
	        	 
	        }else {
	        	System.out.println(" Roll Number is Valid \n");
	        	break;
	        }
	    	}
	         
	        String email = ValidInput.getValidEmail();

	       
	        

	        boolean updated = stuDAO.updateStudentEmail(roll, email);
	        if (updated) {
	            System.out.println("✅ Email updated successfully.");
	        } else {
	            System.out.println("❌ Failed to update email. Please check the roll number.");
	        }
	    }


	    
	    //Update student Phone
	    public void updateStudentContact() throws ClassNotFoundException, SQLException {
	    	String roll;
	    	while(true) {
	    	
	        System.out.print("Enter Roll Number: ");
	         roll = sc.nextLine().trim();
	        if(!ValidInput.isValidRollNo(roll)) {
	        	System.out.println("Invalid Roll Number\n");
	        	 
	        }else {
	        	System.out.println(" Roll Number is Valid \n");
	        	break;
	        }
	    	}

	        // ==== Contact ====
	        String phone;
	        while (true) {
	            System.out.print("Enter Phone Number: ");
	            phone = sc.nextLine().trim();
	            if (phone.matches("\\d{10}")) 
	            	break;
	                else 
	                System.out.println("❌ Invalid phone number! Enter a 10-digit number.");
	  	        }

	        

	        boolean updated = stuDAO.updateStudentContact(roll, phone);
	        if (updated) {
	            System.out.println("✅ Contact number updated successfully.");
	        } else {
	            System.out.println("❌ Failed to update contact number. Please check the roll number.");
	        }
	    }

	    
	    //Update semester
	    public void updateStudentSemester() throws ClassNotFoundException, SQLException {
	    	String roll;
	    	while(true) {
	    	
	        System.out.print("Enter Roll Number: ");
	         roll = sc.nextLine().trim();
	        if(!ValidInput.isValidRollNo(roll)) {
	        	System.out.println("Invalid Roll Number\n");
	        	 
	        }else {
	        	System.out.println(" Roll Number is Valid \n");
	        	break;
	        }
	    	}
	    	
	        int semester = -1;
	        boolean validSemester = false;
	        
	        while (!validSemester) {
	            System.out.print("Enter New Semester: ");
	            try {
	                semester = Integer.parseInt(sc.nextLine().trim());
                    validSemester = true;
	              } catch (NumberFormatException e) {
	                System.out.println("❌ Invalid input! Please enter a valid semester number.");
	            }
	        }

	        boolean updated = stuDAO.updateStudentSemester(roll, semester);
	        if (updated) {
	            System.out.println("✅ Semester updated successfully.");
	        } else {
	            System.out.println("❌ Failed to update semester. Please check the roll number.");
	        }
	    }

	    public void updateStudentGender() throws ClassNotFoundException, SQLException {
	    	String roll;
	    	while(true) {
	    	
	        System.out.print("Enter Roll Number: ");
	         roll = sc.nextLine().trim();
	        if(!ValidInput.isValidRollNo(roll)) {
	        	System.out.println("Invalid Roll Number\n");
	        	 
	        }else {
	        	System.out.println(" Roll Number is Valid \n");
	        	break;
	        }
	    	}
		    	
	    
	        String gender = "";
	        boolean validGender = false;
	        
	        while (!validGender) {
	            System.out.print("Enter New Gender (M/F/O): ");
	            gender = sc.nextLine().trim().toUpperCase();

	            if (gender.equals("M") || gender.equals("F") || gender.equals("O")) {
	                validGender = true;
	            } else {
	                System.out.println("❌ Invalid input! Please enter 'M', 'F', or 'O'.");
	            }
	        }

	        boolean updated = stuDAO.updateStudentGender(roll, gender);
	        if (updated) {
	            System.out.println("✅ Gender updated successfully.");
	        } else {
	            System.out.println("❌ Failed to update gender. Please check the roll number.");
	        }
	    }

	    //Update BRANCH
	    public void updateStudentBranch() throws ClassNotFoundException, SQLException {
	    	String roll;
	    	while(true) {
	    	
	        System.out.print("Enter Roll Number: ");
	         roll = sc.nextLine().trim();
	        if(!ValidInput.isValidRollNo(roll)) {
	        	System.out.println("Invalid Roll Number\n");
	        	 
	        }else {
	        	System.out.println(" Roll Number is Valid \n");
	        	break;
	        }
	    	}


	        int courseId = course.getCourseIdByRollNumber(roll);
	        if (courseId == -1) {
	            System.out.println("Invalid roll number or student not found.");
	            return;
	        }

	        List<Branch> branches = branch.getAllBranchesByCourseId(courseId);
	        

            if (branches.isEmpty()) {
                System.out.println("❌ No branches found for selected course.");
                return;
            }

            int branchId = -1;
            for (int i = 0; i < branches.size(); i++) {
                Branch b = branches.get(i);
                System.out.println("(" + (i + 1) + ") Branch Name: " + b.getName());
            

            System.out.print("Enter Branch Serial Number: ");
            int branchChoice = -1;

            try {
                branchChoice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Enter a valid number.");
                continue;
            }

            if (branchChoice < 1 || branchChoice > branches.size()) {
                System.out.println("❌ Invalid branch selection.");
                continue;
            }

            Branch selectedBranch = branches.get(branchChoice - 1);
             branchId = selectedBranch.getBranchId();
            break;
        }

	        boolean updated = stuDAO.updateStudentBranch(roll, branchId);
	        if (updated) {
	            System.out.println("Branch updated successfully.");
	        } else {
	            System.out.println("Failed to update branch.");
	        }

	    }

}
