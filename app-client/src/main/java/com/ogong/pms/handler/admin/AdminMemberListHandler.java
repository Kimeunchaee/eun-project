package com.ogong.pms.handler.admin;

import java.util.Collection;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class AdminMemberListHandler implements Command {

  MemberDao memberDao;

  public AdminMemberListHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  // 관리자
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 회원 목록");
    System.out.println();

    Collection<Member> memberList = memberDao.findAll();

    for (Member member : memberList) {
      System.out.printf(" (%d)\n 닉네임 : %s\n 이메일 : %s\n 가입일 : %s\n",
          member.getPerNo(),
          member.getPerNickname(), 
          member.getPerEmail(),
          member.getPerRegisteredDate());
      System.out.println();
    }
  }
}






