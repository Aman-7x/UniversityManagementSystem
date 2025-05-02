package com.university.model;

import java.sql.Date;

public class Attendance {
	
	private String subjectName;
	private Date attendanceDate;
	private String status; // P or A
	
	
	    private int subjectId;
	    private String rollNumber;
	    private String employeeId; // faculty's unique employee_id

	    private String studentName;
	
	 

	public int getSubjectId() {
			return subjectId;
		}


		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
		}


		public String getRollNumber() {
			return rollNumber;
		}


		public void setRollNumber(String rollNumber) {
			this.rollNumber = rollNumber;
		}


		public String getEmployeeId() {
			return employeeId;
		}


		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public Date getAttendanceDate() {
		return attendanceDate;
	}


	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	
	 	
	
}
