package com.ogong.pms.handler.study;

import java.util.Collection;
import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class StudySearchHandler implements Command {

  RequestAgent requestAgent;

  public StudySearchHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 검색");
    System.out.println();

    System.out.println(" 지역 / 분야 / 스터디명으로 검색할 수 있습니다.");
    String input = Prompt.inputString(" 검색어 : ");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("keyword", String.valueOf(input));

    requestAgent.request("study.selectByKeyword", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 다시 입력해 주세요.");
      return;
    }

    int count = 0;

    Collection<Study> studyList = requestAgent.getObjects(Study.class);

    for (Study searchStudy : studyList) {

      if (searchStudy.getStudyTitle().contains(input) ||
          searchStudy.getSubject().contains(input) ||
          searchStudy.getArea().contains(input)) {
        System.out.printf(" \n (%s)\n", searchStudy.getStudyNo());
        System.out.printf(" [%s]\n", searchStudy.getStudyTitle());
        System.out.printf(" >> 조장 : %s\n", searchStudy.getOwner().getPerNickname());
        System.out.printf(" >> 분야 : %s\n", searchStudy.getSubject());
        System.out.printf(" >> 지역 : %s\n", searchStudy.getArea());
        System.out.printf(" >> 인원수 : %s/%s명\n", searchStudy.getMembers().size() + 1, searchStudy.getNumberOfPeple());
        System.out.printf(" >> 대면 : %s\n", searchStudy.getFace());
        System.out.printf(" >> 소개글 : %s\n", searchStudy.getIntroduction());
        count++;
      }
    }

    if (count == 0) {
      System.out.println(" >> 검색어를 다시 입력해 주세요.");
      return;
    }
  }
}

