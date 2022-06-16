
package com.crs.flipkart.application;

import java.util.*;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.*;
import com.crs.flipkart.exceptions.*;


public class CRSAdmin {
	AdminInterface adminService = new AdminService();
	Scanner sc = new Scanner(System.in);
	
	/**
     * method for displaying admin dashboard and selecting the available choices.
     */
	public void create_menu()
	{
		Scanner sc = new Scanner(System.in);
		int choice;
		do {

			    System.out.println("---------------------ADMIN DASHBOARD-----------------------------\n\n");
			    System.out.println("Choose one of the following options: ");
			    System.out.println("1 -> For Student Approval");
				System.out.println("2 -> To view Professor List");
				System.out.println("3 -> To add Professor");
				System.out.println("4 -> To drop Professor");
				System.out.println("5 -> To view Course List");
				System.out.println("6 -> To add Course");
				System.out.println("7 -> To drop Course ");
				System.out.println("8 -> To generate GradeCard");
				System.out.println("9 -> Logout");
				choice = sc.nextInt();
			
				switch (choice) {
				
				case 1:
					approveStudent();
					break;
					
				case 2: 
					// Add Course;
					viewProfessorList();
					break;
					
				case 3:
					// Drop Course
					addProfessor();
					break;
					
				case 4:
					dropProfessor();
					break;
					
				case 5:
					viewCourses();
					break;
					
				case 6:
					// View Professor List
					addCourse();
					break;
					
				case 7:
					dropCourse();
					break;
				
				case 8:
					// Generate Grade Card
					generateGradeCard();
					break;
					
				case 9:
					CRSApplication.loggedIn = false;
					break;			
					
				default:
					System.out.println("Invalid Entry");
			} 
			    
			
		}while(CRSApplication.loggedIn);
	}
	
	 /**
     * method for viewing the courses
     */
	public void viewCourses() {
		// TODO Auto-generated method stub
		
		List<Course>courseList=adminService.viewCourse();
		
		if(courseList.size()==0)
		{
			System.err.println("No courses added yet!!");
			return;
		}
		
		System.out.println(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","PROFESSOR ID"));
		courseList.forEach(CRSStudent::printCourse);
		
	}
	
	/**
     * method for adding a course
     */
	public void addCourse() {
		// TODO Auto-generated method stub
		Course course=new Course();
		System.out.print("Enter the Course ID.....\n");
		course.setCourseId(sc.nextLine());
		
		try {
			adminService.verifyCourse(course.getCourseId());
			System.out.print("Enter the Course Name......\n");
			course.setCourseName(sc.nextLine());
			System.out.print("Enter the Department Name......\n");
			course.setDepartmentName(sc.nextLine());
			System.out.print("Enter the Course Fee........ \n");
			course.setCourseFee(sc.nextFloat());
			sc.nextLine();
			course.setProfessorId(null);
			course.setStudentCount(0);
			System.out.println(adminService.addCourse(course));
		}
		catch (CourseAlreadyExistsException e){
			System.err.println("Error Found: " + e.getMessage());
		}
	}
	
	/**
     * method for removing a course
     */
	public void dropCourse() {
		// TODO Auto-generated method stub
		System.out.print("Enter the Course Id.......\n");
		String courseId=sc.nextLine();
		try {
			adminService.dropCourse(courseId);
			System.out.println("Course has been dropped successfully!!!");
		}
		catch(CourseNotFoundException e) {
			System.err.println("Error Found: " + e.getMessage());
		}
		
	}
	
	 /**
     * method for approving student registration
     */
	public void approveStudent() {
		// TODO Auto-generated method stub
		System.out.println("Student entries to be approved by the admin...");
		List<Student> studentList = adminService.getPendingStudents();
		
		if(studentList.size() == 0) {
			System.err.println("Student Approval List empty....No student left to be approved...");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","S/No","Student ID", "Student Name"));
		for(Student student:studentList)
		{
			System.out.println(String.format("%10s %20s %20s",i,student.getStudentEnrollmentId(), student.getName()));
			i++;
		}
		System.out.print("Enter the serial number of student to be approved....");
		int studentIndex=sc.nextInt();
		
		if(studentIndex<=0 || studentIndex>=i)
		{
			System.err.println("Invalid entry of serial number.....");
			return;
		}
		
		System.out.println(adminService.approveStudent(studentList.get(studentIndex-1)));
		
	}
	
	 /**
     * method for adding a professor
     */
	public void addProfessor() {
		// TODO Auto-generated method stub
		Professor newProfessor = new Professor();
		System.out.print("Enter the Professor Name....");
		newProfessor.setName(sc.nextLine());
		System.out.print("Enter the Email id....");
		newProfessor.setEmail(sc.nextLine());
		System.out.print("Enter Password ");
		newProfessor.setPassword(sc.nextLine());
		System.out.print("Enter the department name :");
		newProfessor.setDepartment(sc.nextLine());
		System.out.print("Enter the professor salary :");
		newProfessor.setSalary(sc.nextFloat());
		sc.nextLine();
		System.out.print("Enter the contact number:");
		newProfessor.setContactNumber(sc.nextLine());
		System.out.print("Enter the DOJ :");
		newProfessor.setDoj(sc.nextLine());
		
		try {
			System.out.println(adminService.addProfessor(newProfessor));
		}
		catch(EmailAlreadyInUseException e) {
			System.err.println("Error Found: " + e.getMessage());
		}
	}
	
	 /**
     * method for removing a professor
     */
	public void dropProfessor() {
		// TODO Auto-generated method stub
		viewProfessorList();
		List<Professor> professorList = adminService.viewProfessorList();
		
		if(professorList.size() == 0) {
			System.err.println("Empty Professor list!!!!");
			return ;
		}
		System.out.print("Enter the serial number of Professor to be dropped!!!");
		int index=sc.nextInt();
		
		if(index <=0 || index >professorList.size())
		{
			System.err.println("Invalid serial number entered!!");
			return;
		}
		
		try {
			adminService.dropProfessor(professorList.get(index-1).getProfessorId());
			System.out.println("Respective Professor has been dropped successfully");
		}
		catch(UserNotFoundException e) {
			System.err.println("Error Found: " + e.getMessage());
		}
	}
	
	 /**
     * method for viewing all professors
     */
	public void viewProfessorList() {
		// TODO Auto-generated method stub
		List<Professor> professorList = adminService.viewProfessorList();
		
		if(professorList.size()==0)
		{
			System.err.println("Professor Database Empty!!!");
			return;
		}
		
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s %20s","S/No","PROFESSOR ID", "PROFESSOR NAME", "DEPARTMENT"));
		for(Professor professor:professorList)
		{
			System.out.println(String.format("%10s %20s %20s %20s",i,professor.getProfessorId(), professor.getName(), professor.getDepartment()));
			i++;
		}
	}
	
	 /**
     * method for generating the student grade card
     */
	public void generateGradeCard() {
		// TODO Auto-generated method stub
		List<Student> studentList = adminService.getPendingGradeStudents();
		
		if(studentList.size() == 0) {
			System.err.println("All the Grade Cards have been generated already....");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","S/No","Student ID", "Student NAME"));
		for(Student student:studentList)
		{
			System.out.println(String.format("%10s %20s %20s",i,student.getStudentEnrollmentId(), student.getName()));
			i++;
		}
		
		System.out.print("Enter the serial number of the student:");
		int index=sc.nextInt();
		
		if(index<=0 || index>=i)
		{
			System.err.println("Invalid Entry...");
			return;
		}
		
		String studentId=studentList.get(index-1).getStudentEnrollmentId();
		System.out.print("Enter the semester...");
		String semester=sc.next();
		try {			
			adminService.generateGradeCard(studentId, semester);
			System.out.println("GradeCard has been generated successfully....");
		}
		catch(GradeNotAllotedException e) {
			System.err.println("Error Found: " + e.getMessage() );
		}
	}
	
	
}
