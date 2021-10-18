package com.ogong.pms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;

public class MariadbMemberDao implements MemberDao {

  Connection con;

  public MariadbMemberDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Member member) throws Exception {

  }

  @Override
  public List<Member> findAll() throws Exception {
    return null;
  }

  @Override
  public Member findByNo(int no) throws Exception {
    return null;
  }

  @Override
  public Member findByNickName(String inputNick) throws Exception {
    return null;
  }

  @Override
  public Member findByEmail(String inputEmail) throws Exception {
    return null;
  }

  @Override
  public Member findByEmailAndPassword(String inputEmail, String inputPassword) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select m.member_no, m.nickname, m.email from member m"
            + " join per_member pm on m.member_no=pm.member_no" 
            + " where email=? and password=password(?)")) {

      // 일반문장은 try 안에 선언할 수 없다.
      stmt.setString(1, inputEmail);
      stmt.setString(2, inputPassword);

      // ResultSet을 자동 close 하기위해 try 안에 try를 선언한다.
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Member member = new Member();
        member.setPerNo(rs.getInt("member_no"));
        member.setPerNickname(rs.getString("nickname"));
        member.setPerEmail(rs.getString("email"));

        return member;
      }
    }
  }

  @Override
  public void update(Member member) throws Exception {

  }

  @Override
  public void delete(int no) throws Exception {

  }


}
