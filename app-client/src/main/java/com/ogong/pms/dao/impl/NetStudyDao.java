package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.request.RequestAgent;

public class NetStudyDao implements StudyDao {

  RequestAgent requestAgent;

  public NetStudyDao (RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }


  // 전체 스터디--------------------------------------------
  @Override
  public List<Study> findAll() throws Exception {    
    requestAgent.request("study.selectList", null);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 목록 조회 실패");
      return null;
    }
    return new ArrayList<>(requestAgent.getObjects(Study.class));
  }

  @Override
  public List<Study> findByKeyword(String keyword) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("keyword", String.valueOf(keyword));

    requestAgent.request("study.selectByKeyword", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 다시 입력해 주세요.");
      return null;
    }
    return new ArrayList<>(requestAgent.getObjects(Study.class));
  }

  @Override
  public Study findByNo(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(no));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디를 찾을 수 없습니다.");
      return null;
    }
    return requestAgent.getObject(Study.class);
  }

  @Override
  public void insert(Study study) throws Exception {
    requestAgent.request("study.insert", study);
    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      throw new Exception(" >> 스터디 저장 실패");
    }
    System.out.println(" >> 스터디가 등록되었습니다.");

  }

  // 내 스터디--------------------------------------------
  @Override
  public Study findByMyStudy(int memberNo, int studyNo) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("memberNo",String.valueOf(memberNo));
    params.put("studyNo", String.valueOf(studyNo));

    requestAgent.request("study.my.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 내 스터디 조회 실패");
      return null;
    }
    return requestAgent.getObject(Study.class);
  }

  @Override
  public void update(Study myStudy) throws Exception {
    requestAgent.request("study.update", myStudy);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 수정 실패");
      return;
    }
    System.out.println(" >> 스터디가 수정되었습니다.");
  }

  @Override
  public void delete(int no) throws Exception {
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(no));

    requestAgent.request("study.delete", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 삭제 실패");
      return;
    }
    System.out.println(" >> 스터디가 삭제 되었습니다.");
  }
}