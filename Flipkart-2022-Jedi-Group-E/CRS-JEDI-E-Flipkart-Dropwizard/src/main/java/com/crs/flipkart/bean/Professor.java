/**
 * 
 */
package com.crs.flipkart.bean;

import java.util.Date;

public class Professor extends User{

	private String professorId;
	private float salary;
	private String department;
	private String doj;

	/**
	 * @return the professorId
	 */
	public Professor()
	{
		
	}
	public String getProfessorId() {
		return professorId;
	}

	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(String professorId) {
		this.setUserId(professorId);
		this.professorId = professorId;
	}

	/**
	 * @return the salary
	 */
	public float getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(float salary) {
		this.salary = salary;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the doj
	 */
	public String getDoj() {
		return doj;
	}

	/**
	 * @param doj the doj to set
	 */
	public void setDoj(String doj) {
		this.doj = doj;
	}

}
