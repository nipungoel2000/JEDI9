package com.flipkart.restApi;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.checkerframework.checker.nullness.Opt;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.dao.ProfessorInterfaceDAO;
//import com.flipkart.dao.ProfessorUtilsInterface;

@Path("/professor")
public class ProfessorRestApi {
	ProfessorInterfaceDAO profUtil;
	
	public ProfessorRestApi(ProfessorInterfaceDAO profUtil) {
		super();
		this.profUtil = profUtil;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeById(@PathParam("id") String userId, @QueryParam("pass") String pass) throws ClassNotFoundException, SQLException {
		Optional<Professor> prof = profUtil.validateCredentialsWithDB(userId, pass);
		if(prof.isPresent()) {
			return Response.ok(prof.get()).build();
		}
		else {
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("/viewAvailableCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewCourses(@PathParam("profId") String profId) throws ClassNotFoundException, SQLException {
		List<Course> courses = profUtil.viewAvailableCoursesWithDB(profId);
		if(courses!=null) {
			return Response.ok(courses).build();
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/viewEnrolStud")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewStudents(@QueryParam("profId") String profId) throws SQLException {
//		if(!(currentProf.getId().equals(profId))) {
//			throw new WebApplicationException(401);
//		}
		Map<String, ArrayList<String>> students = profUtil.viewEnrolledStudentsWithDB(profId);
		if(students!=null) {
			return Response.ok(students).build();
		}
		else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/regCour")
	public Response registerCourses(@QueryParam("profId") String profId, @QueryParam("courseId") String courseId) throws SQLException {

		int ok = profUtil.registerCoursesWithDB(profId, courseId);
		if(ok!=0) {
			return Response.created(URI.create("/professor/regCour")).entity("registered succesfully").build();
		}
		else {
			throw new WebApplicationException(400);
		}
	}
	
	@POST
	@Path("/assignGrades")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignGrades(@QueryParam("professorId") String profId,@QueryParam("courseId") String courseId,@QueryParam("studentId") String studentId,@QueryParam("Grade") String Grade) throws SQLException, IOException {

        //check course in it or not
		int ok = profUtil.provideGrade(courseId, studentId, Grade);
		if(ok!=0) {
			return Response.created(URI.create("/professor/assignGrades")).entity(ok).build();
		}
		else {
			throw new WebApplicationException(400);
		}
    }
	
	
	
	
}
