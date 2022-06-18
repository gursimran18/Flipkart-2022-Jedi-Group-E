/**
 * 
 */
package com.crs.flipkart.restcontroller;

import java.util.List;

import javax.validation.constraints.NotNull;
/**
 * @author Shubham
 *
 */
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.*;
import com.crs.flipkart.validators.StudentValidator;


@Path("/student")
public class StudentRestAPI {
	
	private static Logger logger = Logger.getLogger(StudentRestAPI.class);
	StudentInterface studentService = new StudentService();
	SemesterRegistrationInterface semesterRegistrationService = new SemesterRegistrationService();
	
	
	/**
	 * Endpoint for View all courses
	 * 
	 * @return 201,List of courses if student is logged in, otherwise 500
	 */
	@GET
    @Path("/viewCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses() {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        try {
            List<Course> courseList = semesterRegistrationService.viewCourses();
            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
            logger.info("Success");
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for add course to opted courses list
	 * 
	 * @param course
	 * @return 201, if course is opted successfully, else 500
	 */
	@POST
    @Path("/addCourse")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addCourse(@NotNull Course course) {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have finalized the courses!");
        	return Response.status(500).entity("You have finalized the courses!").build();
		}
        try {
        	semesterRegistrationService.addCourse(UserService.user.getUserId(),course.getCourseId());
        	return Response.status(201).entity("Course added successfully!").build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Error : "+e.getMessage()).build();
        }
    }
	
	/**
	 * Endpoint for drop course from opted courses
	 * 
	 * @param course
	 * @return 201, if course is dropped successfully, else 500 in case of error
	 */
	@DELETE
    @Path("/dropCourse")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response dropCourse(@NotNull Course course) {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have finalized the courses!");
        	return Response.status(500).entity("You have finalized the courses!").build();
		}
        try {
        	String status=semesterRegistrationService.dropCourse(UserService.user.getUserId(),course.getCourseId());
        	return Response.status(201).entity(status).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Error : "+e.getMessage()).build();
        }
    }
	
	/**
	 * Endpoint for view opted courses
	 * 
	 * @return 201, and list of courses if user logged in, else 500 in case of error
	 */
	@GET
    @Path("/viewOptedCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewOptedCourses() {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have finalized the courses!");
        	return Response.status(500).entity("You have finalized the courses!").build();
		}
        try {
            List<Course> courseList = semesterRegistrationService.viewOptedCourses(UserService.user.getUserId());
            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for Submit opted courses
	 * 
	 * @return 201, if courses submitted successfully, else 500 in case of error
	 */
	@PUT
    @Path("/submitChoices")
    public Response submitChoices() {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have finalized the courses!");
        	return Response.status(500).entity("You have finalized the courses!").build();
		}
        try {
        	String status = semesterRegistrationService.submitOptedCourses(UserService.user.getUserId());
        	if(!(status.equals("Please select some other courses as some courses are filled and dropped from the list")||status.equals("Please select atleast 4 courses")))
        		return Response.status(201).entity(status).build();
        	return Response.status(500).entity(status).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for view Registered courses
	 * 
	 * @return 201, and list of courses if user logged in, else 500 in case of error
	 */
	@GET
    @Path("/viewRegisteredCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewRegisteredCourses() {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(!StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have not finalized the courses!");
        	return Response.status(500).entity("You have not finalized the courses!").build();
		}
        try {
            List<Course> courseList = studentService.viewRegisteredCourses(UserService.user.getUserId());
            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(courseList) {};
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for getting total fee
	 * 
	 * @return 201, and total fee if user is logged in, else 500 in case of error
	 */
	@GET
    @Path("/getTotalFee")
    public Response getTotalFee(){
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(!StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have not finalized the courses!");
        	return Response.status(500).entity("You have not finalized the courses!").build();
		}
        if(StudentValidator.getFeeStatus(UserService.user.getUserId()))
		{
        	logger.debug("You have already paid the fees!");
        	return Response.status(500).entity("You have already paid the fees!").build();
		}
        try {
        	float fee = studentService.getTotalFee(UserService.user.getUserId());
            return Response.status(201).entity("Fee : "+fee).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for Make payment
	 * 
	 * @param payment
	 * @return 201, if payment is successful, else 500 in case of error
	 */
	@POST
    @Path("/makePayment")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response makePayment(@NotNull Payment payment) {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(!StudentValidator.submittedCourses(UserService.user.getUserId()))
		{
        	logger.debug("You have not finalized the courses!");
        	return Response.status(500).entity("You have not finalized the courses!").build();
		}
        if(StudentValidator.getFeeStatus(UserService.user.getUserId()))
		{
        	logger.debug("You have already paid the fees!");
        	return Response.status(500).entity("You have already paid the fees!").build();
		}
        if(!StudentValidator.verifyFeeAmount(studentService.getTotalFee(UserService.user.getUserId()), payment.getAmount()))
		{
			logger.debug("Incorrect amount entered!");
        	return Response.status(500).entity("Incorrect amount entered!").build();
		}

        try {
        	String status = studentService.makePayment(UserService.user.getUserId(),payment.getPaymentType(),payment.getAmount());
            return Response.status(201).entity(status).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Something went wrong").build();
        }
    }
	
	/**
	 * Endpoint for viewing grade card
	 * 
	 * @return 201, and gradeCard if user is logged in, else 500 in case of error
	 */
	@GET
    @Path("/gradeCard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewGradeCard() {
        if (!StudentValidator.validateUser(UserService.user)) {
        	logger.debug("User not authenticated");
        	return Response.status(500).entity("User not authenticated").build();
        }
        if(!StudentValidator.getFeeStatus(UserService.user.getUserId()))
		{
        	logger.debug("You have not paid the fees!");
        	return Response.status(500).entity("You have not paid the fees!").build();
		}
        try {
        	GradeCard gradeCard = studentService.viewGradeCard(UserService.user.getUserId());
        	GenericEntity<GradeCard> entity = new GenericEntity<GradeCard>(gradeCard) {};
            return Response.ok(entity).build();
        } catch (Exception e) {
        	logger.error("Error : "+e.getMessage());
        	return Response.status(500).entity("Error : "+e.getMessage()).build();
        }
    }
}
