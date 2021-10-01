package com.ogong.pms.handler.mystudy;

import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.util.Prompt;

public class MyStudyGuilder {

  MyStudyGuilderList myStudyGuilderList; // 구성원 목록
  MyStudyGuilderDelete myStudyGuilderDelete; // 구성원 탈퇴
  MyStudyGuilderEntrust myStudyGuilderEntrust; // 조장 위임

  public MyStudyGuilder(MyStudyGuilderList myStudyGuilderList, 
      MyStudyGuilderDelete myStudyGuilderDelete, MyStudyGuilderEntrust myStudyGuilderEntrust) {
    this.myStudyGuilderList = myStudyGuilderList;
    this.myStudyGuilderDelete = myStudyGuilderDelete;
    this.myStudyGuilderEntrust = myStudyGuilderEntrust;
  }

  // 스터디 구성원 목록
  public void ownerGuilder(Study myStudy) {
    System.out.println();
    System.out.println("▶ 구성원");
    System.out.println();

    System.out.printf(" >> 스터디 구성원 (%s/%s명)\n" , myStudy.getMembers().size() + 1,
        myStudy.getNumberOfPeple());
    System.out.println(" 조  장 : " + myStudy.getOwner().getPerNickname());
    System.out.println(" 구성원 : " + myStudy.getMemberNames());

    // 조장만 보임
    if (!AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname())) {
      return;
    }

    if(AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname()) && !myStudy.getWatingMemberNames().isEmpty()) {
      System.out.printf("\n ★ > 승인 대기 중인 회원이 %d명 있습니다.", myStudy.getWatingMember().size());
    } 

    else if(AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname()) && myStudy.getWatingMemberNames().isEmpty()) {
      System.out.println("\n ☆ > 승인 대기 중인 회원이 없습니다.");
    }

    System.out.println("\n----------------------");
    System.out.println();
    System.out.println("1. 승인 대기 목록");
    System.out.println("2. 조장 권한 위임");
    System.out.println("3. 구성원 탈퇴시키기");
    System.out.println("0. 뒤로 돌아가기");
    System.out.println();

    int inputGuilerNo = Prompt.inputInt("선택> ");
    switch (inputGuilerNo) {
      case 1: myStudyGuilderList.listGuilder(myStudy); break;
      case 2: myStudyGuilderEntrust.entrustGuilder(myStudy); return;
      case 3: myStudyGuilderDelete.guilderDelete(myStudy); break;
      default: return;
    }
  }

}
