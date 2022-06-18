/**
 * 
 */
package com.crs.flipkart.restcontroller;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.*;
import com.crs.flipkart.constants.Roles;
import com.crs.flipkart.exceptions.UserNotFoundException;
import com.crs.flipkart.validators.StudentValidator;


@Path("/user")
public class UserRestAPI {
	private static Logger logger = Logger.getLogger(UserRestAPI.class);
	UserInterface userService=new UserService();
	StudentInterface studentService = new StudentService();
	
	/**
	 * Endpoint for Updating the password
	 * 
	 * @param email: email address of the user
	 * @param oldPassword: old password to be stored in db.
	 * @param newPassword: new password to be stored in db.
	 * @return @return 201, if password is updated, else 500 in case of error
	 */
	@POST
	@Path("/updatePassword/{email}/{oldPassword}/{newPassword}")
	public Response updatePassword(
			@NotNull
			@PathParam("email") String email,
			@NotNull
			@PathParam("oldPassword") String oldPassword,
			@NotNull
			@PathParam("newPassword") String newPassword) {
		try
		{
			System.out.println(userService.updatePassword(email, oldPassword, newPassword));
			if(UserService.user!=null)
			{
				UserService.user.setPassword(newPassword);
			}
			logger.info("Password updated successfully!");
			return Response.status(201).entity("Password updated successfully! ").build();
		}
		catch(UserNotFoundException e)
		{
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Email or password is incorrect!").build();
		}
	}
	
	
	/**
	 * Endpoint for Logging in
	 * 
	 * @param email
	 * @param password
	 * @return 200 if logged in successfully, else 500
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verifyCredentials(@NotNull User loginUser) {
			if(UserService.user!=null)
			{
//				logger.error("You need to logout first");
				return Response.status(500).entity("You need to logout first!").build();
			}
		try 
		{
			System.out.println("KKKKKKKKKKKKKKKKKKK");

			String email=loginUser.getEmail();
			String password=loginUser.getPassword();
			System.out.println(email+"______________"+password);
			User user=userService.login(email, password);

			System.out.println("logged in");
			Roles role = Roles.stringToName(user.getRole());
			System.out.println("got role");
			switch(role)
			{
			case STUDENT:
				String studentId=user.getUserId();
				boolean isApproved=StudentValidator.isApproved(studentId);
				if(!isApproved)	
				{
					logger.info("Student not approved");
					return Response.status(200).entity("Login unsuccessful! Student has not been approved by the administration!" ).build();
				}
				break;
			}
			UserService.user=user;
			logger.info("Login successful");
			return Response.status(200).entity("Login successful").build();
		}
		catch (UserNotFoundException e) 
		{
			logger.error("Error : "+e.getMessage());
			return Response.status(500).entity("Invalid credentials!").build();
		}		
		
}
	
	/**
	 * Endpoint for Student Self Registration
	 * 
	 * @param student
	 * @return 201, if user is created, else 500 in case of error
	 */
	@POST
	@Path("/studentRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(@NotNull Student student)
	{
		try
		{
			String status=userService.registerStudent(student.getName(),student.getContactNumber(), student.getEmail(), student.getPassword(), student.getBranch(), student.getBatch());
			if(!status.equals("Account created"))
			{
				logger.error("Account already created");
				return Response.status(500).entity(status).build();
			}
		}
		catch(Exception ex)
		{
			logger.error("Error : "+ex.getMessage());
			return Response.status(500).entity("Something went wrong! Please try again. " + ex.getMessage()).build(); 
		}
		logger.info("Registration successful for "+student.getName());
		return Response.status(201).entity("Registration Successful for "+student.getName()).build(); 
	}
	
	/**
	 * Endpoint for logout
	 * 
	 * @return 200, if user is logged out
	 */
	@POST
    @Path("/logout")
    public Response logout() {
        UserService.user=null;
        logger.info("Logged out successfully");
        return Response.status(200).entity("User logged out Successfully").build();
    }
}
