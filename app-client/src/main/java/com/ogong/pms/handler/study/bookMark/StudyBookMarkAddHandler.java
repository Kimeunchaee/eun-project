package com.ogong.pms.handler.study.bookMark;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyBookMarkAddHandler implements Command {

  StudyDao studyDao;

  public StudyBookMarkAddHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 북마크 - 스터디 추가");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    int inputNo = (int) request.getAttribute("inputNo");

    Study study = studyDao.findByNo(inputNo);

    for (Member joinMember : study.getMembers()) {
      if (member.getPerNo() == joinMember.getPerNo()) {
        System.out.println(" >> 이미 참여 중인 스터디입니다.");
        return;
      }
    }

    for (Member watingMember : study.getWatingMember()) {
      if (member.getPerNo() == watingMember.getPerNo()) {
        System.out.println(" >> 이미 승인 대기 중인 스터디입니다.");
        return;
      }
    }

    for (Member bookMarkMember : study.getBookMarkMember()) {
      if (member.getPerNo() == bookMarkMember.getPerNo()) {
        System.out.println(" >> 이미 북마크 중인 스터디입니다.");
        return;
      }
    }

    String input = Prompt.inputString(" 이 스터디를 북마크하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 북마크가 취소되었습니다.");
      return;
    }

    study.getBookMarkMember().add(member);
    studyDao.update(study);

    System.out.println();
    System.out.println(" >> 북마크가 완료되었습니다.");
  }
}
