package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.Member;

public interface MemberDao {

  void insert(Member member) throws Exception;
  List<Member> findAll() throws Exception;
  Member findByNo(int no) throws Exception;
  Member findByNickName(String inputNick) throws Exception;
  Member findByEmail(String inputEmail) throws Exception;
  void update(Member member) throws Exception;
  void delete(int no) throws Exception;




  //  void selectOnByNickname(Member member) throws Exception;
  //  int indexNo(int no) throws Exception;
  //  List<Member> findByKeyword(String keyword) throws Exception;

}
