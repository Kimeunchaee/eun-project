package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AbstractCeoMemberHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptCeoMember;
import com.ogong.util.Prompt;

public class AdminCeoMemberDeleteHandler extends AbstractCeoMemberHandler {

  PromptCeoMember promptCeoMember;
  List<Cafe> cafeList;

  public AdminCeoMemberDeleteHandler(
      List<CeoMember> ceoMemberList, PromptCeoMember promptCeoMember,
      List<Cafe> cafeList) {
    super(ceoMemberList);
    this.promptCeoMember = promptCeoMember;
    this.cafeList = cafeList;
  }

  //관리자용
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 기업회원 탈퇴");
    int inputceoNo = (int) request.getAttribute("inputceoNo");
    System.out.println();

    CeoMember ceoMember = promptCeoMember.findByCeoMemberNo(inputceoNo);

    if (ceoMember == null) {
      System.out.println(" >> 해당 번호의 기업 회원이 없습니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 탈퇴시키겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 기업 회원 삭제를 취소하였습니다.");
      return;
    }

    for (int i = cafeList.size() - 1; i >= 0; i--) {
      if (cafeList.get(i).getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        cafeList.remove(cafeList.get(i));
      }
    }

    ceoMemberList.remove(ceoMember);

    System.out.println(" >> 기업 회원이 삭제되었습니다.");
  }

}
