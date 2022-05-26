package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.GradeCard;
import com.flipkart.bean.Student;
import com.flipkart.constants.SQLQueriesConstants;
import com.flipkart.db.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class StudentDaoImplementation implements StudentDaoInterface {

	ConnectionUtil connectionUtil;
	
	
    public static StudentDaoInterface getInstance() {
        return null;
    }
    

    public StudentDaoImplementation(ConnectionUtil connectionUtil) {
		super();
		this.connectionUtil = connectionUtil;
	}

    public String addStudent(Student student) {
    	System.out.println("Adding Student..");
    	String str = null;
        try(Connection connection = connectionUtil.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesConstants.ADD_USER_QUERY);
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getUserName());
            preparedStatement.setString(3, student.getEmailId());
            preparedStatement.setString(4, student.getPassword());

            int rows = preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement(SQLQueriesConstants.ADD_STUDENT_QUERY);
            preparedStatement1.setString(1, student.getId());
            preparedStatement1.setString(2, student.getFeeStatus());
            preparedStatement1.setBoolean(3, student.isApproved());
            int rowsAffected1 = preparedStatement1.executeUpdate();
            if (rowsAffected1 == 1 && rows == 1) {
                System.out.println("Student is registered");
            }
            str = student.getId();
        }
        catch(SQLException e)
        {
            System.out.println("Student with the ID exists. Try Again!!");
        }
        return str;
    }

    @Override
    public Optional<Student> getStudent(String studentId) throws SQLException {
//    	System.out.println("Check2");
//    	System.out.println(studentId);
    	Optional<Student> opt = Optional.empty();
        Connection conn = connectionUtil.getConnection();
        String sql = "SELECT * FROM student where id='"+studentId+"'";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        String sql1 = "SELECT * FROM user where id='"+studentId+"'";
        PreparedStatement statement1 = conn.prepareStatement(sql1);
        ResultSet rs1 = statement1.executeQuery();
//        System.out.println("Check3");
//        System.out.println(rs.next());
//        System.out.println(rs1.next());
        if(rs.next() && rs1.next())
        {
            //Student student1=new Student(studentId)
        	System.out.println("Here");
            Student student=new Student(studentId, rs1.getString(2),rs1.getString(3), rs1.getString(4), rs.getString(2), rs.getBoolean(3)); //rs1.getString(5), rs.getString(6),rs.getBoolean(7));
            opt = Optional.of(student);
        }
        return opt;
    }
//
//    public Optional<Student> validateCredentials(String studentId, String password){
//    	Optional<Student> student = Optional.empty();
//        try(Connection conn = connectionUtil.getConnection();){
//            
//            String sql = "SELECT * FROM user where userid = ? and password = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1,studentId);
//            statement.setString(2,password);
//            ResultSet rs = statement.executeQuery();
//            if(rs.next())
//            {
//                student= getStudent(studentId);
//                //  Student student=new Student(studentId,rs1.getString(3),rs1.getString(4), rs1.getString(2),rs1.getString(5),studentId,rs.getInt(2),rs.getString(3),rs.getString(4),true);
//                
//            }
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//        return student;
//    }
//    @Override
//    public String getfeeStatus(String studentId) throws SQLException {
//        Connection conn = connectionUtil.getConnection();
//        String sql = "SELECT paymentId FROM bookkeeper where studentId='"+studentId+"'";
//        PreparedStatement statement = conn.prepareStatement(sql);
//        ResultSet rs = statement.executeQuery();
//        while(rs.next())
//        {String x = "Fees Paid";
//            return x;
//        }
//        return "Fees not paid";
//    }

    @Override
    public ArrayList<String> registeredCoursesList(String studentId) throws SQLException {
        Connection conn = connectionUtil.getConnection();
        String sql = "SELECT * FROM registrar where id='"+studentId+"'";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<String> courses=new ArrayList<>();
        while(rs.next())
        {
            courses.add(rs.getString(2));
        }
        return courses;
    }

    @Override
    public ArrayList<Boolean> registerCourses(String studentId, ArrayList<String> courses) throws SQLException {
        Connection connection = connectionUtil.getConnection();
        ArrayList<Boolean> isEnrolled= new ArrayList<>(); 
        
        String sql = "SELECT COUNT(*) FROM registrar where id='"+studentId+"'";
        PreparedStatement alreadyRegdCoursesQuery = connection.prepareStatement(sql); 
        ResultSet rs = alreadyRegdCoursesQuery.executeQuery();
        int alreadyRegCourses = 0;
        if(rs.next())
        {
        	alreadyRegCourses = rs.getInt(1);
        }
        Statement stmt = connection.createStatement();
        
        for(String courseId:courses) {
        	if(alreadyRegCourses==4)
        		break;
        	
        	//CHECK IF THAT COURSE IS ALREADY TAKEN BY THIS STUDENT
//        	String availableSeatsQuerySQL = "SELECT seats from course where courseId = '"+courseId + "'";
//        	PreparedStatement availableSeatsQuery = connection.prepareStatement(availableSeatsQuerySQL); 
//            ResultSet resultSet = availableSeatsQuery.executeQuery();
//            int availableSeats = 0;
//            if(resultSet.next())
//            {
//            	availableSeats = rs.getInt(1);
//            }  
//            if(availableSeats==0)
//            	continue;   	
        	
            //Check if Seats are avaiable in the course
        	String availableSeatsQuerySQL = "SELECT seats from course where courseId = '"+courseId + "'";
        	PreparedStatement availableSeatsQuery = connection.prepareStatement(availableSeatsQuerySQL); 
            ResultSet resultSet = availableSeatsQuery.executeQuery();
            int availableSeats = 0;
            if(resultSet.next())
            {	
            	availableSeats = resultSet.getInt(1);
            }  
            if(availableSeats==0)
            {	
            	isEnrolled.add(false);
            	continue;
            }
            System.out.println(availableSeats);
            
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesConstants.ADD_REGISTERCOURSE_QUERY);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.setString(3, " NA ");
            preparedStatement.executeUpdate();
            
            availableSeats--;
            System.out.println(availableSeats);
            String updateSeatsQuerySQL = "UPDATE COURSE set seats=" + Integer.toString(availableSeats) + " where courseId = '" + courseId +"'";
            PreparedStatement updateSeatsQuery = connection.prepareStatement(updateSeatsQuerySQL);
            updateSeatsQuery.executeUpdate();
            alreadyRegCourses += 1;
            isEnrolled.add(true);
        }
        while(isEnrolled.size()<4)
        	isEnrolled.add(false);
        return isEnrolled;
    }

    @Override
    public ArrayList<Course> viewCourses() throws SQLException {
        ArrayList<Course> courses=new ArrayList<Course>();
        Connection conn = connectionUtil.getConnection();
        String sql = "SELECT * FROM course";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
        {
          Course course=new Course();
          course.setCourseId(rs.getString(1));
          course.setCourseName(rs.getString(2));
          course.setProfId(rs.getString(3));
          course.setSeats(rs.getInt(4));
          courses.add(course);
        }
        return courses;
    }
//
//    @Override
//    public Course viewCourse(int courseId) throws SQLException {
//        ArrayList<Course> courses=new ArrayList<Course>();
//        Connection conn = connectionUtil.getConnection();
//        String sql = "SELECT * FROM course where courseId="+courseId;
//        PreparedStatement statement = conn.prepareStatement(sql);
//        ResultSet rs = statement.executeQuery();
//        while(rs.next())
//        {
//            Course course=new Course();
//            course.setCourseId(rs.getString(1));
//            course.setCourseName(rs.getString(2));
//            return course;
//        }
//        return null;
//    }
//
//    @Override
//    public String removeStudent(String studentId) throws SQLException {
//
//        boolean st = true;
//            Connection con = connectionUtil.getConnection();
//            Statement stmt = con.createStatement();
//            String sql = "delete from student where studentId = " + studentId;
//        int rowsAffected = stmt.executeUpdate(sql);
//        if (rowsAffected == 1) {
//            return "Student Removed!";
//        }
//        return null;
//    }

    @Override
    public ArrayList<GradeCard> viewGrades(String studentId) throws SQLException {
    ArrayList<GradeCard> gradeCards=new ArrayList<>();
        Connection conn = connectionUtil.getConnection();
        String sql = "SELECT * FROM gradecard where id='"+studentId+"'";
        System.out.println(sql);
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
        {
            GradeCard g=new GradeCard(rs.getString(1), rs.getString(2),rs.getString(3));
            gradeCards.add(g);
        }
        return gradeCards;
    }
    
    @Override
    public Boolean dropCourse(String studentId, String c1) throws SQLException{
    	
    	String checkCourseSQL = "SELECT * from registrar where id = '"+studentId + "' and courseId = '" + c1 +"'";
    	Connection conn = connectionUtil.getConnection();
    	PreparedStatement checkCourseQuery = conn.prepareStatement(checkCourseSQL); 
        ResultSet resultSet = checkCourseQuery.executeQuery();
        if(resultSet.next())
        {	
        	String dropCourseSQL = "DELETE from registrar where id = '"+studentId + "' and courseId = '" + c1 +"'";
        	PreparedStatement dropCourseQuery = conn.prepareStatement(dropCourseSQL);
            dropCourseQuery.executeUpdate();
            
            String updateSeatsSQL = "Update COURSE set seats = seats + 1 where courseId = '" + c1 + "'";
            PreparedStatement updateSeatsQuery = conn.prepareStatement(updateSeatsSQL);
            updateSeatsQuery.executeUpdate();
        	
            return true;
        }    	
    	return false;
    }
}