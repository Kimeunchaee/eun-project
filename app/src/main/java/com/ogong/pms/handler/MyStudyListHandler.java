//  수정 중.....

package com.ogong.pms.handler;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MyStudyListHandler extends AbstractStudyHandler {

  HashMap<String, Command> commandMap;

  public MyStudyListHandler(List<Study> studyList, HashMap<String, Command> commandMap) {
    super(studyList);
    this.commandMap = commandMap;
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
      if (studyList.get(i).getWatingMemberNames().equals(member.getPerNickname())) {
        System.out.printf(" (%s)\n [%s]\n", studyList.get(i).getStudyNo(),
            studyList.get(i).getStudyTitle());
        System.out.println();
        waitCount++;
      }
    } 
    if (waitCount == 0) {
      System.out.println(" >> 승인 대기 중인 스터디가 없습니다.\n");
    }


    System.out.println("----------------------");
    System.out.println("1. 상세");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt("선택> ");

    switch (selectNo) {
      //case 1 : commandMap.get("/myStudy/detail").execute(); break;
      case 2 : System.out.println(" 이전"); break;
      default : return;
    }
  }
}