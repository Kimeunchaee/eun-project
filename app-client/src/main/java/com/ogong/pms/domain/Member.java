package com.ogong.pms.domain;

import java.sql.Date;

public class Member {

  public static final int INUSER = 1;   // 일반 유저
  public static final int OUTUSER = 2;  // 탈퇴 유저

  public static final int PER = 1;   // 개인유저
  public static final int CEO = 2;   // 사장유저

  private int perNo;
  private String perName;
  private String perNickname;
  private String perEmail;
  private String perPassword;
  private String perTel;
  private String perPhoto;
  private Date perRegisteredDate;
  private int active;   //  1: 일반, 2: 탈퇴
  private int perStatus; // 1: 개인, 2: 사장

  @Override
  public String toString() {
    return "Member [perNo=" + perNo + ", perName=" + perName + ", perNickname=" + perNickname
        + ", perEmail=" + perEmail + ", perPassword=" + perPassword + ", perTel=" + perTel
        + ", perPhoto=" + perPhoto + ", perRegisteredDate=" + perRegisteredDate + ", active="
        + active + ", perStatus=" + perStatus + "]";
  }

  public int getPerNo() {
    return perNo;
  }
  public void setPerNo(int perNo) {
    this.perNo = perNo;
  }
  public String getPerName() {
    return perName;
  }
  public void setPerName(String perName) {
    this.perName = perName;
  }
  public String getPerNickname() {
    return perNickname;
  }
  public void setPerNickname(String perNickname) {
    this.perNickname = perNickname;
  }
  public String getPerEmail() {
    return perEmail;
  }
  public void setPerEmail(String perEmail) {
    this.perEmail = perEmail;
  }
  public String getPerPassword() {
    return perPassword;
  }
  public void setPerPassword(String perPassword) {
    this.perPassword = perPassword;
  }
  public String getPerTel() {
    return perTel;
  }
  public void setPerTel(String perTel) {
    this.perTel = perTel;
  }
  public String getPerPhoto() {
    return perPhoto;
  }
  public void setPerPhoto(String perPhoto) {
    this.perPhoto = perPhoto;
  }
  public Date getPerRegisteredDate() {
    return perRegisteredDate;
  }
  public void setPerRegisteredDate(Date perRegisteredDate) {
    this.perRegisteredDate = perRegisteredDate;
  }
  public int getActive() {
    return active;
  }
  public void setActive(int active) {
    this.active = active;
  }
  public int getPerStatus() {
    return perStatus;
  }
  public void setPerStatus(int perStatus) {
    this.perStatus = perStatus;
  }
  public static int getInuser() {
    return INUSER;
  }
  public static int getOutuser() {
    return OUTUSER;
  }



}