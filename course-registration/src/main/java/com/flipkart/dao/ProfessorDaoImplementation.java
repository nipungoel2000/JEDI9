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
	public Map<String, ArrayList<String>> viewEnrolledStudentsWithDB(String professorId) throws SQLException {
    	Connection con = connectionUtil.getConnection();
        Map<String,ArrayList<String>> students=new LinkedHashMap<>();
        
        String sql = "select registrar.userId,user.userName,course.courseId,course.courseName " +
                "from registrar,user,course where registrar.courseId in(select courseId from professorreg " +
                "where professorreg.userId='"+professorId+"' ) and registrar.userId=user.userId and " +
                "registrar.courseId=course.courseId ";
        
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while(rs.next())
        {
            String user=rs.getString(1)+" "+rs.getString(2);
            String course=rs.getString(3)+" "+rs.getString(4);
            if(!students.containsKey(course))
                students.put(course,new ArrayList<>());
            students.get(course).add(user);
        }
        return students;
    }
	
    @Override
    public int provideGrade(int courseId,String studentId,String Grade) throws SQLException {
    	Connection con = connectionUtil.getConnection();
        String SQL = "UPDATE registrar set grade='"+Grade+"' where userId='"+studentId+"' and courseId="+courseId;

        int id = 0;
        //inserting into table
        try (
                PreparedStatement pstmt = con.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS)) {

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    
    @Override
    public int registerCoursesWithDB(String professorId,String courseId) throws SQLException {
        ArrayList<Course> courses=new ArrayList<Course>();
        Connection con = connectionUtil.getConnection();
        String SQL = "INSERT INTO professorreg(userId,courseId)"
                + "VALUES(?,?)";

        int id = 0;
        //inserting into table
        try (
                PreparedStatement pstmt = con.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setString(1, professorId);
            pstmt.setString(2, String.valueOf(courseId));



            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    
    @Override
    public ArrayList<Course> viewAvailableCoursesWithDB(String professorId) throws SQLException {
        ArrayList<Course> courses=new ArrayList<Course>();
        Connection con = connectionUtil.getConnection();
        String sql="select courseId,courseName from course where courseId not in (select courseId from professorReg)";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
        {
            Course course=new Course();
            course.setCourseId(rs.getString(1));
            course.setCourseName(rs.getString(2));
            courses.add(course);
        }
        return courses;
    }
    
    @Override
    public Optional<Professor> validateCredentialsWithDB(String userId, String password){
        Optional<Professor> opt = Optional.empty();
    	try(Connection con = connectionUtil.getConnection();){
        	
            String SQL = "select * from user,professor where user.userId like '"+userId+"' and professor.professorId like '"+userId+"' and user.password like '"+password+"'";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            if(rs.next()) {
                Professor professor=new Professor(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                //System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7)+"  "+rs.getString(8));
                opt = Optional.of(professor);
            }
            //while(rs.next())
            //    System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7)+"  "+rs.getString(8));
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

        return opt;
    }

    @Override
    public ArrayList<Course> viewCoursesWithDB() throws SQLException{

        ArrayList<Course> courses=new ArrayList<Course>();
        Connection con = connectionUtil.getConnection();
        String sql = "SELECT * FROM course";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
        {
            Course course=new Course();
            course.setCourseId(rs.getString(1));
            course.setCourseName(rs.getString(2));
            courses.add(course);
        }
        return courses;

    }

}
