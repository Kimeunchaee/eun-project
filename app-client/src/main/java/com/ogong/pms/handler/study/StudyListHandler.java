package com.ogong.pms.handler.study;

import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class StudyListHandler implements Command {

  StudyDao studyDao;

  public StudyListHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 목록");
    System.out.println();

    List<Study> studyList = studyDao.findAll();

    for (Study study : studyList) {

      if (study.getStudyTitle().contains("탈퇴")) {
        System.out.printf(" (%d)\n 스터디명 : %s\n",
            study.getStudyNo(),
            study.getStudyTitle());
        System.out.println();
      }

      else {
        System.out.printf(" (%d) 🌟%d \n [%s] | 분류 : %s | 인원수 : %s/%s명 | 조장 : %s | 대면/비대면 : %s\n",
            study.getStudyNo(),
            study.getBookMarkMember().size(),
            study.getStudyTitle(),
            study.getSubject(),
            study.getMembers().size() + 1,
            study.getNumberOfPeple(),
            study.getOwner().getPerNickname(),
            study.getFace());
        System.out.println();
      }
    }
  }
}