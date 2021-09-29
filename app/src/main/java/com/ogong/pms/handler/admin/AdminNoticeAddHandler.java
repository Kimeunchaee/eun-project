package com.ogong.pms.handler.admin;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.AbstractAdminNoticeHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeAddHandler extends AbstractAdminNoticeHandler {

  int adminNotiNo = 3;

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

    adminWriteList.setAdminNotiTitle(Prompt.inputString(" 제목 : "));
    adminWriteList.setAdminNotiContent(Prompt.inputString(" 내용 : "));
    adminWriteList.setAdminNotiWriter(AuthAdminLoginHandler.getLoginAdmin().getMasterNickname());
    adminWriteList.setAdminNotiRegisteredDate(new Date(System.currentTimeMillis()));

    System.out.println();
    String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equals("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }
    adminWriteList.setAdminNotiNo(adminNotiNo++);
    adminNoticeList.add(adminWriteList);
    System.out.println(" >> 공지글 등록이 완료되었습니다.");
  }
}
