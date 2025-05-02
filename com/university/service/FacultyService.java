package com.university.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.university.connection.Connect;
import com.university.dao.FacultyDAO;
import com.university.dao.StudentDAO;
import com.university.model.Attendance;
import com.university.model.Faculty;
import com.university.model.Marks;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.util.ValidInput;

public class FacultyService {

	
private FacultyDAO facDAO;
private StudentDAO stuDAO;
private Scanner sc;
    public FacultyService() {
        this.facDAO = new FacultyDAO();
        this.stuDAO= new StudentDAO();
        this.sc = new Scanner(System.in);
    }
    
    
    
    
    //Method to faculty login
    public Faculty facultyLogIn(String email , String password) throws ClassNotFoundException {
    	return facDAO.getFacultyLogin(email, password);
    }

    // Method to view personal details of faculty
    public void viewFacultyDetails(String employeeId) throws ClassNotFoundException {
        Faculty faculty = facDAO.getFacultyDetails(employeeId);
        
        if (faculty != null) {
            System.out.println("Personal Details:");
            System.out.println("-----------------");
            System.out.println("Name: " + faculty.getName());
            System.out.println("Email: " + faculty.getEmail());
            System.out.println("Contact Number: " + faculty.getContactNumber());
            System.out.println("Gender: " + faculty.getGender());
            System.out.println("Employee ID: " + faculty.getEmployeeId());
        } else {
            System.out.println("No faculty found with ID: " + employeeId);
        }
    }
    
    
 // Method to view subjects assigned to faculty
    public void printAssignedSubjects(String employeeId) throws ClassNotFoundException {
        
        ArrayList<Subject> subjects = facDAO.getAssignedSubjects(employeeId);

        // Printing the subjects in tabular format
        System.out.println("Subject Name\t\tSubject Type\tSemester");
        System.out.println("---------------------------------------------");

        for (Subject subject : subjects) {
            System.out.println(subject.getName() + "\t\t" + subject.getSubjectType() + "\t" + subject.getSemester());
        }
    }
    
    
    
    
    public void showStudentsUnderFaculty(String employeeId) throws ClassNotFoundException {
        try {
            List<Student> students = facDAO.getStudentsByFaculty(employeeId);

            if (students.isEmpty()) {
                System.out.println("No students assigned to your subjects yet.");
                return;
            }

            System.out.println("\n=== Students Assigned to You ===");
            System.out.printf("%-15s %-20s %-25s %-10s %-10s%n",
                    "Roll No", "Name", "Email", "Semester", "Branch ID");
            System.out.println("--------------------------------------------------------------------------");

            for (Student s : students) {
                System.out.printf("%-15s %-20s %-25s %-10d %-10d%n",
                        s.getRollno(), s.getName(), s.getEmail(),
                        s.getSemester(), s.getBranchId());
            }

        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
    }
    
    
 // 👇 1. Select Subject
    public Subject chooseSubject(List<Subject> subjectList) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== Assigned Subjects ===");
        for (int i = 0; i < subjectList.size(); i++) {
            Subject sub = subjectList.get(i);
            System.out.printf("%d. %s (%s - Semester %d, Branch: %s)\n",
                    i + 1, sub.getName(), sub.getSubjectType(), sub.getSemester(), sub.getBranchName());
        }

        System.out.print("Choose a subject number: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > subjectList.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return subjectList.get(choice - 1);
    }

    
 // 👇 2. Date input from user
    public LocalDate getValidDateFromUser() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            try {
                System.out.print("Enter attendance date (dd-MM-yyyy): ");
                String input = sc.nextLine();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
    }

 // 👇 3. Attendance input for students
    public List<Attendance> getAttendanceListFromUser(List<Student> students, LocalDate date, String empId , int subjectId) {
        Scanner sc = new Scanner(System.in);
        List<Attendance> list = new ArrayList<>();

        for (Student s : students) {
            String status;
            while (true) {
                System.out.print(s.getRollno() + " - " + s.getName() + ": ");
                status = sc.nextLine().trim().toUpperCase();

                if (status.equals("P") || status.equals("A")) {
                    break;
                } else {
                    System.out.println("Enter only P (Present) or A (Absent).");
                }
            }

            Attendance att = new Attendance();
            att.setRollNumber(s.getRollno());
            att.setAttendanceDate(java.sql.Date.valueOf(date));
            att.setStatus(status);
            att.setEmployeeId(empId);
            att.setSubjectId(subjectId);
            list.add(att);
        }

        return list;
    }

    
    
    public void uploadAttendance(String employeeId) throws ClassNotFoundException {
        List<Subject> assignedSubjects = facDAO.getAssignedSubjects(employeeId);

        if (assignedSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        Subject selectedSubject = chooseSubject(assignedSubjects);
        if (selectedSubject == null) return;

        int semester = selectedSubject.getSemester();
        String branchName = selectedSubject.getBranchName();
        int subjectId = selectedSubject.getSubject_id();
        List<Student> studentList = facDAO.getStudentsBySemesterAndBranch(semester, branchName);
        if (studentList.isEmpty()) {
            System.out.println("No students found for this subject.");
            return;
        }

        LocalDate attendanceDate = getValidDateFromUser();
        List<Attendance> attendanceList = getAttendanceListFromUser(studentList, attendanceDate, employeeId , subjectId);

        boolean success = facDAO.uploadAttendance(attendanceList);
        System.out.println(success ? "Attendance uploaded successfully!" : "Failed to upload attendance.");
    }

    
    
    
    public void viewAttendance(String employeeId) throws ClassNotFoundException, SQLException {
        // Step 1: Get assigned subjects
        List<Subject> assignedSubjects = facDAO.getAssignedSubjects(employeeId);

        if (assignedSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        // Step 2: Choose subject using reusable method
        Subject selectedSubject = chooseSubject(assignedSubjects);
        if (selectedSubject == null) return;

        String subjectName = selectedSubject.getName();
        int subjectId = selectedSubject.getSubject_id();
        // Step 3: Get date using reusable method
        LocalDate attendanceDate = getValidDateFromUser();

        // Step 4: Fetch attendance data
        List<Attendance> attendanceList = facDAO.getAttendanceBySubjectAndDate(subjectId, java.sql.Date.valueOf(attendanceDate));

        if (attendanceList.isEmpty()) {
            System.out.println("No attendance found for this subject on " + attendanceDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            return;
        }

        // Step 5: Display Attendance
        System.out.printf("\n=== Attendance for %s on %s ===\n", subjectName, attendanceDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.printf("%-15s %-25s %-10s\n", "Roll No", "Name", "Status");
        System.out.println("-----------------------------------------------------");

        for (Attendance a : attendanceList) {
            System.out.printf("%-15s %-25s %-10s\n", a.getRollNumber(), a.getStudentName(), a.getStatus());
        }
    }

    
 // Upload Marks
    public void uploadMarks(String employeeId) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);

        // 1. Get assigned subjects
        List<Subject> assignedSubjects = facDAO.getAssignedSubjects(employeeId);
        if (assignedSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        // 2. Choose subject
        Subject selectedSubject = chooseSubject(assignedSubjects);
        if (selectedSubject == null) return;

        int subjectId = selectedSubject.getSubject_id();
        int semester = selectedSubject.getSemester();
        String branchName = selectedSubject.getBranchName();

        // 3. Get exam type
        String examType = getValidExamTypeFromUser();

        // 4. Enter max marks
        System.out.print("Enter maximum marks for this exam: ");
        int maxMarks = Integer.parseInt(sc.nextLine());

        // 5. Get students
        List<Student> studentList = facDAO.getStudentsBySemesterAndBranch(semester, branchName);
        if (studentList.isEmpty()) {
            System.out.println("No students found for this subject.");
            return;
        }

        // 6. Input marks and result status
        List<Marks> marksList = new ArrayList<>();
        System.out.println("\n=== Students for Subject: " + selectedSubject.getName() + " ===");

        for (Student s : studentList) {
            // Get marks for the student
            int marks = getValidMarksFromUser(s.getRollno(), s.getName(), maxMarks);
            
         // Define your passing criteria (e.g., 40% for pass)
            int passPercentage = 40;
            String resultStatus = marks >= (maxMarks * passPercentage / 100) ? "PASS" : "FAIL";


            // Get remarks if any
            System.out.print("Enter remarks for " + s.getName() + " (optional): ");
            String remarks = sc.nextLine().trim();

            // Create StudentMarks object to hold this data
            Marks sm = new  Marks();
            sm.setSubjectId(subjectId);
            sm.setRollNo(s.getRollno());
            sm.setExamType(examType);
            sm.setMarksObtained(marks);
            sm.setMaxMarks(maxMarks);
            sm.setResultStatus(resultStatus);
            sm.setRemarks(remarks);

            sm.setEmployee_id(employeeId);
            marksList.add(sm);
        }

        // 7. Upload marks to database
        boolean success = facDAO.uploadMarks(marksList);
        System.out.println(success ? "Marks uploaded successfully!" : "Failed to upload marks.");
    }

    // Valid Exam Type
    public String getValidExamTypeFromUser() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Exam Types Available: MID, INTERNAL, END, EXTERNAL");
            System.out.print("Enter exam type: ");
            String examType = sc.nextLine().trim().toUpperCase();

            if (examType.matches("MID|INTERNAL|END|EXTERNAL")) {
                return examType;
            } else {
                System.out.println("Invalid exam type. Try again.");
            }
        }
    }

    // Valid Marks Input
    public int getValidMarksFromUser(String rollNo, String name, int maxMarks) {
        Scanner sc = new Scanner(System.in);
        int marks;

        while (true) {
            System.out.print(rollNo + " - " + name + " : ");
            try {
                marks = Integer.parseInt(sc.nextLine());
                if (marks >= 0 && marks <= maxMarks) {
                    return marks;
                } else {
                    System.out.println("Marks must be between 0 and " + maxMarks);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    
    
    //view Marks
    public void viewMarks(String employeeId) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);

        // Fetch the list of subjects assigned to the faculty using the existing method
        List<Subject> assignedSubjects = facDAO.getAssignedSubjects(employeeId);

        if (assignedSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        System.out.println("\n=== Assigned Subjects ===");
        for (int i = 0; i < assignedSubjects.size(); i++) {
            Subject sub = assignedSubjects.get(i);
            System.out.printf("%d. %s (%s - Semester %d, Branch: %s)\n",
                    i + 1, sub.getName(), sub.getSubjectType(), sub.getSemester(), sub.getBranchName());
        }

        // Allow faculty to choose a subject by number
        System.out.print("Choose a subject number to view marks: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > assignedSubjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Subject selectedSubject = assignedSubjects.get(choice - 1);
        int subjectId = selectedSubject.getSubject_id();

        // Fetch the marks for the selected subject using the existing DAO method
        List<Marks> marksList = facDAO.getMarksBySubject(subjectId);

        if (marksList.isEmpty()) {
            System.out.println("No marks uploaded for this subject yet.");
            return;
        }

        // Display the marks for each student
        System.out.println("\n=== Marks for Subject: " + selectedSubject.getName() + " ===");
        System.out.printf("%-15s %-20s %-10s %-10s %-15s %-15s%n", 
                          "Roll No", "Name", "Exam Type", "Marks", "Result Status", "Remarks");
        System.out.println("---------------------------------------------------------------------");

        for (Marks marks : marksList) {
            System.out.printf("%-15s %-20s %-10s %-10d %-15s %-15s%n", 
                              marks.getRollNo(), marks.getStudentName(), 
                              marks.getExamType(), marks.getMarksObtained(),
                              marks.getResultStatus(), marks.getRemarks());
        }
    }

    
    public void viewTop10Toppers(String employeeId) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);

        // Fetch the list of subjects assigned to the faculty
        List<Subject> assignedSubjects = facDAO.getAssignedSubjects(employeeId);

        if (assignedSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        System.out.println("\n=== Assigned Subjects ===");
        for (int i = 0; i < assignedSubjects.size(); i++) {
            Subject sub = assignedSubjects.get(i);
            System.out.printf("%d. %s (%s - Semester %d, Branch: %s)\n",
                    i + 1, sub.getName(), sub.getSubjectType(), sub.getSemester(), sub.getBranchName());
        }

        // Allow faculty to choose a subject by number
        System.out.print("Choose a subject number to view top 10 toppers: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > assignedSubjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Subject selectedSubject = assignedSubjects.get(choice - 1);
        int subjectId = selectedSubject.getSubject_id();

        // Fetch the top 10 toppers for the selected subject
        List<Marks> marksList = facDAO.getTop10Toppers(subjectId);

        if (marksList.isEmpty()) {
            System.out.println("No marks uploaded for this subject yet.");
            return;
        }

        // Display the top 10 toppers
        System.out.println("\n=== Top 10 Toppers for Subject: " + selectedSubject.getName() + " ===");
        System.out.printf("%-15s %-20s %-10s%n", "Roll No", "Name", "Marks");
        System.out.println("-----------------------------------------------");

        for (Marks marks : marksList) {
            System.out.printf("%-15s %-20s %-10d%n", marks.getRollNo(), marks.getStudentName(), marks.getMarksObtained());
        }
    }
    
    
    
    //All Faculty view
    
    public void viewAllFaculties() {
        List<Faculty> list = facDAO.getAllFaculties();
        
        if (list.isEmpty()) {
            System.out.println("No faculty records found.");
        } else {
            System.out.printf("%-15s %-20s %-25s %-15s %-10s %-20s\n",
                    "Faculty ID", "Name", "Email", "Contact", "Gender", "Joined At");

            for (Faculty f : list) {
                System.out.printf("%-15s %-20s %-25s %-15s %-10s %-20s\n",
                        f.getEmployeeId(), f.getName(), f.getEmail(),
                        f.getContactNumber(), f.getGender(), f.getCreatedAt());
            }
        }
    }

    
    // faculty by employee id 
    public void viewFacultyByEmployeeId() throws ClassNotFoundException, SQLException {
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
        Faculty f = facDAO.getFacultyByEmployeeId(empId);

        if (f == null) {
            System.out.println("No faculty found with Faculty ID: " + empId);
            return;
        }

        System.out.println("Faculty Details:");
        System.out.println("---------------------------------------------");
      //  System.out.println("Faculty ID    : " + f.getFacultyId());
        System.out.println("Name          : " + f.getName());
        System.out.println("Email         : " + f.getEmail());
        System.out.println("Contact       : " + f.getContactNumber());
        System.out.println("Gender        : " + f.getGender());
        System.out.println("Faculty ID   : " + f.getEmployeeId());
        System.out.println("Joined At    : " + f.getCreatedAt());
        System.out.println("---------------------------------------------");
    }

    
    //Delete Faculty
    public void deleteFacultyByEmployeeId() {
    	  String empId = "";
          boolean validEmpId = false;
    	 while (!validEmpId) {
             System.out.print("Enter Employee ID of Faculty to delete: ");
             empId = sc.nextLine().trim();
             if (!empId.isEmpty()) {
                 validEmpId = true;
             } else {
                 System.out.println("Employee ID cannot be empty. Please try again.");
             }
         }

        Faculty faculty = facDAO.getFacultyByEmployeeId(empId);

        if (faculty == null) {
            System.out.println("No faculty found with Employee ID: " + empId);
            return;
        }

        System.out.println("Faculty Found: " + faculty.getName() + " (" + faculty.getEmail() + ")");
        
         String confirm = "";
        boolean validConfirmation = false;

        while (!validConfirmation) {
            System.out.print("Are you sure you want to delete this faculty? (yes/no): ");
            confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("yes") || confirm.equals("no")) {
                validConfirmation = true;
            } else {
                System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            }
        }

        if (confirm.equals("yes")) {
            boolean deleted = facDAO.deleteFacultyByEmployeeId(empId);
            if (deleted) {
                System.out.println("Faculty deleted successfully.");
            } else {
                System.out.println("Failed to delete faculty.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    
 // Update Email
    public void updateEmail(String empId) throws ClassNotFoundException {
    	
    	String newEmail = ValidInput.getValidEmail();
        if (facDAO.updateFacultyEmail(empId, newEmail)) {
            System.out.println("✅ Email updated successfully for: " + empId);
        } else {
            System.out.println("❌ Failed to update email. Check Employee ID or try again.");
        }
    }

    // Update Contact Number
    public void updateContact(String empId) throws ClassNotFoundException {
    	Faculty faculty = new Faculty();
    	 String phone;
	        while (true) {
	            System.out.print("Enter Phone Number: ");
	            phone = sc.nextLine().trim();
	            if (phone.matches("\\d{10}")) {
	            	faculty.setContactNumber(phone);
	                break;
	            }
	            System.out.println("Invalid Phone Number! Enter a 10-digit number.");
	        }
        if (facDAO.updateFacultyContact(empId, phone)) {
            System.out.println("✅ Contact number updated successfully for: " + empId);
        } else {
            System.out.println("❌ Failed to update contact number. Check Employee ID or try again.");
        }
    }

    // Update Gender
    public void updateGender(String empId ) throws ClassNotFoundException {
    	Faculty faculty = new Faculty();
    	String gender = "";
        while (true) {
            System.out.println("Enter Gender (M/F/O):");
            gender = sc.nextLine().trim().toUpperCase();

            if (gender.equals("M") || gender.equals("F") || gender.equals("O")) {
                faculty.setGender(gender);
                break;
            } else {
                System.out.println("❌ Invalid input! Please enter only M, F, or O.");
            }
        }
    	
        if (facDAO.updateFacultyGender(empId, gender)) {
            System.out.println("✅ Gender updated successfully for: " + empId);
        } else {
            System.out.println("❌ Failed to update gender. Make sure gender is 'M', 'F', or 'O' and ID is correct.");
        }
    }

    // Update Password
    public void updatePassword(String empId) throws ClassNotFoundException {
    	String newPassword;
    	while(true) {
    	System.out.println("Enter New Password : ");
    	 newPassword = sc.nextLine();
    	if(newPassword.isEmpty()) {
    		System.out.println("Password Can't be empty");
    	}else
    		break;
    	}
    	
        if (facDAO.updateFacultyPassword(empId, newPassword)) {
            System.out.println("✅ Password updated successfully for: " + empId);
        } else {
            System.out.println("❌ Failed to update password. Check Employee ID or try again.");
        }
    }
    
    //view Faculty by branch and course 
    public void viewFacultyByBranchAndCourse() throws ClassNotFoundException {
        facDAO.viewFacultyByBranchAndCourse();
    }
}
