package com.ogong.pms.handler;

import java.util.HashMap;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Admin;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class AuthAdminLoginHandler extends AbstractLoginHandler implements Command {

  RequestAgent requestAgent;

  static Admin loginAdmin;
  public static Admin getLoginAdmin() {
    return loginAdmin;
  }

  public AuthAdminLoginHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;

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
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String adminEmail = Prompt.inputString(" 이메일 : ");
    String adminPassword = Prompt.inputString(" 비밀번호 : ");

    HashMap<String,String> params = new HashMap<>();
    params.put("adminEmail", adminEmail);
    params.put("adminPassword", adminPassword);

    requestAgent.request("admin.selectOneByEmailPassword", params);

    if (requestAgent.getStatus().equals(RequestAgent.SUCCESS)) {
      Admin admin = requestAgent.getObject(Admin.class);
      System.out.printf(" >> %s님 환영합니다!\n", admin.getMasterNickname());
      loginAdmin = admin;
      accessLevel = Menu.ADMIN_LOGIN;

    } else {
      System.out.println(" >> 이메일과 암호가 일치하지 않습니다.");
    }
  }
}
