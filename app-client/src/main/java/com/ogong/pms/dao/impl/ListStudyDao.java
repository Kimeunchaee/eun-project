package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;

public class ListStudyDao implements StudyDao {

  List<Study> list = new ArrayList<>();

  // --------------- [ 스터디 ] ----------------------------------------

  @Override
  public void insert(Study study) throws Exception {
    list.add(study);
  }

  @Override
  public void update(Study study) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getStudyNo() == study.getStudyNo()) {
        list.set(i, study);
        return;
      }
    }
  }

  @Override
  public void delete(int no) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getStudyNo() == no) {
        return;
      }
    }
  }

  @Override
  public List<Study> findAll() throws Exception {
    return list;
  }

  @Override
  public Study findByNo(int studyinputNo) throws Exception {
    for (Study study : list) {
      if (study.getStudyNo() == studyinputNo) {
        return study;
      }
    }
    return null;
  }

  @Override
  public List<Study> findByKeyword(String keyword) throws Exception {
    ArrayList<Study> searchResult = new ArrayList<>();
    for (Study study : list) {
      if (!study.getArea().contains(keyword) &&
          !study.getSubject().contains(keyword) &&
          !study.getStudyTitle().contains(keyword)) {
        searchResult.add(study);
      }
    }
    return searchResult;
  }

  @Override
  public Study findMyStudy(int memberNo, int studyNo) throws Exception {
    //    Study study = null;
    for (Study s : list) {
      if (s.getStudyNo() == studyNo) {
        if (s.getOwner().getPerNo() == memberNo) {
          //          study = s;
          return s;
        } else {
          for (Member m : s.getMembers()) {
            if(m.getPerNo() == memberNo) {
              //              study = s;
              return s;
            }
          }
        }
      }
    }
    return null;
  }

}
