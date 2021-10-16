package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.Study;

public interface StudyDao {
  List<Study> findAll() throws Exception;
  List<Study> findByKeyword(String keyword) throws Exception;
  Study findByNo(int no) throws Exception;
  Study findByMyStudy(int memberNo, int studyNo) throws Exception;
  void insert(Study study) throws Exception;
  void update(Study myStudy) throws Exception;
  void delete(int no) throws Exception;
}