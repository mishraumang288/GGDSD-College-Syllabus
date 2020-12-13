package com.example.syllabusagain.models;

public class Download {
    String CourseName;
    String SemNumber;
    String PdfLink;

    public Download(String courseName, String semNumber, String pdfLink) {
        CourseName = courseName;
        SemNumber = semNumber;
        PdfLink = pdfLink;
    }

    public Download() {
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getSemNumber() {
        return SemNumber;
    }

    public void setSemNumber(String semNumber) {
        SemNumber = semNumber;
    }

    public String getPdfLink() {
        return PdfLink;
    }

    public void setPdfLink(String pdfLink) {
        PdfLink = pdfLink;
    }
}
