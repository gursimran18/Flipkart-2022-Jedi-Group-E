package com.flipkart.application;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.service.ProfessorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class CrsProfessorMenu {
    ProfessorService profServ=new ProfessorService();
    public void professorMenu(Professor professor) throws IOException, SQLException {
        while(true) {
            System.out.println("---Professor Menu-----");
            System.out.println("1.view Details\n2.view Courses\n3.Register for Courses\n4.View Enrolled Students in courses\n5.Make Report Card for a student\n6.Exit");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the choice");
            int option = Integer.parseInt(br.readLine());
            switch (option) {
                case 1:
                    System.out.println("Details");
                    System.out.println("Id: "+professor.getUserId()+"\nProfessorName: "+professor.getUserName()+"\nEmailId: "+professor.getEmailId()+"\nAreaOfExpertise: "+professor.getAreaOfExpertise()+"\nYearsOfExperience: "+professor.getYearsOfExperience());
                    break;
                case 2:
                    ArrayList<Course> courses= profServ.viewAllCourses();
                    System.out.println("CourseId-CourseName");
                    for(Course c:courses)
                        System.out.println(c.getCourseId()+"\t-\t"+c.getCourseName());
                    break;
                case 3:
                    System.out.println("Register for the courses");
                    profServ.registerCourses(professor);
                    break;
                case 4:
                    System.out.println("View enrolled students in each course");
                    Map<String,ArrayList<String>> courseWithStudents=profServ.viewEnrolledStudents(professor);
                    int courseindex=1;
                    for(String CourseName:courseWithStudents.keySet()){
                        System.out.println(courseindex+". "+"("+CourseName+")");
                        int studentsIndex=1;
                        for(String student:courseWithStudents.get(CourseName)){
                            System.out.println("\t"+studentsIndex+". ("+student+")");
                            studentsIndex++;
                        }
                        courseindex++;
                    }
                    break;
                case 5:
                    System.out.println("Make a report card for a student");
                    profServ.assignGrades(professor);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid Choice");

            }
        }
    }

}
