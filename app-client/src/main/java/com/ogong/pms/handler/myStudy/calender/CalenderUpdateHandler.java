package com.ogong.pms.handler.myStudy.calender;

import java.sql.Date;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderUpdateHandler implements Command {

  StudyDao studyDao;

  public CalenderUpdateHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println("▶ 일정 수정");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyNocalNo");

    Study myStudy = studyDao.findByNo(arry[0]);
    Calender calender = myStudy.getMyStudyCalender().get(arry[1]);

    int updateMonth;
    while (true) {
      updateMonth = Prompt.inputInt(" 월(" + calender.getMonth()  + ") : ");
      if (updateMonth > 12 || updateMonth < 1) {
        System.out.println(" >> 등록할 수 없는 '월'입니다.");
        System.out.println("    다시 등록해 주세요.");
        continue;
      }
      break;
    }

    int updateDay;
    while (true) {
      updateDay = Prompt.inputInt(" 일(" + calender.getDay() + ") : ");
      if (((updateMonth == 2) && (updateDay > 28 || updateDay < 1)) ||
          ((((updateMonth < 8 && (updateMonth % 2) == 1) && (updateDay > 31 || updateDay < 1)) ||
              ((updateMonth < 8 && (updateMonth % 2) == 0) && (updateDay > 30 || updateDay < 1))) ||
              (((updateMonth >= 8 && (updateMonth % 2) == 1)
                  && (updateDay > 30 || updateDay < 1)) ||
                  ((updateMonth >= 8 && (updateMonth % 2) == 0)
                      && (updateDay > 31 || updateDay < 1))))) {
        System.out.println(" >> 등록할 수 없는 '일'입니다.");
        System.out.println("    다시 등록해 주세요.");
        continue;
      } 
      break;
    }

    String updateDayOfTheWeek;
    while (true) {
      updateDayOfTheWeek = Prompt.inputString(" 요일(" + calender.getDayOftheWeek() + ") : ");
      if (updateDayOfTheWeek.equals("월") || updateDayOfTheWeek.equals("화") 
          ||updateDayOfTheWeek.equals("수") || updateDayOfTheWeek.equals("목") 
          || updateDayOfTheWeek.equals("금") || updateDayOfTheWeek.equals("토") ||
          updateDayOfTheWeek.equals("일")) {
        calender.setDayOftheWeek(updateDayOfTheWeek);
        break;
      }
      else {
        System.out.println("\n >> 등록할 수 없는 '요일'입니다.\n");
      }
    }   

    String updateContent = Prompt.inputString(" 내용(" + calender.getCalenderContent() + ") : ");

    //    String updateImportant = stateImportant();
    //
    //    if (updateImportant == null) {
    //      System.out.println(" 입력 오류입니다.");
    //      return;
    //    }

    Date updateEndDay; 
    while (true) {
      updateEndDay =  Prompt.inputDate(" 종료일(" + calender.getEndDay() + ") : ");
      if ((updateEndDay.getMonth() + 1 < updateMonth) || 
          ((updateEndDay.getMonth() + 1 == updateMonth) && (updateEndDay.getDate() < updateDay))) {
        System.out.println("\n >> 등록일 이후 날짜를 입력하세요.\n");
        continue;
      }
      break;
    }

    String input = Prompt.inputString("\n 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 일정 변경이 취소되었습니다.\n");
      return;
    }

    calender.setMonth(updateMonth);
    calender.setDay(updateDay);
    calender.setDayOftheWeek(updateDayOfTheWeek);
    calender.setCalenderContent(updateContent);
    //calender.setImportanceCalender(updateImportant);
    calender.setEndDay(updateEndDay);

    studyDao.update(myStudy);

    System.out.println(" >> 일정을 수정하였습니다.\n");
  }

}
