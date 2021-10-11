package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeUpdateHandler implements Command {

  AdminDao adminDao;

  public AdminNoticeUpdateHandler(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 변경");
    System.out.println();
    int noticeNo = (int) request.getAttribute("noticeNo");

    List<AdminNotice> noticeList = adminDao.findAll();
    AdminNotice notice = adminDao.findByNoticeNo(noticeNo);

    System.out.println("1. 제목");
    System.out.println("2. 내용");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");
    System.out.println();

    String adminNoticeTitle = notice.getAdminNotiTitle();
    String adminNoticeContent = notice.getAdminNotiContent();

    switch (selectNo) {
      case 1: adminNoticeTitle = Prompt.inputString(" 제목(" + notice.getAdminNotiTitle()  + ") : ");
      break;
      case 2: adminNoticeContent = Prompt.inputString(" 내용(" + notice.getAdminNotiTitle()  + ") : ");
      break;
      default : 
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    System.out.println();
    String inputnotice = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!inputnotice.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경이 취소되었습니다.");
      request.getRequestDispatcher("/adminNotice/list").forward(request);
      return;
    }

    if (selectNo == 1) {
      notice.setAdminNotiTitle(adminNoticeTitle);
    } else if (selectNo == 2) {
      notice.setAdminNotiContent(adminNoticeContent);
    }

    adminDao.update(notice);

    System.out.println(" >> 공지가 변경되었습니다.");
    request.getRequestDispatcher("/adminNotice/list").forward(request);
    return;
  }

}
