package com.ogong.pms.handler;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.util.Prompt;

public class AdminNoticeAddHandler extends AbstractAdminNoticeHandler {


  public AdminNoticeAddHandler(List<AdminNotice> adminNoticeList) {
    super(adminNoticeList);

    //    AdminNotice adminWriteList = new AdminNotice();
    //    adminWriteList.setAdminNotiNo(1);
    //    adminWriteList.setAdminNotiTitle("오늘의 공부 회원 가입을 환영합니다!");
    //    adminWriteList.setAdminNotiContent("오늘의 공부와 함께 목표를 향해 으쌰으쌰!");
    //    adminWriteList.setAdminNotiWriter("관리자");
    //    adminWriteList.setAdminNotiRegisteredDate(new Date(System.currentTimeMillis()));
    //
    //    adminNoticeList.add(adminWriteList);
    //
    //    adminWriteList = new AdminNotice();
    //    adminWriteList.setAdminNotiNo(2);
    //    adminWriteList.setAdminNotiTitle("오늘의 공부 홈페이지 이용 방법");
    //    adminWriteList.setAdminNotiContent("내용 업데이트 예정입니다.");
    //    adminWriteList.setAdminNotiWriter("관리자");
    //    adminWriteList.setAdminNotiRegisteredDate(new Date(System.currentTimeMillis()));
    //
    //    adminNoticeList.add(adminWriteList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 공지 등록");
    System.out.println();

    AdminNotice adminWriteList = new AdminNotice();

    adminWriteList.setAdminNotiNo(Prompt.inputInt(" 번호 : "));
    adminWriteList.setAdminNotiTitle(Prompt.inputString(" 제목 : "));
    adminWriteList.setAdminNotiContent(Prompt.inputString(" 내용 : "));
    adminWriteList.setAdminNotiWriter(AuthAdminLoginHandler.getLoginAdmin().getMasterNickname());
    adminWriteList.setAdminNotiRegisteredDate(new Date(System.currentTimeMillis()));

    adminNoticeList.add(adminWriteList);
  }

}
