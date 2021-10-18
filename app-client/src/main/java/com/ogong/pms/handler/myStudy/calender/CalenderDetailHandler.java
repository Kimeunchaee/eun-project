package com.ogong.pms.handler.myStudy.calender;

import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderDetailHandler implements Command {

  StudyDao studyDao;

  public CalenderDetailHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 상세");
    System.out.println();

    int[] arr = (int[]) request.getAttribute("arryNo");

    Study myStudy = studyDao.findByNo(arr[0]);

    List<Calender> calenderList = myStudy.getMyStudyCalender();

    while (true) {
      int inputNo  = Prompt.inputInt(" 번호 > ");
      if (inputNo == 0) {
        request.getRequestDispatcher("/myStudy/calenderList").forward(request); return;
      }
      for (int i = 0; i < calenderList.size(); i++) {
        if (calenderList.get(i).getCalenderNo() == inputNo && 
            calenderList.get(i).getMonth() == arr[1]) {
          System.out.println();
          System.out.println("["+calenderList.get(i).getCalenderNo()+"]");
          System.out.printf(" >> 등록일 : %d월 %d일 %s요일\n",
              calenderList.get(i).getMonth(),
              calenderList.get(i).getDay(),
              calenderList.get(i).getDayOftheWeek());
          System.out.printf(" >> 종료일 : %s\n", calenderList.get(i).getEndDay());
          System.out.printf(" >> 내  용 : %s\n", calenderList.get(i).getCalenderContent());
          System.out.printf(" >> 중요도 : %s\n", 
              calenderList.get(i).getImportanceCalender());
          arr[1] = i;
          inputNo = 0;
          System.out.println();
        }
      }
      if (inputNo != 0) {
        System.out.println(" >> 해당 월에 정확한 번호를 입력해주세요");
        System.out.println(" 이전 > 0");
        continue;
      }
      break;
    }

    System.out.println("---------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("0. 이전");

    request.setAttribute("studyNocalNo", arr);

    int selectNo = Prompt.inputInt("선택> ");
    System.out.println();
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/myStudy/calenderUpdate").forward(request); return;
      case 2: request.getRequestDispatcher("/myStudy/calenderDelete").forward(request); return;
      case 0: request.getRequestDispatcher("/myStudy/calenderList").forward(request); return;
      default : System.out.println(" >> 무효한 번호입니다."); return;
    }
  }

}
