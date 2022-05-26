package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.GradeCard;
import com.flipkart.bean.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface StudentDaoInterface
{
    String addStudent(Student student) throws SQLException, ClassNotFoundException;

    Optional<Student> getStudent(String studentId) throws SQLException;

//    Optional<Student> validateCredentials(String studentId, String password);

//    String getfeeStatus(String studentId) throws SQLException;

    ArrayList<String> registeredCoursesList(String studentId) throws SQLException;

    ArrayList<Boolean> registerCourses(String studentId,ArrayList<String> list) throws SQLException;

    ArrayList<Course> viewCourses() throws SQLException;

//    Course viewCourse(int courseId) throws SQLException;

//    String removeStudent(String studentId) throws SQLException;

    ArrayList<GradeCard> viewGrades(String studentId) throws SQLException;
    
    Boolean dropCourse(String studentId, String c1) throws SQLException;

}