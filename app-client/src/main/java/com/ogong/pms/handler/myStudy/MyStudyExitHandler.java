package com.ogong.pms.handler.myStudy;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
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

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 탈퇴하기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    int studyNo = (int) request.getAttribute("inputNo");
    int memberNo = member.getPerNo();

    Study myStudy = studyDao.findByMyStudy(memberNo, studyNo);

    // 구성원이 탈퇴
    if (myStudy.getMemberNames().contains(member.getPerNickname())) {

      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      //myStudy.getMembers().remove(myStudy.getMembers().indexOf(member));
      myStudy.getMembers().remove(member);
      studyDao.update(myStudy);
      System.out.println(" >> 구성원에서 탈퇴되었습니다.");
    }

    // 조장 탈퇴 (구성원 있을때)
    if (myStudy.getOwner().getPerNickname().equals(member.getPerNickname()) &&
        myStudy.getMembers().size() > 0) {

      System.out.println(" >> 구성원에게 조장 권한을 위임하고 탈퇴를 진행해 주세요.");
      return;

      // 조장 탈퇴 (구성원 없을때)
    } else if ((myStudy.getOwner().getPerNickname().equals(member.getPerNickname()) && 
        myStudy.getMembers().size() == 0)) {

      System.out.print(" >> 스터디 구성원이 없어서 스터디가 삭제됩니다.");
      String input = Prompt.inputString(" 정말 탈퇴하시겠습니까?(네 / 아니오)");
      if (!input.equals("네")) {
        System.out.println(" >> 탈퇴를 취소하였습니다.");
        return;
      }

      if (myStudy.getMembers().isEmpty()) {
        studyDao.delete(studyNo);
      } else {
        System.out.println("스터디 삭제 실패");
      }
    }
  }
}