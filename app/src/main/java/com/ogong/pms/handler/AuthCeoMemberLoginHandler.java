package com.ogong.pms.handler;

import java.util.List;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class AuthCeoMemberLoginHandler extends AbstractLoginHandler {

  List<CeoMember> ceoMemberList;
  PromptCeoMember promptCeoMember;

  static CeoMember loginCeoMember;
  public static CeoMember getLoginCeoMember() {
    return loginCeoMember;
  }

  public static int getUserAccessLevel() {
    return accessLevel;
  }

  public AuthCeoMemberLoginHandler(List<CeoMember> ceoMemberList, PromptCeoMember promptCeoMember) {
    this.ceoMemberList = ceoMemberList;
    this.promptCeoMember = promptCeoMember;
  }

  // ----------------------------------------------------------------------

  @Override
  public void execute(CommandRequest request) {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = "";
    CeoMember ceoMember = promptCeoMember.findByCeoMemberEmail(inputEmail);

    if (ceoMember == null) {
      System.out.println("\n >> 등록된 회원이 아닙니다.");
    }

    while (ceoMember != null) {
      inputPassword = Prompt.inputString(" 비밀번호 : ");

      if (ceoMember.getCeoPassword().equals(inputPassword)) {
        ceoMember.setCeoEmail(inputEmail);
        ceoMember.setCeoPassword(inputPassword);
        System.out.printf("\n >> '%s'님 환영합니다!\n", ceoMember.getCeoBossName());
        loginCeoMember = ceoMember;
        accessLevel = Menu.CEO_LOGIN;
        return;
      }

      System.out.println(" >> 비밀번호를 다시 입력하세요.\n");
      return;
    } 
  }

}

