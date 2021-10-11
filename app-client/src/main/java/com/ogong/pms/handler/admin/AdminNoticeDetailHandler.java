package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeDetailHandler implements Command {

  AdminDao adminDao;

  public AdminNoticeDetailHandler(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 상세");
    int adminnotiNo = Prompt.inputInt(" 번호 : ");
    System.out.println();

    AdminNotice adminNotice = adminDao.findByNoticeNo(adminnotiNo);

    System.out.printf(" [%s]\n", adminNotice.getAdminNotiTitle());
    System.out.printf(" >> 내용 : %s\n", adminNotice.getAdminNotiContent());
    System.out.printf(" >> 작성자 : %s\n", adminNotice.getAdminNotiWriter());
    System.out.printf(" >> 등록일 : %s\n", adminNotice.getAdminNotiRegisteredDate());

    if (AuthAdminLoginHandler.getLoginAdmin() != null) {

      request.setAttribute("noticeNo", adminNotice.getAdminNotiNo());

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

