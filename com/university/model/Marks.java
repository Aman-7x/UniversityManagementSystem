package com.university.model;

public class Marks {

	private int subjectId;
	private String subjectName;
	private String examType;
	private int marksObtained;
	private int maxMarks;
	private String resultStatus;
	private String remarks;
	private String rollNo;
	private String employee_id;
	private String studentName;
	public Marks(String subjectName, String examType, int marksObtained, int maxMarks, String resultStatus,
			String remarks) {
		   
		this.subjectName = subjectName;
		this.examType = examType;
		this.marksObtained = marksObtained;
		this.maxMarks = maxMarks;
		this.resultStatus = resultStatus;
		this.remarks = remarks;
	}


	public Marks() {
		// TODO Auto-generated constructor stub
	}


	
	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getEmployee_id() {
		return employee_id;
	}


	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}


	public String getRollNo() {
		return rollNo;
	}


	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}


	public int getSubjectId() {
		return subjectId;
	}

	
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	public String getExamType() {
		return examType;
	}


	public void setExamType(String examType) {
		this.examType = examType;
	}


	public int getMarksObtained() {
		return marksObtained;
	}


	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}


	public int getMaxMarks() {
		return maxMarks;
	}


	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}


	public String getResultStatus() {
		return resultStatus;
	}


	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	
	
	
	
}
