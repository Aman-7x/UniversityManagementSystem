package com.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.university.connection.Connect;
import com.university.model.Faculty;
import com.university.model.Subject;

public class SubjectDAO {

	
	
	
	 

	    //add subject
	    public boolean addSubject(Subject subject) {
	        boolean status = false;
	        try {  
	            String query = "INSERT INTO subject (branch_id, name, semester, subject_type) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = Connect.getConnection().prepareStatement(query);
	            ps.setInt(1, subject.getBranch_id());
	            ps.setString(2, subject.getSubject_name());
	            ps.setInt(3, subject.getSemester());
	            ps.setString(4, subject.getSubjectType());
	            int rows = ps.executeUpdate();
	            status = rows > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return status;
	    }
	
	    
	    //assign subject
	    public void assignSubjectToFaculty(String employeeId, int subjectId) {
	        String sql = "INSERT INTO faculty_subject (employee_id, subject_id) VALUES (?, ?)";

	        try (PreparedStatement ps = Connect.getConnection().prepareStatement(sql)) {
	            ps.setString(1, employeeId);
	            ps.setInt(2, subjectId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("✅ Subject assigned successfully to faculty.");
	            } else {
	                System.out.println("❌ Failed to assign subject.");
	            }

	        } catch (SQLIntegrityConstraintViolationException e) {
	            System.out.println("❗ This subject is already assigned.");
	        } catch (SQLException | ClassNotFoundException e) {
	            System.out.println("Error assigning subject: " + e.getMessage());
	        }
	    }


	    public List<Subject> getAllSubjects() {
	        List<Subject> list = new ArrayList<>();

	        String sql = "SELECT s.subject_id, s.name AS subject_name, s.semester, s.subject_type, " +
	                     "b.name AS branch_name " +
	                     "FROM subject s " +
	                     "JOIN branch b ON s.branch_id = b.branch_id " +
	                     "ORDER BY b.name, s.subject_type, s.semester";

	        try (PreparedStatement ps = Connect.getConnection().prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                Subject s = new Subject();
	                s.setSubject_id(rs.getInt("subject_id"));
	                s.setName(rs.getString("subject_name"));
	                s.setSemester(rs.getInt("semester"));
	                s.setSubjectType(rs.getString("subject_type"));
	                s.setBranchName(rs.getString("branch_name")); // extra field in Subject class
	                list.add(s);
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            System.out.println("❌ Error fetching subjects: " + e.getMessage());
	        }

	        return list;
	    }
	    
	    
	    // view subject by course and semester
	    public List<Subject> viewSubjectsByCourseSemester(int courseId, int semester) {
	        List<Subject> subjectList = new ArrayList<>();

	        String sql = "SELECT s.subject_id, s.name, s.semester, s.subject_type, b.name AS branch_name " +
	                     "FROM subject s " +
	                     "JOIN branch b ON s.branch_id = b.branch_id " +
	                     "WHERE b.course_id = ? AND s.semester = ?";

	        try (PreparedStatement ps = Connect.getConnection().prepareStatement(sql)) {
	            ps.setInt(1, courseId);
	            ps.setInt(2, semester);

	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                Subject subject = new Subject();
	                subject.setSubject_id(rs.getInt("subject_id"));
	                subject.setName(rs.getString("name"));
	                subject.setSemester(rs.getInt("semester"));
	                subject.setSubjectType(rs.getString("subject_type"));
	                subject.setBranchName(rs.getString("branch_name"));

	                subjectList.add(subject);
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            System.out.println("❌ Error while fetching subjects: " + e.getMessage());
	        }

	        return subjectList;
	    }


	  //which subject assign to which faculty
	    public List<Faculty> getAllFacultySubjectAssignments() {
	        List<Faculty> list = new ArrayList<>();

	        String sql = "SELECT f.name AS faculty_name, f.employee_id, \r\n"
	        		+ "       s.name AS subject_name, c.course_name, \r\n"
	        		+ "       s.semester, b.name AS branch_name, s.subject_type\r\n"
	        		+ "FROM faculty_subject fs\r\n"
	        		+ "JOIN faculty f ON fs.employee_id = f.employee_id\r\n"
	        		+ "JOIN subject s ON fs.subject_id = s.subject_id\r\n"
	        		+ "JOIN branch b ON s.branch_id = b.branch_id\r\n"
	        		+ "JOIN course c ON b.course_id = c.course_id";

	        try (Connection conn = Connect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                Faculty dto = new Faculty();

	                dto.setName(rs.getString("faculty_name"));
	                dto.setEmployeeId(rs.getString("employee_id"));
	                dto.setSubjectName(rs.getString("subject_name"));
	                dto.setCourseName(rs.getString("course_name"));
	                dto.setSemester(rs.getInt("semester"));
	                dto.setBranchName(rs.getString("branch_name"));
	                dto.setSubjectType(rs.getString("subject_type"));

	                list.add(dto);
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            System.out.println("❌ Error fetching assigned subjects: " + e.getMessage());
	        }

	        return list;
	    }
}
