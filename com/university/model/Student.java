package com.university.model;

import java.time.LocalDate;

public class Student {

		private String name;
	    private String email;
	    private String contactNumber;
	    private String gender;
	    private int branchId;
	    private int semester;
	    private String password;
	    private String rollno;
	    private LocalDate created_at;
	    private int course_id;
	    private String branchName;
	    private String courseName;
	    
	    //Constructor
	    public Student(String name, String email, String contactNumber, String gender, int branchId, int semester,
	    		String password , String rollno) {
	    	super();
	    	this.name = name;
	    	this.email = email;
	    	this.contactNumber = contactNumber;
	    	this.gender = gender;
	    	this.branchId = branchId;
	    	this.semester = semester;
	    	this.password = password;
	    	this.rollno=rollno;
	    }

	    public Student() {
	    	// TODO Auto-generated constructor stub
	    }

	    
	    //Getter Setter 
	    



		public String getName() {
			return name;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		public int getCourse_id() {
			return course_id;
		}

		public void setCourse_id(int course_id) {
			this.course_id = course_id;
		}

		public LocalDate getCreated_at() {
			return created_at;
		}

		public void setCreated_at(LocalDate created_at) {
			this.created_at = created_at;
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

		public int getBranchId() {
			return branchId;
		}

		public void setBranchId(int branchId) {
			this.branchId = branchId;
		}

		public int getSemester() {
			return semester;
		}

		public void setSemester(int semester) {
			this.semester = semester;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRollno() {
			return rollno;
		}

		public void setRollno(String rollno) {
			this.rollno = rollno;
		}
	    
		
	     
	    
	    
}
