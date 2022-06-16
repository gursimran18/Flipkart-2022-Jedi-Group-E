/**
 * 
 */
package com.crs.flipkart.bean;

import java.util.*;


public class GradeCard {

	private String studentEnrollmentId;
	private List<Grade> gradeList = new ArrayList<Grade>();
	private int semester;
	private float studentCgpa;

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
	 * @return the gradeList
	 */
	public List<Grade> getGradeList() {
		return gradeList;
	}

	/**
	 * @param gradeList the gradeList to set
	 */
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	/**
	 * @return the semester
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * @param semester the semester to set
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * @return the studentCgpa
	 */
	public float getStudentCgpa() {
		return studentCgpa;
	}

	/**
	 * @param studentCgpa the studentCgpa to set
	 */
	public void setStudentCgpa(float studentCgpa) {
		this.studentCgpa = studentCgpa;
	}

}
