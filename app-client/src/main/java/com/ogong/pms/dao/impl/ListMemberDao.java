package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;

public class ListMemberDao implements MemberDao {
  List<Member> list = new ArrayList<>(); 

  @Override
  public void insert(Member member) throws Exception {
    list.add(member);
  }

  @Override
  public List<Member> findAll() throws Exception {
    return list;
  }

  @Override
  public Member findByNo(int no) throws Exception {
    for (Member m : list) {
      if (m.getPerNo() == no) {
        return m;
      }
    }
    return null;
  }

  @Override
  public Member findByNickName(String inputNick) throws Exception {
    for (Member m : list) {
      if (m.getPerNickname().equals(inputNick)) {
        return m;
      }
    }
    return null;
  }

  @Override
  public Member findByEmail(String inputEmail) throws Exception {
    for (Member m : list) {
      if (m.getPerEmail().equals(inputEmail)) {
        return m;
      }
    }
    return null;
  }

  @Override
  public Member findByEmailAndPassword(String email, String password) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(Member member) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getPerNo() == member.getPerNo()) {
        list.set(i, member);
        return;
      }
    }
  }

  @Override
  public void delete(int no) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getPerNo() == no) {
        list.remove(i);
        return;
      }
    }
  }
}