package com.ogong.pms.domain;

import java.sql.Date;

public class Member {

  public static final int INUSER = 0;   // 일반 유저
  public static final int OUTUSER = 1;       // 탈퇴 유저

  private int perNo;
  private String perNickname;
  private String perEmail;
  private String perPassword;
  private String perPhoto;
  private Date perRegisteredDate;
  private int perStatus;

  @Override
  public String toString() {
    return "Member [perNo=" + perNo + ", perNickname=" + perNickname + ", perEmail=" + perEmail
        + ", perPassword=" + perPassword + ", perPhoto=" + perPhoto + ", perRegisteredDate="
        + perRegisteredDate + ", perStatus=" + perStatus + "]";
  }

  public int getPerNo() {
    return perNo;
  }

  public void setPerNo(int perNo) {
    this.perNo = perNo;
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

  public int getPerStatus() {
    return perStatus;
  }

  public void setPerStatus(int perStatus) {
    this.perStatus = perStatus;
  }



}