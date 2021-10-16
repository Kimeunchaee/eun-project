package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminStudyDeleteHandler implements Command {

  StudyDao studyDao;

  public AdminStudyDeleteHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 삭제");

    int inputNo = Prompt.inputInt(" 번호 : ");

    Study study = studyDao.findByNo(inputNo);

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

    studyDao.delete(inputNo);

    System.out.println(" >> 스터디를 삭제하였습니다.");
    request.getRequestDispatcher("/study/list").forward(request);
  }
}