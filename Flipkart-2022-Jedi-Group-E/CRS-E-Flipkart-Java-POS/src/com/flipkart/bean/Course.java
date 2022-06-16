/**
 * 
 */
package com.crs.flipkart.bean;


public class Course {

	private String courseId;
	private String courseName;
	private float courseFee;
	private String departmentName;
	private String professorId;
	private int studentCount;

	
	public Course()
	{
		
	}
	
	/**
	 * @param courseId
	 * @param courseName
	 * @param courseFee
	 * @param departmentName
	 * @param professorId
	 * @param studentCount
	 */
	public Course(String courseId, String courseName, float courseFee, String departmentName, String professorId,
			int studentCount) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseFee = courseFee;
		this.departmentName = departmentName;
		this.professorId = professorId;
		this.studentCount = studentCount;
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
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the professorId
	 */
	public String getProfessorId() {
		return professorId;
	}

	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}

	/**
	 * @return the courseFee
	 */
	public float getCourseFee() {
		return courseFee;
	}

	/**
	 * @param courseFee the courseFee to set
	 */
	public void setCourseFee(float courseFee) {
		this.courseFee = courseFee;
	}


	/**
	 * @return the studentCount
	 */
	public int getStudentCount() {
		return studentCount;
	}

	/**
	 * @param studentCount the studentCount to set
	 */
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}


}
