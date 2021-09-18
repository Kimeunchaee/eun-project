package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MyStudyGuilder  {



  // 스터디 구성원 목록
  protected void listGuilder(Study myStudy) {
    System.out.println();
    System.out.println("▶ 구성원 보기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    System.out.println(" >> 스터디 구성원");
    System.out.println(" 조  장 : " + myStudy.getOwner().getPerNickname());
    System.out.println(" 구성원 : " + myStudy.getMemberNames());

    System.out.println();
    System.out.println(">> 승인 대기 중");
    if(myStudy.getWatingMemberNames().isEmpty()) {
      System.out.println(" 승인 대기 중인 회원이 없습니다.");
    }
    System.out.println(myStudy.getWatingMemberNames());

    List<Member> waitingMembers = myStudy.getWatingMember();

    if (member != null && myStudy.getOwner().getPerEmail().equals(member.getPerEmail())) {
      System.out.println();
      if (!myStudy.getWatingMemberNames().equals("")) {
        String input = Prompt.inputString(" 대기 중인 회원 중 승인할 닉네임을 입력하세요 : ");
        Member m = new Member();

        for (Member watingMember : waitingMembers) {        
          if (watingMember.getPerNickname().equals(input)) {
            myStudy.getMembers().add(watingMember);
            System.out.printf(" '%s님'이 구성원으로 승인되었습니다.\n", watingMember.getPerNickname());
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
