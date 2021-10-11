package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;

public class ListCeoMemberDao implements CeoMemberDao {
  List<CeoMember> list = new ArrayList<>(); 


  @Override
  public List<CeoMember> findAll() throws Exception {
    return list;
  }

  @Override
  public CeoMember findByNo(int no) throws Exception {
    for (CeoMember c : list) {
      if (c.getCeoNo() == no) {
        return c;
      }
    }
    return null;
  }

  @Override
  public CeoMember findByNickName(String inputNick) throws Exception {
    for (CeoMember c : list) {
      if (c.getCeoBossName().equals(inputNick)) {
        return c;
      }
    }
    return null;
  }

  @Override
  public CeoMember findByEmail(String inputEmail) throws Exception {
    for (CeoMember c : list) {
      if (c.getCeoEmail().equals(inputEmail)) {
        return c;
      }
    }
    return null;
  }

  @Override
  public void insert(CeoMember ceoMember) throws Exception {
    list.add(ceoMember);
  }

  @Override
  public void update(CeoMember ceoMember) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getCeoNo() == ceoMember.getCeoNo()) {
        list.set(i, ceoMember);
        return;
      }
    }
  }

  @Override
  public void delete(int no) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getCeoNo() == no) {
        list.remove(i);
        return;
      }
    }
  }
}