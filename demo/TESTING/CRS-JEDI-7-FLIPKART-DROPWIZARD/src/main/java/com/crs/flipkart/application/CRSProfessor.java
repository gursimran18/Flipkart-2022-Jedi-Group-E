/**
 * 
 */
package com.crs.flipkart.application;

import java.util.List;
import java.util.Scanner;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.ProfessorInterface;
import com.crs.flipkart.business.ProfessorService;
import com.crs.flipkart.exceptions.CourseNotAvailableException;
import com.crs.flipkart.exceptions.CourseNotFoundException;


public class CRSProfessor {

	ProfessorInterface professorService=new ProfessorService();
	Scanner sc=new Scanner(System.in);
	public static String id;
	
	/**
     * method for displaying the professor dashboard and selecting the available choices.
     */
	public void create_menu(String professorId)
	{
		Scanner sc = new Scanner(System.in);
		id=professorId;
		int choice;
		do {

				System.out.println("\n\n_____________________________________________________________________________");
				System.out.println("");
				System.out.println("                           PROFESSOR DASHBOARD                               ");
				System.out.println("_____________________________________________________________________________\n");
				System.out.println("1. View Available Courses");
				System.out.println("2. View Selected Courses");
				System.out.println("3. Select Courses");
				System.out.println("4. Grade Student");
				System.out.println("5. View Enrolled Students");
				System.out.println("6. Logout");
				System.out.print("Option : ");
			
				choice = sc.nextInt();
			
				switch (choice) {
				
				case 1:
					
					viewAvailableCourses();
					break;
			
				case 2: 
					viewSelectedCourses(professorId);
					break;
			
				case 3: 
			
					selectCourses();
					break;
					
				case 4:
					
					gradeStudent();
					break;
					
				case 5:
					viewEnrolledStudents();
					break;

				case 6:
					CRSApplication.loggedIn = false;
					break;			
					
				default:
					System.out.println("Incorrect Choice!");
			}
			
		}while(CRSApplication.loggedIn);
	}
	
	/**
	 * Method to print a course
	 * @param course : which is to be printed
	 */
	public static void printCourse(Course course)
	{
		System.out.println(String.format("%20s %20s ",course.getCourseId(),course.getCourseName()));
	}
    
	 /**
     * method for viewing available courses.
     */
	public void viewAvailableCourses() {
		// TODO Auto-generated method stub
		List<Course>courseList=professorService.viewCourses();
		
		if(courseList.size() == 0)
		{
			System.err.println("No available courses in the system");
			return;
		}
		
		System.out.println(String.format("%20s %20s ","COURSE ID","COURSE NAME"));
		courseList.forEach(CRSProfessor::printCourse);
	}
	
	 /**
     * method for selected courses.
     */
	public void viewSelectedCourses(String professorId) {
		
		List<Course>courseList=professorService.viewSelectedCourses(professorId);
		
		if(courseList.size() == 0)
		{
			System.err.println("You haven't selected any course yet");
			return;
		}
		
		System.out.println(String.format("%20s %20s ","COURSE ID","COURSE NAME"));
		courseList.forEach(CRSProfessor::printCourse);
	}
	
	/**
     * method for selecting a course
     */
	public void selectCourses() {
		// TODO Auto-generated method stub
		System.out.print("Enter course Id for selecting course: ");
		String courseId=sc.next();
		try {			
			professorService.indicateCourse(id, courseId);
			System.out.println("Course selected successfully");
		}
		catch(Exception e) {
			System.err.println("Error : " + e.getMessage());
		}
		
	}
	
	
	/**
    * method for grading a student
    */
	public void gradeStudent() {
		// TODO Auto-generated method stub
		System.out.print("Enter the semester : ");
		String semester=sc.next();
		System.out.print("Enter course ID : ");
		String courseId=sc.next();
		if(!professorService.validateCourse(courseId, id))
		{
			System.err.println("You don't teach this course");
			return;
		}
		List<Student> enrolledStudents = professorService.viewUngradedStudents(courseId);

		if(enrolledStudents.size() == 0) {
			System.err.println("No student left to be graded!");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","INDEX","STUDENT ID", "STUDENT NAME"));
		for(Student student:enrolledStudents)
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
			
		String studentId=enrolledStudents.get(index-1).getStudentEnrollmentId();
		System.out.print("Enter the grade : ");
		float grade=sc.nextFloat();
		System.out.println(professorService.gradeStudent(studentId, courseId, grade, semester));
		
	}
     
	/**
     * method for viewing enrolled students in a course
     */
	public void viewEnrolledStudents() {
		// TODO Auto-generated method stub
		System.out.print("Enter course ID :");
		String courseId=sc.next();
		if(!professorService.validateCourse(courseId, id))
		{
			System.err.println("You don't teach this course");
			return;
		}
		List<Student>enrolledStudents=professorService.viewEnrolledStudents(courseId);
		if(enrolledStudents.size() == 0) {
			System.err.println("No student enrolled for this course!");
			return;
		}
		int i=1;
		
		System.out.println(String.format("%10s %20s %20s","INDEX","STUDENT ID", "STUDENT NAME"));
		for(Student student:enrolledStudents)
		{
			System.out.println(String.format("%10s %20s %20s",i,student.getStudentEnrollmentId(), student.getName()));
			i++;
		}
	}
	
	
}
