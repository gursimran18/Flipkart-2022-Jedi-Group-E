/**
 * 
 */
package com.crs.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.bean.Grade;
import com.crs.flipkart.bean.GradeCard;
import com.crs.flipkart.bean.Payment;
import com.crs.flipkart.bean.PaymentNotification;
import com.crs.flipkart.constants.SQLQueries;
import com.crs.flipkart.exceptions.GradeCardNotGeneratedException;
import com.crs.flipkart.utils.dbUtil;


public class StudentDaoService implements StudentDaoInterface {

	private static Logger logger = Logger.getLogger(StudentDaoService.class);
	public static Connection conn = dbUtil.getConnection();
	
	public boolean isApproved(String studentId) 
	{
		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_STUDENT);

			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getInt("verificationStatus")==1)
			{
					return true;
			}
			
			return false;
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return false;
		}
	}
	
	public boolean submittedCourses(String studentId)
	{
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.SUBMITTED_COURSES);

			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getInt("registrationStatus")==1)
			{
					return true;
			}
			return false;
		}
		catch(SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return false;
		}
	}
	
	public boolean getFeeStatus(String studentId)
	{
		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_STUDENT);

			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getInt("feeStatus")==1)
			{
					return true;
			}
			
			return false;
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return false;
		}
	}

	@Override
	public String makePayment(Payment payment) {
		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_PAYMENT);

			ps.setString(1, payment.getStudentEnrollmentId());
			ps.setString(2, payment.getReferenceId());
			ps.setFloat(3, payment.getAmount());
			ps.setString(4, payment.getPaymentType());

			int rs = ps.executeUpdate();
			if (rs == 0) {
				return "Payment unsuccessful";
			}

			ps = conn.prepareStatement(SQLQueries.UPDATE_PAYMENT_STATUS);
			ps.setString(1, payment.getStudentEnrollmentId());
			rs= ps.executeUpdate();
			if (rs == 0) {
				return "Payment unsuccessful";
			}
			return "Payment Successful. Reference Id : " + payment.getReferenceId();
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return "Payment unsuccessful";
		}

	}

	@Override
	public void generatePaymentNotification(Payment payment)
	{
		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_NOTIFICATION);

			ps.setString(2, payment.getStudentEnrollmentId());
			ps.setString(1, payment.getReferenceId());
			ps.setString(3, "Payment Successful!");

			ps.executeUpdate();

		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
		}

	}
	
	@Override
	public List<PaymentNotification> viewNotification(String studentId) {
		List<PaymentNotification> notificationList = new ArrayList<PaymentNotification>();

		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_NOTIFICATIONS);
			ps.setString(1, studentId);

			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
//				System.out.println("Hello ji");
				String referenceId = rs.getString("referenceId");
				String message = rs.getString("message");
				
				PaymentNotification notification = new PaymentNotification(studentId,referenceId,message);
				notificationList.add(notification);
			}
			return notificationList;
			
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return null;
		}
	}

	
	
	@Override
	public GradeCard viewGradeCard(String studentId) throws GradeCardNotGeneratedException{
		GradeCard gradeCard = new GradeCard();
		try {

			PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_GRADE);
			PreparedStatement ps1 = conn.prepareStatement(SQLQueries.FETCH_GRADECARD);

			ps.setString(1, studentId);
			ps1.setString(1, studentId);

			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next()) {
				throw new GradeCardNotGeneratedException(studentId);
			}
			gradeCard.setStudentCgpa(rs1.getFloat("gpa"));
			gradeCard.setSemester(1);
			ResultSet rs = ps.executeQuery();
			List<Grade> gradeList = new ArrayList<Grade>();

			while (rs.next()) {
				String courseId = rs.getString("courseId");
				String studentID = rs.getString("studentId");
				float gpa = rs.getFloat("gpa");

				Grade grade = new Grade();
				grade.setCourseId(courseId);
				grade.setStudentEnrollmentId(studentID);
				grade.setStudentGrade(gpa);

				gradeList.add(grade);

			}

			gradeCard.setGradeList(gradeList);

			return gradeCard;
		} catch (SQLException e) {
			logger.debug("Error: " + e.getMessage());
			return null;
		}

	}

	
}
