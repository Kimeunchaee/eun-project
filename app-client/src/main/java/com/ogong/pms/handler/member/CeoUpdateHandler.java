package com.ogong.pms.handler.member;

import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoUpdateHandler implements Command {

  CeoMemberDao ceoMemberDao;

  public CeoUpdateHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  // 기업회원 개인정보 수정은 이름,이메일,비밀번호만 가능
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업 정보 변경"); 
    System.out.println();

    int no = (int) request.getAttribute("inputCeoNo");

    CeoMember ceoMember = ceoMemberDao.findByNo(no);

    System.out.println("1. 대표자명");
    System.out.println("2. 사진");
    System.out.println("3. 이메일");
    System.out.println("4. 비밀번호");
    System.out.println("0. 이전");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");

    String ceoBossName = ceoMember.getCeoBossName();
    String ceophoto = ceoMember.getCeoPhoto();
    String ceoEmail = ceoMember.getCeoEmail();
    String ceoPassword = ceoMember.getCeoPassword();

    switch (selectNo) {
      case 1:
        ceoBossName = Prompt.inputString(" 대표자명(" + ceoMember.getCeoBossName()  + ") : ");
        break;
      case 2:
        ceophoto = Prompt.inputString(" 사  진(" + ceoMember.getCeoPhoto()  + ") : ");
        break;
      case 3:
        ceoEmail = Prompt.inputString(" 이메일(" + ceoMember.getCeoEmail() + ") : ");
        break;
      case 4:
        ceoPassword = Prompt.inputString(" 비밀번호(" + ceoMember.getCeoPassword() + ") : ");
        break;
      case 0: return;
      default : 
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    //String ceoStoreName = Prompt.inputString(" 점포명(" + ceoMember.getCeoStoreName() + ") : ");
    //String ceoStoreDetailAddress = Prompt.inputString("점포주소(" + ceoMember.getCeoStoreDetailAddress() + ") : ");

    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 변경을 취소하였습니다.");
      return;
    }
    if (selectNo == 1) {
      ceoMember.setCeoBossName(ceoBossName);
    } else if (selectNo == 2) {
      ceoMember.setCeoPhoto(ceophoto);
    } else if (selectNo == 3) {
      ceoMember.setCeoEmail(ceoEmail);
    } else if (selectNo == 4) {
      ceoMember.setCeoPassword(ceoPassword);
    }
    //    ceoMember.setceoStoreName(ceoStoreName);
    //    ceoMember.setceoStoreDetailAddress(ceoStoreDetailAddress);

    ceoMemberDao.update(ceoMember);
    System.out.println(" >> 회원 정보를 변경하였습니다.");

  }
}







