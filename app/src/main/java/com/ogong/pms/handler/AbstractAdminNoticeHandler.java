package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.AdminNotice;

public abstract class AbstractAdminNoticeHandler implements Command {

  List<AdminNotice> adminNoticeList;

  public AbstractAdminNoticeHandler(List<AdminNotice> adminNoticeList) {
    this.adminNoticeList = adminNoticeList;
  }

  protected AdminNotice findByNotiNo(int adminnotiNo) {
    for (AdminNotice adminNotice : adminNoticeList) {
      if (adminNotice.getAdminNotiNo() == adminnotiNo) {
        return adminNotice;
      }
    }
    return null;
  }
}

