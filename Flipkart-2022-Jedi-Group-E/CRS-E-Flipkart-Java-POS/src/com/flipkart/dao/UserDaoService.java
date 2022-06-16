/**
 * 
 */
package com.crs.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.User;
import com.crs.flipkart.constants.Roles;
import com.crs.flipkart.constants.SQLQueries;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.UserNotFoundException;
import com.crs.flipkart.utils.dbUtil;


public class UserDaoService implements UserDaoInterface {

	private static Logger logger = Logger.getLogger(UserDaoService.class);
	public static Connection conn = dbUtil.getConnection();

	/**
	 * Method to check if a user with given email-id and password exists
	 *
	 * @param emailId of the user
	 * @param password of the user
	 * @return returns a User containing user info id found in database or returns null
	 * @throws throws UserNotFoundException if user credentials aren't present in the database
	 */
	@Override
	public User login(String emailId, String password) throws UserNotFoundException{
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_USER_EMAIL_PASSWORD);
			ps.setString(1, emailId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("user.userId"));
				user.setRole(rs.getString("role"));
				
				if(user.getRole().equals("Student")) {
					user.setName(getStudentName(user.getUserId()));
				}
				if(user.getRole().equals("Professor")) {
					user.setName(getProfessorName(user.getUserId()));
				}
				return user;
			}
			else {
				throw new UserNotFoundException(emailId);
			}

		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return null;
		}
	}


	/**
	 * Method to get the name of the student
	 *
	 * @param studentId of the student
	 * @return returns the name of the student
	 */
	public String getStudentName(String studentId){
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_STUDENT);
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();


			if(rs.next()) {
				return rs.getString("name");
			}
			return null;

		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Method to get the name of the professor
	 *
	 * @param professorId of the professor
	 * @return returns the name of the professor
	 */
	public String getProfessorName(String professorId){
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_PROFESSOR);
			ps.setString(1, professorId);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				return rs.getString("name");
			}
			return null;

		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Method to enter a new user in the database
	 *
	 * @param name of the user
	 * @param emailId of the user
	 * @param password of the user
	 * @param role of the user
	 * @return returns a string that indicates if the user is successfully entered in the database
	 * @throws throws EmailAlreadyInUseException if email is already present in the database
	 */
	@Override
	public String createUser(String name, String email, String password, Roles role) throws EmailAlreadyInUseException{

		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.VERIFY_EMAIL);

			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				throw new EmailAlreadyInUseException(email);
			}

		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return "User not created";
		}

		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.ALL_USER_ID);
			ResultSet rs=ps.executeQuery();
			List<String> userIds=new ArrayList<String>();
			while(rs.next())
			{
				userIds.add(rs.getString("userId"));
			}
			Random rand = new Random();
			String userId;
			while(true)
			{
				long id = 10000000 + rand.nextInt(10000000);
				userId = Roles.nameToString(role).charAt(0) + Long.toString(id);
				if(!userIds.contains(userId))
					break;
			}
			ps = conn.prepareStatement(SQLQueries.ADD_USER_QUERY);
			ps.setString(1, userId);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.executeUpdate();
			ps = conn.prepareStatement(SQLQueries.ADD_USER_ROLE);
			ps.setString(1,userId);
			ps.setString(2, Roles.nameToString(role));
			ps.executeUpdate();
			return userId;
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return "User not created";
		}
	}


	/**
	 * Method to enter a new student in the database
	 *
	 * @param name of the user
	 * @param contact number of the user
	 * @param emailId of the user
	 * @param password of the user
	 * @param branch of the user
	 * @param batch of the user
	 * @return returns a string that indicates if the student is successfully entered in the database
	 * @throws throws EmailAlreadyInUseException if email is already present in the database
	 */
	
	@Override
	public String registerStudent(String name, String contactNumber, String email, String password, String branch, String batch) throws EmailAlreadyInUseException{
		try {
			String id = createUser(name, email, password, Roles.STUDENT);
			if (id.equals("User not created")) {
				return id;
			} else {
				PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_STUDENT);
				ps.setString(1, id);
				ps.setString(2, name);
				ps.setString(3, contactNumber);
				ps.setString(4, branch);
				ps.setString(5, batch);
				ps.setInt(6, 0);
				ps.setInt(7, 0);
				
				System.out.println("ID = "+id);
				
				if (ps.executeUpdate() == 1)
					return "Account created";
			}
		}
		catch(EmailAlreadyInUseException e) {
			throw e;
		}
		catch (Exception e) {
			logger.debug("Error: " + e.getMessage());
			return "User not created";
		}
		return "User not created";
	}


	/**
	 * Method to update password of the user
	 *
	 * @param emailId of the user
	 * @param old password of the user
	 * @param new password of the user
	 * @return returns a string that indicates if the password is changed successfully
	 * @throws throws UserNotFoundException if user credentials aren't present in the database
	 */
	@Override
	public String updatePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException{
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.UPDATE_PASSWORD);
			ps.setString(2, email);
			ps.setString(3, oldPassword);
			ps.setString(1, newPassword);
			int rowAffected = ps.executeUpdate();
			if (rowAffected == 1)
				return "Password Updated";
			else 
				throw new UserNotFoundException(email);
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return "Password not updated";
		}
	}
}
