package com.ogong.pms.handler.myStudy.calender;

import java.sql.Date;
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

    Date now = new Date(System.currentTimeMillis());
    System.out.printf("      << %d월 >>\n", now.getMonth() + 1 );
    System.out.println();

    int month = 0;
    for (Calender calender : study.getMyStudyCalender()) {
      if (now.getMonth() + 1 == calender.getMonth()) {
        System.out.printf(
            " 중요도<%s>\n[(%d) %d월 %d일 %s요일 ]\n %s\n",
            calender.getImportanceCalender(),
            calender.getCalenderNo(),
            calender.getMonth(), 
            calender.getDay(),
            calender.getDayOftheWeek(),
            calender.getCalenderContent());
        month = calender.getMonth();
        System.out.println();
      }
    }


    if (!selectCategory(no, month, request)) {
      return;
    }

    int count = 0; 
    while (true) {

      study = studyDao.findByNo(no);

      int selectMonth = Prompt.inputInt(" >> 다른 '월' 일정 보기 : ");
      System.out.println();

      if (selectMonth > 12 || selectMonth < 1) {
        System.out.println(" >> 정확한 '월'을 입력해 주세요.\n");
        continue;
      }

      System.out.printf("      << %d월 >>\n", selectMonth);
      System.out.println();

      for (Calender calender : study.getMyStudyCalender()) {
        if (selectMonth == calender.getMonth()) {
          ;
          System.out.printf(
              " 중요도<%s> \n[(%d) %d월 %d일 %s요일 ]\n %s\n",
              calender.getImportanceCalender(),
              calender.getCalenderNo(),
              calender.getMonth(), 
              calender.getDay(),
              calender.getDayOftheWeek(),
              calender.getCalenderContent());
          count = selectMonth;
          System.out.println();
        }
      }

      if (!selectCategory(no, count, request)) {
        count = 0;
        return;
      }
    }
  }

  private boolean selectCategory(int no, int selectMonth, CommandRequest request) throws Exception {

    int[] arry = {no, selectMonth};

    request.setAttribute("inputNo", arry);

    if (selectMonth == 0) {
      System.out.println();
      System.out.println(" 등록된 일정이 없습니다.");
      System.out.println("---------------------");
      System.out.println("1. 다른 월");
      System.out.println("2. 등록");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : return true;
        case 2 : request.getRequestDispatcher("/myStudy/calenderAdd").forward(request); break;
        default : return false;
      }

    } else {
      System.out.println("---------------------");
      System.out.println("1. 상세");
      System.out.println("2. 다른 월");
      System.out.println("3. 등록");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : request.getRequestDispatcher("/myStudy/calenderDetail").forward(request); break;
        case 2 : return true;
        case 3 : request.getRequestDispatcher("/myStudy/calenderAdd").forward(request); break;
        default : return false;
      }
    }
    return true;
  }

}
