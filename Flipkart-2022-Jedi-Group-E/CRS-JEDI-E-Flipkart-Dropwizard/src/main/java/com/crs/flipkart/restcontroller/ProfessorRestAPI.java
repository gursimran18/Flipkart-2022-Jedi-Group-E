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
import com.crs.flipkart.validators.ProfessorValidator;

/**
 *
 */
@Path("/professor")
public class ProfessorRestAPI {

	private static Logger logger = Logger.getLogger(ProfessorRestAPI.class);
	ProfessorInterface professorService = new ProfessorService();
	
	/**
	 * Endpoint for View available courses for professor
	 * 
	 * @return 201, list of courses if user is logged in, else 500 in case of error
	 */
	@GET
    @Path("/viewAvailableCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAvailableCourses() {
        if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        try {
            List<Course> courseList = professorService.viewCourses();
            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
			logger.info("List of available courses returned");
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for View selected courses for professor
	 * 
	 * @return 201, list of courses if user is logged in, else 500 in case of error
	 */
	@GET
    @Path("/viewSelectedCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewSelectedCourses() {
        if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        try {
            List<Course> courseList = professorService.viewSelectedCourses(UserService.user.getUserId());
            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
			logger.info("List of selected courses returned");
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for Indicating courses
	 * 
	 * @param course
	 * @return 201, if course successfully selected by professor, else 500 in case of error
	 */
	@PUT
	@Path("/selectCourse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response selectCourse(@NotNull Course course)
	{
		if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		try {			
			professorService.indicateCourse(UserService.user.getUserId(), course.getCourseId());
			logger.info("Course selected");
			return Response.status(201).entity("Course selected").build();
		}
		catch(Exception e) {
        	logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
		
	}
	
	/**
	 * Endpoint for View enrolled students in a course
	 * 
	 * @param courseId
	 * @return 201, list of students if user is logged in and teaches the given course, else 500 in case of error
	 */
	@GET
    @Path("/viewEnrolledStudents/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewEnrolledStudents(
    		@NotNull
			@PathParam("courseId") String courseId) {
        if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        try {
        	if(!professorService.validateCourse(courseId, UserService.user.getUserId()))
    		{
            	logger.debug("You do not teach this course");
            	return Response.status(500).entity("You do not teach this course").build();
    		}
    		List<Student>enrolledStudents=professorService.viewEnrolledStudents(courseId);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(enrolledStudents) {};
			logger.info("List of enrolled students returned");
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for View enrolled students in a course
	 * 
	 * @param courseId
	 * @return 201, list of students if user is logged in and teaches the given course, else 500 in case of error
	 */
	@GET
    @Path("/viewUngradedStudents/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewUngradedStudents(
    		@NotNull
			@PathParam("courseId") String courseId) {
        if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        try {
        	if(!professorService.validateCourse(courseId, UserService.user.getUserId()))
    		{
            	logger.debug("You do not teach this course");
            	return Response.status(500).entity("You do not teach this course").build();
    		}
    		List<Student>enrolledStudents=professorService.viewUngradedStudents(courseId);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(enrolledStudents) {};
			logger.info("List of ungraded students returned");
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }

	/**
	 * Endpoint for Grading the student
	 * 
	 * @param grade
	 * @return 201, if grade successfully added, else 500 in case of error
	 */
	@POST
	@Path("/gradeStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response gradeStudent(@NotNull Grade grade)
	{
		if (!ProfessorValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
		try {	
			if(!professorService.validateCourse(grade.getCourseId(), UserService.user.getUserId()))
    		{
            	logger.debug("You do not teach this course");
            	return Response.status(500).entity("You do not teach this course").build();
    		}
			List<Student> enrolledStudents = professorService.viewEnrolledStudents(grade.getCourseId());
			boolean flag=false;
			for(Student student:enrolledStudents)
			{
				if(student.getStudentEnrollmentId().equals(grade.getStudentEnrollmentId()))
				{
					flag=true;
					break;
				}
			}
			if(!flag)
			{
            	logger.debug("Student has not enrolled in this course");
            	return Response.status(500).entity("Student has not enrolled in this course").build();
			}
			List<Student> ungradedStudents = professorService.viewUngradedStudents(grade.getCourseId());
			for(Student student:ungradedStudents)
			{
				System.out.println(student.getStudentEnrollmentId());
				if(student.getStudentEnrollmentId().equals(grade.getStudentEnrollmentId()))
				{
					professorService.gradeStudent(grade.getStudentEnrollmentId(), grade.getCourseId(), grade.getStudentGrade(), grade.getSemester());
					logger.info("Student has been graded");
					return Response.status(201).entity("Student has been graded").build();
				}
			}
        	logger.debug("This student has already been graded");
			return Response.status(500).entity("This student has already been graded").build();
		}
		catch(Exception e) {
        	logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Error : " + e.getMessage()).build();
		}
		
	}
	
}
