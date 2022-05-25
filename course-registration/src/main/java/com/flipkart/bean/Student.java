package com.flipkart.bean;

public class Student extends User{

//    private String grade;
    private String feeStatus;
    private boolean isApproved;
    public  Student(){}
    public Student(String id, String name, String email, String password, /*String grade,*/ String feeStatus,boolean isApproved) {
    	super(id, name, email, password);
    	
//        this.grade = grade;
        this.feeStatus = feeStatus;
        this.isApproved = isApproved;
    }


//    public String getGrade() {
//        return grade;
//    }
//
//    public void setGrade(String grade) {
//        this.grade = grade;
//    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }
}