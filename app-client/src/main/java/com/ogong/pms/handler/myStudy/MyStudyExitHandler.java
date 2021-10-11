package com.ogong.pms.handler.myStudy;

import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class MyStudyExitHandler implements Command {

  RequestAgent requestAgent;

  public MyStudyExitHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 탈퇴하기");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    if (myStudy.getOwner().getPerNickname().equals(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname()) &&
        myStudy.getMembers().size() > 0) {
      System.out.println(" >> 구성원에게 조장 권한을 위임하고 탈퇴를 진행해 주세요.");
      return;
    }

    if (myStudy.getMemberNames().contains(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname())) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      myStudy.getMembers().remove(AuthPerMemberLoginHandler.getLoginUser());
      requestAgent.request("study.update", myStudy);

      if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
        System.out.println("스터디 탈퇴 실패!");
        return;
      }

      System.out.println(" >> 탈퇴되었습니다.");
    }

    else if ( (myStudy.getOwner().getPerNickname().equals(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname()) && 
        myStudy.getMembers().size() == 0)) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      requestAgent.request("study.delete", params);
      if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
        System.out.println(" >> 스터디 탈퇴 실패.");
        return;
      }
      System.out.println(" >> 스터디가 삭제 되었습니다.");
    }
  }

}
