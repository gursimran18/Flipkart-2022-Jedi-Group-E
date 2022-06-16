/**
 * 
 */
package com.crs.flipkart.bean;


public class Grade {

	private String studentEnrollmentId;
	private String courseId;
	private float studentGrade;
	private String semester;

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
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the studentGrade
	 */
	public float getStudentGrade() {
		return studentGrade;
	}

	/**
	 * @param studentGrade the studentGrade to set
	 */
	public void setStudentGrade(float studentGrade) {
		this.studentGrade = studentGrade;
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

}
