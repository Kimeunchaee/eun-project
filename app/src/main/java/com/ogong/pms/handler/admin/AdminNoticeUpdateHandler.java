package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.AbstractAdminNoticeHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeUpdateHandler extends AbstractAdminNoticeHandler {

  public AdminNoticeUpdateHandler(List<AdminNotice> adminNoticeList) {
    super(adminNoticeList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 공지 변경");
    int adminnotiNo = (int) request.getAttribute("adminnotiNo");

    AdminNotice adminWriteList = findByNotiNo(adminnotiNo);

    if (adminWriteList == null) {
      System.out.println(" >> 공지를 다시 선택하세요.");
      return;
    }

    String adminNoticeTitle = Prompt.inputString(
        String.format(" 제목(%s) : ", adminWriteList.getAdminNotiTitle()));
    String adminNoticeContent = Prompt.inputString(
        String.format(" 내용(%s) : ", adminWriteList.getAdminNotiContent()));

    String inputnotice = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!inputnotice.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경이 취소되었습니다.");
      return;
    }

    adminWriteList.setAdminNotiTitle(adminNoticeTitle);
    adminWriteList.setAdminNotiContent(adminNoticeContent);
    System.out.println(" >> 공지가 변경되었습니다.");
  }

}
