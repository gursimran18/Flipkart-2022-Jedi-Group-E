package com.flipkart.service;

import com.flipkart.exception.OldPasswordNotValidException;

import java.sql.SQLException;

public interface UpdatePasswordInterface {
    void updatePassword() throws OldPasswordNotValidException;
}
