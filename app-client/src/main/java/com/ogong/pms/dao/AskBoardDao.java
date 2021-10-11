package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.AskBoard;

public interface AskBoardDao {

  List<AskBoard> findAll() throws Exception;
  AskBoard findByNo(int no) throws Exception;
  AskBoard findByPerAskBoard(int askNo, int perMemberNo) throws Exception;
  AskBoard findByCeoAskBoard(int askNo, int ceoMemberNo) throws Exception;
  //AskBoard findByNickName(String inputNick) throws Exception;
  //AskBoard findByEmail(String inputEmail) throws Exception;
  void update(AskBoard askBoard) throws Exception;
  void delete(int no) throws Exception;
  void insert(AskBoard askBoard) throws Exception;


}
