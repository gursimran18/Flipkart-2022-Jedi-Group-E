package com.flipkart.service;

import com.flipkart.bean.*;
import com.flipkart.dao.StudentDaoImplementation;
import com.flipkart.exception.CourseAlreadyRegisteredException;
import com.flipkart.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class pair {
	String course;
	String name;
	String prof;
	pair(String c,String n ,String p){
		course = c;prof = p;name = n;
	}
}

public class StudentOperations implements StudentInterface {

    private static volatile StudentOperations instance = null;
    private int value;
    // private constructor
    public StudentOperations() {
    }
    StudentDaoImplementation studentDaoImplementation=new StudentDaoImplementation();
    public static StudentOperations getInstance() {
        if (instance == null) {
            synchronized (StudentOperations.class) {
                instance = new StudentOperations();
            }
        }
        return instance;
    }

    @Override
    public void registeredCourseList(String studentId) throws SQLException {
      ArrayList<Integer> courses= studentDaoImplementation.registeredCoursesList(studentId);
      value = courses.size();
      for(Integer c:courses)
      {
          Course course=studentDaoImplementation.viewCourse(c);
        System.out.println(c+"-"+course.getCourseName());
      }
    }

    @Override
    public void registerCourses(String studentID) throws SQLException,CourseAlreadyRegisteredException{
        Scanner sc=new Scanner(System.in);
        System.out.println("The courses are: ");
        
        Connection connection = DBUtils.getConnection();
//        Statement stmt = connection.createStatement();
        
        String sql = "select course.courseId,course.courseName, userId from professorreg, course where course.courseId = professorreg.courseId;";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        ResultSet rs = statement.executeQuery();
        
        ArrayList<pair> courses = new ArrayList<pair>();
        
        while (rs.next()) {
            String courseId = rs.getString(1);
            String professorId = rs.getString(3);
            String name = rs.getString(2);
            
            courses.add(new pair(courseId,name,professorId));
        }
        
        System.out.println("CourseId-CourseName-ProfessorId");
        for(pair p:courses)
            System.out.println(p.course+"\t-\t"+p.name+"\t-\t"+p.prof);
        if(courses.size()==4)
            System.out.println("You have reached your limit of max courses");
        else {

            System.out.println("Enter number of courses you want to register : ");
            int count = sc.nextInt();
            
            while(count+courses.size()>4) {
                System.out.println("You can register for max 4 courses");
                System.out.println("Enter number of courses you want to register : ");
                count = sc.nextInt();
            }
            ArrayList<Integer> selectedCourse = new ArrayList<Integer>();
            for (int i = 0; i < count; i++) {
                selectedCourse.add(sc.nextInt());
            }
            studentDaoImplementation.registerCourses(studentID, selectedCourse);
        }
    }

    @Override
    public void viewGradeCard(String studentId) throws SQLException {
     ArrayList<GradeCard> gradeCards=studentDaoImplementation.viewGrades(studentId);
     for(GradeCard g:gradeCards)
     {
         System.out.println(studentDaoImplementation.viewCourse(g.getCourseId()).getCourseName()+"-"+g.getGrade());
     }
    }


    @Override
    public List<Course> viewCourses() throws SQLException {
        return studentDaoImplementation.viewCourses();
    }
}
