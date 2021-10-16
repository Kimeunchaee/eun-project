package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.request.RequestAgent;

public class NetStudyDao implements StudyDao {

  RequestAgent requestAgent;

  public NetStudyDao(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void insert(Study study) throws Exception {
    requestAgent.request("study.insert", study);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 등록 실패");
      return;
    }
  }

  // 마이 스터디에서 업데이트
  @Override
  public void update(Study study) throws Exception {
    requestAgent.request("study.update", study);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 수정 실패!");
    }

  }

  @Override
  public void delete(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(no));

    requestAgent.request("study.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 삭제 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
  }

  @Override
  public List<Study> findAll() throws Exception {
    requestAgent.request("study.selectList", null);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 목록 조회 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(Study.class));
  }

  @Override
  public Study findByNo(int studyinputNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(studyinputNo));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 선택 실패");
      return null;
    }
    return requestAgent.getObject(Study.class);
  }

  @Override
  public List<Study> findByKeyword(String keyword) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("keyword", keyword);

    requestAgent.request("study.selectByKeyword", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 검색 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    return new ArrayList<>(requestAgent.getObjects(Study.class));
  }

  // 내 스터디에서 찾기 - MyStudyDetail
  @Override
  public Study findMyStudy(int memberNo, int studyNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("memberNo",String.valueOf(memberNo));
    params.put("studyNo", String.valueOf(studyNo));

    requestAgent.request("study.my.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("내 스터디 찾기 실패");
      throw new Exception(requestAgent.getObject(String.class));
    }
    // 이거 빼야 하는 건지 모르겠음
    return requestAgent.getObject(Study.class);
  }

}
