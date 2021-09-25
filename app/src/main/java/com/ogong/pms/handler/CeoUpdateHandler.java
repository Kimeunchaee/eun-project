package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoUpdateHandler extends AbstractCeoMemberHandler {

  PromptCeoMember promptCeoMember;

  public CeoUpdateHandler(List<CeoMember> ceoMemberList, PromptCeoMember promptCeoMember) {
    super(ceoMemberList);
    this.promptCeoMember = promptCeoMember;
  }


  // 기업회원 개인정보 수정은 이름,이메일,비밀번호만 가능
  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 기업 정보 변경"); 
    System.out.println();

    int inputceoNo = (int) request.getAttribute("inputceoNo");

    CeoMember ceoMember = promptCeoMember.findByCeoMemberNo(inputceoNo);

    if (ceoMember == null) {
      System.out.println(" >> 해당 회원이 없습니다.");
      return;
    }

    ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    System.out.println("1. 대표자명");
    System.out.println("2. 사진");
    System.out.println("3. 이메일");
    System.out.println("4. 비밀번호");
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
        ceoBossName = Prompt.inputString(" 사  진(" + ceoMember.getCeoPhoto()  + ") : ");
        break;
      case 3:
        ceoEmail = Prompt.inputString(" 이메일(" + ceoMember.getCeoEmail() + ") : ");
        break;
      case 4:
        ceoPassword = Prompt.inputString(" 비밀번호(" + ceoMember.getCeoPassword() + ") : ");
        break;
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
    //    ceoMember.setCeoPassword(ceoStoreName);
    //    ceoMember.setCeoPhoto(ceoStoreDetailAddress);

    System.out.println(" >> 회원 정보를 변경하였습니다.");

  }
}







