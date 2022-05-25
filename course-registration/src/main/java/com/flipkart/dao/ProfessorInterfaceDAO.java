package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public interface ProfessorInterfaceDAO {
    public Optional<Professor> validateCredentialsWithDB(String userId,String password) throws ClassNotFoundException, SQLException;
//    public ArrayList<Course> viewCoursesWithDB() throws SQLException, ClassNotFoundException;

    public int registerCoursesWithDB(String professorId,String courseId) throws SQLException;
    public ArrayList<Course> viewAvailableCoursesWithDB(String professorId) throws SQLException ;
    public Map<String, ArrayList<String>> viewEnrolledStudentsWithDB(String professorId) throws SQLException;
    public int provideGrade(String courseId,String studentId,String Grade) throws SQLException;

}