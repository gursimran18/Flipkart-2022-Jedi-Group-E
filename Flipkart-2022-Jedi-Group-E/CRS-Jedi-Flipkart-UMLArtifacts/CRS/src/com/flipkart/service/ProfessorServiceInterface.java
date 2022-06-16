package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ProfessorServiceInterface {
    public Professor validateCredentials(String userId,String password);
    public ArrayList<Course> viewAllCourses() throws SQLException, ClassNotFoundException;
    public void registerCourses(Professor professor) throws SQLException, IOException;
    public Map<String, ArrayList<String>> viewEnrolledStudents(Professor professor) throws SQLException;
    public void assignGrades(Professor professor) throws SQLException, IOException;
    }
