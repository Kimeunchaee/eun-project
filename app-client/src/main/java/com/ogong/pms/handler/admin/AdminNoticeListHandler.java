package com.ogong.pms.handler.admin;

import java.util.Collection;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class AdminNoticeListHandler implements Command {

  AdminDao adminDao;

  public AdminNoticeListHandler(AdminDao adminDao) {
    this.adminDao = adminDao;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 목록");
    System.out.println();

    Collection<AdminNotice> adminNoticeList = adminDao.findAll();

    for (AdminNotice noticeList : adminNoticeList) {
      System.out.printf(" (%d)\n 제목 : %s\n 작성자 : %s\n 등록일 : %s\n", 
          noticeList.getAdminNotiNo(), 
          noticeList.getAdminNotiTitle(),
          noticeList.getAdminNotiWriter(),
          noticeList.getAdminNotiRegisteredDate());
      System.out.println();
    }      
  }

}
