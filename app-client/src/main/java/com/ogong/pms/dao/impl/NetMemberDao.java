package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.request.RequestAgent;

public class NetMemberDao implements MemberDao {

  RequestAgent requestAgent;

  public NetMemberDao(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void insert(Member member) throws Exception {
    requestAgent.request("member.insert", member);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 등록 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    requestAgent.request("member.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 목록 조회 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(Member.class));
  }

  @Override
  public Member findByNo(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("memberNo", String.valueOf(no));

    requestAgent.request("member.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 상세 조회 실패");
      return null;
    }
    return requestAgent.getObject(Member.class);
  }

  @Override
  public Member findByNickName(String inputNick) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("memberNick", inputNick);

    requestAgent.request("member.selectOnByNickname", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 닉네임 조회 실패");
      return null;
    }
    return requestAgent.getObject(Member.class);
  }

  @Override
  public Member findByEmail(String inputEmail) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("email", inputEmail);

    requestAgent.request("member.selectOneByEmail", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 이메일 조회 실패");
      return null;
    }
    return requestAgent.getObject(Member.class);
  }

  @Override
  public void update(Member member) throws Exception {
    requestAgent.request("member.update", member);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 수정 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public void delete(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("memberNo", String.valueOf(no));

    requestAgent.request("member.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("회원 삭제 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }


}
