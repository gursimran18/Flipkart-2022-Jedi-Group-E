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
	public static User user =null;
	public User login(String emailId, String password) throws UserNotFoundException
	{
		try {
			System.out.println(":::::::inside busines::::::::");
			System.out.println(emailId+"   :::     "+password);
			return userDaoService.login(emailId, password);
		} catch (UserNotFoundException e) {
			throw e;
		}
	}

	public String registerStudent(String name,String contactNumber, String email, String password, String branch, String batch) throws EmailAlreadyInUseException
	{
		try {
			return userDaoService.registerStudent(name,contactNumber, email, password, branch, batch);
		} catch (EmailAlreadyInUseException e) {
			throw e;
		}
	}

	public String updatePassword(String email, String oldPassword, String newPassword) throws UserNotFoundException
	{
		try {
			return userDaoService.updatePassword(email, oldPassword, newPassword);
		} catch (UserNotFoundException e) {
			throw e;
		}
	}
}
