package com.ogong.pms.handler.mystudy;

import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.util.Prompt;

public class MyStudyExit {

  public void exitMyStudy(Study myStudy) {
    System.out.println();
    System.out.println("▶ 탈퇴하기");
    System.out.println();

    String str = AuthPerMemberLoginHandler.getLoginUser().getPerNickname();

    if (myStudy.getOwner().getPerNickname().equals(str) &&
        myStudy.getMembers().size() > 0) {
      System.out.println(" >> 구성원에게 조장 권한을 위임하고 탈퇴를 진행해 주세요.");
      return;
    }

    if (myStudy.getMemberNames().contains(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname()) || 
        (myStudy.getOwner().getPerNickname().equals(str) && myStudy.getMembers().size() == 0)) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      myStudy.getMembers().remove(AuthPerMemberLoginHandler.getLoginUser());
      System.out.println(" >> 탈퇴되었습니다.");

    }




  }

}
