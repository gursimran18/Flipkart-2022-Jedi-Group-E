/**
 * 
 */
package com.crs.flipkart.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



/**
 * dbUtil class is Utility class for Database which creates the connection to the database.
 */
public class dbUtil {
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		
        if (connection != null)
            return connection;
        else {
            try {
//            	Properties prop = new Properties();
//                InputStream inputStream = dbUtil.class.getClassLoader().getResourceAsStream("config.properties");
//                prop.load(inputStream);
                String driver="com.mysql.cj.jdbc.Driver";
                String url="jdbc:mysql://localhost:3306/crs";
                String user="root";
                String password = "Blue_175973";
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
        }

    }

	
	/**
	 * Default function to close connections
	 */
	public static boolean closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } 
        catch (SQLException se) {
        	return false;
        }
        return true;
	}
}
