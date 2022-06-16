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
	
	public void indicateCourse(String professorId,String courseId) throws CourseNotFoundException, CourseNotAvailableException, CourseAlreadyIndicatedException
	{
		try {			
			professorDaoService.selectCourse(professorId, courseId);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public boolean validateCourse(String courseId, String professorId) 
	{
		List<Course> courseList = professorDaoService.viewCourses();
		
		return ProfessorValidator.validateCourse(courseId, professorId, courseList);
	}
	
	public List<Student> viewEnrolledStudents(String courseId)
	{
		return professorDaoService.viewEnrolledStudents(courseId);
	}
	
	public List<Student> viewUngradedStudents(String courseId)
	{
		return professorDaoService.viewUngradedStudents(courseId);
	}
	
	public String gradeStudent(String studentId, String courseId, float grade,String semester)
	{
		return professorDaoService.addGrade(studentId, courseId, grade, semester);
	}
}

