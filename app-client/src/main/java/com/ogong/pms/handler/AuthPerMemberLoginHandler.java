package com.ogong.pms.handler;

import com.ogong.menu.Menu;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class AuthPerMemberLoginHandler extends AbstractLoginHandler implements Command {

  MemberDao memberDao;

  public static Member loginUser;
  public static Member getLoginUser() {
    return loginUser;
  }

  public AuthPerMemberLoginHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = Prompt.inputString(" 비밀번호 : ");

    Member member = memberDao.findByEmailAndPassword(inputEmail, inputPassword);

    if (member != null) {

      if (member.getActive() == Member.OUTUSER) {
        System.out.println(" >> 회원가입을 진행해 주세요.");
        return;
      }

      System.out.println();
      System.out.printf(" '%s'님 환영합니다! 🖐\n", member.getPerNickname());
      loginUser = member;
      accessLevel = Menu.PER_LOGIN;

    } else {
      System.out.println("\n >> 이메일과 암호가 일치하는 회원을 찾을 수 없습니다.");
      return;
    }
  } 
}
