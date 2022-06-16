/**
 * 
 */
package com.crs.flipkart.application;

import java.time.*;
import java.time.format.*;
import java.util.*;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.business.StudentInterface;
import com.crs.flipkart.business.StudentService;
import com.crs.flipkart.business.UserInterface;
import com.crs.flipkart.business.UserService;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.UserNotFoundException;
import com.crs.flipkart.validators.StudentValidator;
import com.crs.flipkart.constants.Roles;


public class CRSApplication {

	/**
	 * @param args
	 */

	static boolean loggedIn = false;
	UserInterface userService = new UserService();
	StudentInterface studentService = new StudentService();

	/**
     * Main function. Program starts execution from here. Displays initial menu
     */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Main screen will have 4 things

		Scanner sc = new Scanner(System.in);
		CRSApplication crsApplication = new CRSApplication();
		int choice;
		do {
			System.out.println("\n\n_____________________________________________________________________________");
			System.out.println("");
			System.out.println("                    WELCOME TO COURSE REGISTRATION SYSTEM                    ");
			System.out.println("_____________________________________________________________________________");
			System.out.print("\n1. Login\n2. Student Registration\n3. Update Password\n4. Exit\nOption : ");
			choice = sc.nextInt();
			switch (choice) {
			case 1: // Login
				crsApplication.login();
				break;
			case 2: // Student registration
				crsApplication.registerStudent();
				break;
			case 3: // update password
				crsApplication.updatePassword();
				break;
			case 4: // exit
				break;
			default:
				System.out.print("Invalid option\n");
			}
		} while (choice != 4);
	}

	/**
     * method for all users to login
     */
	public void login() {
		Scanner sc = new Scanner(System.in);

		System.out.println("\n\n_____________________________________________________________________________");
		System.out.println("");
		System.out.println("                                    LOGIN                                    ");
		System.out.println("_____________________________________________________________________________\n");
		String email, password;
		System.out.print("Email:");
		email = sc.next();
		System.out.print("Password:");
		password = sc.next();
		try {			
			
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
			 
			 LocalDateTime myDateObj = LocalDateTime.now();
			   
			 String formattedDate = myDateObj.format(myFormatObj);  
			
			User user = userService.login(email, password);
			
			loggedIn=true;
			
			Roles userRole = Roles.stringToName(user.getRole());
			
			switch (userRole) {
			
			case ADMIN:

				System.out.println("\n\nAdmin successfully logged in at " + formattedDate );
				CRSAdmin admin=new CRSAdmin ();
				admin.create_menu();
				break;
			case PROFESSOR:
				
				System.out.println("\n\n" + user.getName() +" successfully logged in at " + formattedDate );
				String professorId=user.getUserId();
				CRSProfessor professor = new CRSProfessor();
				professor.create_menu(professorId);
				break;
			case STUDENT:
				String studentId = user.getUserId();
				boolean isApproved = StudentValidator.isApproved(studentId);
				if (isApproved) {
					System.out.println("\n\n" + user.getName() +" successfully logged in at " + formattedDate );
					CRSStudent student = new CRSStudent();
					student.create_menu(studentId);

				} else {
					System.err.print("Failed to login, you have not been approved by the administration!");
					loggedIn = false;
				}
				break;
			}
		}
		catch(UserNotFoundException e) {
			System.err.println("Invalid Credentials!");
		}			

	}
	
	/**
     * method for registration of new students
     */
	public void registerStudent() {
		Scanner sc = new Scanner(System.in);

		String email, name, password, branchName, batch, contactNumber;
		// input all the student details
		System.out.println("\n\n_____________________________________________________________________________");
		System.out.println("");
		System.out.println("                           STUDENT REGISTRATION                              ");
		System.out.println("_____________________________________________________________________________\n");
		System.out.print("Name:");
		name = sc.nextLine();
		System.out.print("Contact: ");
		contactNumber = sc.nextLine();
		System.out.print("Email:");
		email = sc.next();
		System.out.print("Password:");
		password = sc.next();
		sc.nextLine();
		System.out.print("Branch:");
		branchName = sc.nextLine();
		System.out.print("Batch:");
		batch = sc.nextLine();

		try {
			System.out.println(userService.registerStudent(name,contactNumber,email, password, branchName, batch));
		}
		catch(EmailAlreadyInUseException e) {
			System.err.println("Error : " + e.getMessage());
		}

	}
	
	/**
     * method for updating the existing password
     */
	public void updatePassword()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\n_____________________________________________________________________________");
		System.out.println("");
		System.out.println("                             UPDATE PASSWORD                                 ");
		System.out.println("_____________________________________________________________________________\n");

		String email, oldPassword, newPassword;
		System.out.print("Email:");
		email = sc.next();
		System.out.print("Old Password:");
		oldPassword = sc.next();
		System.out.print("New Password:");
		newPassword = sc.next();
		
		try {			
			System.out.println(userService.updatePassword(email, oldPassword,newPassword));
		}
		catch(UserNotFoundException e) {
			System.err.println("Email or password is incorrect!");
		}
	}

}
