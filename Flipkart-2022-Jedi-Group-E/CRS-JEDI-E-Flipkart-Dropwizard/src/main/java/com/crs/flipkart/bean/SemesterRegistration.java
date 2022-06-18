/**
 * 
 */
package com.crs.flipkart.bean;

import java.util.ArrayList;
import java.util.List;


public class SemesterRegistration {

	private String studentEnrollmentId;
	private String semester;
	private List<Course> optedCourses = new ArrayList<Course>();

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
	 * @return the semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * @param semester the semester to set
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * @return the optedCourses
	 */
	public List<Course> getOptedCourses() {
		return optedCourses;
	}

	/**
	 * @param optedCourses the optedCourses to set
	 */
	public void setOptedCourses(List<Course> optedCourses) {
		this.optedCourses = optedCourses;
	}

}
