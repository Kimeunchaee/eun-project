package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AbstractMemberHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.util.Prompt;

public class AdminMemberDeleteHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember; 
  List<Study> studyList;

  public AdminMemberDeleteHandler(List<Member> memberList, PromptPerMember promptPerMember,
      List<Study> studyList) {
    super(memberList);
    this.promptPerMember = promptPerMember;
    this.studyList = studyList;
  }

  // 개인
  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 회원 삭제");

    int inputMemberNo = (int) request.getAttribute("inputMemberNo");

    Member member = promptPerMember.findByMemberNo(inputMemberNo);

    if (member.getPerNickname() != AuthAdminLoginHandler.getLoginAdmin().getMasterNickname()) {

      String input = Prompt.inputString(" 정말 탈퇴시키겠습니까? (네 /아니오) ");

      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 회원 삭제를 취소하였습니다.");
        return;
      }

      for (int i = studyList.size() - 1; i >= 0; i--) { 
        if (studyList.get(i).getOwner().getPerNo() == member.getPerNo()) {
          studyList.remove(studyList.get(i));
        }
      }

      memberList.remove(member);

      System.out.println(" >> 회원이 삭제되었습니다.");
      return;
    }
  }

}