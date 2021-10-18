package com.ogong.pms.handler.myStudy.calender;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CalenderAddHandler implements Command {

  StudyDao studyDao;
  int calenderNo;

  public CalenderAddHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 등록");

    int no = (int) request.getAttribute("inputNo");

    Study myStudy = studyDao.findByNo(no);

    List<Calender> calenderList = myStudy.getMyStudyCalender();
    Calender calender = new Calender();

    System.out.println();
    System.out.println(" 시작 날짜를 입력해 주세요.\n");

    int month; 
    while (true) {
      month = Prompt.inputInt(" 월(1~12) : ");
      if (month > 12) {
        System.out.println("\n >> 등록할 수 없는 '일'입니다.");
        System.out.println("    다시 등록해 주세요.");
        continue;
      }
      break;
    }
    calender.setMonth(month);

    int day;
    while (true) {
      day = Prompt.inputInt(" 일(1~31) : ");
      if (((month == 2) && (day > 28 || day < 1)) ||
          ((((month < 8 && (month % 2) == 1) && (day > 31 || day < 1)) ||
              ((month < 8 && (month % 2) == 0) && (day > 30 || day < 1))) ||
              (((month >= 8 && (month % 2) == 1) && (day > 30 || day < 1)) ||
                  ((month >= 8 && (month % 2) == 0) && (day > 31 || day < 1))))) {
        System.out.println(" >> 등록할 수 없는 '일'입니다.");
        System.out.println("    다시 등록해 주세요.");
        continue;
      } 
      break;
    }
    calender.setDay(day);

    String inputDay;
    while (true) {
      inputDay = Prompt.inputString(" 요일(예시> 월) : ");
      if (inputDay.equals("월") || inputDay.equals("화") || inputDay.equals("수") ||
          inputDay.equals("목") || inputDay.equals("금") || inputDay.equals("토") ||
          inputDay.equals("일")) {
        calender.setDayOftheWeek(inputDay);
        break;
      }
      else {
        System.out.println(" >> 등록할 수 없는 '요일'입니다.");
      }
    }

    calender.setCalenderContent(Prompt.inputString(" 내용 : "));

    String important = stateImportant();
    if (important == null) {
      System.out.println(" 입력 오류입니다.");
      return;
    }
    calender.setImportanceCalender(important);

    Date inputEndDate;
    while (true) {
      inputEndDate = Prompt.inputDate(" 종료일 : ");
      if ((inputEndDate.getMonth() + 1 < month) || 
          ((inputEndDate.getMonth() + 1 == month) && (inputEndDate.getDate() < day))) {
        System.out.println("\n >> 등록일 이후 날짜를 입력하세요.\n");
        continue;
      }
      break;
    }
    calender.setEndDay(inputEndDate);

    String input = Prompt.inputString("\n 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equals("네")) {
      System.out.println(" >> 등록을 취소하였습니다.");
      return;
    }
    System.out.printf(" '%d'월에 일정이 추가되었습니다.\n",
        calender.getMonth());
    System.out.println();

    // 고유번호+1
    Calender lastCalender = null;
    if (!calenderList.isEmpty()) {
      lastCalender = calenderList.get(calenderList.size() - 1);
      calender.setCalenderNo(lastCalender.getCalenderNo() +1);
    } else {
      calender.setCalenderNo(1);
    }

    calenderList.add(calender);
    myStudy.setMyStudyCalender(calenderList);

    studyDao.update(myStudy);
    request.getRequestDispatcher("/myStudy/calenderList").forward(request); 
    return;

  }

  private String stateImportant() {
    System.out.println(" 중요도 ");
    System.out.println("  1. 매우중요");
    System.out.println("  2. 중요");
    System.out.println("  3. 보통");
    System.out.println("  4. 약간 중요");
    System.out.println("  5. 중요하지 않음");

    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1 : return "★★★★★";
      case 2 : return "★★★★☆";
      case 3 : return "★★★☆☆";
      case 4 : return "★★☆☆☆";
      case 5 : return "★☆☆☆☆";
    }
    return null;
  }
}
