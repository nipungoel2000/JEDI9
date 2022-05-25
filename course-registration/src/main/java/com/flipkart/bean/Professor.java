package com.flipkart.bean;

import java.util.ArrayList;

public class Professor extends User{
	String department;
	String courseId;

	public Professor() {
		super();
	}
	public Professor(String id, String userName, String emailId, String password, String department) {
		super(id, userName, emailId, password);
		this.department = department;
//		this.courseId = courseId;
	}

//	public String getCourseId() {
//		return courseId;
//	}
//
//	public void setCourseId(String courseId) {
//		this.courseId = courseId;
//	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	

}