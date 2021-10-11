package com.ogong.pms.handler.myStudy;

import java.util.Collection;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;

public class MyStudyListHandler implements Command {

  private static final String Collection = null;
  RequestAgent requestAgent;

  public MyStudyListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 목록");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null ) {
      System.out.println(" >> 로그인 한 회원만 조회 가능합니다.");
      return;
    }

    requestAgent.request("study.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("목록 조회 실패!");
      return;
    }

    Collection<Study> studyList = requestAgent.getObjects(Study.class);

    // test용으로 setPerMyStudy에 new로 생성해서
    // 값은 안 넣었지만 null값이 있으므로 사이즈로 비교해야 한다.
    int joinCount = 0;

    System.out.println(" << 참여 중 >> \n");

    for (Study study : studyList) {
      if (study.getMemberNames().contains(member.getPerNickname())  || 
          study.getOwner().getPerNickname().equals(member.getPerNickname())) {
        System.out.printf(" (%s)\n [%s]\n", study.getStudyNo(),
            study.getStudyTitle());
        System.out.println();
        joinCount++;
      }

    }

    if(joinCount == 0) {
      System.out.println(" >> 가입한 스터디가 없습니다.\n");
    }

    int waitCount = 0;
    System.out.println(" << 승인 대기 중>> \n"); 

    for (Study study : studyList) {
      if (study.getWatingMemberNames().contains(member.getPerNickname())) {
        System.out.printf(" (%s)\n [%s]\n", study.getStudyNo(),
            study.getStudyTitle());
        System.out.println();
        waitCount++;
      }
    }

    if (waitCount == 0) {
      System.out.println(" >> 승인 대기 중인 스터디가 없습니다.\n");
    }
  }
}