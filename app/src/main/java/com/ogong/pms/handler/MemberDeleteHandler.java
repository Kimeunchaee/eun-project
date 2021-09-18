package com.ogong.pms.handler;

import java.util.List;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MemberDeleteHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember; 
  List<Study> studyList;

  public MemberDeleteHandler(List<Member> memberList, PromptPerMember promptPerMember,
      List<Study> studyList) {
    super(memberList);
    this.promptPerMember = promptPerMember;
    this.studyList = studyList;
  }

  // 개인
  @Override
  public void execute(CommandRequest request) {

    System.out.println();
    System.out.println("▶ 회원 탈퇴");
    System.out.println();

    if ( AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 로그인 하세요.");
      return;
    }

    System.out.println(" << 이메일 확인 >>");
    String inputEmail = Prompt.inputString(" 이메일을 입력하세요 : ");
    Member member = promptPerMember.findByMemberEmail(inputEmail);
    if (member == null) {
      System.out.println();
      System.out.println(" >> 이메일이 일치하지 않습니다.");
      return;
    }

    System.out.println();
    System.out.println(" << 비밀번호 확인 >>");
    String inputPW = Prompt.inputString(" 비밀번호를 입력하세요 : ");
    Member memberPW = promptPerMember.findByMemberPW(inputPW);
    if (memberPW == null) {
      System.out.println();
      System.out.println(" >> 비밀번호가 일치하지 않습니다.");
      return;
    }

    System.out.println();
    String input = Prompt.inputString(" 정말 탈퇴하시겠습니까? (네 /아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 탈퇴를 취소하였습니다.");
      return;
    }

    for (int i = studyList.size() - 1; i >= 0; i--) {
      if (studyList.get(i).getOwner().getPerNo() == member.getPerNo()) {
        studyList.remove(studyList.get(i));
      }
    }

    memberList.remove(member);
    AuthPerMemberLoginHandler.loginUser = null;
    AuthPerMemberLoginHandler.accessLevel = Menu.LOGOUT;
    System.out.println();
    System.out.println(" >> 회원 탈퇴를 완료하였습니다.");
    return;
  }
}
