package com.ogong.pms.domain;

import java.sql.Date;

public class CeoMember {

  private int ceoNo;                // 사장회원 고유번호 
  private String ceoEmail;          // 사장회원 이메일 - 회원가입, 로그인 할 때 필요
  private String ceoPassword;       // 사장회원 패스워드
  private String ceoPhoto;          // 사장회원 사진
  private String ceoLicenseNo;      // 사장회원 사업자등록번호
  private String ceoBossName;       // 사장회원 대표자명
  private Date ceoregisteredDate;   // 사장회원 가입일

  @Override
  public String toString() {
    return "CeoMember [ceoNo=" + ceoNo + ", ceoEmail=" + ceoEmail + ", ceoPassword=" + ceoPassword
        + ", ceoPhoto=" + ceoPhoto + ", ceoLicenseNo=" + ceoLicenseNo + ", ceoBossName="
        + ceoBossName + ", ceoregisteredDate=" + ceoregisteredDate + "]";
  }

  public int getCeoNo() {
    return ceoNo;
  }

  public void setCeoNo(int ceoNo) {
    this.ceoNo = ceoNo;
  }

  public String getCeoEmail() {
    return ceoEmail;
  }

  public void setCeoEmail(String ceoEmail) {
    this.ceoEmail = ceoEmail;
  }

  public String getCeoPassword() {
    return ceoPassword;
  }

  public void setCeoPassword(String ceoPassword) {
    this.ceoPassword = ceoPassword;
  }

  public String getCeoPhoto() {
    return ceoPhoto;
  }

  public void setCeoPhoto(String ceoPhoto) {
    this.ceoPhoto = ceoPhoto;
  }

  public String getCeoLicenseNo() {
    return ceoLicenseNo;
  }

  public void setCeoLicenseNo(String ceoLicenseNo) {
    this.ceoLicenseNo = ceoLicenseNo;
  }

  public String getCeoBossName() {
    return ceoBossName;
  }

  public void setCeoBossName(String ceoBossName) {
    this.ceoBossName = ceoBossName;
  }

  public Date getCeoregisteredDate() {
    return ceoregisteredDate;
  }

  public void setCeoregisteredDate(Date ceoregisteredDate) {
    this.ceoregisteredDate = ceoregisteredDate;
  }

}