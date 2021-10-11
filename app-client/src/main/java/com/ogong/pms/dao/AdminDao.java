package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;

public interface AdminDao {

  // ----------- [ 관리자 ] ------------------------------------------------------

  void update(Admin admin) throws Exception;
  Admin findByAdminNo(int no) throws Exception;

  // ---------- [ 공지사항 ] -----------------------------------------------------

  void insert(AdminNotice adminNotice) throws Exception;
  void update(AdminNotice notice) throws Exception;
  void delete(int noticeNo) throws Exception;
  List<AdminNotice> findAll() throws Exception;
  AdminNotice findByNoticeNo(int noticeNo) throws Exception;

}

//case "notice.insert" : insert(request, response); break;
//case "notice.selectOne" : selectOne(request, response); break;
//case "notice.selectList" : selectList(request, response); break;
//case "notice.update" : update(request, response); break;
//case "notice.delete" : delete(request, response); break;
