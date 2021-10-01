package com.ogong.pms.handler.member;

import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AbstractCeoMemberHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoDetailHandler extends AbstractCeoMemberHandler {

  public CeoDetailHandler(List<CeoMember> ceoMemberList) {
    super(ceoMemberList);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 프로필");

    CeoMember ceoMember = null;

    try {
      ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();
      System.out.println();
      System.out.printf(" [%s]\n", ceoMember.getCeoBossName());
      System.out.printf(" >> 이메일 : %s\n", ceoMember.getCeoEmail());
      System.out.printf(" >> 사  진 : %s\n", ceoMember.getCeoPhoto());
      System.out.printf(" >> 대표자명 : %s\n", ceoMember.getCeoBossName());
      System.out.printf(" >> 사업자 번호 : %s\n", ceoMember.getCeoLicenseNo());
      System.out.printf(" >> 가입일 : %s\n", ceoMember.getCeoregisteredDate());

    } catch (NullPointerException e) {
      System.out.println(" >> 로그인 하세요.");
    }

    if (ceoMember == null) {
      return;
    }

    request.setAttribute("inputceoNo", ceoMember.getCeoNo());

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("2. 탈퇴");
    System.out.println("0. 이전");

    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/ceoMember/update").forward(request); return;
        case 2: request.getRequestDispatcher("/ceoMember/delete").forward(request); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
