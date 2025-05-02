package com.university.model;

import java.sql.Date;
import java.time.LocalDate;

public class Faculty {
    private int facultyId;
    private String name;
    private String email;
    private String contactNumber;
    private String gender;
    private String password;
    private LocalDate createdAt;
    private String employeeId;

     
    private String subjectName;
    private String courseName;
    private int semester;
    private String branchName;
    private String subjectType;
    // All-args constructor
    public Faculty(  String name, String email, String contactNumber, String gender,
                     LocalDate createdAt, String employeeId) {
       
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
       
        this.createdAt = createdAt;
        this.employeeId = employeeId;
    }

     

	 



	public Faculty() {
		// TODO Auto-generated constructor stub
	}







	// Getters and Setters
    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate localDate) {
        this.createdAt = localDate;
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







	public String getCourseName() {
		return courseName;
	}







	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}







	public int getSemester() {
		return semester;
	}







	public void setSemester(int semester) {
		this.semester = semester;
	}







	public String getBranchName() {
		return branchName;
	}







	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}







	public String getSubjectType() {
		return subjectType;
	}







	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
    
    
    
    
}

