package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;

public class ListAdminDao implements AdminDao {
  List<Admin> list = new ArrayList<>();
  List<AdminNotice> noticeList = new ArrayList<>();

  // ----------- [ 관리자 ] ------------------------------------------------------

  @Override
  public void update(Admin admin) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getMasterNo() == admin.getMasterNo()) {
        list.set(i, admin);
        return;
      }
    }
  }

  @Override
  public Admin findByAdminNo(int no) throws Exception {
    for (Admin admin : list) {
      if (admin.getMasterNo() == no) {
        return admin;
      }
    }
    return null;
  }

  // ---------- [ 공지사항 ] -----------------------------------------------------

  @Override
  public void insert(AdminNotice adminNotice) throws Exception {
    noticeList.add(adminNotice);
  }

  @Override
  public void update(AdminNotice notice) throws Exception {
    for (int i = 0; i < noticeList.size(); i++) {
      if (noticeList.get(i).getAdminNotiNo() == notice.getAdminNotiNo()) {
        noticeList.set(i, notice);
        return;
      }
    }
  }

  @Override
  public void delete(int noticeNo) throws Exception {
    for (int i = 0; i < noticeList.size(); i++) {
      if (noticeList.get(i).getAdminNotiNo() == noticeNo) {
        noticeList.remove(i);
        return;
      }
    }
  }

  @Override
  public List<AdminNotice> findAll() throws Exception {
    return noticeList;
  }

  @Override
  public AdminNotice findByNoticeNo(int noticeNo) throws Exception {
    for (AdminNotice notice : noticeList) {
      if (notice.getAdminNotiNo() == noticeNo) {
        return notice;
      }
    }
    return null;
  }

}
