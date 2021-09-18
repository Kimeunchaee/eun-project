package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class StudySearchHandler extends AbstractStudyHandler{

  public StudySearchHandler(List<Study> studyList) {
    super(studyList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 스터디 검색");
    System.out.println();

    System.out.println(" 지역 / 분야 / 스터디명으로 검색할 수 있습니다.");
    String input = Prompt.inputString(" 검색어 : ");
    System.out.println();

    int count = 0;

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

    //    System.out.println("\n----------------------");
    //    System.out.println("1. 상세보기(참여신청)");
    //    System.out.println("0. 뒤로가기");
    //    int selectNo = Prompt.inputInt("선택> ");
    //    switch (selectNo) {
    //      case 1 : detail(); return;
    //      default : return;
    //    }
  }

}

