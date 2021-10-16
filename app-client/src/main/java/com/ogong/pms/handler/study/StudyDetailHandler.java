package com.ogong.pms.handler.study;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyDetailHandler implements Command {

  StudyDao studyDao;

  public StudyDetailHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 상세");
    System.out.println();

    int inputNo = Prompt.inputInt(" 번호 : ");

    Study study = studyDao.findByNo(inputNo);

    System.out.printf(" \n(%s)\n", study.getStudyNo());
    System.out.printf(" [%s]\n", study.getStudyTitle());
    System.out.printf(" >> 조장 : %s\n", study.getOwner().getPerNickname());
    System.out.printf(" >> 분야 : %s\n", study.getSubject());
    System.out.printf(" >> 지역 : %s\n", study.getArea());
    System.out.printf(" >> 인원수 : %s/%s명\n",
        study.getMembers().size() + 1, study.getNumberOfPeple());
    System.out.printf(" >> 대면 : %s\n", study.getFace());
    System.out.printf(" >> 소개글 : %s\n", study.getIntroduction());

    request.setAttribute("inputNo", study.getStudyNo());

    if (AuthPerMemberLoginHandler.loginUser != null) {

      if (study.getOwner().getPerNickname().equals(
          AuthPerMemberLoginHandler.loginUser.getPerNickname())) {
        System.out.println();
        System.out.println();
        System.out.println("1. 수정");
        System.out.println("2. 삭제");
        System.out.println("0. 이전");

        while (true) {
          int selectNo = Prompt.inputInt("선택> ");
          switch (selectNo) {
            case 1: request.getRequestDispatcher("/myStudy/update").forward(request); return;
            case 2: request.getRequestDispatcher("/myStudy/delete").forward(request); return;
            case 0: return;
            default : 
              System.out.println(" >> 번호를 다시 선택해 주세요.");
          }
        }
      } else {

        System.out.println("\n----------------------");
        System.out.println("1. 참여 신청하기");
        System.out.println("0. 이전");

        while (true) {
          int selectNo = Prompt.inputInt("선택> ");
          switch (selectNo) {
            case 1: request.getRequestDispatcher("/study/join").forward(request); return;
            case 0: return;
            default : 
              System.out.println(" >> 번호를 다시 선택해 주세요.");
          }
        }
      }
    }
  }

}
