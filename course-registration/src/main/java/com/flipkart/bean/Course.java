package com.flipkart.bean;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private String courseId;
	private String profId;
	private String courseName;
	private int seats;
//	private ArrayList<String> preReq;
//	private String department;
//	private int numberOfStudents;
//	private List<String> enrolledStudents;
	
	
	public Course() {
		super();
	}


	public Course(String courseId, String profId, String courseName, int seats) {
		super();
		this.courseId = courseId;
		this.profId = profId;
		this.courseName = courseName;
		this.seats = seats;
	}


	public String getCourseId() {
		return courseId;
	}


	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}


	public String getProfId() {
		return profId;
	}


	public void setProfId(String profId) {
		this.profId = profId;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public int getSeats() {
		return seats;
	}


	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	
	
	
	
}