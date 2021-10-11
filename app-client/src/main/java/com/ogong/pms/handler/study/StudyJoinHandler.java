package com.ogong.pms.handler.study;

import java.util.HashMap;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class StudyJoinHandler implements Command {

  RequestAgent requestAgent;

  public StudyJoinHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 신청");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디를 찾을 수 없습니다.");
      return;
    }

    Study study = requestAgent.getObject(Study.class);

    for (Member pM : study.getMembers()) {
      if (pM.getPerNickname().equals(member.getPerNickname())) {
        System.out.println(" >> 이미 참여 중인 스터디입니다.");
        return;
      }
    }

    for (Member memberWating : study.getWatingMember()) {
      if (member.getPerNickname().equals(memberWating.getPerNickname())) {
        System.out.println(" >> 이미 승인 대기 중인 스터디입니다.");
        return;
      }
    }

    if(study.getMembers().size() == (study.getNumberOfPeple() - 1)) {
      System.out.println(" >> 참여 가능 인원수를 초과하였습니다.");
      return;
    }

    String input = Prompt.inputString(" 스터디에 참여하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 참여 신청이 취소되었습니다.");
      return;
    }
    study.getWatingMember().add(member);
    requestAgent.request("study.update", study);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 참여 실패!");
      return;
    }
    System.out.println();
    System.out.println(" >> 참여 신청이 완료되었습니다.\n   승인이 완료될 때까지 기다려 주세요.");
  }
}
