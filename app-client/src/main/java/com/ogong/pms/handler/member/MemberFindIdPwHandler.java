package com.ogong.pms.handler.member;

import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;
import com.ogong.util.SendMail;

public class MemberFindIdPwHandler implements Command {

  RandomPw randomPw;
  MemberDao memberDao;

  public MemberFindIdPwHandler(RandomPw randomPw, MemberDao memberDao) {
    this.randomPw = randomPw;
    this.memberDao = memberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("1. 이메일 찾기");
    System.out.println("2. 비밀번호 찾기");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : wantPerEmail(); break;
      case 2 : wantByPerPw(); break;
      default : return;
    }
  }


  public void wantPerEmail() throws Exception {
    System.out.println();
    System.out.println("▶ 이메일 찾기");

    while (true) {
      System.out.println();
      String inputNick =  Prompt.inputString(" 닉네임 : ");

      Member member = memberDao.findByNickName(inputNick);

      if (member != null) {
        System.out.println();
        System.out.printf(" '%s님'의 이메일 >> ", member.getPerNickname());
        System.out.println(member.getPerEmail());

      } else {
        System.out.println(" >> 해당 닉네임이 존재하지 않습니다.");
        return;
      }

      String input = Prompt.inputString(" 비밀번호 찾기로 넘어가시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 찾기를 종료합니다.");
        return;
      } 
      wantByPerPw();
      break;
    }
  }
  //--------------------------------------------------------------------------------------------
  public void wantByPerPw() throws Exception {
    System.out.println();
    System.out.println("▶ 임시 비밀번호 발급");

    SendMail sendMail = new SendMail();

    while (true) {
      System.out.println();
      String inputEmail =  Prompt.inputString(" 이메일 : ");

      Member member = memberDao.findByEmail(inputEmail);

      if (member != null) {
        String pw = randomPw.randomPw();
        member.setPerPassword(pw);
        System.out.println(" >> 처리 중입니다. 잠시만 기다려 주세요.");
        sendMail.sendMail(inputEmail, pw);
        System.out.println();
        System.out.printf(" '%s님'의 임시 비밀번호가 메일로 전송되었습니다.\n", member.getPerNickname());
        System.out.println(" >> 로그인 후 비밀번호를 변경해 주세요.");
        memberDao.update(member);
        return;

      } else {
        System.out.println(" >> 해당 이메일이 존재하지 않습니다.");
        continue;
      }
    }
  }
}