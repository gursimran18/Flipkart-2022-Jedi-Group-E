/**
 * 
 */
package com.crs.flipkart.bean;


public class Payment {

	private String studentEnrollmentId;
	private String referenceId;
	private float amount;
	private boolean paymentStatus;
	private String paymentType;

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
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the paymentStatus
	 */
	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
