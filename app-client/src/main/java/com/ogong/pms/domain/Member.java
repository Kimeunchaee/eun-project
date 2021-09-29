package com.ogong.pms.domain;

import java.sql.Date;

public class Member {

  private int perNo;
  private String perNickname;
  private String perEmail;
  private String perPassword;
  private String perPhoto;
  private Date perRegisteredDate;

  @Override
  public String toString() {
    return "Member [perNo=" + perNo + ", perNickname=" + perNickname + ", perEmail=" + perEmail
        + ", perPassword=" + perPassword + ", perPhoto=" + perPhoto + ", perRegisteredDate="
        + perRegisteredDate + "]";
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

}