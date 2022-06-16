/**
 * 
 */
package com.crs.flipkart.business;

import java.util.ArrayList;
import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.Student;
import com.crs.flipkart.dao.CatalogDaoInterface;
import com.crs.flipkart.dao.CatalogDaoService;
import com.crs.flipkart.dao.ProfessorDaoInterface;
import com.crs.flipkart.dao.ProfessorDaoService;
import com.crs.flipkart.exceptions.CourseNotAvailableException;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.validators.ProfessorValidator;
import com.crs.flipkart.exceptions.CourseAlreadyIndicatedException;


public class ProfessorService implements ProfessorInterface {

	ProfessorDaoInterface professorDaoService = new ProfessorDaoService();

	/**
	 * Method for getting the list of all courses available for the professor to choose
	 *
	 * @return returns a list of all the courses where professorId is null
	 */
	public List<Course> viewCourses()
	{
		List<Course> courseList = professorDaoService.viewCourses();
		List<Course> newCourseList = new ArrayList<Course> ();
		
		for(Course course : courseList)
		{
			if(course.getProfessorId() == null)
				newCourseList.add(course);
		}
		
		return newCourseList;
	}

	/**
	 * Method for getting the list of all courses selected by the professor
	 *
	 * @param professorId unique Id to represent a professor
	 * @return returns a list of all the courses where professorId matches the professorId provided
	 */
	public List<Course> viewSelectedCourses(String professorId)
	{
		List<Course> courseList = professorDaoService.viewCourses();
		List<Course> newCourseList = new ArrayList<Course> ();
		
		for(Course course : courseList)
		{
			if(course.getProfessorId() != null && course.getProfessorId().equals(professorId))
				newCourseList.add(course);
		}
		
		return newCourseList;
	}

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
	public void indicateCourse(String professorId,String courseId) throws CourseNotFoundException, CourseNotAvailableException, CourseAlreadyIndicatedException
	{
		try {			
			professorDaoService.selectCourse(professorId, courseId);
		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Method to check whether the course is taught by the professor or not
	 *
	 * @param courseId unique Id to represent a course
	 * @param professorId unique Id to represent a professor
	 * @return returns true if the course is taught by the professor otherwise false
	 */
	public boolean validateCourse(String courseId, String professorId) 
	{
		List<Course> courseList = professorDaoService.viewCourses();
		
		return ProfessorValidator.validateCourse(courseId, professorId, courseList);
	}

	/**
	 * Method to view all the students enrolled in the course
	 *
	 * @param courseId unique Id to represent a course
	 * @return returns a list of all the students enrolled in the course
	 */
	public List<Student> viewEnrolledStudents(String courseId)
	{
		return professorDaoService.viewEnrolledStudents(courseId);
	}

	/**
	 * Method for retrieving the ungraded students enrolled in a course using SQL commands
	 *
	 * @param courseId unique Id to represent a course
	 * @return returns a list of strings indicating the ungraded students enrolled in a course from the database
	 */
	public List<Student> viewUngradedStudents(String courseId)
	{
		return professorDaoService.viewUngradedStudents(courseId);
	}

	/**
	 * Method for the professor to grade a student for a particular course
	 *
	 * @param studentId unique Id to represent a student
	 * @param courseId unique Id to represent a course
	 * @param grade floating point number that represents the grade provided for the course
	 * @param semester indicates the semester
	 * @return returns a string indicating the student was graded successfully
	 */
	public String gradeStudent(String studentId, String courseId, float grade,String semester)
	{
		return professorDaoService.addGrade(studentId, courseId, grade, semester);
	}
}

