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
	
	public List<Course> viewCourse()
	{
		return adminDaoService.viewCourses();
	}

	public void verifyCourse (String courseId) throws CourseAlreadyExistsException
	{

			List<Course> courseList = viewCourse();
			
			if(AdminValidator.verifyCourse(courseList, courseId))
				throw new CourseAlreadyExistsException(courseId);
		
	}
	
	public String addCourse(Course newCourse)
	{
		return adminDaoService.addCourse(newCourse);
	}
	
	public void dropCourse(String courseId) throws CourseNotFoundException
	{
		try {
			adminDaoService.dropCourse(courseId);
		}
		catch(CourseNotFoundException e) {
			throw e;
		}
	}
	
	public List<Student> getPendingStudents()
	{
		return adminDaoService.getPendingStudents();
	}
	
	public String approveStudent(Student newStudent)
	{
		return adminDaoService.approveStudent(newStudent);
	}
	
	public String addProfessor(Professor newProfessor) throws EmailAlreadyInUseException
	{
		try {
			return adminDaoService.addProfessor(newProfessor);
		}
		catch(EmailAlreadyInUseException e) {
			throw e;
		}
		
	}
	
	@Override
	public List<Professor> viewProfessorList() {
		// TODO Auto-generated method stub
		return adminDaoService.viewProfessorList();
	}
	
	public  void dropProfessor(String professorId) throws UserNotFoundException
	{
		try {
			adminDaoService.dropProfessor(professorId);
		}
		
		catch(UserNotFoundException e) {
			throw e;
		}
	}	
	
	public List<Student> getPendingGradeStudents()
	{
		return adminDaoService.getPendingGradeStudents();
	}
	
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
