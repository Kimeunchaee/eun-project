package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AbstractMemberHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.util.Prompt;

public class AdminMemberDetailHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember;

  public AdminMemberDetailHandler(List<Member> memberList, PromptPerMember promptPerMember) {
    super(memberList);
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 개인회원 상세");
    int inputMemberNo = Prompt.inputInt(" 번호 : ");
    System.out.println();

    Member member = promptPerMember.findByMemberNo(inputMemberNo);

    if (member == null) {
      System.out.println(" >> 해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf(" [%s]\n", member.getPerNickname());
    System.out.printf(" >> 이메일 : %s\n", member.getPerEmail());
    System.out.printf(" >> 사진 : %s\n", member.getPerPhoto());
    System.out.printf(" >> 가입일 : %s\n", member.getPerRegisteredDate());

    request.setAttribute("inputMemberNo", inputMemberNo);

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("2. 탈퇴");
    System.out.println("0. 이전");

    while (true) {
      int selcetNo = Prompt.inputInt("선택> ");
      switch (selcetNo) {
        case 1: request.getRequestDispatcher("/adminMember/update").forward(request); return;
        case 2: request.getRequestDispatcher("/adminMember/delete").forward(request); return;
        case 0: return;
        default :
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
