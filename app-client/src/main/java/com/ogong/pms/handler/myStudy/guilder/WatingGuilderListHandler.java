package com.ogong.pms.handler.myStudy.guilder;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class WatingGuilderListHandler implements Command {

  RequestAgent requestAgent;

  public WatingGuilderListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  // 스터디 구성원 목록
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 승인 대기 목록");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo",String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    if (!myStudy.getWatingMemberNames().isEmpty()) {

      System.out.println(" " +myStudy.getWatingMemberNames());
      System.out.println("\n----------------------");
      System.out.println(" [ 승인 / 거절 ] ");
      System.out.println();
      System.out.println("1. 승인");
      System.out.println("2. 거절");
      System.out.println("0. 취소");
      System.out.println();

      int inputGuilerNo = Prompt.inputInt("선택> ");
      switch (inputGuilerNo) {
        case 1: agreeStudyMember(myStudy, requestAgent); return;
        case 2: disagreeStudyMember(myStudy, requestAgent); return;
        case 0: return;
        default: return;
      }
    }
  }

  // 승인
  private void agreeStudyMember(Study myStudy, RequestAgent requestAgent) throws Exception {

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    List<Member> waitingMembers = myStudy.getWatingMember();

    if (myStudy.getMembers().size()+1 >= myStudy.getNumberOfPeple()) {
      System.out.println("인원수 가득차 승인할 수 없습니다.");
      return;
    }

    if (member != null && myStudy.getOwner().getPerEmail().equals(member.getPerEmail())) {
      System.out.println();
      if (!myStudy.getWatingMemberNames().equals("")) {
        String input = Prompt.inputString(" >> 대기 중인 회원 중 승인할 닉네임을 입력하세요 : ");
        Member m = null;

        for (Member watingMember : waitingMembers) {    

          if (watingMember.getPerNickname().equals(input)) {
            myStudy.getMembers().add(watingMember);
            System.out.printf(" >> '%s님'이 구성원으로 승인되었습니다.\n", watingMember.getPerNickname());
            m = watingMember;
            break;
          }
        }
        if (m == null) {
          System.out.println(" >> 닉네임을 다시 입력하세요.");
        }
        else {
          myStudy.getWatingMember().remove(m);
        }

        requestAgent.request("study.update", myStudy);

        if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
          System.out.println("구성원 승인 실패!");
          return;
        }
        return;
      }
    }
  }

  // 거절
  private void disagreeStudyMember(Study myStudy, RequestAgent requestAgent) throws Exception {

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    List<Member> waitingMembers = myStudy.getWatingMember();

    if (member != null && myStudy.getOwner().getPerEmail().equals(member.getPerEmail())) {
      System.out.println();
      if (!myStudy.getWatingMemberNames().equals("")) {
        String input = Prompt.inputString(" >> 대기 중인 회원 중 거절할 닉네임을 입력하세요 : ");
        Member m = new Member();

        for (Member watingMember : waitingMembers) {  

          if (!watingMember.getPerNickname().equals(input)) {
            System.out.println(" >> 닉네임을 다시 입력하세요.");
          }

          if (watingMember.getPerNickname().equals(input)) {
            System.out.printf(" >> '%s님'의 구성원 신청이 거절되었습니다.\n", watingMember.getPerNickname());
            m = watingMember;
            break;
          }
        }
        if (m != null) {
          myStudy.getWatingMember().remove(m);

          requestAgent.request("study.update", myStudy);

          if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
            System.out.println("스터디 거절 실패!");
            return;
          }
        }
        return;
      }
    }
  }

}
