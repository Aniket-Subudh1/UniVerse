package com.cms.model;

public class Course1 {
    private int courseId;
    private String courseName;
    private int teacherId;

    public Course1(int courseId, String courseName, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
