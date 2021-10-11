package com.ogong.pms.domain;

import java.sql.Date;

public class AdminNotice {
  private int adminNotiNo; // 공지게시판 번호
  private String adminNotiTitle; // 공지게시판 제목
  private String adminNotiContent; // 공지게시판 내용
  private String adminNotiWriter; // 공지게시판 작성자
  private Date adminNotiRegisteredDate; // 공지게시판 등록일

  @Override
  public String toString() {
    return "AdminNotice [adminNotiNo=" + adminNotiNo + ", adminNotiTitle=" + adminNotiTitle
        + ", adminNotiContent=" + adminNotiContent + ", adminNotiWriter=" + adminNotiWriter
        + ", adminNotiRegisteredDate=" + adminNotiRegisteredDate + "]";
  }

  public int getAdminNotiNo() {
    return adminNotiNo;
  }
  public void setAdminNotiNo(int adminNotiNo) {
    this.adminNotiNo = adminNotiNo;
  }
  public String getAdminNotiTitle() {
    return adminNotiTitle;
  }
  public void setAdminNotiTitle(String adminNotiTitle) {
    this.adminNotiTitle = adminNotiTitle;
  }
  public String getAdminNotiContent() {
    return adminNotiContent;
  }
  public void setAdminNotiContent(String adminNotiContent) {
    this.adminNotiContent = adminNotiContent;
  }


  public String getAdminNotiWriter() {
    return adminNotiWriter;
  }
  public void setAdminNotiWriter(String adminNotiWriter) {
    this.adminNotiWriter = adminNotiWriter;
  }
  public Date getAdminNotiRegisteredDate() {
    return adminNotiRegisteredDate;
  }
  public void setAdminNotiRegisteredDate(Date adminNotiRegisteredDate) {
    this.adminNotiRegisteredDate = adminNotiRegisteredDate;
  }


}
