package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.CeoMember;

public interface CeoMemberDao {

  List<CeoMember> findAll() throws Exception;
  CeoMember findByNo(int no) throws Exception;
  CeoMember findByNickName(String inputNick) throws Exception;
  CeoMember findByEmail(String inputEmail) throws Exception;
  CeoMember findByEmailAndPassword(String inputEmail, String inputPassword) throws Exception;
  void insert(CeoMember ceoMember) throws Exception;
  void update(CeoMember ceoMember) throws Exception;
  void delete(int no) throws Exception;

}
