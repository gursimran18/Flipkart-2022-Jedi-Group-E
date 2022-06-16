package com.flipkart.application;

import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseAlreadyRegisteredException;
import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.ProfessorService;
import com.flipkart.dao.AdminDaoImplementation;
import com.flipkart.dao.StudentDaoImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class CrsLoginMenu {


    public void crsLoginMenu() throws IOException, SQLException, UserNotFoundException, CourseAlreadyRegisteredException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the User Type\n1.Student \n2.Professor \n3.Admin");
        int userType=Integer.parseInt(br.readLine());
        System.out.println("Enter the userId:");
        String userId=br.readLine();
        System.out.println("Enter the password:");
        String password=br.readLine();
        switch(userType){
            case 1:

                System.out.println("Validating Student credentials ........");
                StudentDaoImplementation studentDaoImplementation=new StudentDaoImplementation();
                Student student=studentDaoImplementation.validateCredentials(userId,password);

                if(student!=null && student.isApproved()){
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    System.out.println("Hey Student. Welcome to the portal");
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    CrsStudentMenu crsStudentMenu=new CrsStudentMenu();
                    crsStudentMenu.studentMenu(student);
                }
                else if (student==null){
                    throw new UserNotFoundException(userId);
                }
                else{
                    System.out.println("-----------------Cant LOGIN--------------------");
                    System.out.println("--- Student has not been approved yet !! -------");
                    System.out.println("------------------------------------------------");
                }
                break;
            case 2:
                System.out.println("Validating Professor credentials ......");
                ProfessorService profServ=new ProfessorService();
                Professor professor=profServ.validateCredentials(userId,password);
                if(professor!=null){
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    System.out.println("Hey Professor. Welcome to the portal");
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    CrsProfessorMenu crsProfessorMenu=new CrsProfessorMenu();
                    crsProfessorMenu.professorMenu(professor);
                }
                else{
                    System.out.println("Invalid User ID");
                    return;
                }
                break;
            case 3:
                System.out.println("Validating Admin credentials ..........");
                AdminDaoImplementation adminDaoImplementation=new AdminDaoImplementation();
                boolean x= adminDaoImplementation.validateCredentials(userId,password);
                if(x==true){
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    System.out.println("Hey Admin. Welcome to the portal");
                    System.out.println("+++++++++++++++++++++++++++++++++++");
                    AdminMenu adminMenu=new AdminMenu();
                    adminMenu.adminMenu();
                }
                else{
                    System.out.println("Invalid User ID");
                    return;
                }
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
}
