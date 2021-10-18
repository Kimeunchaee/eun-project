package com.ogong.pms.handler;

import com.ogong.menu.Menu;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;
import com.ogong.util.Prompt;

public class AuthAdminLoginHandler extends AbstractLoginHandler implements Command {

  AdminDao adminDao;

  static Admin loginAdmin;
  public static Admin getLoginAdmin() {
    return loginAdmin;
  }

  public AuthAdminLoginHandler(AdminDao adminDao) {
    this.adminDao = adminDao;

  }

  // ----------------------------------------------------------------------

  @Override
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String adminEmail = Prompt.inputString(" 이메일 : ");
    String adminPassword = Prompt.inputString(" 비밀번호 : ");

    Admin admin = adminDao.findByEmailAndPassword(adminEmail, adminPassword);

    if (admin != null) {
      System.out.printf(" >> %s님 환영합니다!\n", admin.getMasterNickname());
      loginAdmin = admin;
      accessLevel = Menu.ADMIN_LOGIN;

    } else {
      System.out.println(" >> 이메일과 암호가 일치하지 않습니다.");
    }
  }
}
