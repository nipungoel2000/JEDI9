package com.flipkart.dao;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.db.ConnectionUtil;


import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AdminDaoImplementation implements AdminDaoInterface{
	
	ConnectionUtil connectionUtil;
	
    private volatile AdminDaoImplementation instance = null;
    public AdminDaoImplementation() {}
    public AdminDaoImplementation getInstance() {
        if (instance == null) {
            synchronized (AdminDaoImplementation.class) {
                instance = new AdminDaoImplementation();
            }
        }
        return instance;
    }
    
    public AdminDaoImplementation(ConnectionUtil connectionUtil) {
		super();
		this.connectionUtil = connectionUtil;
	}
    
    
	@Override
    public boolean addProfessor(Professor professor) {
        boolean ok = true;
        try(Connection con = connectionUtil.getConnection();){
            Statement stmt = con.createStatement();
            String sql = "insert into user values('" + professor.getId() + "' , '"+ professor.getUserName()+ "', '" + professor.getEmailId() + "', '" + professor.getPassword()+ "')";
            stmt.executeUpdate(sql);
            sql = "insert into professor values('"+ professor.getId()+ "' ,' "+professor.getDepartment()+"')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            ok = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }

	@Override
    public Optional<Admin> validateCredentials(String adminId, String password){
    	Optional<Admin> opt = Optional.empty();
        try(Connection conn = connectionUtil.getConnection();){

            String sql = "SELECT * FROM user where id=? and password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, adminId);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            System.out.println("hello2");
            if(rs.next()) {
            	System.out.println("hello");
            	
				Admin adm = new Admin(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4));
				
				opt = Optional.of(adm);
			}
        }
        catch(Exception e){
            System.out.println(e);
       
        }
        return opt;
    }
    @Override
    public boolean addCourse(Course course) {
        boolean ok = true;
        try{
            Connection con = connectionUtil.getConnection();
            Statement stmt = con.createStatement();
            if(con==null)System.out.println("connection not established");
            System.out.println("Connection established");
            String sql = "insert into course values('" + course.getCourseId() + "' , '" + course.getCourseName()+ "' , '" + course.getProfId() + "'," +course.getSeats() +");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            ok = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean dropCourse(String courseId) {
        boolean ok = true;
        try{
            Connection con = connectionUtil.getConnection();
            
            if(con==null)System.out.println("connection not established");
            String sql = "DELETE FROM course WHERE courseId= '" + courseId +"';";
            Statement stmt = con.createStatement();
//            stmt.setString(1,courseId);
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            ok = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean approveStudents() {
        boolean ok = true;
        try{
            Connection con = connectionUtil.getConnection();
            Statement stmt = con.createStatement();
            if(con==null)System.out.println("connection not established");
            Scanner in= new Scanner(System.in);
            int ch =1;
            while(ch!=0) {
                String sql = "select * from student where isApproved= 0";
                ResultSet rs = stmt.executeQuery(sql);
                int flag=0;
                System.out.println("Here is a list of all pending students ++++++++++++");
                while (rs.next()) {
                    int sId = rs.getInt(1);
                    System.out.println(rs.getInt(1));
                    flag=1;
//                String s = "select * from user where userId = " +sId;
//                Statement st = con.createStatement();
//                ResultSet r = st.executeQuery(s);
//                System.out.println(r.getString(3)+ " " +r.getString(4));
                }
                if(flag==1) {
                    System.out.println("Enter student id");
                    int id = in.nextInt();
                    sql = "UPDATE student SET isApproved = 1 where studentId = " + id;
                    stmt.executeUpdate(sql);
                }
                else{
                    System.out.println("<<<<<<< No student left to be approved >>>>>>>>>>>");
                }
                System.out.println("To exit, press 0 : To continue, press 1");
                ch = in.nextInt();
            }
        } catch (SQLException e) {
            ok = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ok;
    }
    public boolean isApproved(String studentId) throws Exception{
        Connection con = connectionUtil.getConnection();
        String sql = "select * from student where studentId=? and isApproved = 1";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,studentId);
        ResultSet rs =  stmt.executeQuery();
        while(rs.next()) {
            return true;
        }
        return false;
    }
    public void approveStudents(String studentId) throws Exception {
        String id = studentId;
        String sql1 = "UPDATE student SET isApproved = 1 where studentId = ?";
        Connection con = connectionUtil.getConnection();
        PreparedStatement statement = con.prepareStatement(sql1);
        statement.setString(1,id);
        statement.executeUpdate();
    }
//
//    @Override
//    public ArrayList<Grade> fetchGrade(int userId) {
//        return null;
//    }
	@Override
	public boolean approveStudent(String studentId) {
		// TODO Auto-generated method stub
		return false;
	}
}