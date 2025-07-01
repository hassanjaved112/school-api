package com.school.school_api.dto;

public class GradeSummaryDTO {

    private String courseName;
    private String gradeValue;
    private double gpaValue;

    public GradeSummaryDTO() {}

    public GradeSummaryDTO(String courseName, String gradeValue, double gpaValue) {
        this.courseName = courseName;
        this.gradeValue = gradeValue;
        this.gpaValue = gpaValue;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public double getGpaValue() {
        return gpaValue;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public void setGpaValue(double gpaValue) {
        this.gpaValue = gpaValue;
    }
}