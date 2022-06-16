/**
 * 
 */
package com.crs.flipkart.validators;

import java.util.List;

import com.crs.flipkart.bean.Course;


public class AdminValidator {

	/*
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
}
