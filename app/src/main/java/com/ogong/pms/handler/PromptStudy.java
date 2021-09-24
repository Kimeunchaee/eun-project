package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;

public class PromptStudy {

  List<Study> studyList;

  public PromptStudy(List<Study> studyList) {
    this.studyList = studyList;
  }

  //전체 스터디에서 번호로 찾기
  public Study findByStudyNo(int inputNo) {
    for (Study study : studyList) {
      if (study.getStudyNo() == inputNo) {
        return study;
      }
    }
    return null;
  }

  // 내 스터디에서만 번호 찾기
  public Study findByMyStudyNo(int inputNo) {

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







