package com.flipkart.constants;

public class SQLQueriesConstants {

    public static final String ADD_REGISTERCOURSE_QUERY="insert into registrar values (?, ?, ?)";
    public static final String ADD_STUDENT_QUERY = "insert into student values (?, ?, ?)";
    public static final String ADD_USER_QUERY = "insert into user values (?, ?, ?, ?)";
    public static final String UPDATE_PASSWORD="update user set password = ? where userid=?";
    public static final String GET_DETAILS="select id,password from user where id = ? and password = ?";
    public static final String VIEW_GRADE_CARD = "select * from gradeCard where id = ?";
    public static final String VIEW_REGISTERED_COURSES=" select * from course inner join gradeCard on course.courseId = gradeCard.courseId where gradeCard.userid = ?";
}
