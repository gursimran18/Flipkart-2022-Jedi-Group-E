/**
 * 
 */
package com.crs.flipkart.business;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.Professor;
import com.crs.flipkart.bean.Student;
import com.crs.flipkart.dao.*;
import com.crs.flipkart.exceptions.CourseAlreadyExistsException;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.GradeNotAllotedException;
import com.crs.flipkart.exceptions.UserNotFoundException;
import com.crs.flipkart.validators.AdminValidator;


public class AdminService implements AdminInterface {

	
	AdminDaoInterface adminDaoService=new AdminDaoService();
	/**
	 * method for viewing all courses in the catalog
	 *
	 * @return returns List of all courses from the catalog
	 */
	public List<Course> viewCourse()
	{
		return adminDaoService.viewCourses();
	}

	/**
	 * method for verifying the presence of a course in the catalog
	 *
	 * @param courseId unique Id to represent a course
	 * @throws throws CourseAlreadyExistsException if the course is present in the catalog
	 */
	public void verifyCourse (String courseId) throws CourseAlreadyExistsException
	{
			List<Course> courseList = viewCourse();
			
			if(AdminValidator.verifyCourse(courseList, courseId))
				throw new CourseAlreadyExistsException(courseId);
	}

	/**
	 * method for adding course into the catalog
	 *
	 * @param newCourse		Course object containing details of the course
	 * @return returns status of addCourse operation as a string
	 */
	public String addCourse(Course newCourse)
	{
		return adminDaoService.addCourse(newCourse);
	}

	/**
	 * method for removing course from the catalog
	 *
	 * @param courseId unique Id to represent a course
	 * @throws throws CourseNotFoundException if the course is not present in the catalog
	 */
	public void dropCourse(String courseId) throws CourseNotFoundException
	{
		try {
			adminDaoService.dropCourse(courseId);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}

	/**
	 * method for getting all admission requests
	 *
	 * @return List of Students who made Admission Request
	 */
	public List<Student> getPendingStudents()
	{
		return adminDaoService.getPendingStudents();
	}

	/**
	 * method for approving students admission request.
	 *
	 * @param studentId unique Id for a student
	 * @return returns status of approveStudent operation as a string
	 */
	public String approveStudent(Student newStudent)
	{
		return adminDaoService.approveStudent(newStudent);
	}

	/**
	 * method for adding a new professor in the system
	 *
	 * @param newProfessor	Professor object containing details of the professor
	 * @return returns status of addProfessor operation as a string
	 * @throws throws EmailAlreadyInUseException if email is already in use
	 */
	public String addProfessor(Professor newProfessor) throws EmailAlreadyInUseException
	{
		try {
			return adminDaoService.addProfessor(newProfessor);
		}
		catch(EmailAlreadyInUseException e) {
			throw e;
		}
		
	}

	/**
	 * method for getting all the professors
	 *
	 * @return List of Professors
	 */
	@Override
	public List<Professor> viewProfessorList() {
		// TODO Auto-generated method stub
		return adminDaoService.viewProfessorList();
	}

	/**
	 * method for removing professor from the system
	 *
	 * @param professorId		unique Id to represent a course
	 * @return returns status of dropProfessor operation as a string
	 * @throws throws UserNotFoundException if professor not present in the system
	 */
	public  void dropProfessor(String professorId) throws UserNotFoundException
	{
		try {
			adminDaoService.dropProfessor(professorId);
		}
		
		catch(UserNotFoundException e) {
			throw e;
		}
	}

	/**
	 * method for getting all pending Grade card request
	 *
	 * @return List of Students whose Grade Card has not been generated
	 */
	public List<Student> getPendingGradeStudents()
	{
		return adminDaoService.getPendingGradeStudents();
	}

	/**
	 * method for generating grade card and calculating aggregate CGPA of student
	 *
	 * @param studentId			unique Id to represent a student
	 * @param semester			semester for which gradeCard is to be generated
	 * @return returns status of generateGradeCard operation as a string
	 * @throws throws GradeNotAllotedException if student hasn't been graded for all subjects
	 */
	public void generateGradeCard(String studentId, String semester) throws GradeNotAllotedException
	{
		try {			
			adminDaoService.generateGradeCard(studentId, semester);
		}
		catch(GradeNotAllotedException e) {
			throw e;
		}
	}
	
}
