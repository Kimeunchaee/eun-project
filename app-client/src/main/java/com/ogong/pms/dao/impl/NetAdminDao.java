package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.request.RequestAgent;

public class NetAdminDao implements AdminDao {

  RequestAgent requestAgent;

  public NetAdminDao(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  // ----------- [ 관리자 ] ------------------------------------------------------

  @Override
  public void update(Admin admin) throws Exception {
    requestAgent.request("admin.update", admin);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("관리자 수정 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public Admin findByAdminNo(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("adminNo", String.valueOf(no));

    requestAgent.request("admin.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("관리자 조회 실패");
      return null;
    }
    return requestAgent.getObject(Admin.class);
  }

  // ---------- [ 공지사항 ] -----------------------------------------------------

  @Override
  public void insert(AdminNotice adminNotice) throws Exception {
    requestAgent.request("notice.insert", adminNotice);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("공지게시판 등록 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void update(AdminNotice notice) throws Exception {
    requestAgent.request("notice.update", notice);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("공지게시판 수정 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void delete(int noticeNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("noticeNo", String.valueOf(noticeNo));

    requestAgent.request("notice.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("공지게시판 삭제 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public List<AdminNotice> findAll() throws Exception {
    requestAgent.request("notice.selectList", null);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("공지게시판 목록 조회 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(AdminNotice.class));
  }

  @Override
  public AdminNotice findByNoticeNo(int noticeNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("noticeNo", String.valueOf(noticeNo));

    requestAgent.request("notice.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 공지를 다시 선택하세요.");
      return null;
    }
    return requestAgent.getObject(AdminNotice.class);
  }

}
