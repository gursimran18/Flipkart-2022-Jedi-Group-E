/**
 * 
 */
package com.crs.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.Course;
import com.crs.flipkart.constants.SQLQueries;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.CourseNotOptedException;
import com.crs.flipkart.utils.dbUtil;

public class SemesterRegistrationDaoService implements SemesterRegistrationDaoInterface {

	private static Logger logger = Logger.getLogger(SemesterRegistrationDaoService.class);
	CatalogDaoInterface catalogService = new CatalogDaoService();

	public static Connection conn = dbUtil.getConnection();

	/**
	 * Method to return the opted course list for a given student
	 *
	 * @param student id of the student
	 * @return returns a list of courses that the student has opted
	 */
	@Override
	public List<Course> viewOptedCourses(String studentId) {
		List<Course> courseList = new ArrayList<Course>();

		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_OPTED_COURSES);
			ps.setString(1, studentId);

			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
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
			logger.debug("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Method to enter a course for the student into the opted course list
	 *
	 * @param student id of the student
	 * @param course if of the course to be added
	 * @return returns a string that indicates if the course is successfully opted
	 */
	@Override
	public void addCourse(String studentId, String courseId) throws CourseNotFoundException{
		// TODO Auto-generated method stub
		try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_OPTED_COURSE);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            Boolean rs=ps.execute();
            System.out.println(rs);
            if(rs == false)
            	return;
            else {
            	System.out.println("jisdhfk");
            	throw new CourseNotFoundException(courseId);
            }
        } catch (SQLException e) {
        	System.out.println(e);
        }
	}

	/**
	 * Method to remove a course from the opted course list for student
	 *
	 * @param student id of the student
	 * @param course if of the course to be added
	 * @return returns a string that indicates if the course is dropped opted
	 * @throws CourseNotFoundException
	 * @throws CourseNotOptedException
	 */
	@Override
	public String dropCourse(String studentId, String courseId) throws CourseNotFoundException, CourseNotOptedException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_COURSES);
			ResultSet rs = ps.executeQuery();
			boolean flag = false;
			while(rs.next())
			{
				if(rs.getString("courseId").equals(courseId))
				{
					flag = true;
					break;
				}
			}
			if(!flag)
				throw new CourseNotFoundException(courseId);
			
			ps = conn.prepareStatement(SQLQueries.FETCH_OPTED_COURSES);
			ps.setString(1, studentId);
			rs = ps.executeQuery();
			
			flag = false;
			while(rs.next())
			{
				if(rs.getString("courseId").equals(courseId))
				{
					flag = true;
					break;
				}
			}
			if(!flag)
				throw new CourseNotOptedException(courseId);
			
            ps = conn.prepareStatement(SQLQueries.DROP_OPTED_COURSE);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            if(ps.executeUpdate()==1)
            	return "Successfully dropped course";
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        	return "Database Error";
        }
		return "Database Error";
	}

	/**
	 * Method to submit the courses in the opted course list for a student
	 *
	 * @param student id of the student
	 * @return returns a string that indicates if the courses are successfully registered in
	 */
	@Override
	public String submitOptedCourses(String studentId) {
		// TODO Auto-generated method stub
		try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.SUBMIT_OPTED_COURSES);
            ps.setString(1, studentId);
            int rs=ps.executeUpdate();
            if(rs==4)
            {
            	increaseStudentCount(studentId);
            	ps = conn.prepareStatement(SQLQueries.UPDATE_REGISTRATION_STATUS);
                ps.setString(1, studentId);
                int rs1=ps.executeUpdate();
                if(rs1==1)
                	return "Successfully submitted courses";
            }
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        	return "Database Error";
        }
		return "Database Error";
	}

	/**
	 * Method to increase the student count when the student registers for the course successfully
	 *
	 * @param student id of the student
	 * @return returns a boolean that indicates if the course count is successfully incremented
	 */
	public boolean increaseStudentCount(String studentId) {
		// TODO Auto-generated method stub
		try {
			List<Course> optedCourses = viewOptedCourses(studentId);
            for(int i=0;i<4;i++)
            {
            	PreparedStatement ps = conn.prepareStatement(SQLQueries.INCREMENT_STUDENT_COUNT);
            	ps.setInt(1, optedCourses.get(i).getStudentCount()+1);
            	ps.setString(2, optedCourses.get(i).getCourseId());
            	ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        	return false;
        }
	}
	
}
