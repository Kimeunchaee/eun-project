package com.ogong.pms.handler;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Member;

public class AdminMemberListHandler extends AbstractMemberHandler {

  HashMap<String, Command> commandMap;


  public AdminMemberListHandler(List<Member> memberList, HashMap<String, Command> commandMap) {
    super(memberList);
    this.commandMap = commandMap;
  }

  // 관리자
  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 회원 목록");
    System.out.println();

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






