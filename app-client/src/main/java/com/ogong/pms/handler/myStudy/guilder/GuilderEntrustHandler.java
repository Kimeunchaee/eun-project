package com.ogong.pms.handler.myStudy.guilder;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class GuilderEntrustHandler implements Command { 

  RequestAgent requestAgent;

  public GuilderEntrustHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 조장 권한 위임");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    Study myStudy = requestAgent.getObject(Study.class);

    List<Member> entrustGuilers = myStudy.getMembers();

    if (myStudy.getMembers().isEmpty()) {
      System.out.println(" >> 해당 스터디 구성원이 없습니다.");
      return;
    }

    System.out.println(" 구성원 : " + myStudy.getMemberNames());
    System.out.println();
    System.out.println("----------------------");
    System.out.println();

    if (!myStudy.getMemberNames().equals("")) {
      String inputGuilderName = Prompt.inputString(" >> 조장 권한을 위임해 줄 구성원을 선택하세요 : ");
      Member entrustList = new Member();

      for (Member entrustGuiler : entrustGuilers) {

        if (!entrustGuiler.getPerNickname().equals(inputGuilderName)) {
          System.out.println();
          System.out.println(" >> 구성원의 닉네임을 다시 입력하세요.");
        }

        if (entrustGuiler.getPerNickname().equals(inputGuilderName)) {
          System.out.println();
          System.out.printf(" '%s'님에게 조장 권한을 위임하시겠습니까?", entrustGuiler.getPerNickname());
          String input = Prompt.inputString(" (네 / 아니오) ");

          if (!input.equalsIgnoreCase("네")) {
            System.out.println();
            System.out.println(" >> 다시 진행해 주세요.");
            return;
          }

          System.out.printf(" >> '%s'님이 조장이 되셨습니다.", inputGuilderName);
          System.out.println();
          entrustList = entrustGuiler;
          if (entrustList != null) {
            myStudy.getMembers().remove(entrustList);
          }

          System.out.println();
          String inputGuilder = Prompt.inputString(" >> 구성원으로 다시 돌아가시겠습니까? (네 / 아니오) ");

          if (!inputGuilder.equalsIgnoreCase("네")) {
            myStudy.setOwner(null);
            myStudy.setOwner(entrustGuiler);
            System.out.println();
            System.out.println(" >> 해당 스터디에서 탈퇴되었습니다.");
            return;
          }

          myStudy.getMembers().add(member);
          myStudy.setOwner(null);
          myStudy.setOwner(entrustGuiler);
          System.out.println();
          System.out.println(" >> 구성원이 되셨습니다.");
        }
      }

      requestAgent.request("study.update", myStudy);

      if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
        System.out.println("조장 위임 실패!");
        return;
      }

      return;
    }
  }
}
