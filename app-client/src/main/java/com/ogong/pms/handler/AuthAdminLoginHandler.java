package com.ogong.pms.handler;

import java.util.List;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Admin;
import com.ogong.util.Prompt;

public class AuthAdminLoginHandler extends AbstractLoginHandler {

  List<Admin> adminList;

  static Admin loginAdmin;
  public static Admin getLoginAdmin() {
    return loginAdmin;
  }

  public static int getUserAccessLevel() {
    return accessLevel;
  }

  public AuthAdminLoginHandler(List<Admin> adminList) {
    this.adminList = adminList;

    //    Admin testAdmin = new Admin();
    //    testAdmin.setMasterNo(1);
    //    testAdmin.setMasterNickname("관리자");
    //    testAdmin.setMasterEmail("ogong");
    //    testAdmin.setMasterPassword("1234");
    //
    //    adminList.add(testAdmin);
  }

  // ----------------------------------------------------------------------

  @Override
  public void execute(CommandRequest request) {

    System.out.println();
    String inputadminEmail = Prompt.inputString(" 이메일 : ");
    String inputadminPassword = "";
    Admin admin = findByAdminEmail(inputadminEmail);

    if (admin == null) {
      System.out.println("\n >> 관리자 이메일이 아닙니다.");
    }

    while (admin != null) {
      inputadminPassword = Prompt.inputString(" 비밀번호 : ");

      if (admin.getMasterPassword().equals(inputadminPassword)) {
        admin.setMasterEmail(inputadminEmail);
        admin.setMasterPassword(inputadminPassword);
        System.out.printf("\n >> '%s'님 환영합니다!\n", admin.getMasterNickname());
        loginAdmin = admin;
        accessLevel = Menu.ADMIN_LOGIN;
        return;
      }

      System.out.println(" >> 비밀번호를 다시 입력하세요.\n");
      return;
    } 
  } 


  private Admin findByAdminEmail (String inputEmail) {
    for (Admin adminEmail : adminList) {
      if (inputEmail.equals(adminEmail.getMasterEmail())) {
        return adminEmail;
      }
    }
    return null;
  }
}

