package com.flipkart.application;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;

import java.util.Scanner;

public class AdminMenu {
    public void adminMenu(){

        int choice;
        int d=1;
        while(d!=0) {
            System.out.println("Enter your choice : ");
            System.out.println("1. Add Professor");
            System.out.println("2. Add Course");
            System.out.println("3. Drop Course");
            System.out.println("4. Approve Pending Students");
            System.out.println("5. Exit");
            Scanner in= new Scanner(System.in);

            choice = in.nextInt();
            switch (choice) {

                case 1:
                    System.out.println("Enter Prof ID");
                    String profId = in.next();
                    System.out.println("Name:");
                    String name = in.next();
                    System.out.println("Professor Email id ");
                    String email = in.next();
                    System.out.println("Password: ");
                    String password = in.next();
                    System.out.println("Professor Contact number");
                    String contact = in.next();
                    System.out.println("Area of expertise");
                    String areaOfExpertise = in.next();
                    System.out.println("Years of experience");
                    int yoe = in.nextInt();
                    Professor p = new Professor();
                    p.setUserId(profId);
                    p.setUserName(name);
                    p.setEmailId(email);
                    p.setPassword(password);
                    p.setContactNo(contact);
                    p.setAreaOfExpertise(areaOfExpertise);
                    p.setYearsOfExperience(yoe);
                    AdminInterface admin0 = AdminOperation.getInstance();

                    try{
                        admin0.addProfessor(p);
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                case 2:
                    System.out.println("Enter Course ID");
                    int courseId = in.nextInt();
                    System.out.println("Course Name:");
                    String courseName = in.next();
                    Course c = new Course();
                    c.setCourseId(courseId);
                    c.setCourseName(courseName);
                    AdminInterface admin1 = AdminOperation.getInstance();

                    try{
                        admin1.addCourse(c);
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                case 3:
                    System.out.println("Enter Course ID");
                    int cId = in.nextInt();

                    AdminInterface admin2= AdminOperation.getInstance();

                    try{
                        admin2.dropCourse(cId);
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                case 4:
                    AdminInterface admin3= AdminOperation.getInstance();
                    try{
                        admin3.approveStudents();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;

                case 5:
                    System.out.println("ok bye");
                    d=0;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
