package com.ogong.pms.domain;

import java.sql.Date;

public class Reply {

  private int replyNo; // 답변 번호
  private String replyTitle;  // 답변 제목
  private String replyContent;   // 답변 내용
  private Admin replyAdminWiter;  // 관리자(답변 작성자)
  private Date replyRegisteredDate;   // 답변 작성일

  @Override
  public String toString() {
    return "Reply [replyNo=" + replyNo + ", replyTitle=" + replyTitle + ", replyContent="
        + replyContent + ", replyAdminWiter=" + replyAdminWiter + ", replyRegisteredDate="
        + replyRegisteredDate + "]";
  }

  public int getReplyNo() {
    return replyNo;
  }

  public void setReplyNo(int replyNo) {
    this.replyNo = replyNo;
  }

  public String getReplyTitle() {
    return replyTitle;
  }

  public void setReplyTitle(String replyTitle) {
    this.replyTitle = replyTitle;
  }

  public String getReplyContent() {
    return replyContent;
  }

  public void setReplyContent(String replyContent) {
    this.replyContent = replyContent;
  }

  public Admin getReplyAdminWiter() {
    return replyAdminWiter;
  }

  public void setReplyAdminWiter(Admin replyAdminWiter) {
    this.replyAdminWiter = replyAdminWiter;
  }

  public Date getReplyRegisteredDate() {
    return replyRegisteredDate;
  }

  public void setReplyRegisteredDate(Date replyRegisteredDate) {
    this.replyRegisteredDate = replyRegisteredDate;
  }



}
