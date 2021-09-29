package com.ogong.pms.domain;

import java.sql.Date;

public class Comment {

  private int commentNo; // 댓글 번호
  private String commentText;   // 댓글 내용
  private Member commentWiter;  // 회원 댓글 작성자
  private Admin commentAdminWiter;  // 관리자 댓글 작성자
  private Date commentRegisteredDate;   // 댓글 작성일


  @Override
  public String toString() {
    return "Comment [commentNo=" + commentNo + ", commentText=" + commentText + ", commentWiter="
        + commentWiter + ", commentAdminWiter=" + commentAdminWiter + ", commentRegisteredDate="
        + commentRegisteredDate + "]";
  }

  public int getCommentNo() {
    return commentNo;
  }
  public void setCommentNo(int commentNo) {
    this.commentNo = commentNo;
  }
  public String getCommentText() {
    return commentText;
  }
  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }
  public Member getCommentWiter() {
    return commentWiter;
  }
  public void setCommentWiter(Member commentWiter) {
    this.commentWiter = commentWiter;
  }
  public Admin getCommentAdminWiter() {
    return commentAdminWiter;
  }
  public void setCommentAdminWiter(Admin commentAdminWiter) {
    this.commentAdminWiter = commentAdminWiter;
  }
  public Date getCommentRegisteredDate() {
    return commentRegisteredDate;
  }
  public void setCommentRegisteredDate(Date commentRegisteredDate) {
    this.commentRegisteredDate = commentRegisteredDate;
  }
}
