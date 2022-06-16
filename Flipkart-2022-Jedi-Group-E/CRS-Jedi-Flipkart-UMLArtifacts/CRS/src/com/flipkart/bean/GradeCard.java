package com.flipkart.bean;

public class GradeCard {
    private String userId;
    private int courseId;
    private String grade;

    /**
     *
     * @param userId
     * @param courseId
     * @param grade
     */
    public GradeCard(String userId, int courseId, String grade) {
        this.userId = userId;
        this.courseId = courseId;
        this.grade = grade;
    }

    /**
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    /**
     *
     * @param courseId
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    /**
     *
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }
}