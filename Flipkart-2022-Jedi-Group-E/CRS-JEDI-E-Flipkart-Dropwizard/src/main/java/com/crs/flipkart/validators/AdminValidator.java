/**
 * 
 */
package com.crs.flipkart.validators;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.User;
import com.crs.flipkart.constants.Roles;

/**
 * @author jasan
 *
 */
public class AdminValidator {

	/**
	 * Method to check is courseId is present in the list of courses
	 * 
	 * @param courseList 	List of all courses in the system
	 * @param courseId		courseId against which check is performed
	 * @return true if courseId is present in the system
	 */
	public static boolean verifyCourse(List<Course> courseList, String courseId)
	{
		for(Course course:courseList)
		{
			if(course.getCourseId().equals(courseId))
				return true;
		}
	
		return false;
	}
	
	/**
	 * method to check legitimacy of user present in the system
	 * 
	 * @param user user object that contains details of the user
	 * @return returns true if user is a valid user
	 */
	public static boolean validateUser(User user)
	{
		if(user == null)
			return false;
		
		Roles role = Roles.stringToName(user.getRole());
		
		if(role != Roles.ADMIN)
			return false;
		
		return true;
	}
}
