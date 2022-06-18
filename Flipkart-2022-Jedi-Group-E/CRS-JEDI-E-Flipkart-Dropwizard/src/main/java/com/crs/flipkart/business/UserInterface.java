/**
 * 
 */
package com.crs.flipkart.business;

import com.crs.flipkart.bean.User;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.UserNotFoundException;


public interface UserInterface {
	
	/**
	 * Method to validate the credentials of a user
	 * 
	 * @param emailId of the user
	 * @param password of the user
	 * @return returns a User containing user info if found in the system else returns null
	 * @throws throws UserNotFoundException if user credentials aren't present in the system
	 */
	User login(String emailId, String password) throws UserNotFoundException;

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
	String registerStudent(String name,String contactNumber, String email, String password, String branch, String batch) throws EmailAlreadyInUseException;

	/**
	 * Method to update password of the user
	 * 
	 * @param emailId of the user
	 * @param old password of the user
	 * @param new password of the user
	 * @return returns a string that indicates if the password is changed successfully
	 * @throws throws UserNotFoundException if user credentials aren't present in the system
	 */
	String updatePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException;

}

