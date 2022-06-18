/**
 * 
 */
package com.crs.flipkart.bean;

import java.util.ArrayList;
import java.util.List;


public class Student extends User {

	private String studentEnrollmentId;
	private String branch;
	private String batch;
	private boolean isVerified;
	// isRegistered boolean
	private List<Course> registeredCourses = new ArrayList<Course>();

	/**
	 * @return the studentEnrollmentId
	 * 
	 */
	public String getStudentEnrollmentId() {
		return studentEnrollmentId;
	}

	/**
	 * @param studentEnrollmentId the studentEnrollmentId to set
	 */
	public void setStudentEnrollmentId(String studentEnrollmentId) {
		this.setUserId(studentEnrollmentId);
		this.studentEnrollmentId = studentEnrollmentId;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the batch
	 */
	public String getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}

	/**
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * @return the registeredCourses
	 */
	public List<Course> getRegisteredCourses() {
		return registeredCourses;
	}

	/**
	 * @param registeredCourses the registeredCourses to set
	 */
	public void setRegisteredCourses(List<Course> registeredCourses) {
		this.registeredCourses = registeredCourses;
	}

}
