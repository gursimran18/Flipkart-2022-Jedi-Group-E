package com.crs.flipkart.dao;

import java.util.List;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.exceptions.CourseAlreadyIndicatedException;
import com.crs.flipkart.exceptions.CourseNotAvailableException;
import com.crs.flipkart.exceptions.CourseNotFoundException;

/**
 * 
 * ProfessorDaoService dao interface
 */
public interface ProfessorDaoInterface {

	/**
	 * Method to obtain a list of courses using SQL commands
	 * 
	 * @return returns the courses present in the database
	 */
	List<Course> viewCourses();

	/**
	 * Method to check if the course is already alloted or not, and then to allot the course using SQL commands
	 * 
	 * @param professorId unique Id to represent a professor
	 * @param courseId unique Id to represent a course
	 * @return returns a string that indicates if the course is successfully alloted in the database
	 * @throws CourseNotFoundException if course with courseId not present in the database
	 * @throws CourseNotAvailableException if course with courseId already alloted to some professor
	 * @throws CourseAlreadyIndicatedException if course already selected by professor
	 */
	void selectCourse(String professorId, String courseId) throws CourseNotFoundException, CourseNotAvailableException, CourseAlreadyIndicatedException;

	/**
	 * Method for retrieving the students enrolled in a course using SQL commands
	 * 
	 * @param courseId unique Id to represent a course
	 * @return returns a list of strings indicating the students enrolled in a course from the database
	 */
	List<Student> viewEnrolledStudents(String courseId);
	
	
	/**
	 * Method for retrieving the ungraded students enrolled in a course using SQL commands
	 * 
	 * @param courseId unique Id to represent a course
	 * @return returns a list of strings indicating the ungraded students enrolled in a course from the database
	 */
	List<Student> viewUngradedStudents(String courseId);
	
	/**
	 * Method for adding the grades of a student for a course in a semester using SQL commands
	 * 
	 * @param studentId unique Id to represent a student
	 * @param courseId unique Id to represent a course
	 * @param grade grade point provided for the student enrolled in the course taught by the professor
	 * @param semester indicates the semester
	 * @return returns a string indicating the if the grade was successfully added in the database
	 */
	String addGrade(String studentId, String courseId, float grade, String semester);

}