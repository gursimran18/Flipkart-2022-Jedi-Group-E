package com.crs.flipkart.dao;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.CourseNotOptedException;

public interface SemesterRegistrationDaoInterface {

	/**
	 * Method to return the opted course list for a given student
	 * 
	 * @param student id of the student
	 * @return returns a list of courses that the student has opted
	 */
	List<Course> viewOptedCourses(String studentId);
	
	/**
	 * Method to enter a course for the student into the opted course list
	 * 
	 * @param student id of the student
	 * @param course if of the course to be added
	 * @return returns a string that indicates if the course is successfully opted
	 */
	void addCourse(String studentId, String courseId) throws CourseNotFoundException;

	/**
	 * Method to remove a course from the opted course list for student
	 * 
	 * @param student id of the student
	 * @param course if of the course to be added
	 * @return returns a string that indicates if the course is dropped opted
	 * @throws CourseNotFoundException 
	 * @throws CourseNotOptedException 
	 */
	String dropCourse(String studentId, String courseId) throws CourseNotFoundException, CourseNotOptedException;

	/**
	 * Method to submit the courses in the opted course list for a student
	 * 
	 * @param student id of the student
	 * @return returns a string that indicates if the courses are successfully registered in
	 */
	String submitOptedCourses(String studentId);
	
	/**
	 * Method to increase the student count when the student registers for the course successfully
	 * 
	 * @param student id of the student
	 * @return returns a boolean that indicates if the course count is successfully incremented
	 */
	boolean increaseStudentCount(String studentId);

}