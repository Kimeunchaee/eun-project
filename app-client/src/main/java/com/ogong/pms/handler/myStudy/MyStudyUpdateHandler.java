package com.ogong.pms.handler.myStudy;

import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class MyStudyUpdateHandler implements Command {

  RequestAgent requestAgent;

  public MyStudyUpdateHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 수정");
    System.out.println();


    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디를 찾을 수 없습니다.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    if (myStudy.getOwner().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 수정 권한이 없습니다.");
      return;
    }

    // 스터디명
    String studyTitle;
    while (true) {
      studyTitle = Prompt.inputString(" 스터디명(" + myStudy.getStudyTitle()  + ") : ");
      if (studyTitle.length() == 0) {
        System.out.println("한 글자 이상 입력해주세요.");
        continue;
      }
      break;
    }
    myStudy.setStudyTitle(studyTitle);

    // 인원수
    int nop = 0;
    while (true) {
      try {
        nop = Prompt.inputInt(" 인원수(" + myStudy.getNumberOfPeple() + ") : ");
        if (nop < myStudy.getNumberOfPeple()) {
          System.out.println(" >> 현재 참여 중인 인원보다 적게 수정할 수 없습니다.");
          continue;
        }
      }catch (NumberFormatException e) {
        System.out.println("숫자만 입력하세요.");
        continue;
      }
      break;
    }
    myStudy.setNumberOfPeple(nop);

    // 대면 비대면
    String face;
    while (true) {
      System.out.println();
      face = Prompt.inputString(" 대면 , 비대면 , 대면/비대면 : ");
      if ((face.length() == 3 && face.equals("비대면")) ||
          (face.length() == 2 && face.equals("대면")) ||
          (face.length() == 6 && face.equals("대면/비대면"))) {
        break;
      }
      System.out.println(" >> 대면/비대면 중에 입력하세요.");
    }
    myStudy.setFace(face);

    // 소개글
    String introduction;
    while (true) {
      introduction = Prompt.inputString(" 소개글(" + myStudy.getIntroduction() + ") : ");
      if (introduction.length() == 0) {
        System.out.println("한 글자 이상 입력해주세요.");
        continue;
      }
      break;
    }
    myStudy.setIntroduction(introduction);

    System.out.println();
    String input = Prompt.inputString(" 정말 수정하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디 수정을 취소하였습니다.");
      return;
    }

    requestAgent.request("study.update", myStudy);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 수정 실패!");
      return;
    }
    System.out.println(" >> 스터디가 수정되었습니다.");
  }
}