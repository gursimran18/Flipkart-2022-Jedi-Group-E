package com.crs.flipkart.business;

import java.util.*;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.dao.*;
import com.crs.flipkart.exceptions.CourseAlreadyOptedException;
import com.crs.flipkart.exceptions.CourseCountExceededException;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.CourseNotOptedException;
import com.crs.flipkart.validators.StudentValidator;

public class SemesterRegistrationService implements SemesterRegistrationInterface{

	CatalogDaoInterface catalogDaoService = new CatalogDaoService();
	SemesterRegistrationDaoInterface semesterRegistrationDaoService = new SemesterRegistrationDaoService();
	private static Logger logger = Logger.getLogger(SemesterRegistrationService.class);

	@Override
	public List<Course> viewCourses() {
		return catalogDaoService.viewCourses();
	}

	@Override
	public void addCourse(String studentId, String courseId) throws CourseNotFoundException, CourseCountExceededException, CourseAlreadyOptedException{
		// TODO Auto-generated method stub
		List<Course> optedCourses = semesterRegistrationDaoService.viewOptedCourses(studentId);
		if (optedCourses.size() >= 6)
			throw new CourseCountExceededException(6);
		
		if(StudentValidator.isCourseOpted(optedCourses, courseId))
			throw new CourseAlreadyOptedException(courseId);
		
		try {			
			semesterRegistrationDaoService.addCourse(studentId, courseId);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}

	@Override
	public String dropCourse(String studentId, String courseId) throws CourseNotFoundException, CourseNotOptedException{
		// TODO Auto-generated method stub
		try {
			return semesterRegistrationDaoService.dropCourse(studentId,courseId);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	@Override
	public List<Course> viewOptedCourses(String studentId) {
		return semesterRegistrationDaoService.viewOptedCourses(studentId);
	}

	@Override
	public String submitOptedCourses(String studentId) {
		// TODO Auto-generated method stub
		List<Course> optedCourses = semesterRegistrationDaoService.viewOptedCourses(studentId);
		if(optedCourses.size()<4)
			return "Please select atleast 4 courses";
		int count=0;
		String end="You have registered for : \n";
		for(Course course:optedCourses)
		{
			if(count<4 && course.getStudentCount()<10)
			{
				count++;
				end+=course.getCourseId()+" : "+course.getCourseName()+"\n";
			}
			else
			{
				try {
				semesterRegistrationDaoService.dropCourse(studentId, course.getCourseId());
				}
				catch(Exception e)
				{
					logger.error("Error: "+e.getMessage());
				}
			}
		}
		if(count<4)
			return "Please select some other courses as some courses are filled and dropped from the list";
		return semesterRegistrationDaoService.submitOptedCourses(studentId)+"\n"+end;
	}

}
