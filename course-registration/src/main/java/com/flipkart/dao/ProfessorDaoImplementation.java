package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.db.ConnectionUtil;

public class ProfessorDaoImplementation implements ProfessorInterfaceDAO {

	ConnectionUtil connectionUtil;
	

    public ProfessorDaoImplementation(ConnectionUtil connectionUtil) {
		super();
		this.connectionUtil = connectionUtil;
	}
    
    @Override
	public Map<String, ArrayList<String>> viewEnrolledStudentsWithDB(String professorId){
    	Map<String,ArrayList<String>> students=new LinkedHashMap<>();
    	try(Connection con = connectionUtil.getConnection();){
	    	
	        
	        String sql = "select id, courseId from registrar where courseId in (select courseId from course where profid=?)";
	        
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, professorId);
	        ResultSet rs = statement.executeQuery();
	
	        while(rs.next())
	        {
	            String user=rs.getString(1);
	            String course=rs.getString(2);
	            if(!students.containsKey(course))
	                students.put(course,new ArrayList<>());
	            students.get(course).add(user);
	        }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
        return students;
    }
	
    @Override
    public int provideGrade(String courseId,String studentId,String Grade){
    	
        String SQL = "UPDATE registrar set grade='"+Grade+"' where Id='"+studentId+"' and courseId='"+courseId+"'";

        int id = 0;
        //inserting into table
        try(Connection con = connectionUtil.getConnection();){
        	
            PreparedStatement pstmt = con.prepareStatement(SQL); 
            int affectedRows = pstmt.executeUpdate();
            
            // check the affected rows
            if (affectedRows > 0) {
                id = 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    
    @Override
    public int registerCoursesWithDB(String professorId,String courseId) throws SQLException {
        
        String sql = "UPDATE course set profId=? where courseid=?";
        
        int id = 0;
        //inserting into table
        try(Connection con = connectionUtil.getConnection();){
	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	
	
	            pstmt.setString(1, professorId);
	            pstmt.setString(2, courseId);
	
	
	            int affectedRows = pstmt.executeUpdate();
	            System.out.println(affectedRows);
	            // check the affected rows
	            if (affectedRows > 0) {
	                id = 1;
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
        }
        catch(SQLException ex) {
        	ex.printStackTrace();
        }
        return id;
    }
    
    @Override
    public ArrayList<Course> viewAvailableCoursesWithDB(String professorId){
        ArrayList<Course> courses=new ArrayList<Course>();
        
        try(Connection con = connectionUtil.getConnection();){
	        String sql="select * from course where profId is null";
	        PreparedStatement statement = con.prepareStatement(sql);
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
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return courses;
    }
    
    @Override
    public Optional<Professor> validateCredentialsWithDB(String userId, String password){
        Optional<Professor> opt = Optional.empty();
    	try(Connection con = connectionUtil.getConnection();){
        	
            String SQL = "select * from user,professor where user.Id like '"+userId+"' and professor.Id like '"+userId+"' and user.password like '"+password+"'";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            if(rs.next()) {
                Professor professor=new Professor(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                opt = Optional.of(professor);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return opt;
    }
}
