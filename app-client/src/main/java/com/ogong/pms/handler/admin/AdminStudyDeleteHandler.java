package com.ogong.pms.handler.admin;

import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class AdminStudyDeleteHandler implements Command {

  RequestAgent requestAgent;

  public AdminStudyDeleteHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 삭제");

    int inputNo = Prompt.inputInt(" 번호 : ");

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(inputNo));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 해당 스터디가 없습니다.");
      return;
    }

    Study study = requestAgent.getObject(Study.class);

    if (study.getOwner().getPerNickname() 
        != AuthAdminLoginHandler.getLoginAdmin().getMasterNickname()) {

      System.out.println();
      String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 스터디 삭제를 취소하였습니다.");
        request.getRequestDispatcher("/study/list").forward(request);
        return;
      }
    }

    requestAgent.request("study.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 삭제가 실패되었습니다.");
      return;
    }

    System.out.println(" >> 스터디를 삭제하였습니다.");
    request.getRequestDispatcher("/study/list").forward(request);
  }
}