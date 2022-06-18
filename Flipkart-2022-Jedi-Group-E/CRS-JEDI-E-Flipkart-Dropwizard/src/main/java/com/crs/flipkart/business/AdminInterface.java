/**
 * 
 */
package com.crs.flipkart.business;

import com.crs.flipkart.exceptions.*;

import java.sql.SQLException;
import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.Professor;
import com.crs.flipkart.bean.Student;
import com.crs.flipkart.exceptions.*;


public interface AdminInterface {

	/**
     * method for viewing all courses in the catalog
     *
     * @return returns List of all courses from the catalog
     */
	List<Course> viewCourse();
	
	/**
     * method for verifying the presence of a course in the catalog
     *
     * @param courseId unique Id to represent a course
	 * @throws throws CourseAlreadyExistsException if the course is present in the catalog
     */
	void verifyCourse (String courseId) throws CourseAlreadyExistsException;
	
	/**
     * method for adding course into the catalog
     *
     * @param newCourse		Course object containing details of the course
     * @return returns status of addCourse operation as a string
     */
	String addCourse(Course newCourse);
	
	/**
     * method for removing course from the catalog
     *
     * @param courseId unique Id to represent a course
     * @throws throws CourseNotFoundException if the course is not present in the catalog
     */
	void dropCourse(String courseId) throws CourseNotFoundException;
	
	/**
     * method for getting all admission requests
     *
     * @return List of Students who made Admission Request
     */
	List<Student> getPendingStudents();
	
	/**
     * method for approving students admission request.
     *
     * @param studentId unique Id for a student
     * @return returns status of approveStudent operation as a string
     */
	String approveStudent(Student newStudent);
	
	/**
     * method for adding a new professor in the system
     *
     * @param newProfessor	Professor object containing details of the professor
     * @return returns status of addProfessor operation as a string
     * @throws throws EmailAlreadyInUseException if email is already in use
     */
	String addProfessor(Professor newProfessor) throws EmailAlreadyInUseException;
	
	/**
     * method for getting all the professors
     *
     * @return List of Professors
     */
	List<Professor> viewProfessorList();
	
	/**
     * method for removing professor from the system
     *
     * @param professorId		unique Id to represent a course
     * @return returns status of dropProfessor operation as a string
     * @throws throws UserNotFoundException if professor not present in the system
     */
	void dropProfessor(String professorId) throws UserNotFoundException;
	
	/**
     * method for getting all pending Grade card request
     *
     * @return List of Students whose Grade Card has not been generated
     */
	List<Student> getPendingGradeStudents();

	
	/**
     * method for generating grade card and calculating aggregate CGPA of student
     *
     * @param studentId			unique Id to represent a student
     * @param semester			semester for which gradeCard is to be generated
     * @return returns status of generateGradeCard operation as a string
     * @throws throws GradeNotAllotedException if student hasn't been graded for all subjects
     */
	void generateGradeCard(String studentId, String semester) throws GradeNotAllotedException;
	
}
