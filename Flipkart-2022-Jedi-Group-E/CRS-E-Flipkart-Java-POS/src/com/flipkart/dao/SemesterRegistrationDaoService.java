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
