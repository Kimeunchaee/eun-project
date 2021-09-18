package com.ogong.pms.handler;

import com.ogong.menu.Menu;

public class AuthCeoMemberLogoutHandler implements Command {

  public void execute(CommandRequest request) {
    System.out.println();
    AuthCeoMemberLoginHandler.loginCeoMember = null;
    AuthCeoMemberLoginHandler.accessLevel = Menu.LOGOUT;
    System.out.println(" >> 로그아웃 되었습니다.");
  }


}
