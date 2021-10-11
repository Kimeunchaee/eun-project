package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.request.RequestAgent;

public class NetCeoMemberDao implements CeoMemberDao {

  RequestAgent requestAgent;

  public NetCeoMemberDao(RequestAgent requestAgent){
    this.requestAgent = requestAgent;
  }

  @Override
  public List<CeoMember> findAll() throws Exception {
    requestAgent.request("ceoMember.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 목록 조회 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(CeoMember.class));
  }

  @Override
  public CeoMember findByNo(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("ceoMemberNo", String.valueOf(no));

    requestAgent.request("ceoMember.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 상세 조회 실패");
      return null;
    }
    return requestAgent.getObject(CeoMember.class);
  }

  @Override
  public CeoMember findByNickName(String inputNick) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("ceoMemberNick", inputNick);

    requestAgent.request("ceoMember.selectOneByNickname", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 닉네임 조회 실패");
      return null;
    }
    return requestAgent.getObject(CeoMember.class);
  }

  @Override
  public CeoMember findByEmail(String inputEmail) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("ceoMemberEmail", inputEmail);

    requestAgent.request("ceoMember.selectOneByEmail", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 이메일 조회 실패");
      return null;
    }
    return requestAgent.getObject(CeoMember.class);
  }

  @Override
  public void insert(CeoMember ceoMember) throws Exception {
    requestAgent.request("ceoMember.insert", ceoMember);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 등록 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void update(CeoMember ceoMember) throws Exception {
    requestAgent.request("ceoMember.update", ceoMember);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 수정 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void delete(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("ceoMemberNo", String.valueOf(no));

    requestAgent.request("ceoMember.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("기업 회원 삭제 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }


}
