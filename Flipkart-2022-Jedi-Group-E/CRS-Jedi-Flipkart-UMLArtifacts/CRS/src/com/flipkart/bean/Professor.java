package com.flipkart.bean;

public class Professor extends User{
    private String professorId;
    private String areaOfExpertise;
    private int yearsOfExperience;

    public Professor() {
        this.professorId = professorId;
        this.areaOfExpertise = areaOfExpertise;
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     *
     * @param userId
     * @param userName
     * @param emailId
     * @param password
     * @param contactNo
     * @param professorId
     * @param areaOfExpertise
     * @param yearsOfExperience
     */
    public Professor(String userId, String userName, String emailId, String password, String contactNo, String professorId, String areaOfExpertise, int yearsOfExperience) {
        super(userId, userName, emailId, password, contactNo);
        this.professorId = professorId;
        this.areaOfExpertise = areaOfExpertise;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
