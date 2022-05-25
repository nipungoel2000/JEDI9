package com.flipkart.restApi;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.flipkart.bean.Course;
import com.flipkart.bean.GradeCard;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.dao.StudentDaoInterface;
import com.google.protobuf.Option;

@Path("/student")
public class StudentRestApi {
	StudentDaoInterface studInter;
	
	
	public StudentRestApi(StudentDaoInterface studInter) {
		super();
		this.studInter = studInter;
	}


	@POST
	@Path("/addStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(Student s) throws Exception {
		String stuId = studInter.addStudent(s);
		//exception if return false
		if(stuId!=null) {
			return Response.created(URI.create("/students/addStudent")).entity(s).build();
		}
		else {
			throw new WebApplicationException(400);
		}
	}
	
	@GET
	@Path("/viewDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent(@QueryParam("studentId") String stuId) throws SQLException {
		System.out.println("Check1");
		Optional<Student> stu = studInter.getStudent(stuId);
		if(stu.isPresent()) {
			return Response.ok(stu.get()).build();
		}
		else {
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("/viewRegisteredCourses/{studentId}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response semesterRegistration(@PathParam("studentId") String studId) throws SQLException {
		List<String> courseList = studInter.registeredCoursesList(studId);
		return Response.ok(courseList).build();
	}
	
	@GET
	@Path("/viewCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewAvailableCourses() throws SQLException {
		ArrayList<Course> availCourse = studInter.viewCourses();
		return Response.ok(availCourse).build();
	}
	
	@POST
	@Path("/registerCourses")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCourses(
			@QueryParam("course1") String c1,
            @QueryParam("course2") String c2,
            @QueryParam("course3") String c3,
            @QueryParam("course4") String c4,
            @QueryParam("studentId") String studentId
            ) throws SQLException {
		ArrayList<String> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        studInter.registerCourses(studentId, list);
        return Response.status(201).entity(list).build();
	}
	
	@GET
    @Path("/viewReportCard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewReportCard(@QueryParam("studentId") String studentId) {
        ArrayList<GradeCard> gradeCards;
        try {
            gradeCards = studInter.viewGrades(studentId);
        }catch(SQLException se) {
            se.printStackTrace();
            return Response.status(201).entity("Some Exception Occured !! check logs").build();
        }
        return Response.ok(gradeCards).build();
    }
	
	
	
}
