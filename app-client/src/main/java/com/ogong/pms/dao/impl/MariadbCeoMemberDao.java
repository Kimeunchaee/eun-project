package com.ogong.pms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;

public class MariadbCeoMemberDao implements CeoMemberDao {

  Connection con;

  public MariadbCeoMemberDao(Connection con){
    this.con = con;
  }

  @Override
  public List<CeoMember> findAll() throws Exception {
    return null;
  }

  @Override
  public CeoMember findByNo(int no) throws Exception {
    return null;
  }

  @Override
  public CeoMember findByNickName(String inputNick) throws Exception {
    return null;
  }

  @Override
  public CeoMember findByEmail(String inputEmail) throws Exception {
    return null;
  }

  @Override
  public CeoMember findByEmailAndPassword(String inputEmail, String inputPassword) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select m.member_no, m.name, m.email from member m"
            + " join ceo_member cm on m.member_no=cm.member_no" 
            + " where email=? and password=password(?)")) {

      // 일반문장은 try 안에 선언할 수 없다.
      stmt.setString(1, inputEmail);
      stmt.setString(2, inputPassword);

      // ResultSet을 자동 close 하기위해 try 안에 try를 선언한다.
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        CeoMember ceoMember = new CeoMember();
        ceoMember.setCeoNo(rs.getInt("member_no"));
        ceoMember.setCeoName(rs.getString("name"));
        ceoMember.setCeoEmail(rs.getString("email"));

        return ceoMember;
      }
    }
  }

  @Override
  public void insert(CeoMember ceoMember) throws Exception {

  }

  @Override
  public void update(CeoMember ceoMember) throws Exception {

  }

  @Override
  public void delete(int no) throws Exception {

  }


}
