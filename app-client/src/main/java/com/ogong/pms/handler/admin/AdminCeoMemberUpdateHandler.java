package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminCeoMemberUpdateHandler implements Command {

  CeoMemberDao ceoMemberDao;

  public AdminCeoMemberUpdateHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  //관리자용
  @Override 
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업회원 변경");
    System.out.println();

    int updateCeoNo = (int) request.getAttribute("inputCeoNo");

    CeoMember ceoMember = ceoMemberDao.findByNo(updateCeoNo);

    String ceoBossName = Prompt.inputString(" 대표자명(" + ceoMember.getCeoBossName()  + ") : ");
    String ceophoto = Prompt.inputString(" 사진(" + ceoMember.getCeoPhoto()  + ") : ");
    String ceoEmail = Prompt.inputString(" 이메일(" + ceoMember.getCeoEmail() + ") : ");
    String ceoPassword = Prompt.inputString(" 비밀번호(" + ceoMember.getCeoPassword() + ") : ");

    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 기업 회원 변경을 취소하였습니다.");
      return;
    }

    ceoMember.setCeoBossName(ceoBossName);
    ceoMember.setCeoEmail(ceoEmail);
    ceoMember.setCeoPassword(ceoPassword);
    ceoMember.setCeoPhoto(ceophoto);

    ceoMemberDao.update(ceoMember);
    System.out.println(" >> 기업 회원 정보를 변경하였습니다.");
  }

}
