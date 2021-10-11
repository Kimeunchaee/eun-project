package com.ogong.pms.handler.member;

import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;
import com.ogong.util.SendMail;

public class CeoFindIdPwHandler implements Command {

  RandomPw randomPw;
  CeoMemberDao ceoMemberDao;

  public CeoFindIdPwHandler(RandomPw randomPw, CeoMemberDao ceoMemberDao) {
    this.randomPw = randomPw;
    this.ceoMemberDao = ceoMemberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("1. 이메일 찾기");
    System.out.println("2. 비밀번호 찾기");

    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : wantCeoEmail(); break;
      case 2 : wantCeoPw(); break;
      default : return;
    }
  }

  public void wantCeoEmail() throws Exception {
    System.out.println();
    System.out.println("▶ 이메일 찾기");

    System.out.println();
    String inputNick =  Prompt.inputString(" 이름 : ");

    CeoMember ceoMember = ceoMemberDao.findByNickName(inputNick);

    if (ceoMember != null) {
      System.out.println();
      System.out.printf(" '%s님'의 이메일 >> ", ceoMember.getCeoBossName());
      System.out.println(ceoMember.getCeoEmail());

    } else {
      System.out.println(" >> 해당 이름이 존재하지 않습니다.");
      return;
    }

    String input = Prompt.inputString(" 비밀번호 찾기로 넘어가시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 찾기를 종료합니다.");
      return;
    } 
    wantCeoPw();
    return;
  }

  public void wantCeoPw() throws Exception {
    System.out.println();
    System.out.println("▶ 임시 비밀번호 발급");

    SendMail sendMail = new SendMail();

    while (true) {
      System.out.println();
      String inputEmail =  Prompt.inputString(" 이메일 : ");

      CeoMember ceoMember = ceoMemberDao.findByEmail(inputEmail);

      if (ceoMember != null) {
        String pw = randomPw.randomPw();
        ceoMember.setCeoPassword(pw);
        System.out.println(" >> 처리 중입니다. 잠시만 기다려 주세요.");
        sendMail.sendMail(inputEmail, pw);
        System.out.println();
        System.out.printf(" '%s님'의 임시 비밀번호가 메일로 전송되었습니다.\n", ceoMember.getCeoPassword());
        System.out.println(" >> 로그인 후 비밀번호를 변경해 주세요.");
        ceoMemberDao.update(ceoMember);
        return;

      } else {
        System.out.println(" >> 해당 이메일이 존재하지 않습니다.");
        continue;
      }
    }
  }
}
