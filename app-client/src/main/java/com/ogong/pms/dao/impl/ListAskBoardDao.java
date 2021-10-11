package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;

public class ListAskBoardDao implements AskBoardDao {

  List<AskBoard> list = new ArrayList<>();

  @Override
  public List<AskBoard> findAll() throws Exception {
    return list;
  }

  @Override
  public AskBoard findByNo(int no) throws Exception {
    for (AskBoard a : list) {
      if (a.getAskNo() == no) {
        return a;
      }
    }
    return null;
  }

  @Override
  public AskBoard findByPerAskBoard(int askNo, int perMemberNo) throws Exception {
    for (AskBoard a : list) {
      if (a.getAskMemberWriter().getPerNo() == perMemberNo) {
        if (a.getAskNo() == askNo) {
          return a;
        }
      }
    }
    return null;
  }

  @Override
  public AskBoard findByCeoAskBoard(int askNo, int ceoMemberNo) throws Exception {
    for (AskBoard a : list) {
      if (a.getAskCeoWriter().getCeoNo() == ceoMemberNo) {
        if (a.getAskNo() == askNo) {
          return a;
        }
      }
    }
    return null;
  }

  @Override
  public void insert(AskBoard askBoard) throws Exception {
    list.add(askBoard);
  }

  @Override
  public void update(AskBoard askBoard) throws Exception {
    for (int i =0; i < list.size(); i++) {
      if (list.get(i).getAskNo() == askBoard.getAskNo()) {
        list.set(i, askBoard);
        return;
      }
    }
  }

  @Override
  public void delete(int no) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAskNo() == no) {
        list.remove(i);
        return;
      }
    }
  }
}
