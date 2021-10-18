package com.ogong.pms.handler;

import com.ogong.menu.Menu;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class AuthCeoMemberLoginHandler extends AbstractLoginHandler implements Command  {

  CeoMemberDao ceoMemberDao;

  public static CeoMember loginCeoMember;
  public static CeoMember getLoginCeoMember() {
    return loginCeoMember;
  }

  public AuthCeoMemberLoginHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  // ----------------------------------------------------------------------

  @Override
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = Prompt.inputString(" 비밀번호 : ");

    CeoMember ceoMember = ceoMemberDao.findByEmailAndPassword(inputEmail, inputPassword);

    if (ceoMember != null) {

      if (ceoMember.getActive() == CeoMember.OUTUSER) {
        System.out.println(" >> 회원가입을 진행해 주세요.");
        return;
      }

      System.out.printf("\n >> '%s'님 환영합니다!\n", ceoMember.getCeoName());
      loginCeoMember = ceoMember;
      accessLevel = Menu.CEO_LOGIN;
      return;
    } else {
      System.out.println("\n >> 이메일과 암호가 일치하는 회원을 찾을 수 없습니다.");
      return;
    }
  }
}
