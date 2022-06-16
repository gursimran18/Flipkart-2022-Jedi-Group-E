/**
 * 
 */
package com.crs.flipkart.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.*;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.utils.*;
import com.crs.flipkart.constants.*;


public class CatalogDaoService implements CatalogDaoInterface {

	private static Logger logger = Logger.getLogger(CatalogDaoService.class);
	public static Connection conn = dbUtil.getConnection();
	
	/**
	 * Method to return all the courses in the catalog
	 * 
	 * @return returns a list of the courses from the database
	 */
	@Override
	public List<Course> viewCourses() {
		List<Course> courseList = new ArrayList<Course>();
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_COURSES);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String courseId = rs.getString("courseId");
				String courseName = rs.getString("courseName");
				float courseFee = rs.getFloat("courseFee");
				String department = rs.getString("department");
				String professorId = rs.getString("professorId");
				int studentCount = rs.getInt("studentCount");
				Course course = new Course(courseId,courseName,courseFee,department,professorId,studentCount);
				courseList.add(course);
			}
			
			return courseList;
		} catch (SQLException e) {
			logger.debug("Error: No course found");
		}
		return null;
	}
}
