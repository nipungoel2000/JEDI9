package com.flipkart.bean;

public class GradeCard {
	private String userId;
	private String courseId;
	private String grade;
	
	public GradeCard(String userId, String courseId, String grade) {
		super();
		this.userId = userId;
		this.courseId = courseId;
		this.grade = grade;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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