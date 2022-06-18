/**
 * 
 */
package com.crs.flipkart.business;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.Student;
import com.crs.flipkart.exceptions.CourseAlreadyIndicatedException;
import com.crs.flipkart.exceptions.CourseNotAvailableException;
import com.crs.flipkart.exceptions.CourseNotFoundException;


public interface ProfessorInterface {

	/**
	 * Method for getting the list of all courses available for the professor to choose
	 * 
	 * @return returns a list of all the courses where professorId is null
	 */
	List<Course> viewCourses();
	
	/**
	 * Method for getting the list of all courses selected by the professor
	 * 
	 * @param professorId unique Id to represent a professor
	 * @return returns a list of all the courses where professorId matches the professorId provided 
	 */
	List<Course> viewSelectedCourses(String professorId);
	
	/**
	 * Method for the professor to indicate their courses
	 * 
	 * @param professorId unique Id to represent a professor
	 * @param courseId unique Id to represent a course
	 * @return returns a string indicating if a course is successfully alloted
	 * @throws CourseNotFoundException if course with courseId not present in the system
	 * @throws CourseNotAvailableException if course with courseId already alloted to some professor
	 * @throws CourseAlreadyIndicatedException if course with courseId already alloted to professorId
	 */
	void indicateCourse(String professorId,String courseId) throws CourseNotFoundException, CourseNotAvailableException, CourseAlreadyIndicatedException;
	
	/**
	 * Method to check whether the course is taught by the professor or not
	 * 
	 * @param courseId unique Id to represent a course
	 * @param professorId unique Id to represent a professor
	 * @return returns true if the course is taught by the professor otherwise false
	 */
	boolean validateCourse(String courseId, String professorId);
	
	/**
	 * Method to view all the students enrolled in the course
	 * 
	 * @param courseId unique Id to represent a course
	 * @return returns a list of all the students enrolled in the course
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
	 * Method for the professor to grade a student for a particular course
	 * 
	 * @param studentId unique Id to represent a student
	 * @param courseId unique Id to represent a course
	 * @param grade floating point number that represents the grade provided for the course
	 * @param semester indicates the semester
	 * @return returns a string indicating the student was graded successfully
	 */
	String gradeStudent(String studentId, String courseId, float grade,String semester);
}
