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

    try {
      ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

      String ceoBossName = Prompt.inputString(" 대표자명(" + ceoMember.getCeoBossName()  + ") : ");
      String ceophoto = Prompt.inputString(" 사진(" + ceoMember.getCeoPhoto()  + ") : ");
      String ceoEmail = Prompt.inputString(" 이메일(" + ceoMember.getCeoEmail() + ") : ");
      String ceoPassword = Prompt.inputString(" 비밀번호(" + ceoMember.getCeoPassword() + ") : ");
      //    String ceoStoreName = Prompt.inputString(" 점포명(" + ceoMember.getCeoStoreName() + ") : ");
      //    String ceoStoreDetailAddress = Prompt.inputString("점포주소(" + ceoMember.getCeoStoreDetailAddress() + ") : ");

      System.out.println();
      String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 회원 변경을 취소하였습니다.");
        return;
      }

      ceoMember.setCeoBossName(ceoBossName);
      ceoMember.setCeoEmail(ceoEmail);
      ceoMember.setCeoPassword(ceoPassword);
      ceoMember.setCeoPhoto(ceophoto);
      //    ceoMember.setCeoPassword(ceoStoreName);
      //    ceoMember.setCeoPhoto(ceoStoreDetailAddress);

      System.out.println(" >> 회원 정보를 변경하였습니다.");

    } catch (NullPointerException e) {
      System.out.println(" >> 로그인 하세요.");
    }
  }
}







