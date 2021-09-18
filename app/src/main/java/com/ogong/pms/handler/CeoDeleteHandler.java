package com.ogong.pms.handler;

import java.util.List;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoDeleteHandler extends AbstractCeoMemberHandler {

  PromptCeoMember promptCeoMember;
  List<Cafe> cafeList;

  public CeoDeleteHandler(
      List<CeoMember> ceoMemberList, PromptCeoMember promptCeoMember, List<Cafe> cafeList) {
    super(ceoMemberList);
    this.promptCeoMember = promptCeoMember;
    this.cafeList = cafeList;
  }

  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 기업회원 탈퇴");

    if (AuthCeoMemberLoginHandler.getLoginCeoMember() == null) {
      System.out.println(" >> 로그인 하세요.");
      return;
    }

    System.out.println(" << 이메일 확인 >>");
    String inputEmail = Prompt.inputString(" 이메일을 입력하세요 : ");
    CeoMember ceoMember = promptCeoMember.findByCeoMemberEmail(inputEmail);
    if (ceoMember == null) {
      System.out.println();
      System.out.println(" >> 이메일이 일치하지 않습니다.");
      return;
    } 

    System.out.println();
    System.out.println(" << 비밀번호 확인 >>");
    String inputPW = Prompt.inputString(" 비밀번호를 입력하세요 : ");
    CeoMember ceoMemberPW = promptCeoMember.findByCeoMemberPW(inputPW);
    if (ceoMemberPW == null) {
      System.out.println();
      System.out.println(" >> 비밀번호가 일치하지 않습니다.");
      return;
    }

    System.out.println();
    String input = Prompt.inputString(" 정말 탈퇴하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 탈퇴를 취소하였습니다.");
      return;
    }

    for (int i = cafeList.size() - 1; i >= 0; i--) {
      if (cafeList.get(i).getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        cafeList.remove(cafeList.get(i));
      }
    }

    ceoMemberList.remove(ceoMember);
    AuthCeoMemberLoginHandler.loginCeoMember = null;
    AuthCeoMemberLoginHandler.accessLevel = Menu.LOGOUT;
    System.out.println();
    System.out.println(" >> 회원 탈퇴를 완료하였습니다.");
    return;
  }
}
