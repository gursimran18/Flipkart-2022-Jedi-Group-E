package com.crs.flipkart.business;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.exceptions.CourseAlreadyOptedException;
import com.crs.flipkart.exceptions.CourseCountExceededException;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.CourseNotOptedException;

public interface SemesterRegistrationInterface {


	 /**
    * method for getting all the courses from course catalog
    * 
    * @return list of course 
    */
	List<Course> viewCourses();

	 /**
     * method for adding course for the student 
     *
     * @param studentId  unique Id to represent a student
     * @param courseId  unique Id to represent a course
     * @throws throws CourseNotFoundException if course with courseId not in the catalog
     * @throws throws CourseCountExceededException if student has already opted 6 courses
     * @throws throws CourseAlreadyOptedException if course is already opted by student
     */
	void addCourse(String studentId, String courseId) throws CourseNotFoundException, CourseCountExceededException, CourseAlreadyOptedException;
	
	/**
     * method for dropping course for the student 
     *
     * @param studentId  unique Id to represent a student
     * @param courseId  unique Id to represent a course
     * @return returns String which represents the status of dropping course 
	 * @throws CourseNotFoundException if courseId not present in the system
     * @throws CourseNotOptedException if studentId has not opted courseId
     */
	String dropCourse(String studentId, String courseId) throws CourseNotFoundException, CourseNotOptedException;
	
	/**
     * method for getting all opted courses by the student 
     *
     * @param studentId  unique Id to represent a student
     * @return list of courses opted by the student 
     */
	List<Course> viewOptedCourses(String studentId);

	/**
     * method for submitting the course choices of the student 
     *
     * @param studentId  unique Id to represent a student
     * @return returns String which represents the status of submitting course choices 
     */
	String submitOptedCourses(String studentId);

}