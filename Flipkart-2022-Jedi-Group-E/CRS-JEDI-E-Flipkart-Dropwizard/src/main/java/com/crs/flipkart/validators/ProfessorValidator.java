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
public class ProfessorValidator {

	/**
	 * Method to check if professorId teaches courseId
	 * 
	 * @param courseId		courseId against which check is performed
	 * @param professorId	professorId of professor whose courses are evaluated
	 * @param courseList	list of all courses taught by professorId
	 * @returns true if courseId is taught by professorId
	 */
	public static boolean validateCourse(String courseId, String professorId, List<Course> courseList) 
	{
		for(Course course : courseList)
		{
			if(course.getProfessorId() != null && course.getProfessorId().equals(professorId) && course.getCourseId().equals(courseId))
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
		
		if(role != Roles.PROFESSOR)
			return false;
		
		return true;
	}
}
