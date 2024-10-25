package com.cms.model;

public class Grade {
    private String studentId;
    private String courseId;
    private String grade;

    public Grade(String studentId, String courseId, String grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
