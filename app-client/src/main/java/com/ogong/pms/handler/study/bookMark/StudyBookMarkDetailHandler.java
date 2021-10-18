package com.ogong.pms.handler.study.bookMark;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyBookMarkDetailHandler implements Command {

  StudyDao studyDao;

  public StudyBookMarkDetailHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 북마크 - 스터디 상세");
    System.out.println();


    Member member = AuthPerMemberLoginHandler.getLoginUser();

    List<Study> studyList = studyDao.findAll();

    List<Study> myBookMarkStudyList = new ArrayList<>();

    for (Study study : studyList) {
      for (Member bookMarkMember : study.getBookMarkMember()) {
        if (member.getPerNo() == bookMarkMember.getPerNo()) {
          myBookMarkStudyList.add(study);
        }
        break;
      }
    }

    int inputNo;
    LOOP : while (true) {
      try {
        System.out.println();
        inputNo = Prompt.inputInt(" 번호 : ");
      } catch(NumberFormatException e) {
        return;
      }

      for (Study myBookMarkStudy : myBookMarkStudyList) {
        if(myBookMarkStudy.getStudyNo() != inputNo) {
          System.out.println(" >> 내가 북마크한 스터디 번호를 입력하세요. / 종료(엔터)");
          continue LOOP;
        }
        break;
      }
      break;
    }

    Study study = studyDao.findByNo(inputNo);

    System.out.printf(" \n (%s) 🌟%d\n", study.getStudyNo(), study.getBookMarkMember().size());
    System.out.printf(" [%s]\n", study.getStudyTitle());
    System.out.printf(" >> 조장 : %s\n", study.getOwner().getPerNickname());
    System.out.printf(" >> 분야 : %s\n", study.getSubject());
    System.out.printf(" >> 지역 : %s\n", study.getArea());
    System.out.printf(" >> 인원수 : %s/%s명\n",
        study.getMembers().size() + 1, study.getNumberOfPeple());
    System.out.printf(" >> 대면 : %s\n", study.getFace());
    System.out.printf(" >> 소개글 : %s\n", study.getIntroduction());

    request.setAttribute("inputNo", study.getStudyNo());

    System.out.println("\n----------------------");
    System.out.println("1. 참여 신청하기");
    System.out.println("2. 북마크 삭제");
    System.out.println("0. 이전");

    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/study/join").forward(request); return;
        case 2: request.getRequestDispatcher("/study/bookMarkDelete").forward(request); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.\n");
      }
    }
  }
}