package com.ogong.pms.handler.myStudy.calender;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderListHandler implements Command {

  StudyDao studyDao;

  public CalenderListHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 목록");
    System.out.println();

    int no = (int)request.getAttribute("inputNo");

    Study study = studyDao.findByNo(no);

    for (int i = 1; i < 13; i++) {
      System.out.printf("-----[ %d월 ]-----\n",i);
      System.out.println();
      for (Calender calender : study.getMyStudyCalender()) {
        if (i == calender.getMonth()) {
          System.out.printf( 
              "  %s\n >>  %d일 %s요일 \n  %s\n",
              calender.getImportanceCalender(),
              calender.getDay(),
              calender.getDayOftheWeek(),
              calender.getCalenderContent());
          System.out.println();
        }
      }
    }

    request.setAttribute("inputNo", no);
    System.out.println("1. 등록");
    System.out.println("2. 월별");
    System.out.println("0. 이전");
    int selectNo  = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/myStudy/calenderAdd").forward(request); return;
      case 2: request.getRequestDispatcher("/myStudy/calenderMonthList").forward(request); return;
      case 0: return; 
      default : System.out.println(" >>무효한 번호입니다."); return;
    }


  }
}

