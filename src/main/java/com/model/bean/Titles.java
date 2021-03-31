package com.model.bean;


public class Titles {

  private long empNo;
  private String title;
  private java.sql.Date fromDate;
  private java.sql.Date toDate;


  public long getEmpNo() {
    return empNo;
  }

  public void setEmpNo(long empNo) {
    this.empNo = empNo;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public java.sql.Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(java.sql.Date fromDate) {
    this.fromDate = fromDate;
  }


  public java.sql.Date getToDate() {
    return toDate;
  }

  public void setToDate(java.sql.Date toDate) {
    this.toDate = toDate;
  }

}
