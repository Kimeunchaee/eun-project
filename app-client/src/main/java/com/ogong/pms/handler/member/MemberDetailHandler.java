package com.ogong.pms.handler.member;

import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class MemberDetailHandler implements Command {

  MemberDao memberDao;

  public MemberDetailHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 프로필");

    int no;
    try {
      no = AuthPerMemberLoginHandler.getLoginUser().getPerNo();
    } catch (NullPointerException e) {
      System.out.println(" >> 로그인 한 회원만 볼 수 있습니다.");
      return;
    }

    Member member = memberDao.findByNo(no);

    try {
      System.out.println();
      System.out.printf(" [%s]\n", member.getPerNickname());
      System.out.printf(" >> 이메일 : %s\n", member.getPerEmail());
      System.out.printf(" >> 사  진 : %s\n", member.getPerPhoto());
      System.out.printf(" >> 가입일 : %s\n", member.getPerRegisteredDate());

    } catch (NullPointerException e) {
      System.out.println();
      System.out.println(" >> 프로필 실행 오류");
    }

    if (member == null) {
      return;
    }

    request.setAttribute("memberNo", member.getPerNo());

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("2. 탈퇴");      
    System.out.println("0. 이전");
    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1 : request.getRequestDispatcher("/member/update").forward(request); return;
        case 2 : request.getRequestDispatcher("/member/delete").forward(request); return;
        case 0 : return;
        default : System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }

  }
}