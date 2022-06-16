
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

				System.out.println("\n\n___________________________________________________________________");
				System.out.println("");
				System.out.println("                            ADMIN DASHBOARD                        ");          
				System.out.println("___________________________________________________________________\n");
				System.out.println("1. View Courses");
				System.out.println("2. Add Courses");
				System.out.println("3. Drop Courses");
				System.out.println("4. Approve Student");
				System.out.println("5. Add Professor");
				System.out.println("6. Drop Professor");
				System.out.println("7. View Professor List");
				System.out.println("8. Generate Grade Card");
				System.out.println("9. Logout");
				System.out.print("Option : ");
			
				choice = sc.nextInt();
			
				switch (choice) {
				
				case 1:
					// View Courses
					viewCourses();
					break;
					
				case 2: 
					// Add Course;
					addCourse();
					break;
					
				case 3:
					// Drop Course
					dropCourse();
					break;
					
				case 4:
					approveStudent();
					break;
					
				case 5:
					addProfessor();
					break;
					
				case 6:
					// View Professor List
					dropProfessor();
					break;
					
				case 7:
					viewProfessorList();
					break;
				
				case 8:
					// Generate Grade Card
					generateGradeCard();
					break;
					
				case 9:
					CRSApplication.loggedIn = false;
					break;			
					
				default:
					System.out.println("Incorrect Choice!");
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
			System.err.println("No courses in the system");
			return;
		}
		
		System.out.println(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","PROFESSOR ID" ));
		courseList.forEach(CRSStudent::printCourse);
		
	}
	
	/**
     * method for adding a course
     */
	public void addCourse() {
		// TODO Auto-generated method stub
		Course course=new Course();
		System.out.print("Enter the course Id :");
		course.setCourseId(sc.nextLine());
		
		try {
			adminService.verifyCourse(course.getCourseId());
			System.out.print("Enter the course name :");
			course.setCourseName(sc.nextLine());
			System.out.print("Enter the course fee :");
			course.setCourseFee(sc.nextFloat());
			sc.nextLine();
			System.out.print("Enter the department name :");
			course.setDepartmentName(sc.nextLine());
			course.setProfessorId(null);
			course.setStudentCount(0);
			System.out.println(adminService.addCourse(course));
		}
		catch (CourseAlreadyExistsException e){
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
     * method for removing a course
     */
	public void dropCourse() {
		// TODO Auto-generated method stub
		System.out.print("Enter the course Id :");
		String courseId=sc.nextLine();
		try {
			adminService.dropCourse(courseId);
			System.out.println("Course successfully dropped");
		}
		catch(CourseNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	 /**
     * method for approving student registration
     */
	public void approveStudent() {
		// TODO Auto-generated method stub
		System.out.println("List of students to be approved : ");
		List<Student> studentList = adminService.getPendingStudents();
		
		if(studentList.size() == 0) {
			System.err.println("No student left to be approved!");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","INDEX","STUDENT ID", "STUDENT NAME"));
		for(Student student:studentList)
		{
			System.out.println(String.format("%10s %20s %20s",i,student.getStudentEnrollmentId(), student.getName()));
			i++;
		}
		System.out.print("Serial number of student to approve: ");
		int studentIndex=sc.nextInt();
		
		if(studentIndex<=0 || studentIndex>=i)
		{
			System.err.println("Invalid index... Returning to previous menu");
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
		System.out.print("Enter the professor name :");
		newProfessor.setName(sc.nextLine());
		System.out.print("Enter email : ");
		newProfessor.setEmail(sc.nextLine());
		System.out.print("Enter password : ");
		newProfessor.setPassword(sc.nextLine());
		System.out.print("Enter the professor salary :");
		newProfessor.setSalary(sc.nextFloat());
		sc.nextLine();
		System.out.print("Enter the department name :");
		newProfessor.setDepartment(sc.nextLine());
		System.out.print("Enter the doj :");
		newProfessor.setDoj(sc.nextLine());
		System.out.print("Enter the contact number:");
		newProfessor.setContactNumber(sc.nextLine());
		
		try {
			System.out.println(adminService.addProfessor(newProfessor));
		}
		catch(EmailAlreadyInUseException e) {
			System.err.println("Error : " + e.getMessage());
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
			System.err.println("No professor in the system!");
			return ;
		}
		System.out.print("Enter the serial number :");
		int index=sc.nextInt();
		
		if(index <=0 || index >professorList.size())
		{
			System.err.println("Invalid index... Returning to previous menu");
			return;
		}
		
		try {
			adminService.dropProfessor(professorList.get(index-1).getProfessorId());
			System.out.println("Professor dropped successfully");
		}
		catch(UserNotFoundException e) {
			System.err.println("Error : " + e.getMessage());
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
			System.err.println("No professors present in the database");
			return;
		}
		
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s %20s","INDEX","PROFESSOR ID", "PROFESSOR NAME", "DEPARTMENT"));
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
			System.err.println("No more grade cards left to be generated");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","INDEX","STUDENT ID", "STUDENT NAME"));
		for(Student student:studentList)
		{
			System.out.println(String.format("%10s %20s %20s",i,student.getStudentEnrollmentId(), student.getName()));
			i++;
		}
		
		System.out.print("Enter the student index : ");
		int index=sc.nextInt();
		
		if(index<=0 || index>=i)
		{
			System.err.println("Invalid index... Returning to previous menu");
			return;
		}
		
		String studentId=studentList.get(index-1).getStudentEnrollmentId();
		System.out.print("Enter the semester : ");
		String semester=sc.next();
		try {			
			adminService.generateGradeCard(studentId, semester);
			System.out.println("GradeCard generated successfully.");
		}
		catch(GradeNotAllotedException e) {
			System.err.println("Error  : " + e.getMessage() );
		}
	}
	
	
}
