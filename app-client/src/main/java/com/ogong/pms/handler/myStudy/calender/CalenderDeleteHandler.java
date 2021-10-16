package com.ogong.pms.handler.myStudy.calender;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderDeleteHandler implements Command {

  StudyDao studyDao;

  public CalenderDeleteHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 삭제");

    int[] arry = (int[]) request.getAttribute("studyNocalNo");

    Study myStudy = studyDao.findByNo(arry[0]);

    Calender calender = myStudy.getMyStudyCalender().get(arry[1]);

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 일정 삭제를 취소하였습니다.\n");
      return;
    }
    myStudy.getMyStudyCalender().remove(calender);

    studyDao.update(myStudy);

    System.out.println(" >> 일정이 삭제되었습니다.");
  }

}
