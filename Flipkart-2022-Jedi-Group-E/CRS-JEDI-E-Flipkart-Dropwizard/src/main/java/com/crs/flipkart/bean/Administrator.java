/**
 * 
 */
package com.crs.flipkart.bean;

import java.util.Date;


public class Administrator extends User {

	private String adminId;
	private Date doj;

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.setUserId(adminId);
		this.adminId = adminId;
	}

	/**
	 * @return the doj
	 */
	public Date getDoj() {
		return doj;
	}

	/**
	 * @param doj the doj to set
	 */
	public void setDoj(Date doj) {
		this.doj = doj;
	}

}
