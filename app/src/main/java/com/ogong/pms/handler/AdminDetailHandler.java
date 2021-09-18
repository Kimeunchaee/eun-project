package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Admin;
import com.ogong.util.Prompt;

public class AdminDetailHandler extends AbstractAdminHandler {

  public AdminDetailHandler(List<Admin> adminList) {
    super(adminList);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 프로필");
    System.out.println();

    Admin adminpro = AuthAdminLoginHandler.getLoginAdmin();

    System.out.printf(" 닉네임 : %s\n", adminpro.getMasterNickname());
    System.out.printf(" 이메일 : %s\n", adminpro.getMasterEmail());

    request.setAttribute("inputNo", adminpro.getMasterNo());

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("0. 이전");

    while (true) {
      int selectAdminNo = Prompt.inputInt("선택> ");
      switch (selectAdminNo) {
        case 1: request.getRequestDispatcher("/admin/update").forward(request); return;
        case 0: return;
        default :
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
