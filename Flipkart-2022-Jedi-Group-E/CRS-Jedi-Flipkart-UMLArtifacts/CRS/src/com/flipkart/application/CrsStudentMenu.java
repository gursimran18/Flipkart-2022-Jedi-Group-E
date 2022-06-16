package com.flipkart.application;
import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseAlreadyRegisteredException;
import com.flipkart.service.PaymentServiceImplementation;
import com.flipkart.service.PaymentServiceInterface;
import com.flipkart.dao.StudentDaoImplementation;
import com.flipkart.service.StudentOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrsStudentMenu {

        public void studentMenu(Student student) throws IOException, SQLException, CourseAlreadyRegisteredException {
            while(true) {
                System.out.println("");
                System.out.println("-----STUDENT MENU-----");
                System.out.println("1.view Details\n2.view Courses\n3.Register for Courses\n4.View Report Card\n5.Pay Fee\n6.Check Fee Status\n7.View Registered Courses\n8.Exit\n");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter the choice");
                int option = Integer.parseInt(br.readLine());
                StudentOperations studentOperations=new StudentOperations();
                StudentDaoImplementation studentDaoImplementation=new StudentDaoImplementation();
                switch (option) {
                    case 1:
                        System.out.println("Student Details -------");
                        System.out.println("Student Id: "+student.getUserId()+"\nStudent Name: "+student.getUserName()+"\nEmail Id: "+student.getEmailId()+"\nSemester: "+student.getSemester());
                        System.out.println("");
                        break;
                    case 2:
                        System.out.println("List of Courses Available");
                        ArrayList<Course> courses= (ArrayList<Course>) studentOperations.viewCourses();
                        System.out.println("CourseId-CourseName");
                        for(Course c:courses)
                            System.out.println(c.getCourseId()+"\t-\t"+c.getCourseName());
                        break;
                    case 3:
                        System.out.println("Register for the courses");
                        studentOperations.registerCourses(student.getUserId());
                        break;
                    case 4:
                        System.out.println("Student Report Card");
                        studentOperations.viewGradeCard(student.getUserId());
                        break;
                    case 5:
                        System.out.println("Pay Fee");
                        PaymentServiceInterface psi = new PaymentServiceImplementation();
                        psi.payFees(student);
                        break;
                    case 6:
                        System.out.println("Current Fee Status Says : ");
                        System.out.println(studentDaoImplementation.getfeeStatus(student.getUserId()));
                        break;
                    case 7:
                        System.out.println("You are Registered to following Courses :");
                        studentOperations.registeredCourseList(student.getUserId());
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Exit");
                }
            }
        }

    }