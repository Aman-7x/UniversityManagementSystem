package com.university.dao;

import com.university.model.Faculty;
import com.university.model.Marks;
import com.university.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.connection.Connect;

public class StudentDAO {

	public StudentDAO(){
		
	}
	//get student by id(email) password
    public Student getStudentByEmailAndPassword(String email, String password) throws ClassNotFoundException  {
        Student student = null;
        
        try {
        String sql = "Select * from student where email = ? and password = ?";
        PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password); // (encrypted password if you're hashing)

        ResultSet rs = ps.executeQuery();        
    
        

        if (rs.next()) {
            student = new Student();
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setContactNumber(rs.getString("contact_number"));
            student.setGender(rs.getString("gender"));
            student.setBranchId(rs.getInt("branch_id"));
            student.setSemester(rs.getInt("semester"));
            student.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
            student.setRollno(rs.getString("roll_number"));
        }
    } catch (SQLException e ) {
    	System.out.println("Invalid Username or Password");
    }
        
        return student;
        
    }
    
    //get student by email
    public Student getStudentByRoll(String rollNumber) throws ClassNotFoundException {
    	  Student student = null;
          
          try {
          String sql = "SELECT s.*, b.name AS branch_name FROM student AS s JOIN branch AS b ON s.branch_id = b.branch_id WHERE s.roll_number = ?";
          PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
          ps.setString(1, rollNumber);
          
          ResultSet rs = ps.executeQuery();        
       

          if (rs.next()) {
              System.out.println("\n============ STUDENT PROFILE ============");
              System.out.println("Name: " + rs.getString("name"));
              System.out.println("Roll Number: " + rs.getString("roll_number"));
              System.out.println("Email: " + rs.getString("email"));
              System.out.println("Contact: " + rs.getString("contact_number"));
              System.out.println("Semester: " + rs.getInt("semester"));
              System.out.println("Branch : " + rs.getString("branch_name"));
          } else {
              System.out.println("❌ No student found with Roll Number: " + rollNumber);
          }
      } catch (SQLException e ) {
      	System.out.println("Error while fetching profile: " + e.getMessage());
      }
          
          return student;
          
    }
    
    
    //Get Marks By Student roll Number
//    public List<Marks> getMarksByStudentRollNo(String rollno) {
//        List<Marks> marksList = new ArrayList<>();
//        try {
//            String sql = "SELECT sm.*, s.name AS subject_name FROM student_marks sm " +
//                         "JOIN subject s ON sm.subject_id = s.subject_id " +
//                         "WHERE sm.roll_number = ?";
//            
//            PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
//            ps.setString(1, rollno);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Marks marks = new  Marks();
//                             
//                marks.setSubjectId(rs.getInt("subject_id"));
//                marks.setSubjectName(rs.getString("subject_name")); // for display
//                marks.setMarksObtained(rs.getInt("marks_obtained"));
//                marks.setMaxMarks(rs.getInt("max_marks"));
//                marks.setExamType(rs.getString("exam_type"));
//                marks.setResultStatus(rs.getString("result_status"));
//                marks.setRemarks(rs.getString("remarks"));
//                marksList.add(marks);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return marksList;
//    }
    

    //View Attendance By Roll Number
    public void getAttendanceByRollNo(String rollNumber) throws ClassNotFoundException {
    	try {
    	String sql = "SELECT    s.name AS subject_name, COUNT(*) AS total_classes, SUM(CASE WHEN sa.status = 'P' THEN 1 ELSE 0 END) AS present, SUM(CASE WHEN sa.status = 'A' THEN 1 ELSE 0 END) AS absent FROM   student_attendance sa JOIN   subject s ON sa.subject_id = s.subject_id JOIN  student st ON sa.roll_number = st.roll_number WHERE   st.roll_number = ? GROUP BY   sa.subject_id";
    	
    	PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
    	pst.setString(1, rollNumber);
        ResultSet rs = pst.executeQuery();

    	 System.out.println("\n📅 ATTENDANCE REPORT 📅");
    	 if(rs.next()) {
         while (rs.next()) {
             String subject = rs.getString("subject_name");
             int total = rs.getInt("total_classes");
             int present = rs.getInt("present");
             int absent = rs.getInt("absent");

             System.out.println("\nSubject: " + subject);
             System.out.println("Total Classes: " + total);
             System.out.println("Present: " + present);
             System.out.println("Absent: " + absent);
         }
    	 }else {
    		 System.out.println("❌ No Attendance found with Roll Number: \" + rollNumber");
    	 }
     } catch (SQLException e) {
         System.out.println("Error fetching attendance: " + e.getMessage());
     }
    }
    
    
    //Get Student Marks By Student ROll No
    public List<Marks> getMarksByRollNumber (String Rollno){
    	List<Marks> list = new ArrayList<Marks>();
    	
    	String sql= "SELECT sm.marks_obtained, sm.max_marks, sm.exam_type, sm.result_status, sm.remarks,  s.name AS subject_name  FROM student_marks sm JOIN student st ON sm.roll_number = st.roll_number  JOIN subject s ON sm.subject_id = s.subject_id  WHERE st.roll_number = ?";
    	
    	try {
    	PreparedStatement pst = Connect.getConnection().prepareStatement(sql);
    	
    	 pst.setString(1, Rollno);
    	 
    	 ResultSet rs = pst.executeQuery();
    	 
    	 
   
    	 while(rs.next()) {
    		 Marks mark=new Marks();
    		 mark.setMarksObtained(rs.getInt("marks_obtained"));
    		  mark.setMaxMarks(rs.getInt("max_marks"));
              mark.setExamType(rs.getString("exam_type"));
              mark.setResultStatus(rs.getString("result_status"));
              mark.setRemarks(rs.getString("remarks"));
              mark.setSubjectName(rs.getString("subject_name"));
              
              list.add(mark);
    	 }
    	}catch (Exception e) {
            e.printStackTrace();
        }
    	
		return list;
      }
    
    
    //Student Result
    public List<Marks> getResultByRollNumber(String rollNumber) {
        List<Marks> resultList = new ArrayList<>();

        String query = "SELECT sm.marks_obtained, sm.max_marks, sm.exam_type, sm.result_status, sm.remarks, s.name AS subject_name  FROM student_marks sm  JOIN student st ON sm.roll_number = st.roll_number  JOIN subject s ON sm.subject_id = s.subject_id  WHERE st.roll_number = ?  ORDER BY s.name, FIELD(sm.exam_type, 'MID', 'INTERNAL', 'END', 'PRACTICAL')";

        try {  
             PreparedStatement pstmt = Connect.getConnection().prepareStatement(query);

            pstmt.setString(1, rollNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Marks mark = new Marks();
                mark.setMarksObtained(rs.getInt("marks_obtained"));
                mark.setMaxMarks(rs.getInt("max_marks"));
                mark.setExamType(rs.getString("exam_type"));
                mark.setResultStatus(rs.getString("result_status"));
                mark.setRemarks(rs.getString("remarks"));

                
               mark.setSubjectName(rs.getString("subject_name"));
                 
                resultList.add(mark);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
    
    
    //Add Student By Admin
    public boolean addStudent(Student student) throws ClassNotFoundException {
        String sql = "INSERT INTO student (name, email, contact_number, gender, branch_id, semester, password, roll_number)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
            
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getContactNumber());
            ps.setString(4, student.getGender());
            ps.setInt(5, student.getBranchId());
            ps.setInt(6, student.getSemester());
            ps.setString(7, student.getPassword()); // already encrypted
            ps.setString(8, student.getRollno());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
        return false;
    }
    
    
    
    

    // Filter by branch
    public List<Student> filterByBranch(String branch) throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, b.name AS branch_name, c.course_name FROM student as s JOIN branch b ON s.branch_id = b.branch_id JOIN course c ON b.course_id = c.course_id WHERE b.name = ?";
        try {
        	PreparedStatement stmt = Connect.getConnection().prepareStatement(query);
            stmt.setString(1, branch);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
               // student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setBranchId(rs.getInt("branch_id"));
                student.setSemester(rs.getInt("semester"));
                student.setRollno(rs.getString("roll_number"));
                student.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                student.setBranchName(rs.getString("branch_name"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Filter by semester
    public List<Student> filterBySemester(int semester) throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, b.name AS branch_name, c.course_name  FROM student as s JOIN branch b ON s.branch_id = b.branch_id JOIN course c ON b.course_id = c.course_id WHERE s.semester = ?";
        try {
        	PreparedStatement stmt = Connect.getConnection().prepareStatement(query);
            stmt.setInt(1, semester);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
             //   student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setBranchId(rs.getInt("branch_id"));
                student.setSemester(rs.getInt("semester"));
                student.setRollno(rs.getString("roll_number"));
                student.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                student.setBranchName(rs.getString("branch_name"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Filter by roll number
    public List<Student> filterByRollNumber(String rollNumber) throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, b.name AS branch_name, c.course_name FROM student as  s JOIN branch b ON s.branch_id = b.branch_id JOIN course c ON b.course_id = c.course_id WHERE s.roll_number = ?";
        try {
        	PreparedStatement stmt = Connect.getConnection().prepareStatement(query);
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
               // student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setBranchId(rs.getInt("branch_id"));
                student.setSemester(rs.getInt("semester"));
                student.setRollno(rs.getString("roll_number"));
                student.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                student.setBranchName(rs.getString("branch_name"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Filter by name
    public List<Student> filterByName(String name) throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, b.name AS branch_name, c.course_name  FROM student as s JOIN branch b ON s.branch_id = b.branch_id JOIN course c ON b.course_id = c.course_id WHERE s.name LIKE ?";
        try {
        	PreparedStatement stmt = Connect.getConnection().prepareStatement(query);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
               // student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setBranchId(rs.getInt("branch_id"));
                student.setSemester(rs.getInt("semester"));
                student.setRollno(rs.getString("roll_number"));
                student.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());
                student.setBranchName(rs.getString("branch_name"));
                student.setCourseName(rs.getString("course_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    
    
    //Student  deleted 
    public boolean deleteStudentByRoll(String rollNumber) throws ClassNotFoundException {
        boolean isDeleted = false;
        try {
        	 
             PreparedStatement ps = Connect.getConnection().prepareStatement("DELETE FROM student WHERE roll_number = ?");

            ps.setString(1, rollNumber);
            int rows = ps.executeUpdate();
            if (rows > 0) isDeleted = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
    
    
    //Update student email by Roll no
    public boolean updateStudentEmail(String rollNumber, String newEmail) throws ClassNotFoundException {
        String query = "UPDATE student SET email = ? WHERE roll_number = ?";
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(query);
             
            ps.setString(1, newEmail);
            ps.setString(2, rollNumber);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
            return false;
        }
    }

    
    //Update Student Phone no
    public boolean updateStudentContact(String rollNumber, String newContact) throws ClassNotFoundException {
        String query = "UPDATE student SET contact_number = ? WHERE roll_number = ?";
        try {  
             PreparedStatement ps = Connect.getConnection().prepareStatement(query);
             
            ps.setString(1, newContact);
            ps.setString(2, rollNumber);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating contact number: " + e.getMessage());
            return false;
        }
    }

    	//Semester Update 
    public boolean updateStudentSemester(String rollNumber, int newSemester) throws ClassNotFoundException {
        String query = "UPDATE student SET semester = ? WHERE roll_number = ?";
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(query);
             
            ps.setInt(1, newSemester);
            ps.setString(2, rollNumber);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating semester: " + e.getMessage());
            return false;
        }
    }

    //Update stundent gender 
    public boolean updateStudentGender(String rollNumber, String newGender) throws ClassNotFoundException {
        String query = "UPDATE student SET gender = ? WHERE roll_number = ?";
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(query);

            ps.setString(1, newGender);
            ps.setString(2, rollNumber);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating gender: " + e.getMessage());
            return false;
        }
    }

    
    //Update Student Branch
    public boolean updateStudentBranch(String rollNumber, int branchId) throws ClassNotFoundException {
        String query = "UPDATE student SET branch_id = ? WHERE roll_number = ?";
        try {
             PreparedStatement ps = Connect.getConnection().prepareStatement(query);
            ps.setInt(1, branchId);
            ps.setString(2, rollNumber);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating branch: " + e.getMessage());
            return false;
        }
    }

    
    

}
