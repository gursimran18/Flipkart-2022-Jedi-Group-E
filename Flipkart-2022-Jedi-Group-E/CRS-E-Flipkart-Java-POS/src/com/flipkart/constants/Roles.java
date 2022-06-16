/**
 * 
 */
package com.crs.flipkart.constants;


public enum Roles {
    ADMIN,PROFESSOR,STUDENT;

    public static Roles stringToName(String role)
    {
        Roles userRole=null;

        if(role.equalsIgnoreCase("ADMIN"))
            userRole=Roles.ADMIN;
        else if(role.equalsIgnoreCase("PROFESSOR"))
            userRole=Roles.PROFESSOR;
        else if(role.equalsIgnoreCase("STUDENT"))
            userRole=Roles.STUDENT;
        return userRole;
    }
    
    public static String nameToString(Roles role)
    {
    	String userRole=null;

        if(role == ADMIN)
            userRole="Admin";
        else if(role == PROFESSOR)
            userRole="Professor";
        else if(role == STUDENT)
            userRole="Student";
        return userRole;
    }
}