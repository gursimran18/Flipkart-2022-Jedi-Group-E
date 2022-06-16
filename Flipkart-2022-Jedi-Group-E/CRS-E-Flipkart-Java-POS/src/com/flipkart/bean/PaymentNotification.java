/**
 * 
 */
package com.crs.flipkart.bean;


public class PaymentNotification {

	private String studentEnrollmentId;
	private String referenceId;
	private String message;

	
	
	public PaymentNotification(String studentEnrollmentId, String referenceId, String message) {
		super();
		this.studentEnrollmentId = studentEnrollmentId;
		this.referenceId = referenceId;
		this.message = message;
	}

	/**
	 * @return the studentEnrollmentId
	 */
	public String getStudentEnrollmentId() {
		return studentEnrollmentId;
	}

	/**
	 * @param studentEnrollmentId the studentEnrollmentId to set
	 */
	public void setStudentEnrollmentId(String studentEnrollmentId) {
		this.studentEnrollmentId = studentEnrollmentId;
	}

	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
