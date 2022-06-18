package com.crs.flipkart.dao;

import com.crs.flipkart.bean.GradeCard;
import com.crs.flipkart.bean.Payment;
import com.crs.flipkart.exceptions.GradeCardNotGeneratedException;

public interface StudentDaoInterface {

	/**
	 * Method to check if a student is approved by the admin
	 * 
	 * @param student id of the student
	 * @return returns a boolean indication approval status
	 */
	boolean isApproved(String studentId);
	
	/**
	 * Method to check the registration status of a student
	 * 
	 * @param student id of the student
	 * @return returns true if student has completed the registration
	 */
	boolean submittedCourses(String studentId);
	
	/**
	 * Method to check the payment status of a student
	 * 
	 * @param student id of the student
	 * @return returns true if student has completed the payment
	 */
	boolean getFeeStatus(String studentId);
	
	/**
	 * Method to add payment made by a student to the payment database
	 * 
	 * @param payment object contains the payment details
	 * @return returns a string indicating the status of the payment
	 */
	String makePayment(Payment payment);
	
	/**
	 * Method to view grade card for a student
	 * 
	 * @param student id of the student
	 * @param course if of the course to be added
	 * @return returns a grade card for the student
	 */
	GradeCard viewGradeCard(String studentId) throws GradeCardNotGeneratedException;

}