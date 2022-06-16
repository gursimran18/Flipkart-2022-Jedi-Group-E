/**
 * 
 */
package com.crs.flipkart.validators;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.dao.StudentDaoInterface;
import com.crs.flipkart.dao.StudentDaoService;
import com.crs.flipkart.exceptions.CourseAlreadyOptedException;


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
}
