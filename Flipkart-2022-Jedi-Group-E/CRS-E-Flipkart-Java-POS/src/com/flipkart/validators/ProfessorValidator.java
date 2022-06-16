/**
 * 
 */
package com.crs.flipkart.validators;

import java.util.List;

import com.crs.flipkart.bean.Course;


public class ProfessorValidator {

	/*
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
	
	
}
