package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.AbstractAdminNoticeHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeDetailHandler extends AbstractAdminNoticeHandler {

  public AdminNoticeDetailHandler(List<AdminNotice> adminNoticeList) {
    super(adminNoticeList);
  }


  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 상세");
    int adminnotiNo = Prompt.inputInt(" 번호 : ");
    System.out.println();

    AdminNotice adminWriteList = findByNotiNo(adminnotiNo);

    if (adminWriteList == null) {
      System.out.println(" >> 공지를 다시 선택하세요.");
      return;
    }

    System.out.printf(" [%s]\n", adminWriteList.getAdminNotiTitle());
    System.out.printf(" >> 내용 : %s\n", adminWriteList.getAdminNotiContent());
    System.out.printf(" >> 작성자 : %s\n", adminWriteList.getAdminNotiWriter());
    System.out.printf(" >> 등록일 : %s\n", adminWriteList.getAdminNotiRegisteredDate());

    if (AuthAdminLoginHandler.getLoginAdmin() != null) {

      request.setAttribute("adminnotiNo", adminnotiNo);

      System.out.println();
      System.out.println("1. 수정");
      System.out.println("2. 삭제");
      System.out.println("0. 이전");

      while (true) {
        int selectNo = Prompt.inputInt("선택> ");
        switch (selectNo) {
          case 1: request.getRequestDispatcher("/adminNotice/update").forward(request); return;
          case 2: request.getRequestDispatcher("/adminNotice/delete").forward(request); return;
          case 0: return;
          default :
            System.out.println(" >> 번호를 다시 선택해 주세요.");
        }
      }
    }
  }
}

