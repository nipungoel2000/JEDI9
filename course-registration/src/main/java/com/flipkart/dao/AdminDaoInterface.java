package com.flipkart.dao;

import java.util.List;
import java.util.Optional;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;

public interface AdminDaoInterface {
	default public  boolean addProfessor(Professor professor) {
        return false;
    }
	public boolean addCourse(Course course);
	public boolean dropCourse(String string);
	public boolean approveStudents();
	public boolean approveStudent(String studentId);
    public Optional<Admin> validateCredentials(String adminId, String password);

}