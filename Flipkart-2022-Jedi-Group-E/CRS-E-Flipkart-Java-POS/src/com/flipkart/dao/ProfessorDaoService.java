/**
 * 
 */
package com.crs.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.crs.flipkart.bean.*;
import com.crs.flipkart.constants.SQLQueries;
import com.crs.flipkart.exceptions.CourseAlreadyIndicatedException;
import com.crs.flipkart.exceptions.CourseNotAvailableException;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.utils.dbUtil;


public class ProfessorDaoService implements ProfessorDaoInterface {

	private static Logger logger = Logger.getLogger(ProfessorDaoService.class);
	public static Connection conn = dbUtil.getConnection();

	@Override
	public List<Course> viewCourses() {
        try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_COURSES);
            ResultSet rs = ps.executeQuery();
            List<Course> courses = new ArrayList<Course>();
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getString("courseId"));
                c.setCourseName(rs.getString("courseName"));
                c.setCourseFee(rs.getFloat("courseFee"));
                c.setDepartmentName(rs.getString("department"));
                c.setProfessorId(rs.getString("professorId"));
                c.setStudentCount(rs.getInt("studentCount"));
                courses.add(c);
            }
            return courses;
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        	return null;
        }
    }

	@Override
	public void selectCourse(String professorId, String courseId) throws CourseNotFoundException, CourseNotAvailableException, CourseAlreadyIndicatedException{
        try {
        	PreparedStatement psCheck = conn.prepareStatement(SQLQueries.CHECK_VACANT_COURSE);
        	psCheck.setString(1, courseId);
        	ResultSet rs = psCheck.executeQuery();
        	if(!rs.next())
        	{	
        		throw new CourseNotFoundException(courseId);
        	}
        	else if(rs.getString("professorId") != null) {
        		if(rs.getString("professorId").equals(professorId))
        			throw new CourseAlreadyIndicatedException(courseId);
        			
        		throw new CourseNotAvailableException(courseId);       			
        	}
        	
        	
            PreparedStatement ps = conn.prepareStatement(SQLQueries.SELECT_COURSE_FOR_PROF);
            ps.setString(1, professorId);
            ps.setString(2, courseId);
            
            if(ps.executeUpdate() == 1)
            	return;
            
 
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        }
    }

	@Override
	public List<Student> viewEnrolledStudents(String courseId)
	{
		try {
			List<Student> enrolledStudents = new ArrayList<Student>();
            PreparedStatement ps = conn.prepareStatement(SQLQueries.VIEW_ENROLLED_STUDENTS);
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next())
            {
            	Student student = new Student();
            	student.setName(rs.getString("student.name"));
            	student.setStudentEnrollmentId(rs.getString("student.studentId"));
            	enrolledStudents.add(student);
            }
            
            return enrolledStudents;
            
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        }
        return null;
	}

	@Override
	public List<Student> viewUngradedStudents(String courseId)
	{
		try {
			List<Student> ungradedStudents = new ArrayList<Student>();
            PreparedStatement ps = conn.prepareStatement(SQLQueries.VIEW_UNGRADED_STUDENTS);
            ps.setString(1, courseId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery(); 
            while(rs.next())
            {
            	Student student = new Student();
            	student.setStudentEnrollmentId(rs.getString("studentId"));
            	student.setName(rs.getString("name"));
            	ungradedStudents.add(student);
            }
            
            return ungradedStudents;
            
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        }
        return null;
	}
	
	@Override
	public String addGrade(String studentId, String courseId, float grade, String semester) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_GRADE);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.setFloat(3, grade);
            ps.setString(4, semester);
            int rowAffected = ps.executeUpdate();
            if (rowAffected == 1)
            	return "Student successfully graded.";
            
        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        }
        return "Student grading failed.";
    }
	
}
