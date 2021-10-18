package com.ogong.pms.handler.myStudy.calender;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderMonthListHandler implements Command {

  StudyDao studyDao;

  public CalenderMonthListHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 월별 목록");
    System.out.println();

    int no = (int)request.getAttribute("inputNo");

    Study study = studyDao.findByNo(no);

    int selectMonth = Prompt.inputInt(" 월> ");
    if (selectMonth < 1 || selectMonth > 12) {
      System.out.println(" 정확한 월을 입력해주세요.");
      return;
    }

    int count = 0;
    for (Calender calender : study.getMyStudyCalender()) {
      if (selectMonth == calender.getMonth()) {
        System.out.printf("      << %d월 >>\n", selectMonth);
        System.out.println();
        System.out.printf(
            " 중요도<%s> \n[(%d) %d월 %d일 %s요일 ]\n %s\n",
            calender.getImportanceCalender(),
            calender.getCalenderNo(),
            calender.getMonth(), 
            calender.getDay(),
            calender.getDayOftheWeek(),
            calender.getCalenderContent());
        System.out.println();
        count ++;
      } 
    }
    if (count == 0) {
      System.out.println(" >> 등록된 일정이 없습니다.");
      request.getRequestDispatcher("/myStudy/calenderList").forward(request); 
      return;
    }

    int[] arr = {no, selectMonth};
    request.setAttribute("arryNo", arr);
    System.out.println("1. 다른 월");
    System.out.println("2. 상세");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/myStudy/calenderMonthList").forward(request); return;
      case 2: request.getRequestDispatcher("/myStudy/calenderDetail").forward(request); return;
      case 0: request.getRequestDispatcher("/myStudy/calenderList").forward(request); return;
      default : System.out.println(" >> 무효한 번호입니다."); return;
    }


  }
}