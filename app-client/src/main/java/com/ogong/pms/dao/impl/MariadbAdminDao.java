package com.ogong.pms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;

public class MariadbAdminDao implements AdminDao {

  Connection con;

  public MariadbAdminDao(Connection con) {
    this.con = con;
  }

  // ----------- [ 관리자 ] ------------------------------------------------------

  @Override
  public void update(Admin admin) throws Exception {

  }

  @Override
  public Admin findByAdminNo(int no) throws Exception {
    return null;
  }

  @Override
  public Admin findByEmailAndPassword(String email, String password) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select admin_no,nickname,email from admin" 
            + " where email=? and password=password(?)")) {

      // 일반문장은 try 안에 선언할 수 없다.
      stmt.setString(1, email);
      stmt.setString(2, password);

      // ResultSet을 자동 close 하기위해 try 안에 try를 선언한다.
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Admin admin = new Admin();
        admin.setMasterNo(rs.getInt("admin_no"));
        admin.setMasterNickname(rs.getString("nickname"));
        admin.setMasterEmail(rs.getString("email"));

        return admin;
      }
    }
  }

  // ---------- [ 공지사항 ] -----------------------------------------------------

  @Override
  public void insert(AdminNotice adminNotice) throws Exception {

  }

  @Override
  public void update(AdminNotice notice) throws Exception {

  }

  @Override
  public void delete(int noticeNo) throws Exception {

  }

  @Override
  public List<AdminNotice> findAll() throws Exception {
    return null;
  }

  @Override
  public AdminNotice findByNoticeNo(int noticeNo) throws Exception {
    return null;
  }

}
