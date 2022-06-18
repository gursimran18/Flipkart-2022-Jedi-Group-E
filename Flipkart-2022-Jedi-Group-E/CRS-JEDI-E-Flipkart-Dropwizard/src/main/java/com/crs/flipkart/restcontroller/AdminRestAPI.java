/**
 * 
 */
package com.crs.flipkart.restcontroller;

import java.util.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.*;
import com.crs.flipkart.validators.AdminValidator;

@Path("/administrator")
public class AdminRestAPI {

	private static Logger logger = Logger.getLogger(AdminRestAPI.class);
	AdminInterface adminService = new AdminService();
	
	/**
	 * Endpoint for view courses
	 * 
	 * @return 201, and list of courses if user logged in, else 500 in case of error
	 */
	@GET
    @Path("/viewCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses() {
		
//		if(!AdminValidator.validateUser(UserService.user))
//		{
//			logger.debug("User not authenticated");
//        	return Response.status(500).entity("User not authenticated").build();
//		}
//
//        if (UserService.user == null || !UserService.user.getRole().equals("Admin")) {
//        	logger.debug("User not authenticated");
//        	return Response.status(500).entity("User not authenticated").build();
//        }
//
        List<Course> courseList = adminService.viewCourse();
        GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
        logger.info("List of courses returned");
        return Response.ok(entity).build();
    }
	
	/**
	 * Endpoint for adding a course
	 * 
	 * @param course
	 * @return 201, if course successfully added, else 500 in case of error
	 */
	@POST
	@Path("/addCourse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCourse(@NotNull Course course)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		try {	
			adminService.verifyCourse(course.getCourseId());
			adminService.addCourse(course);
			logger.info("Course successfully added");
			return Response.status(200).entity("Course successfully added").build();
		}
		catch(Exception e) {
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
	}
	
	/**
	 * Endpoint for dropping the course
	 * 
	 * @param course
	 * @return 201, if course successfully dropped, else 500 in case of error
	 */
	@DELETE
	@Path("/dropCourse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dropCourse(@NotNull Course course)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		
		try {
			adminService.dropCourse(course.getCourseId());
			logger.info("Course dropped successfully");
			return Response.status(200).entity("Course dropped successfully").build();
		}
		catch(Exception e)
		{
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
	}
	
	/**
	 * Endpoint for getting the list of approval pending students
	 * 
	 * @return 201, list of students if user logged in, else 500 in case of error
	 */
	@GET
	@Path("/approvalPendingStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPendingStudents() {
		
        if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        List<Student> studentList = adminService.getPendingStudents();
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(studentList) {};
        logger.info("List of approval pending students");
        return Response.ok(entity).build();
    }
	
	/**
	 * Endpoint for approving the student
	 * 
	 * @param student
	 * @return 201, if student successfully approved, else 500 in case of error
	 */
	@PUT
	@Path("/approveStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveStudent(@NotNull Student student)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		
		List<Student> studentList = adminService.getPendingStudents();
		
		for(Student s : studentList)
		{
			if(s.getStudentEnrollmentId().equals(student.getStudentEnrollmentId()))
			{
				adminService.approveStudent(student);
				logger.info("Student approved successfully");
				return Response.status(200).entity("Student approved successfully").build();
			}
		}
		
		return Response.status(500).entity("Student not approved").build();
	}
	
	/**
	 * Endpoint for adding the professor
	 * 
	 * @param professor
	 * @return 201, if professor successfully added, else 500 in case of error
	 */
	@POST
	@Path("/addProfessor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProfessor(@NotNull Professor professor)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		try {	
			adminService.addProfessor(professor);
			logger.info("Professor successfully added");
			return Response.status(200).entity("Professor successfully added").build();
		}
		catch(Exception e) {
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
	}
	
	/**
	 * Endpoint for deleting the professor
	 * 
	 * @param professor
	 * @return 201, if professor successfully dropped, else 500 in case of error
	 */
	@DELETE
	@Path("/dropProfessor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dropProfessor(@NotNull Professor professor)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }

		try {
			adminService.dropProfessor(professor.getProfessorId());
			logger.info("Professor dropped successfully");
			return Response.status(200).entity("Professor dropped successfully").build();
		}
		catch(Exception e)
		{
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
	}
	
	/**
	 * Endpoint for Getting the list of professors
	 * 
	 * @return 201, list of professors if user logged in, else 500 in case of error
	 */
	@GET
	@Path("/viewProfessorList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewProfessorList()
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		
		List<Professor> professorList = adminService.viewProfessorList();
        GenericEntity<List<Professor>> entity = new GenericEntity<List<Professor>>(professorList) {};
        logger.info("List of professors returned");
        return Response.ok(entity).build();
	}
	
	/**
	 * Endpoint for Getting the list of students whose grade card is not yet generated
	 * 
	 * @return 201, list of students if user logged in, else 500 in case of error
	 */
	@GET
	@Path("/pendingGradeCardStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPendingCardStudents() {
		
        if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        List<Student> studentList = adminService.getPendingGradeStudents();
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(studentList) {};
        logger.info("List of students whose Grade card hasn't been returned");
        return Response.ok(entity).build();
    }
	
	/**
	 * Endpoint for Generating the grade card of student
	 * 
	 * @param student
	 * @return 201, if gradecard successfully generated, else 500 in case of error
	 */
	@POST
	@Path("/generateGradeCard")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateGradeCard(@NotNull Student student)
	{
		if (!AdminValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }

		List<Student> pendingStudents = adminService.getPendingGradeStudents();
		
		for(Student pendingStudent : pendingStudents)
		{
			if(pendingStudent.getStudentEnrollmentId().equals(student.getStudentEnrollmentId()))
			{
				try {
					adminService.generateGradeCard(student.getStudentEnrollmentId(), "1");
					logger.info("GradeCard generated successfully");
					return Response.status(200).entity("GradeCard generated successfully").build();
				}
				catch(Exception e)
				{
					logger.error("Error : "+e.getMessage());
					return Response.status(500).entity("Error : " + e.getMessage()).build();
				}
			}
		}
		logger.debug("GradeCard already generated for student");
		return Response.status(500).entity("GradeCard already generated for student").build();
	}
}
