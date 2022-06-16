/**
 * 
 */
package com.crs.flipkart.constants;


public enum Roles {
    ADMIN,PROFESSOR,STUDENT;

    /**
     *
     * @param role
     * @return returns the Role object of the corresponding role string
     */
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

    /**
     *
     * @param role
     * @return returns the role in string format of the corresponding role object
     */
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