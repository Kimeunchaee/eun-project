package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.Study;

public interface StudyDao {

  // --------------- [ 스터디 ] ----------------------------------------
  void insert(Study study) throws Exception;
  void update(Study study) throws Exception;
  void delete(int no) throws Exception;
  List<Study> findAll() throws Exception;
  Study findByNo(int studyinputNo) throws Exception;
  List<Study> findByKeyword(String keyword) throws Exception;
  Study findMyStudy(int memberNo, int studyNo) throws Exception;

}
