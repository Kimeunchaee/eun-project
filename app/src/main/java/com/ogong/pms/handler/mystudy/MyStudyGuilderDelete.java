package com.ogong.pms.handler.mystudy;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MyStudyGuilderDelete {

  protected void guilderDelete(Study myStudy) {
    System.out.println();
    System.out.println("▶ 구성원 탈퇴시키기");
    System.out.println();

    List<Member> guilerMembers = myStudy.getMembers();

    if (myStudy.getMembers().isEmpty()) {
      System.out.println(" >> 탈퇴시킬 구성원이 없습니다.");
      return;
    }

    if (!myStudy.getMemberNames().equals("")) {
      String inputGuilderName = Prompt.inputString(" >> 탈퇴시킬 구성원의 닉네임을 입력하세요 : ");
      Member guilerList = new Member();

      for (Member guilerMember : guilerMembers) {        

        if (!guilerMember.getPerNickname().equals(inputGuilderName)) {
          System.out.println();
          System.out.println(" >> 닉네임을 다시 입력하세요.");
        }

        if (guilerMember.getPerNickname().equals(inputGuilderName)) {

          System.out.println();
          String input = Prompt.inputString(" 정말 탈퇴시키겠습니까? (네 / 아니오) ");

          if (!input.equalsIgnoreCase("네")) {
            System.out.println();
            System.out.println(" >> 구성원 탈퇴를 취소하였습니다.");
            return;
          }

          myStudy.getMembers().remove(guilerList);
          System.out.println();
          System.out.println(" >> 구성원이 탈퇴되었습니다.");
          guilerList = guilerMember;
        }
      }
      if (guilerList != null) {
        myStudy.getMembers().remove(guilerList);
      }
      return;
    }
  }
}