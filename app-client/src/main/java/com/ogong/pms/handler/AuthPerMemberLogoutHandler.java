package com.ogong.pms.handler;

import com.ogong.menu.Menu;

public class AuthPerMemberLogoutHandler implements Command {

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    AuthPerMemberLoginHandler.loginUser = null;
    AuthPerMemberLoginHandler.accessLevel = Menu.LOGOUT;
    System.out.println(" >> 로그아웃 되었습니다.");
  }

}
