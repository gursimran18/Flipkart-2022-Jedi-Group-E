package com.flipkart.service;

import com.flipkart.bean.Student;

import java.sql.SQLException;

public interface PaymentServiceInterface {
    void payFees(Student student) throws SQLException;
}
