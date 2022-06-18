/**
 * 
 */
package com.crs.flipkart.validators;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.User;
import com.crs.flipkart.constants.Roles;
import com.crs.flipkart.dao.StudentDaoInterface;
import com.crs.flipkart.dao.StudentDaoService;

/**
 * @author jasan
 *
 */
public class StudentValidator {

	static StudentDaoInterface studentDaoService = new StudentDaoService();
	
	
	/**
     * method for checking if course is opted by student or not
     *
     * @param optedCourse 	list of all optedCourses
     * @param courseId 	courseId against which check is performed
     * @return returns true if student has opted courseId
     */
	public static boolean isCourseOpted(List<Course> optedCourses, String courseId)
	{
		for (Course optedCourse : optedCourses) {
			if (optedCourse.getCourseId().equals(courseId)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
     * method for checking that student is approved by administrator or not
     *
     * @param studentId  unique Id to represent a student
     * @return returns true if student is approved by administrator
     */
	public static boolean isApproved(String studentId)
	{
		boolean flag = false;
		
		flag = studentDaoService.isApproved(studentId);
		
		return flag;
	}
	
	/**
     * method for checking that student has already submitted their course choices or not
     * 
     * @param studentId  unique Id to represent a student
     * @return returns true if course choices already submitted by student
     */
	public static boolean submittedCourses(String studentId)
	{
		boolean flag = false;
		
		flag = studentDaoService.submittedCourses(studentId);
		
		return flag;
	}

	/**
     * method for getting the fee status of the student
     *
     * @param studentId  unique Id to represent a student
     * @return returns true if student paid the fees
     */
	public static boolean getFeeStatus(String studentId)
	{
		boolean flag = false;
		
		flag = studentDaoService.getFeeStatus(studentId);
		
		return flag;
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
		
		if(role != Roles.STUDENT)
			return false;
		
		return true;
	}
	
	
	/**
	 * method to check if fee deposited by student is correct or not
	 * 
	 * @param systemFee		Fee generated by the system based on course selection
	 * @param userFee		Fee paid by the student
	 * @return returns true if user pays the correct amount
	 */
	public static boolean verifyFeeAmount(float systemFee, float userFee)
	{
		return systemFee == userFee;
	}
}
