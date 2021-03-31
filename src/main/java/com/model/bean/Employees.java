package com.model.bean;


public class Employees {

  private long empNo;
  private java.sql.Date birthDate;
  private String firstName;
  private String lastName;
  private String gender;
  private java.sql.Date hireDate;


  public long getEmpNo() {
    return empNo;
  }

  public void setEmpNo(long empNo) {
    this.empNo = empNo;
  }


  public java.sql.Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(java.sql.Date birthDate) {
    this.birthDate = birthDate;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }


  public java.sql.Date getHireDate() {
    return hireDate;
  }

  public void setHireDate(java.sql.Date hireDate) {
    this.hireDate = hireDate;
  }

}
