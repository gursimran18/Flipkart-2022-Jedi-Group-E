/**
 * 
 */
package com.crs.flipkart.exceptions;


public class CourseCountExceededException extends Exception{
	private int num;

	/**
	 * Constructor
	 * @param num number of courses
 	 */
	public CourseCountExceededException(int num )
	{	
		this.num = num;
	}


	/**
	 * Message returned when exception is thrown
	 */
	@Override
	public String getMessage() 
	{
		return "You have already registered for " + num + " courses";
	}
}
