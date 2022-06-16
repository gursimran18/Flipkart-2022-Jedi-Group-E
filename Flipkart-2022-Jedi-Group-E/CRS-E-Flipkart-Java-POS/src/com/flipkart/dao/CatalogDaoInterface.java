package com.crs.flipkart.dao;

import java.util.List;

import com.crs.flipkart.bean.Course;

public interface CatalogDaoInterface {

	/**
	 * Method to return all the courses in the catalog
	 * 
	 * @return returns a list of the courses from the database
	 */
	List<Course> viewCourses();

}