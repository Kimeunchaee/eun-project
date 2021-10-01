package com.ogong.pms.handler;

import com.ogong.menu.Menu;

public class AuthAdminLogoutHandler implements Command {

  public void execute(CommandRequest request) {
    System.out.println();
    AuthAdminLoginHandler.loginAdmin = null;
    AuthAdminLoginHandler.accessLevel = Menu.LOGOUT;
    System.out.println(" >> 로그아웃 되었습니다.");
  }


}
