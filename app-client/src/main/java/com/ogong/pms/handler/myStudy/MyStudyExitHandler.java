package com.ogong.pms.handler.myStudy;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class MyStudyExitHandler implements Command {

  StudyDao studyDao;

  public MyStudyExitHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 탈퇴하기");
    System.out.println();
    int inputNo = (int) request.getAttribute("inputNo");

    Study myStudy = studyDao.findByNo(inputNo);

    if (myStudy.getOwner().getPerNickname().equals(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname()) &&
        myStudy.getMembers().size() > 0) {
      System.out.println(" >> 구성원에게 조장 권한을 위임하고 탈퇴를 진행해 주세요.");
      return;
    }

    if (myStudy.getMemberNames().contains(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname())) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      myStudy.getMembers().remove(AuthPerMemberLoginHandler.getLoginUser());
      studyDao.update(myStudy);

      System.out.println(" >> 탈퇴되었습니다.");
    }

    else if ( (myStudy.getOwner().getPerNickname().equals(
        AuthPerMemberLoginHandler.getLoginUser().getPerNickname()) && 
        myStudy.getMembers().size() == 0)) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      studyDao.delete(inputNo);

      System.out.println(" >> 스터디가 삭제 되었습니다.");
    }
  }

}
