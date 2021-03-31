package com.model.bean;


public class DeptEmp {

  private long empNo;
  private String deptNo;
  private java.sql.Date fromDate;
  private java.sql.Date toDate;


  public long getEmpNo() {
    return empNo;
  }

  public void setEmpNo(long empNo) {
    this.empNo = empNo;
  }


  public String getDeptNo() {
    return deptNo;
  }

  public void setDeptNo(String deptNo) {
    this.deptNo = deptNo;
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
