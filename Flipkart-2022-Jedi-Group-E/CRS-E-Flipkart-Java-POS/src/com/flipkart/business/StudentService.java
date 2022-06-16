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

	public boolean submittedCourses(String studentId)
	{
		return studentDaoService.submittedCourses(studentId);
	}

	public boolean getFeeStatus(String studentId)
	{
		return studentDaoService.getFeeStatus(studentId);
	}

	public List<Course> viewRegisteredCourses(String studentID) {
		// TODO Auto-generated method stub
		
		return semesterRegistrationDaoService.viewOptedCourses(studentID);
		
	}
	
	public List<PaymentNotification> viewNotifications(String studentID) {
		// TODO Auto-generated method stub
		
		return studentDaoService.viewNotification(studentID);
		
	}
	
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
