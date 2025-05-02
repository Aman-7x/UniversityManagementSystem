package com.university.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

 

import com.university.connection.Connect;
import com.university.model.Attendance;
import com.university.model.Faculty;
import com.university.model.Marks;
import com.university.model.Student;
import com.university.model.Subject;
import com.university.util.ValidInput;

public class FacultyDAO {

	
	///Method to Faculty LOGIN
	public Faculty getFacultyLogin(String email , String password) throws ClassNotFoundException {
        Faculty faculty = null;
        String sql = "select name, employee_id from faculty where email = ? and password = ?";
        
        try  {
        	
        	
             PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
            

             pst.setString(1, email);
             pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
            	faculty = new Faculty(); // default constructor

            	faculty.setName(rs.getString("name"));
//            	faculty.setEmail(rs.getString("email"));
//            	faculty.setContactNumber(rs.getString("contact_number"));
//            	faculty.setGender(rs.getString("gender"));
//            	faculty.setCreatedAt(rs.getDate("created_at").toLocalDate());
            	faculty.setEmployeeId(rs.getString("employee_id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }
	
	// Method to get faculty details
    public Faculty getFacultyDetails(String employeeId) throws ClassNotFoundException {
        Faculty faculty = null;
        String sql = "select * from faculty where employee_id = ?";
        
        try  {
       	
             PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
            
             pst.setString(1, employeeId);
             ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
            	faculty = new Faculty(); // default constructor

            	faculty.setName(rs.getString("name"));
            	faculty.setEmail(rs.getString("email"));
            	faculty.setContactNumber(rs.getString("contact_number"));
            	faculty.setGender(rs.getString("gender"));
            	faculty.setCreatedAt(rs.getDate("created_at").toLocalDate());
            	faculty.setEmployeeId(rs.getString("employee_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }
    
    
    
//    // Method to get assigned subjects of the faculty
    public ArrayList<Subject> getAssignedSubjects(String employeeId) throws ClassNotFoundException {
        ArrayList<Subject> subjects = new ArrayList<>();
//        String sql = "SELECT s.name, s.subject_type, s.semester FROM faculty_subject fs " +
//                     "JOIN subject s ON fs.subject_id = s.subject_id " +
//                     "WHERE fs.employeeId = ?";
        
        
        String sql = "SELECT s.subject_id , s.name, s.subject_type, s.semester, b.name AS branch_name FROM faculty_subject fs JOIN subject s ON fs.subject_id = s.subject_id JOIN branch b ON s.branch_id = b.branch_id WHERE fs.employee_id = ?";
        
        try {
            PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
            pst.setString(1, employeeId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String subjectName = rs.getString("name");
                String subjectType = rs.getString("subject_type");
                int semester = rs.getInt("semester");
                String branchName = rs.getString("branch_name");
                
                Subject subject = new Subject(subjectName, subjectType, semester , branchName); // Creating Subject object
                subjects.add(subject); // Adding subject to list
                
                subject.setSubject_id(rs.getInt("subject_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
    
    //method to get student by Faculty
    public List<Student> getStudentsByFaculty(String employeeId) throws SQLException, ClassNotFoundException {
        List<Student> studentList = new ArrayList<>();

    

        String sql = "SELECT DISTINCT s.roll_number, s.name, s.email, s.semester, s.branch_id " +
                "FROM student s " +
                "JOIN subject sub ON s.semester = sub.semester AND s.branch_id = sub.branch_id " +
                "JOIN faculty_subject fs ON sub.subject_id = fs.subject_id " +
                "WHERE fs.employee_Id = ?";

        PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
        ps.setString(1, employeeId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setRollno(rs.getString("roll_number"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setSemester(rs.getInt("semester"));
            student.setBranchId(rs.getInt("branch_id"));
            studentList.add(student);
        }

        return studentList;
    }
    
    
    
    //Get Students by semester And Branch Name :
    
    public ArrayList<Student> getStudentsBySemesterAndBranch(int semester, String branchName) throws ClassNotFoundException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT DISTINCT  s.name, s.roll_number " +
                     "FROM student s " +
                     "JOIN subject sub ON s.branch_id = sub.branch_id " +
                     "JOIN branch b ON sub.branch_id = b.branch_id " +
                     "WHERE sub.semester = ? AND b.name = ?";
        
        try {
            PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
            pst.setInt(1, semester);
            pst.setString(2, branchName);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
            	Student student = new Student();
            	
            	 
               // int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                String rollNumber = rs.getString("roll_number");
                student.setName(name);
                student.setRollno(rollNumber);
                students.add(student); // Adding student to list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    //Upload Attendance 
    public boolean uploadAttendance(List<Attendance> list) {
        String sql = "INSERT INTO student_attendance (subject_id, roll_number, attendance_date, status, employee_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Attendance a : list) {
                ps.setInt(1, a.getSubjectId());
                ps.setString(2, a.getRollNumber());
                ps.setDate(3, new java.sql.Date(a.getAttendanceDate().getTime()));
                ps.setString(4, a.getStatus());
                ps.setString(5, a.getEmployeeId());

                ps.executeUpdate(); // 👈 ye line batch ki jagah aayi hai
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
//        	System.out.println("Duplicate Attendance");
            return false;
        }
    }
    
    
    
    //view Attendance 
    public List<Attendance> getAttendanceBySubjectAndDate(int  subjectId, Date date) throws SQLException, ClassNotFoundException {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT sa.roll_number, sa.status, s.name " +
                     "FROM student_attendance sa " +
                     "JOIN student s ON sa.roll_number = s.roll_number " +
                     "WHERE sa.subject_id = ? AND sa.attendance_date = ?";

        try  {
             PreparedStatement ps = Connect.getConnection().prepareStatement(sql); 
            
            ps.setInt(1, subjectId);  // Set subject_id
            ps.setDate(2, date);       // Set attendance date

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setRollNumber(rs.getString("roll_number"));
                attendance.setStatus(rs.getString("status"));
                attendance.setStudentName(rs.getString("name"));
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return attendanceList;
    }

    
    public boolean uploadMarks(List<Marks> marksList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            
            // SQL query to insert marks
            String sql = "INSERT INTO student_marks (subject_id, marks_obtained, max_marks, exam_type, result_status, remarks, roll_number, employee_id) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Prepare statement
            preparedStatement = Connect.getConnection().prepareStatement(sql);

             

            // Add each mark entry to the batch
            for (Marks mark : marksList) {
                preparedStatement.setInt(1, mark.getSubjectId());
                preparedStatement.setInt(2, mark.getMarksObtained());
                preparedStatement.setInt(3, mark.getMaxMarks());
                preparedStatement.setString(4, mark.getExamType());
                preparedStatement.setString(5, mark.getResultStatus());
                preparedStatement.setString(6, mark.getRemarks());
                preparedStatement.setString(7, mark.getRollNo());
                preparedStatement.setString(8, mark.getEmployee_id());

                preparedStatement.executeUpdate();  // Add to batch
            }
 
            return true;

        } catch (SQLException e) {
          
            e.printStackTrace();
            return false;
        } finally {
            // Clean up
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            
        }
    }

    //get marks by subject
    public List<Marks> getMarksBySubject(int subjectId) throws SQLException, ClassNotFoundException {
        List<Marks> marksList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
             

            // SQL query to fetch marks for the subject
            String sql = "SELECT sm.roll_number, s.name AS student_name, sm.exam_type, sm.marks_obtained, sm.result_status, sm.remarks "
                       + "FROM student_marks sm "
                       + "JOIN student s ON sm.roll_number = s.roll_number "
                       + "WHERE sm.subject_id = ?";

            // Prepare statement
            preparedStatement = Connect.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, subjectId);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                Marks mark = new Marks();
                mark.setRollNo(resultSet.getString("roll_number"));
                mark.setStudentName(resultSet.getString("student_name"));
                mark.setExamType(resultSet.getString("exam_type"));
                mark.setMarksObtained(resultSet.getInt("marks_obtained"));
                mark.setResultStatus(resultSet.getString("result_status"));
                mark.setRemarks(resultSet.getString("remarks"));
                marksList.add(mark);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            
        }

        return marksList;
    }

    
    //top 10
    public List<Marks> getTop10Toppers(int subjectId) throws SQLException, ClassNotFoundException {
        List<Marks> marksList = new ArrayList<>();
        String sql = "SELECT sm.roll_number, sm.marks, sm.subject_id, s.name " +
                     "FROM student_marks sm " +
                     "JOIN student s ON sm.roll_number = s.roll_number " +
                     "WHERE sm.subject_id = ? " +
                     "ORDER BY sm.marks DESC " +
                     "LIMIT 10";  // Fetch only the top 10 students

        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Marks marks = new Marks();
                marks.setRollNo(rs.getString("roll_number"));
                marks.setMarksObtained(rs.getInt("marks"));
                marks.setSubjectId(rs.getInt("subject_id"));
                marks.setStudentName(rs.getString("name"));
                marksList.add(marks);
            }
        }
        return marksList;
    }

    
    
    //View All Fculty
    public List<Faculty> getAllFaculties() {
        List<Faculty> facultyList = new ArrayList<>();
        String sql = "SELECT employee_id, name, email, contact_number, gender, created_at FROM faculty";

        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setEmployeeId(rs.getString("employee_id"));
                faculty.setName(rs.getString("name"));
                faculty.setEmail(rs.getString("email"));
                faculty.setContactNumber(rs.getString("contact_number"));
                faculty.setGender(rs.getString("gender"));
                faculty.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                facultyList.add(faculty);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching faculty list: " + e.getMessage());
        
        }
        return facultyList;
    }


    
    //View faculty by employee id 
    public Faculty getFacultyByEmployeeId(String employeeId) {
        Faculty faculty = null;
        String sql = "SELECT * FROM faculty WHERE employee_id = ?";
        
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
            
            ps.setString(1, employeeId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                faculty = new Faculty();
                faculty.setFacultyId(rs.getInt("faculty_id"));
                faculty.setName(rs.getString("name"));
                faculty.setEmail(rs.getString("email"));
                faculty.setContactNumber(rs.getString("contact_number"));
                faculty.setGender(rs.getString("gender"));
                faculty.setEmployeeId(rs.getString("employee_id"));
                faculty.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return faculty;
    }

    
    // Faculty delete by Faculty id (Employee id)
    public boolean deleteFacultyByEmployeeId(String employeeId) {
        String sql = "DELETE FROM faculty WHERE employee_id = ?";
        try  {
             PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
            
            ps.setString(1, employeeId);
            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    //Update Faculty Email
    public boolean updateFacultyEmail(String employeeId, String newEmail) throws ClassNotFoundException {
        String query = "UPDATE faculty SET email = ? WHERE employee_id = ?";
        try {
            PreparedStatement ps = Connect.getConnection().prepareStatement(query);
            ps.setString(1, newEmail);
            ps.setString(2, employeeId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
            return false;
        }
    }

   
    
    
    //Update Faculty Contact Number
         public boolean updateFacultyContact(String employeeId, String newContact) throws ClassNotFoundException {
        String query = "UPDATE faculty SET contact_number = ? WHERE employee_id = ?";
        try {
            PreparedStatement ps = Connect.getConnection().prepareStatement(query);
            ps.setString(1, newContact);
            ps.setString(2, employeeId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating contact number: " + e.getMessage());
            return false;
        }
    }
         
         
       //Update Faculty Gender 
         public boolean updateFacultyGender(String employeeId, String newGender) throws ClassNotFoundException {
        	    String query = "UPDATE faculty SET gender = ? WHERE employee_id = ?";
        	    try {
        	        PreparedStatement ps = Connect.getConnection().prepareStatement(query);
        	        ps.setString(1, newGender); // 'M', 'F', or 'O'
        	        ps.setString(2, employeeId);
        	        
        	        int rowsAffected = ps.executeUpdate();
        	        return rowsAffected > 0;
        	    } catch (SQLException e) {
        	        System.out.println("Error updating gender: " + e.getMessage());
        	        return false;
        	    }
        	}

         //Update PassWord
         public boolean updateFacultyPassword(String employeeId, String newPassword) throws ClassNotFoundException {
        	    String encryptedPassword = ValidInput.encryptPassword(newPassword); // Assuming you have SHA-256 logic here
        	    String query = "UPDATE faculty SET password = ? WHERE employee_id = ?";
        	    try {
        	        PreparedStatement ps = Connect.getConnection().prepareStatement(query);
        	        ps.setString(1, encryptedPassword);
        	        ps.setString(2, employeeId);
        	        
        	        int rowsAffected = ps.executeUpdate();
        	        return rowsAffected > 0;
        	    } catch (SQLException e) {
        	        System.out.println("Error updating password: " + e.getMessage());
        	        return false;
        	    }
        	}

          
         
         //view faculty by branch and course 
         public void viewFacultyByBranchAndCourse() throws ClassNotFoundException {
        	    String query = "SELECT f.name AS faculty_name, f.email AS faculty_email, f.contact_number AS faculty_contact, " +
        	                   "b.name AS branch_name, c.course_name AS course_name, s.name AS subject_name, s.semester AS subject_semester, " +
        	                   "s.subject_type AS subject_type " +
        	                   "FROM faculty f " +
        	                   "JOIN faculty_subject fs ON f.employee_id = fs.employee_id " +
        	                   "JOIN subject s ON fs.subject_id = s.subject_id " +
        	                   "JOIN branch b ON s.branch_id = b.branch_id " +
        	                   "JOIN course c ON b.course_id = c.course_id " +
        	                   "ORDER BY c.course_name, b.name, f.name";

        	    try (Connection conn = Connect.getConnection();
        	         PreparedStatement ps = conn.prepareStatement(query);
        	         ResultSet rs = ps.executeQuery()) {
        	    	
        	    	boolean data_Hai=false;
        	        while (rs.next()) {
        	        	data_Hai=true;
        	            String facultyName = rs.getString("faculty_name");
        	            String facultyEmail = rs.getString("faculty_email");
        	            String facultyContact = rs.getString("faculty_contact");
        	            String branchName = rs.getString("branch_name");
        	            String courseName = rs.getString("course_name");
        	            String subjectName = rs.getString("subject_name");
        	            int semester = rs.getInt("subject_semester");
        	            String subjectType = rs.getString("subject_type");

        	            System.out.println("Faculty Name: " + facultyName);
        	            System.out.println("Faculty Email: " + facultyEmail);
        	            System.out.println("Faculty Contact: " + facultyContact);
        	            System.out.println("Branch Name: " + branchName);
        	            System.out.println("Course Name: " + courseName);
        	            System.out.println("Subject Name: " + subjectName);
        	            System.out.println("Semester: " + semester);
        	            System.out.println("Subject Type: " + subjectType);
        	            System.out.println("--------------------------");
        	        }
        	        if(!data_Hai) {
        	        	  System.out.println("No faculty-subject data found in the system.");
        	        }
        	    } catch (SQLException e) {
        	        System.out.println("Error fetching faculty details: " + e.getMessage());
        	    }
        	}



}
