package com.ogong.pms.handler.admin;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AbstractCeoMemberHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class AdminCeoMemberListHandler extends AbstractCeoMemberHandler  {

  HashMap<String, Command> commandMap;

  public AdminCeoMemberListHandler(List<CeoMember> ceoMemberList, HashMap<String, Command> commandMap) {
    super(ceoMemberList);
    this.commandMap = commandMap;
  }

  // 관리자가 사용
  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 기업회원 목록");
    System.out.println();

    for (CeoMember ceoMember : ceoMemberList) {
      System.out.printf(" (%d)\n 대표자명 : %s\n 이메일 : %s\n 가입일 : %s\n",
          ceoMember.getCeoNo(),
          ceoMember.getCeoBossName(),
          ceoMember.getCeoEmail(),
          ceoMember.getCeoregisteredDate());

      System.out.println();
    }
    //    selectUserModifyPage();
  }

  //  private void selectUserModifyPage() {
  //    System.out.println("1. 상세");
  //    System.out.println("2. 탈퇴");
  //    System.out.println("0. 이전");
  //
  //    int selectAdminNo = Prompt.inputInt("선택> ");
  //    switch (selectAdminNo) {
  //      //      case 1: commandMap.get("/adminCeoMember/detail").execute(); break;
  //      //      case 2: commandMap.get("/adminCeoMember/delete").execute(); break;
  //      default : return;
  //    }
  //  }
}
