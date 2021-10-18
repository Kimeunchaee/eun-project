package com.ogong.pms.handler.study.bookMark;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyBookMarkDeleteHandler implements Command {

  StudyDao studyDao;

  public StudyBookMarkDeleteHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 북마크 - 스터디 삭제");

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    int no = (int) request.getAttribute("inputNo");

    Study myStudy = studyDao.findByNo(no);

    System.out.println();
    String input = Prompt.inputString(" 정말 북마크 목록에서 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 북마크 목록에 스터디 삭제를 취소하였습니다.");
      return;
    }

    for (int i = 0; i < myStudy.getBookMarkMember().size(); i++) {
      if (member.getPerNo() == myStudy.getBookMarkMember().get(i).getPerNo()) {
        myStudy.getBookMarkMember().remove(i);        
        break;
      }
    }

    studyDao.update(myStudy);
    System.out.println(" >> 스터디가 북마크 목록에서 삭제 되었습니다.");
  }
}