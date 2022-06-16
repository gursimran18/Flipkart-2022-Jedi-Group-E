/**
 * 
 */
package com.crs.flipkart.business;

import java.sql.*;
import com.crs.flipkart.bean.*;
import com.crs.flipkart.dao.*;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.UserNotFoundException;


public class UserService implements UserInterface {
	UserDaoInterface userDaoService = new UserDaoService();

	/**
	 * Method to validate the credentials of a user
	 *
	 * @param emailId of the user
	 * @param password of the user
	 * @return returns a User containing user info if found in the system else returns null
	 * @throws throws UserNotFoundException if user credentials aren't present in the system
	 */
	public User login(String emailId, String password) throws UserNotFoundException
	{
		try {
			return userDaoService.login(emailId, password);
		} catch (UserNotFoundException e) {
			throw e;
		}
	}

	/**
	 * Method to register a new student in the system
	 *
	 * @param name of the user
	 * @param contact number of the user
	 * @param emailId of the user
	 * @param password of the user
	 * @param branch of the user
	 * @param batch of the user
	 * @return returns a string that indicates if the student is successfully registered in the system
	 * @throws throws EmailAlreadyInUseException if email is already used by another user
	 */
	public String registerStudent(String name,String contactNumber, String email, String password, String branch, String batch) throws EmailAlreadyInUseException
	{
		try {
			return userDaoService.registerStudent(name,contactNumber, email, password, branch, batch);
		} catch (EmailAlreadyInUseException e) {
			throw e;
		}
	}

	/**
	 * Method to update password of the user
	 *
	 * @param emailId of the user
	 * @param old password of the user
	 * @param new password of the user
	 * @return returns a string that indicates if the password is changed successfully
	 * @throws throws UserNotFoundException if user credentials aren't present in the system
	 */
	public String updatePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException
	{
		try {
			return userDaoService.updatePassword(email, oldPassword, newPassword);
		} catch (UserNotFoundException e) {
			throw e;
		}
	}
}
