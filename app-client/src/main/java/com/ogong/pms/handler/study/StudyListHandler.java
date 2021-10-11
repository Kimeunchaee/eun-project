package com.ogong.pms.handler.study;

import java.util.Collection;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;

public class StudyListHandler implements Command {

  RequestAgent requestAgent;

  public StudyListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 목록");
    System.out.println();

    requestAgent.request("study.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("목록 조회 실패!");
      return;
    }

    Collection<Study> studyList = requestAgent.getObjects(Study.class);

    for (Study study : studyList) {

      if (study.getStudyTitle().contains("탈퇴")) {
        System.out.printf(" (%d)\n 스터디명 : %s\n",
            study.getStudyNo(),
            study.getStudyTitle());
        System.out.println();
      }

      else {
        System.out.printf(" (%d)\n 스터디명 : %s\n 분류 : %s\n 인원수 : %s/%s명\n 조장 : %s\n 대면/비대면 : %s\n",
            study.getStudyNo(),
            study.getStudyTitle(),
            study.getSubject(),
            study.getMembers().size() + 1,
            study.getNumberOfPeple(),
            study.getOwner().getPerNickname(),
            study.getFace());
        System.out.println();
      }
    }
  }
}