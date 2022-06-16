package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseAlreadyRegisteredException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentInterface {

    public void registeredCourseList(String studentId) throws SQLException;
    public void registerCourses(String studentID) throws SQLException, CourseAlreadyRegisteredException;

    void viewGradeCard(String studentId) throws SQLException;

    public List<Course> viewCourses() throws SQLException;
}

