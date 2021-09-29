package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AbstractMemberHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.util.Prompt;

public class AdminMemberUpdateHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember; 

  public AdminMemberUpdateHandler(List<Member> memberList, PromptPerMember promptPerMember) {
    super(memberList);
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 회원 프로필 수정");

    for (Member member : memberList) {
      if (member.getPerNickname() != AuthAdminLoginHandler.getLoginAdmin().getMasterNickname()) {

        int inputMemberNo = (int) request.getAttribute("inputMemberNo");

        member = promptPerMember.findByMemberNo(inputMemberNo);

        String perNickName = Prompt.inputString(" 닉네임(" + member.getPerNickname()  + ") : ");
        String perEmail = Prompt.inputString(" 이메일(" + member.getPerEmail() + ") : ");
        String perPassword = Prompt.inputString(" 비밀번호(" + member.getPerPassword() + ") : ");
        String perPhoto = Prompt.inputString(" 사진(" + member.getPerPhoto() + ") : ");

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
        return;
      }
    }
  }

}







