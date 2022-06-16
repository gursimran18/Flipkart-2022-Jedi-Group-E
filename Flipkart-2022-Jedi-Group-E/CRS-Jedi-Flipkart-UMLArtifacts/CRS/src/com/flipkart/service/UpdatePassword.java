package com.flipkart.service;

import com.flipkart.dao.AdminDaoImplementation;
import com.flipkart.dao.UpdatePasswordDao;
import com.flipkart.exception.OldPasswordNotValidException;
//import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

//import static com.mysql.cj.conf.PropertyKey.logger;

public class UpdatePassword implements UpdatePasswordInterface{
//    final org.apache.log4j.Logger logger = Logger.getLogger(AdminDaoImplementation.class);

    @Override
    public void updatePassword() throws OldPasswordNotValidException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter UserId");
        String userId=sc.next();

        System.out.println("Enter Old Password");
        String oldPassword=sc.next();
        System.out.println("Enter New Password");
        String newPassword=sc.next();
        UpdatePasswordDao updatePasswordDao=new UpdatePasswordDao();
        boolean ok = false;
        try {
            ok = updatePasswordDao.updatePassword(userId,oldPassword,newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(ok == true) System.out.println("Password Updated Successfully");//logger.info("Password updated successfully !!!");
        else{
            throw new OldPasswordNotValidException(userId);
        }
    }
}
