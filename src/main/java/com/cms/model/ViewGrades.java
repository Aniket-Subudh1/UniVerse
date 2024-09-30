package com.cms.model;

public class ViewGrades {
    private String studentId;
    private String subjectCode;
    private String grade;

    // Constructor
    public ViewGrades(String studentId, String subjectCode, String grade) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.grade = grade;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
