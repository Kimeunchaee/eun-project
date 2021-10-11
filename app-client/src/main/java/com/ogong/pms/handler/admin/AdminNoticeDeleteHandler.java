package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeDeleteHandler implements Command {

  AdminDao adminDao;

  public AdminNoticeDeleteHandler(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 삭제");
    int noticeNo = (int) request.getAttribute("noticeNo");

    AdminNotice notice = adminDao.findByNoticeNo(noticeNo);

    String inputnotice = Prompt.inputString("\n 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!inputnotice.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제가 취소되었습니다.");
      request.getRequestDispatcher("/adminNotice/list").forward(request);
      return;
    }

    adminDao.delete(noticeNo);

    System.out.println(" >> 공지가 삭제되었습니다.");
    request.getRequestDispatcher("/adminNotice/list").forward(request);
  }

}
