/**
 * 
 */
package com.crs.flipkart.business;

import java.util.List;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.GradeCard;
import com.crs.flipkart.bean.Payment;
import com.crs.flipkart.bean.PaymentNotification;
import com.crs.flipkart.exceptions.GradeCardNotGeneratedException;

/**
 * @author jasan
 *
 */
public interface StudentInterface {
	
	 /**
     * method for getting all the registered courses for the student
     * 
     * @param studentId  unique Id to represent a student
     * @return list of courses registered by student 
     */
	List<Course> viewRegisteredCourses(String studentID);
	
	public List<PaymentNotification> viewNotifications(String studentID);
	/**
     * method for getting the total fee of all the courses opted by student
     * 
     * @param studentId  unique Id to represent a student
     * @return total fee of all the courses opted by student 
     */
	float getTotalFee(String studentID);
	
	/**
     * method for paying fee
     *
     * @param studentId  unique Id to represent a student
     * @param modeOfPayment represents the mode of payment
     * @param totalFee represents amount to be paid
     * @return returns string which represents the status of the payment
     */
	String makePayment(String studentID, String modeOfPayment,float totalFee);
	
	/**
     * method for viewing  grade card of the student
     * 
     * @param studentId  unique Id to represent a student
     * @return grade card of the student 
     */
	GradeCard viewGradeCard(String studentID) throws GradeCardNotGeneratedException;
}
