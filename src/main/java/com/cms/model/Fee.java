package com.cms.model;
// Fee.java
public class Fee {
    private int studentId;
    private int feeId;
    private String registrationId;
    private double academicFee;
    private double hostelFee;
    private double examFee;

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getRegistrationId() {
        return registrationId;
    }
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
    public double getAcademicFee() {
        return academicFee;
    }
    public void setAcademicFee(double academicFee) {
        this.academicFee = academicFee;
    }
    public double getHostelFee() {
        return hostelFee;
    }
    public void setHostelFee(double hostelFee) {
        this.hostelFee = hostelFee;
    }
    public double getExamFee() {
        return examFee;
    }
    public void setExamFee(double examFee) {
        this.examFee = examFee;
    }
}
