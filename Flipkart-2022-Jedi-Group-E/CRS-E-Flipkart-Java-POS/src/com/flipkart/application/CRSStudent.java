/**
 * 
 */
package com.crs.flipkart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.*;
import com.crs.flipkart.exceptions.GradeCardNotGeneratedException;
import com.crs.flipkart.validators.StudentValidator;


public class CRSStudent {

	Scanner sc = new Scanner(System.in);
	SemesterRegistrationInterface semesterRegistrationService = new SemesterRegistrationService();
	StudentInterface studentService = new StudentService();
	static boolean submittedCourses;
	static boolean feeStatus;
	
	/**
	 * Method to display student dash-board
	 * 
	 * @param studentId of the student
	 */
	public void create_menu(String studentId) {
		int choice;
		do {

				submittedCourses = StudentValidator.submittedCourses(studentId);
				feeStatus = StudentValidator.getFeeStatus(studentId);
				System.out.println("-----------------------------------STUDENT DASHBOARD-----------------------------\n");
				System.out.println("Choose one of the following options: ");
				System.out.println("1 -> To register for semester");
				System.out.println("2 -> To View Course List");
				System.out.println("3 -> To View Registered Courses");
				System.out.println("4 -> To Make Payment");
				System.out.println("5 -> To View Notifications");
				System.out.println("6 -> To View Grade Card");
				System.out.println("7 -> Logout");
				System.out.print("Choice : ");
			
				choice = sc.nextInt();
			
				switch (choice) {
				
				case 1:
					registerSem(studentId);
					break;
					
				case 2: 
					viewCourses();
					break;
					
				case 3:
					viewRegisteredCourses(studentId);
					break;
					
				case 4:
					makePayment(studentId);
					break;
					
				case 5:
					viewNotifications(studentId);
					break;
					
				case 6:
					viewGradeCard(studentId);
					break;
					
				case 7:
					CRSApplication.loggedIn = false;
					break;			
					
				default:
					System.out.println("Invalid Entry!");
			}
			
		}while(CRSApplication.loggedIn);
	}
	
	/**
	 * Method to print a course
	 * @param course : which is to be printed
	 */
	public static void printCourse(Course course)
	{
		if(course.getProfessorId() == null)
			System.out.println(String.format("%20s %20s %20s",course.getCourseId(), course.getCourseName()," "));
		else
			System.out.println(String.format("%20s %20s %20s",course.getCourseId(), course.getCourseName(),course.getProfessorId()));
	}
	
	/**
	 * Method to print notifications
	 * @param notification : which is to be printed
	 */
	public static void printNotification(PaymentNotification notification)
	{
		
		System.out.println(String.format("%30s %30s",notification.getReferenceId(),notification.getMessage()));
	}
	
	/**
	 * Method to display all the courses from the catalog
	 */
	public void viewCourses()
	{
		
		List<Course> courseList = new ArrayList<Course>();
		courseList = semesterRegistrationService.viewCourses();
		
		if(courseList.size()==0)
		{
			System.err.println("No course exists in the system yet");
			return;
		}
		
		System.out.println(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","PROFESSOR ID" ));
		courseList.forEach(CRSStudent::printCourse);
	}
	
	
	/**
	 * Method to display all the notifications
	 */
	public void viewNotifications(String studentID)
	{
		List<PaymentNotification> notificationList = new ArrayList<PaymentNotification>();
		notificationList = studentService.viewNotifications(studentID);
		
		if(notificationList.size()==0)
		{
			System.err.println("No notifications as of now. Come back again!!");
			return;
		}
		
		System.out.println(String.format("%30s %30s ","Reference ID","Status"));
		notificationList.forEach(CRSStudent::printNotification);
		
		
	}
	
	
	
	
	
	/**
	 * Method to display semester registration dash-board
	 * 
	 * @param studentId of the student
	 */
	public void registerSem(String studentID)
	{
		if(submittedCourses)
		{
			System.err.println("You have already finalized your courses.");
			return;
		}
		int choice;
		do
		{
			System.out.println("-----------------------SEMESTER REGISTRATION DASHBOARD-------------------------\n\n");
			System.out.println("1. View Courses");
			System.out.println("2. Add Course");
			System.out.println("3. Drop Course");
			System.out.println("4. View Opted Courses");
			System.out.println("5. Submit the choices");
			System.out.println("6. Exit");
			System.out.print("Option : ");
			choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				viewCourses();
				break;
			case 2:
				addCourse(studentID);
				break;
			case 3:
				dropCourse(studentID);
				break;
			case 4:
				viewOptedCourses(studentID);
				break;
			case 5:
				submittedCourses=submitChoice(studentID);
				break;
			case 6:
				break;
			default:
				System.err.println("Invalid option");
			}
			if(submittedCourses)
				break;
		}while(choice!=6);
	}
	
	/**
	 * Method to add course to opted course for a given student
	 * 
	 * @param studentId of the student
	 */
	public void addCourse(String studentID)
	{
		System.out.print("Enter the course id : ");
		sc.nextLine();
		String courseID = sc.nextLine();
		try {			
			semesterRegistrationService.addCourse(studentID,courseID);
			System.out.println("Course successfully opted!");
		}
		catch(Exception e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
	
	/**
	 * Method to drop courses from opted course for a given student
	 * 
	 * @param studentId of the student
	 */
	public void dropCourse(String studentID)
	{
		System.out.print("Enter the course id : ");
		sc.nextLine();
		String courseID = sc.nextLine();
		try {
		String status=semesterRegistrationService.dropCourse(studentID,courseID);
		System.out.println(status);
		}
		catch(Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Method to view the list of opted courses for a given student
	 * 
	 * @param studentId of the student
	 */
	public void viewOptedCourses(String studentID)
	{
		List<Course> courseList = new ArrayList<Course>();
		courseList = semesterRegistrationService.viewOptedCourses(studentID);
		
		if(courseList.size()==0)
		{
			System.err.println("No courses opted yet");
			return;
		}
		
		System.out.println(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","PROFESSOR ID" ));
		courseList.forEach(CRSStudent::printCourse);
	}
	
	/**
	 * Method to register for the opted course for a given student
	 * 
	 * @param studentId of the student
	 * @return returns a boolean that indicates if the courses are successfully alloted in the database
	 */
	public boolean submitChoice(String studentID)
	{
		String status = semesterRegistrationService.submitOptedCourses(studentID);
		System.out.println(status);
		if(!(status.equals("Please select some other courses as some courses are filled and dropped from the list")||status.equals("Please select atleast 4 courses")))
			return true;
		return false;
	}
	
	
	/**
	 * Method to view the list of registered courses for a given student
	 * 
	 * @param studentId of the student
	 */
	public void viewRegisteredCourses(String studentID)
	{
		if(!submittedCourses)
		{
			System.err.println("You have not yet finalized the courses.");
			return;
		}
		List<Course> courseList = new ArrayList<Course>();
		courseList = studentService.viewRegisteredCourses(studentID);
		
		if(!feeStatus)
		{
			System.err.println("Please pay your fees as soon as possible.");
		}
		
		if(courseList.size()==0)
		{
			System.err.println("No courses registered yet");
			return;
		}
		
		System.out.println(String.format("%20s %20s %20s","COURSE ID","COURSE NAME","PROFESSOR ID" ));
		courseList.forEach(CRSStudent::printCourse);
	}
	
	/**
	 * Method to make payment for a given student
	 * 
	 * @param studentId of the student
	 */
	public void makePayment(String studentID)
	{
		if(!submittedCourses)
		{
			System.err.println("You have not yet finalized the courses.");
			return;
		}
		if(feeStatus)
		{
			System.out.println("You have paid the fees");
			return;
		}
		String[] modeOfPayment = {"Netbanking","Card","Cheque","Cash"};
		
		float totalFee = studentService.getTotalFee(studentID);
		System.out.println("Fees to be paid : "+totalFee);
		System.out.println("1. Netbanking");
		System.out.println("2. Card");
		System.out.println("3. Cheque");
		System.out.println("4. Cash");
		System.out.println("5. Exit");
		System.out.println("Option : ");
		
		int choice = sc.nextInt();
		
		if(choice!=5)
		{
			String status = studentService.makePayment(studentID,modeOfPayment[choice-1],totalFee);
			System.out.println(status);
		}
	}
	
	/**
	 * Method to view the grade card for a given student
	 * 
	 * @param studentId of the student
	 */
	public void viewGradeCard(String studentID)
	{
		if(!feeStatus)
		{
			System.err.println("You have not paid the fees yet.");
			return;
		}
		try {			
			GradeCard gradeCard = studentService.viewGradeCard(studentID);
			
			System.out.println("----------------------------------GRADE CARD------------------------------------------\n\n");
			System.out.println("ID : "+studentID+"\t\t\t\t\tSemester : "+gradeCard.getSemester() + "\n");
//			System.out.println(String.format("%10s %10s ",studentID,"GPA"));
			System.out.println(String.format("%25s %25s ","COURSE ID","GPA"));
			for(Grade grade:gradeCard.getGradeList())
			{
				System.out.println(String.format("%25s %25s ",grade.getCourseId(),grade.getStudentGrade()));
			}
			System.out.println("\n\t\t\t\tCGPA : "+gradeCard.getStudentCgpa());
			System.out.println("\n___________________________________________________________________");
		}
		catch(GradeCardNotGeneratedException e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
	
	

}
