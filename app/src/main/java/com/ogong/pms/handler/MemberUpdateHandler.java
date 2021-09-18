package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class MemberUpdateHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember;

  public MemberUpdateHandler(List<Member> memberList, PromptPerMember promptPerMember) {
    super(memberList);
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 프로필 수정");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");
    Member member = promptPerMember.findByMemberNo(inputNo);

    if (member == null) {
      System.out.println(" >> 해당 회원이 없습니다.");
    }

    try {
      member = AuthPerMemberLoginHandler.getLoginUser();

      String perNickName = Prompt.inputString(" 닉네임(" + member.getPerNickname()  + ") : ");
      String perPhoto = Prompt.inputString(" 사  진(" + member.getPerPhoto() + ") : ");
      String perEmail = Prompt.inputString(" 이메일(" + member.getPerEmail() + ") : ");
      String perPassword = Prompt.inputString(" 비밀번호(" + member.getPerPassword() + ") : ");


      System.out.println();
      String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 회원 변경을 취소하였습니다.");
        return;
      }
      member.setPerNickname(perNickName);
      member.setPerEmail(perEmail);
      member.setPerPassword(perPassword);
      member.setPerPhoto(perPhoto);

      System.out.println(" >> 회원 정보를 변경하였습니다.");

    } catch (NullPointerException e) {
      System.out.println(" >> 로그인 하세요.");
    }
  }
}
