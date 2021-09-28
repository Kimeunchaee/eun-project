package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class AdminMemberBlockHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember;
  List<Study> studyList;

  public AdminMemberBlockHandler(List<Member> memberList, PromptPerMember promptPerMember,
      List<Study> studyList) {
    super(memberList);
    this.promptPerMember = promptPerMember;
    this.studyList = studyList;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 회원 차단");

    int inputMemberNo = (int) request.getAttribute("inputMemberNo");

    Member member = promptPerMember.findByMemberNo(inputMemberNo);

    if (member.getPerNickname() != AuthAdminLoginHandler.getLoginAdmin().getMasterNickname()) {


      String input = Prompt.inputString(" 정말 차단시키겠습니까? (네 /아니오) ");

      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 회원 차단를 취소하였습니다.");
        return;
      }

      // 관리자가 차단
      member.setPerNickname("차단된 회원/" + member.getPerNickname());
      member.setPerStatus(Member.BLOCK);

      for (Study myStudy : studyList) {
        if (myStudy.getOwner().getPerNo() == member.getPerNo()) {
          myStudy.setOwner(member);
          myStudy.setTempStudyTitle(myStudy.getStudyTitle());
          myStudy.setStudyTitle("차단된 회원의 스터디입니다.");
        }
      }

      System.out.println(" >> 회원이 차단되었습니다.");
      return;
    }
  }
}