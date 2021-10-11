package com.ogong.pms.handler;

import java.util.Collection;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class PromptStudy {

  protected RequestAgent requestAgent;

  public PromptStudy(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  public Study promptStudy() throws Exception {
    System.out.println("▶ 스터디 목록");
    System.out.println();

    requestAgent.request("study.selectList", null);
    // null 왜 했지?
    // => 빈 문자열이라도 보내 줘야 서버가 빈 문자열을 받아서 키값을 출력해 줌

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 목록을 불러오지 못했습니다.");
      return null;
    }

    Collection<Study> studyList = requestAgent.getObjects(Study.class);

    for (Study study : studyList) {
      System.out.printf(" (%d)\n %s", study.getStudyNo(), study.getStudyTitle());
    }

    while (true) {
      int studyNo = Prompt.inputInt(" 번호: ");

      if (studyNo == 0) {
        return null;
      }

      Study selectStudy = findByStudyNo(studyNo, studyList);

      if (selectStudy != null) {
        return selectStudy;
      }
      System.out.println(" 번호를 다시 입력하세요.");
    }
  }

  //전체 스터디에서 번호로 찾기
  public Study findByStudyNo(int studyinputNo, Collection<Study> studyList) {
    for (Study study : studyList) {
      if (study.getStudyNo() == studyinputNo) {
        return study;
      }
    }
    return null;
  }

  // 내 스터디에서만 번호 찾기
  public Study findByMyStudyNo(int inputNo, Collection<Study> studyList) {

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    for (Study study : studyList) {
      if (study.getStudyNo() == inputNo) {
        if (study.getMemberNames().contains(member.getPerNickname()) ||
            study.getOwner().getPerNickname().equals(member.getPerNickname())) {
          return study;
        }
      }
    }
    return null;
  }

}







