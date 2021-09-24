package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MyStudyGuilderList {

  // 스터디 구성원 목록
  protected void listGuilder(Study myStudy) {
    System.out.println();
    System.out.println("▶ 승인 대기 목록");
    System.out.println();

    if (!myStudy.getWatingMemberNames().isEmpty()) {

      System.out.println(" " +myStudy.getWatingMemberNames());
      System.out.println("\n----------------------");
      System.out.println(" [ 승인 / 거절 ] ");
      System.out.println();
      System.out.println("1. 승인");
      System.out.println("2. 거절");
      System.out.println("0. 취소");
      System.out.println();

      int inputGuilerNo = Prompt.inputInt("선택> ");
      switch (inputGuilerNo) {
        case 1: agreeStudyMember(myStudy); break;
        case 2: disagreeStudyMember(myStudy); return;
        case 0: return;
        default: return;
      }
    }
  }

  private void agreeStudyMember(Study myStudy) {

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    List<Member> waitingMembers = myStudy.getWatingMember();

    if (member != null && myStudy.getOwner().getPerEmail().equals(member.getPerEmail())) {
      System.out.println();
      if (!myStudy.getWatingMemberNames().equals("")) {
        String input = Prompt.inputString(" >> 대기 중인 회원 중 승인할 닉네임을 입력하세요 : ");
        Member m = new Member();

        for (Member watingMember : waitingMembers) {    

          if (!watingMember.getPerNickname().equals(input)) {
            System.out.println(" >> 닉네임을 다시 입력하세요.");
          }

          if (watingMember.getPerNickname().equals(input)) {
            myStudy.getMembers().add(watingMember);
            System.out.printf(" >> '%s님'이 구성원으로 승인되었습니다.\n", watingMember.getPerNickname());
            m = watingMember;
          }
        }
        if (m != null) {
          myStudy.getWatingMember().remove(m);
        }
        return;
      }
    }
  }

  private void disagreeStudyMember(Study myStudy) {

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    List<Member> waitingMembers = myStudy.getWatingMember();

    if (member != null && myStudy.getOwner().getPerEmail().equals(member.getPerEmail())) {
      System.out.println();
      if (!myStudy.getWatingMemberNames().equals("")) {
        String input = Prompt.inputString(" >> 대기 중인 회원 중 거절할 닉네임을 입력하세요 : ");
        Member m = new Member();

        for (Member watingMember : waitingMembers) {  

          if (!watingMember.getPerNickname().equals(input)) {
            System.out.println(" >> 닉네임을 다시 입력하세요.");
          }

          if (watingMember.getPerNickname().equals(input)) {
            myStudy.getWatingMember().remove(m);
            System.out.printf(" >> '%s님'의 구성원 신청이 거절되었습니다.\n", watingMember.getPerNickname());
            m = watingMember;
          }
        }
        if (m != null) {
          myStudy.getWatingMember().remove(m);
        }
        return;
      }
    }
  }

}
