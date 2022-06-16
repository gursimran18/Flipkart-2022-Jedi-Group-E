/**
 * 
 */
package com.crs.flipkart.business;

import java.util.List;
import java.util.Random;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.GradeCard;
import com.crs.flipkart.bean.Payment;
import com.crs.flipkart.bean.PaymentNotification;
import com.crs.flipkart.dao.*;
import com.crs.flipkart.dao.StudentDaoInterface;
import com.crs.flipkart.dao.StudentDaoService;
import com.crs.flipkart.exceptions.GradeCardNotGeneratedException;


public class StudentService implements StudentInterface {

	StudentDaoInterface studentDaoService = new StudentDaoService();
	SemesterRegistrationDaoInterface semesterRegistrationDaoService = new SemesterRegistrationDaoService();

	/**
	 * Method to check the registration status of a student
	 *
	 * @param student id of the student
	 * @return returns true if student has completed the registration
	 */
	public boolean submittedCourses(String studentId)
	{
		return studentDaoService.submittedCourses(studentId);
	}

	/**
	 * method for viewing  fee status of the student
	 *
	 * @param studentId  unique Id to represent a student
	 * @return true or false depending on if the fees is paid or not
	 */
	public boolean getFeeStatus(String studentId)
	{
		return studentDaoService.getFeeStatus(studentId);
	}

	/**
	 * method for viewing list of courses a student is registered in
	 *
	 * @param studentId  unique Id to represent a student
	 * @return list of registered courses of a student
	 */
	public List<Course> viewRegisteredCourses(String studentID) {
		// TODO Auto-generated method stub
		
		return semesterRegistrationDaoService.viewOptedCourses(studentID);
		
	}

	/**
	 * method for viewing  all payment related notifications of a student
	 *
	 * @param studentId  unique Id to represent a student
	 * @return list of payment notications of that student
	 */
	public List<PaymentNotification> viewNotifications(String studentID) {
		// TODO Auto-generated method stub
		
		return studentDaoService.viewNotification(studentID);
		
	}

	/**
	 * method for getting the total fee of all the courses opted by student
	 *
	 * @param studentId  unique Id to represent a student
	 * @return total fee of all the courses opted by student
	 */
	public float getTotalFee(String studentID) {
		// TODO Auto-generated method stub
		List<Course> registeredCourses =  semesterRegistrationDaoService.viewOptedCourses(studentID);
		float totalFee = 0;
		for(int i = 0;i<4;i++)
		{	
			totalFee+=registeredCourses.get(i).getCourseFee();
		}
		return totalFee;
	}

	/**
	 * method for paying fee
	 *
	 * @param studentId  unique Id to represent a student
	 * @param modeOfPayment represents the mode of payment
	 * @param totalFee represents amount to be paid
	 * @return returns string which represents the status of the payment
	 */
	public String makePayment(String studentID, String modeOfPayment,float totalFee) {
	
		Payment payment = new Payment();
		
		Random rand = new Random();
        long id = 100000000+rand.nextInt(100000000);
        String refId = Long.toString(id);
       
		
		payment.setStudentEnrollmentId(studentID);
		payment.setAmount(totalFee);
		payment.setPaymentType(modeOfPayment);
		payment.setPaymentStatus(true);
		payment.setReferenceId(refId);
		
		String status = studentDaoService.makePayment(payment);
		
		if(!status.equals("Payment unsuccessful"))
			studentDaoService.generatePaymentNotification(payment);
		
		return status;
		
	}

	/**
	 * method for viewing  grade card of the student
	 *
	 * @param studentId  unique Id to represent a student
	 * @return grade card of the student
	 */
	public GradeCard viewGradeCard(String studentID) throws GradeCardNotGeneratedException{
		// TODO Auto-generated method stub
		try {
			return studentDaoService.viewGradeCard(studentID);			
		}
		catch(GradeCardNotGeneratedException e) {
			throw e;
		}
	}
}
