package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;

public interface AdminInterface {
    public void addProfessor(Professor professor);
    public void addCourse(Course course);
    public void dropCourse(int courseId);
    public boolean approveStudents();
//    public void generateReportCard(String studentId);
}
