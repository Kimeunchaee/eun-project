package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.request.RequestAgent;

public class NetAskBoardDao implements AskBoardDao {

  RequestAgent requestAgent;

  public NetAskBoardDao(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public List<AskBoard> findAll() throws Exception {
    requestAgent.request("askBoard.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 목록 조회 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(AskBoard.class));
  }

  @Override
  public AskBoard findByNo(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("no", String.valueOf(no));

    requestAgent.request("askBoard.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      return null;
    }
    return requestAgent.getObject(AskBoard.class);
  }

  @Override
  public AskBoard findByPerAskBoard(int askNo, int perMemberNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("askNo", String.valueOf(askNo));
    params.put("memberNo", String.valueOf(perMemberNo));

    requestAgent.request("askBoard.perMy.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      return null;
    }
    return requestAgent.getObject(AskBoard.class);
  }

  @Override
  public AskBoard findByCeoAskBoard(int askNo, int ceoMemberNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("askNo", String.valueOf(askNo));
    params.put("memberNo", String.valueOf(ceoMemberNo));

    requestAgent.request("askBoard.perMy.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      return null;
    }
    return requestAgent.getObject(AskBoard.class);
  }

  @Override
  public void insert(AskBoard askBoard) throws Exception {
    requestAgent.request("askBoard.insert", askBoard);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("문의글 등록 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void update(AskBoard askBoard) throws Exception {
    requestAgent.request("askBoard.update", askBoard);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 문의글 수정 실패!");
    }
  }

  @Override
  public void delete(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("no", String.valueOf(no));

    requestAgent.request("askBoard.delete", params);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 문의글 삭제 실패.");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }





}
