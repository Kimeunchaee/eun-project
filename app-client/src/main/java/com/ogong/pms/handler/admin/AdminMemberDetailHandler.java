package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminMemberDetailHandler implements Command {

  MemberDao memberDao;

  public AdminMemberDetailHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 개인회원 상세");
    System.out.println();

    int no = 0;
    while (true) {
      try {
        no = Prompt.inputInt(" 번호 : ");
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자를 입력해 주세요.\n");
        continue;
      } 
      break;
    }

    Member member = memberDao.findByNo(no);

    System.out.printf(" [%s]\n", member.getPerNickname());
    System.out.printf(" >> 이메일 : %s\n", member.getPerEmail());
    System.out.printf(" >> 사진 : %s\n", member.getPerPhoto());
    System.out.printf(" >> 가입일 : %s\n", member.getPerRegisteredDate());

    request.setAttribute("memberNo", member.getPerNo());

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
