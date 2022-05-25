package com.flipkart.bean;

public class GradeCard {
	private String id;
	private String courseId;
	private String grade;
	
	public GradeCard(String id, String courseId, String grade) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.grade = grade;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	

}