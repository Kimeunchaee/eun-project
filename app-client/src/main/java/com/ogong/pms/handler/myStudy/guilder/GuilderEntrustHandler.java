package com.ogong.pms.handler.myStudy.guilder;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class GuilderEntrustHandler implements Command { 

  StudyDao studyDao;

  public GuilderEntrustHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 조장 권한 위임");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    int studyNo = (int) request.getAttribute("inputNo"); 
    int memberNo = member.getPerNo();

    Study myStudy = studyDao.findByMyStudy(memberNo, studyNo);

    if (myStudy.getMembers().isEmpty()) {
      System.out.println(" >> 해당 스터디 구성원이 없습니다.");
      return;
    }

    System.out.println(" 구성원 : " + myStudy.getMemberNames());
    System.out.println();
    System.out.println("----------------------");
    System.out.println();

    if (!myStudy.getMemberNames().equals("")) {
      String inputGuilderName = Prompt.inputString(" >> 조장 권한을 위임해 줄 구성원을 선택하세요 : ");

      Member owner = new Member();

      for (Member guilerMember : myStudy.getMembers()) {

        if (!guilerMember.getPerNickname().equals(inputGuilderName)) {
          System.out.println();
          System.out.println(" >> 구성원의 닉네임을 다시 입력하세요.");
          return;
        }

        if (guilerMember.getPerNickname().equals(inputGuilderName)) {
          System.out.println();
          System.out.printf(" '%s'님에게 조장 권한을 위임하시겠습니까?", inputGuilderName);
          String inputAnswer = Prompt.inputString(" (네 / 아니오) ");

          if (!inputAnswer.equalsIgnoreCase("네")) {
            System.out.println();
            System.out.println(" >> 다시 진행해 주세요.");
            return;
          }

          owner = guilerMember;
          if (owner != null) {
            myStudy.getMembers().remove(owner);
            myStudy.setOwner(owner);
            myStudy.getMembers().add(member);
          }

          System.out.printf(" >> '%s'님이 조장이 되셨습니다.", owner.getPerNickname());
          System.out.println();

          System.out.println();
          if (!owner.equals(member)) {
            String input = Prompt.inputString(" >> 구성원으로 다시 돌아가시겠습니까? (네 / 아니오) ");

            if (!input.equalsIgnoreCase("네")) {
              myStudy.getMembers().remove(member);
              studyDao.update(myStudy);

              System.out.println();
              System.out.println(" >> 해당 스터디에서 탈퇴되었습니다.");
              return;
            }

            System.out.println();
            System.out.println(" >> 구성원이 되셨습니다.");
          }
        }
      }

      studyDao.update(myStudy);
      return;
    }
  }
}
