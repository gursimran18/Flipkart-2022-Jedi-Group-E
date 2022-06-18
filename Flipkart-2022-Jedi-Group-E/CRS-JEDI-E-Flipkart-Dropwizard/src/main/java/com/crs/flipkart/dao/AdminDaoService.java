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

import com.crs.flipkart.bean.*;
import com.crs.flipkart.constants.Roles;
import com.crs.flipkart.constants.SQLQueries;
import com.crs.flipkart.exceptions.CourseNotFoundException;
import com.crs.flipkart.exceptions.EmailAlreadyInUseException;
import com.crs.flipkart.exceptions.GradeNotAllotedException;
import com.crs.flipkart.exceptions.UserNotFoundException;
import com.crs.flipkart.utils.dbUtil;

/**
 * Implementation of admin dao interface
 */
public class AdminDaoService implements AdminDaoInterface {
	
	private static Logger logger = Logger.getLogger(AdminDaoService.class);
	public static Connection conn = dbUtil.getConnection();
	UserDaoInterface userDaoService = new UserDaoService();

	public List<Course> viewCourses(){
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
        	logger.debug("Error: No course found");
        	return null;
        }
    }

	public String addCourse(Course newCourse){
        try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_COURSE);
            ps.setString(1, newCourse.getCourseId());
            ps.setString(2, newCourse.getCourseName());
            ps.setFloat(3, newCourse.getCourseFee());
            ps.setString(4, newCourse.getDepartmentName());

            if(ps.executeUpdate() == 1)
            	return "Course added successfully.";

        } catch (SQLException e) {
        	logger.debug("Error: Database error");
        }
        return "Course not added.";
    }

	public void dropCourse(String courseId) throws CourseNotFoundException{
        try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.DROP_COURSE);
            ps.setString(1, courseId);
            if(ps.executeUpdate()!=0)
            	logger.info("Course dropped successfully.\n");
            else
            	throw new CourseNotFoundException(courseId);

        } catch (SQLException e) {
        	logger.error("Error: " + e.getMessage());
        }
        
    }

	public List<Student> getPendingStudents()
	{
		List<Student> studentList= new ArrayList<Student>();
		 try {
	            PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_PENDING_STUDENTS);
	      
	            ResultSet rs = ps.executeQuery(); 
	            while(rs.next())
	            {
	            	Student student = new Student();
	            	student.setStudentEnrollmentId(rs.getString("studentId"));
	            	student.setName(rs.getString("name"));
	            	studentList.add(student);
	            }
	            return studentList;
	        } catch (SQLException e) {
	        	logger.debug("Error: Database failure");
	        }
		return null;
	}
	
	public String approveStudent(Student newStudent) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQLQueries.APPROVE_ADDMISSION_REQUEST);
            ps.setString(1, newStudent.getStudentEnrollmentId());
            ps.executeUpdate();
            ps = conn.prepareStatement(SQLQueries.INSERT_REGISTRATION);
            ps.setString(1, newStudent.getStudentEnrollmentId());
            ps.setString(2, "1");
            ps.executeUpdate();
            return "Student approved";

        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        }
        return "Student not approved.";
    }
	
   public String addProfessor(Professor newProfessor) throws EmailAlreadyInUseException{
        try {
    		String id = userDaoService.createUser(newProfessor.getName(), newProfessor.getEmail(), newProfessor.getPassword(), Roles.PROFESSOR);
    	
            PreparedStatement ps = conn.prepareStatement(SQLQueries.ADD_PROFESSOR);
            ps.setString(1, id);
            ps.setString(2, newProfessor.getName());
            ps.setString(3, newProfessor.getContactNumber());
            ps.setFloat(4, newProfessor.getSalary());
            ps.setString(5, newProfessor.getDepartment());
            ps.setString(6, newProfessor.getDoj());

            if(ps.executeUpdate() == 1)
            	return "Professor added successfully. Professor id : "+id;

        } catch (SQLException e) {
        	logger.debug("Error: " + e.getMessage());
        } catch(EmailAlreadyInUseException e) {
        	throw e;
        }
        return "Professor not added.";
    }
	
	
	  @Override
	public List<Professor> viewProfessorList() {
		   try {
				List<Professor> professorList = new ArrayList<Professor>();
	            PreparedStatement ps = conn.prepareStatement(SQLQueries.LIST_PROFESSORS);
	            ResultSet rs = ps.executeQuery(); 
	            while(rs.next())
	            {
	            	Professor professor = new Professor();
	            	String userId=rs.getString("professorId");
	            	professor.setUserId(userId);
	            	professor.setProfessorId(userId);
	            	professor.setContactNumber(rs.getString("contactNumber"));
	            	professor.setDoj(rs.getString("doj"));
	            	professor.setRole("Professor");
	            	professor.setName(rs.getString("name"));
	            	professor.setSalary(rs.getFloat("salary"));
	            	professor.setEmail("********");
	            	professor.setPassword("********");
	            	professor.setDepartment(rs.getString("department"));
	            	professorList.add(professor);
	            }
	            
	            return professorList;
	            
	        } catch (SQLException e) {
	        	logger.debug("Error: " + e.getMessage());
	        }
	        return null;
		}

	   public void dropProfessor(String professorId) throws UserNotFoundException{
	        try {
	            PreparedStatement ps = conn.prepareStatement(SQLQueries.REMOVE_USER);
	            ps.setString(1, professorId);
	            if(ps.executeUpdate() == 1)
	            {
	            	ps = conn.prepareStatement(SQLQueries.DROP_PROFESSOR);
	            	ps.setString(1, professorId);
	            	if(ps.executeUpdate() == 1)
	            		return;
	            }
	            else {
	            	throw new UserNotFoundException(professorId);
	            }
	        } catch (SQLException e) {
	        	logger.error("Error: " + e.getMessage());
	        }
	    }

		public List<Student> getPendingGradeStudents()
		{
			List<Student> studentList= new ArrayList<Student>();
			 try {
		            PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_PENDING_GRADE_STUDENTS);
	
		            ResultSet rs = ps.executeQuery(); 
		            while(rs.next())
		            {
		            	Student student = new Student();
		            	student.setStudentEnrollmentId(rs.getString("studentId"));
		            	student.setName(rs.getString("name"));
		            	studentList.add(student);
		            }
		            return studentList;
		        } catch (SQLException e) {
		        	logger.debug("Error: " + e.getMessage());
		        }
			return null;
		}

	public void generateGradeCard(String studentId, String semester) throws GradeNotAllotedException
	   {
		   try {
	            PreparedStatement ps = conn.prepareStatement(SQLQueries.GET_GRADES);
	            ps.setString(1, studentId);
	            ps.setString(2, semester);
	            ResultSet rs = ps.executeQuery();
	            float total = 0;
	            int countCourses = 0;
	            while (rs.next()) {
	                countCourses++;
	                total += rs.getFloat("gpa");
	            }
	            if(countCourses!=4)
	            	throw new GradeNotAllotedException(studentId);
	            total = total/(float)countCourses;
	            
	            PreparedStatement psUpdate = conn.prepareStatement(SQLQueries.GENERATE_GRADES);
	            psUpdate.setString(1, studentId);
	            psUpdate.setString(2, semester);
	            psUpdate.setFloat(3, total);
	            
	            if(psUpdate.executeUpdate() == 1)
	            	return;
	            
	        } catch (SQLException e) {
	        	logger.debug("Error: " + e.getMessage());
	        }
	   }
}
