package com.ogong.pms.handler.mystudy;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AbstractStudyHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;

public class MyStudyListHandler extends AbstractStudyHandler {


  public MyStudyListHandler(List<Study> studyList) {
    super(studyList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 스터디 목록");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null ) {
      System.out.println(" >> 로그인 한 회원만 조회 가능합니다.");
      return;
    }

    // test용으로 setPerMyStudy에 new로 생성해서
    // 값은 안 넣었지만 null값이 있으므로 사이즈로 비교해야 한다.
    int joinCount = 0;

    System.out.println(" << 참여 중 >> \n");
    for (int i = 0; i < studyList.size(); i++) {
      if (studyList.get(i).getMemberNames().contains(member.getPerNickname()) ||
          studyList.get(i).getOwner().getPerNickname().equals(member.getPerNickname())) {
        System.out.printf(" (%s)\n [%s]\n", studyList.get(i).getStudyNo(),
            studyList.get(i).getStudyTitle());
        System.out.println();
        joinCount++;
      }
    }

    if(joinCount == 0) {
      System.out.println(" >> 가입한 스터디가 없습니다.\n");
    }

    int waitCount = 0;
    System.out.println(" << 승인 대기 중>> \n"); 
    for (int i = 0; i < studyList.size(); i++) {
      if (studyList.get(i).getWatingMemberNames().contains(member.getPerNickname())) {
        System.out.printf(" (%s)\n [%s]\n", studyList.get(i).getStudyNo(),
            studyList.get(i).getStudyTitle());
        System.out.println();
        waitCount++;
      }
    } 
    if (waitCount == 0) {
      System.out.println(" >> 승인 대기 중인 스터디가 없습니다.\n");
    }
  }
}