package com.ogong.pms.domain;

import java.util.Date;
import java.util.List;

public class Study {

  private int studyNo;              // 스터디 번호
  private String studyTitle;        // 스터디명
  private Member owner;             // 작성자(조장)
  private String subject;           // 분야
  private String area;              // 지역
  private int numberOfPeple;         // 인원수
  private String face;              // 대면/비대면
  private String introduction;       // 소개글
  private Date registeredDate;       // 스터디 가입일
  private List<Member> members;     //  참여중인 구성원
  private List<Member> watingMember; // 참여승인을 기다리는 회원
  private List<Member> bookMarkMember; // 북마크한 회원

  private List<Calender> myStudyCalender;  // 내 스터디 캘린더
  private List<FreeBoard> myStudyFreeBoard; // 내 스터디 자유 게시판
  private List<ToDo> myStudyToDo; // 내 스터디 투두리스트



  @Override
  public String toString() {
    return "Study [studyNo=" + studyNo + ", studyTitle=" + studyTitle + ", owner=" + owner
        + ", subject=" + subject + ", area=" + area + ", numberOfPeple=" + numberOfPeple + ", face="
        + face + ", introduction=" + introduction + ", registeredDate=" + registeredDate
        + ", members=" + members + ", watingMember=" + watingMember + ", bookMarkMember="
        + bookMarkMember + ", myStudyCalender=" + myStudyCalender + ", myStudyFreeBoard="
        + myStudyFreeBoard + ", myStudyToDo=" + myStudyToDo + "]";
  }


  public int getStudyNo() {
    return studyNo;
  }


  public void setStudyNo(int studyNo) {
    this.studyNo = studyNo;
  }


  public String getStudyTitle() {
    return studyTitle;
  }


  public void setStudyTitle(String studyTitle) {
    this.studyTitle = studyTitle;
  }


  public Member getOwner() {
    return owner;
  }


  public void setOwner(Member owner) {
    this.owner = owner;
  }


  public String getSubject() {
    return subject;
  }


  public void setSubject(String subject) {
    this.subject = subject;
  }


  public String getArea() {
    return area;
  }


  public void setArea(String area) {
    this.area = area;
  }


  public int getNumberOfPeple() {
    return numberOfPeple;
  }


  public void setNumberOfPeple(int numberOfPeple) {
    this.numberOfPeple = numberOfPeple;
  }


  public String getFace() {
    return face;
  }


  public void setFace(String face) {
    this.face = face;
  }


  public String getIntroduction() {
    return introduction;
  }


  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }


  public Date getRegisteredDate() {
    return registeredDate;
  }


  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }


  public List<Member> getMembers() {
    return members;
  }


  public void setMembers(List<Member> members) {
    this.members = members;
  }


  public List<Member> getWatingMember() {
    return watingMember;
  }


  public void setWatingMember(List<Member> watingMember) {
    this.watingMember = watingMember;
  }

  public String getMemberNames() {
    if (this.members == null) {
      return "";
    }
    StringBuilder names = new StringBuilder();
    for (Member member : this.members) {
      if (names.length() > 0) {
        names.append(", ");
      }
      names.append(member.getPerNickname());
    }
    return names.toString();
  }

  public List<Member> getBookMarkMember() {
    return bookMarkMember;
  }


  public void setBookMarkMember(List<Member> bookMarkMember) {
    this.bookMarkMember = bookMarkMember;
  }


  public List<Calender> getMyStudyCalender() {
    return myStudyCalender;
  }

  public void setMyStudyCalender(List<Calender> myStudyCalender) {
    this.myStudyCalender = myStudyCalender;
  }


  public List<FreeBoard> getMyStudyFreeBoard() {
    return myStudyFreeBoard;
  }


  public void setMyStudyFreeBoard(List<FreeBoard> myStudyFreeBoard) {
    this.myStudyFreeBoard = myStudyFreeBoard;
  }

  public FreeBoard findFreeBoardByNo(int inputFreeBoardNo) {
    for (FreeBoard freeBoard : this.myStudyFreeBoard) {
      if (freeBoard.getFreeBoardNo() == inputFreeBoardNo) {
        return freeBoard;
      }
    }
    return null;
  }

  public String getWatingMemberNames() {
    if (this.members == null) {
      return "";
    }
    StringBuilder names = new StringBuilder();
    for (Member member : this.watingMember) {
      if (names.length() > 0) {
        names.append(",");
      }
      names.append(member.getPerNickname());
    }
    return names.toString();
  }

  public List<ToDo> getMyStudyToDo() {
    return myStudyToDo;
  }


  public void setMyStudyToDo(List<ToDo> myStudyToDo) {
    this.myStudyToDo = myStudyToDo;
  }

}
