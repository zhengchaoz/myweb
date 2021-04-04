package com.model.bean;


import java.util.Date;
import java.util.Objects;

public class DeptManager {

    private long empNo;
    private String deptNo;
    private Date fromDate;
    private Date toDate;


    public DeptManager() {
    }

    public DeptManager(long empNo, String deptNo, Date fromDate, Date toDate) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


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


    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.sql.Date fromDate) {
        this.fromDate = new Date(fromDate.getTime());
    }


    public Date getToDate() {
        return toDate;
    }

    public void setToDate(java.sql.Date toDate) {
        this.toDate = new Date(toDate.getTime());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptManager that = (DeptManager) o;
        return empNo == that.empNo && Objects.equals(deptNo, that.deptNo) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo, fromDate, toDate);
    }


    @Override
    public String toString() {
        return "DeptManager{" +
                "empNo=" + empNo +
                ", deptNo='" + deptNo + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
