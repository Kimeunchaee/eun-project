package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminCeoMemberDetailHandler implements Command {

  CeoMemberDao ceoMemberDao;

  public AdminCeoMemberDetailHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업회원 상세");

    int inputCeoNo = 0;
    while (true) {
      try {
        inputCeoNo = Prompt.inputInt(" 번호 : ");
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자를 입력해 주세요.\n");
        continue;
      } 
      break;
    }

    System.out.println();

    CeoMember ceoMember = ceoMemberDao.findByNo(inputCeoNo);

    System.out.printf(" [%s]\n", ceoMember.getCeoBossName());
    System.out.printf(" >> 점포명 : %s\n", ceoMember.getCeoEmail());
    System.out.printf(" >> 이메일 : %s\n", ceoMember.getCeoEmail());
    System.out.printf(" >> 사업자등록번호 : %s\n", ceoMember.getCeoLicenseNo());
    System.out.printf(" >> 사진 : %s\n", ceoMember.getCeoPhoto());
    System.out.printf(" >> 가입일 : %s\n", ceoMember.getCeoregisteredDate());

    request.setAttribute("inputCeoNo", inputCeoNo);

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("2. 탈퇴");
    System.out.println("0. 이전");

    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/adminCeoMember/update").forward(request); return;
        case 2: request.getRequestDispatcher("/adminCeoMember/delete").forward(request); return;
        case 0: return;
        default :
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
