package com.university.model;

public class Subject {
	  private String name;
	    private String subjectType;
	    private int semester;

	    private String branchName;
	    private int subject_id;
	    
	    private int branch_id;
	    private String subject_name;
	    
	    // Constructor
	    public Subject(String name, String subjectType, int semester, String branchName) {
	        this.name = name;
	        this.subjectType = subjectType;
	        this.semester = semester;
	        this.branchName=branchName;
	    }

	    // Getters and Setters
	    
	    
	    
	    public Subject() {
			// TODO Auto-generated constructor stub
		}

		public String getName() {
	        return name;
	    }

	    public String getSubject_name() {
			return subject_name;
		}

		public void setSubject_name(String subject_name) {
			this.subject_name = subject_name;
		}

		public int getBranch_id() {
			return branch_id;
		}

		public void setBranch_id(int branch_id) {
			this.branch_id = branch_id;
		}

		public int getSubject_id() {
			return subject_id;
		}

		public void setSubject_id(int subject_id) {
			this.subject_id = subject_id;
		}

		public void setName(String name) {
	        this.name = name;
	    }

	    public String getSubjectType() {
	        return subjectType;
	    }

	    public void setSubjectType(String subjectType) {
	        this.subjectType = subjectType;
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
			this.branchName=branchName;
		}
}
