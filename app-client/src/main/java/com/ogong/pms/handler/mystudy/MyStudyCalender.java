package com.ogong.pms.handler.mystudy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class MyStudyCalender {

  List<Calender> calenderList;
  List<Study> studyList;

  public MyStudyCalender(List<Calender> calenderList, List<Study> studyList) {
    this.calenderList = calenderList;
    this.studyList = studyList;

    //    Calender testCal = new Calender();
    //    testCal.setMonth(9);
    //    testCal.setDay(10);
    //    testCal.setDayOftheWeek("월");
    //    testCal.setCalenderContent("제발1");
    //    testCal.setImportanceCalender("★★★★★");
    //    testCal.setEndDay(Date.valueOf("2021-12-25"));
    //    calenderList.add(testCal);
    //    studyList.get(1).getMyStudyCalender().add(testCal);
    //
    //    testCal = new Calender();
    //    testCal.setMonth(10);
    //    testCal.setDay(15);
    //    testCal.setDayOftheWeek("월");
    //    testCal.setCalenderContent("제발2");
    //    testCal.setImportanceCalender("★★★");
    //    testCal.setEndDay(Date.valueOf("2021-10-17"));
    //    calenderList.add(testCal);
    //    studyList.get(1).getMyStudyCalender().add(testCal);
    //
    //    testCal = new Calender();
    //    testCal.setMonth(9);
    //    testCal.setDay(13);
    //    testCal.setDayOftheWeek("월");
    //    testCal.setCalenderContent("제발3");
    //    testCal.setImportanceCalender("★");
    //    testCal.setEndDay(Date.valueOf("2021-9-16"));
    //    calenderList.add(testCal);
    //    studyList.get(1).getMyStudyCalender().add(testCal);

  }

  private void addCalender(Study study) {
    System.out.println();
    System.out.println("▶ 일정 등록");

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

    calenderList.add(calender);
    study.getMyStudyCalender().add(calender);

  }

  // 캘린더 목록
  public void listCalender(Study study) {
    System.out.println();
    System.out.println("▶ 일정 목록");
    System.out.println();

    Date now = new Date(System.currentTimeMillis());
    System.out.printf("      << %d월 >>\n", now.getMonth() + 1 );
    System.out.println();

    List<Calender> tempCal = new ArrayList<>();
    for (Calender calender : study.getMyStudyCalender()) {
      if (now.getMonth() + 1 == calender.getMonth()) {
        System.out.printf(
            " 중요도<%s>\n[ %d월 %d일 %s요일 ]\n %s\n",
            calender.getImportanceCalender(),
            calender.getMonth(), 
            calender.getDay(),
            calender.getDayOftheWeek(),
            calender.getCalenderContent());
        tempCal.add(calender);
        System.out.println();
      }
    }

    boolean selectBack = selectCategory(tempCal, study);
    if (!selectBack) {
      return;
    }

    int selectMonth; 
    while (true) {
      selectMonth = Prompt.inputInt(" >> 다른 '월' 일정 보기 : ");
      System.out.println();
      if (selectMonth > 12 || selectMonth < 1) {
        System.out.println(" >> 정확한 '월'을 입력해 주세요.\n");
        continue;
      }
      System.out.printf("      << %d월 >>\n", selectMonth);
      System.out.println();

      List<Calender> selecMontCal = new ArrayList<>();
      for (Calender calender : study.getMyStudyCalender()) {
        if (selectMonth == calender.getMonth()) {
          selecMontCal.add(calender);
          System.out.printf(
              " 중요도<%s> \n[ %d월 %d일 %s요일 ]\n %s\n",
              calender.getImportanceCalender(),
              calender.getMonth(), 
              calender.getDay(),
              calender.getDayOftheWeek(),
              calender.getCalenderContent());
          System.out.println();
        }
      }
      selectBack = selectCategory(selecMontCal, study);
      if (!selectBack) {
        return;
      }

    }
  }

  // 캘린더 상세보기
  private void detailCalender(List<Calender> calenderList, Study study) {
    System.out.println();
    System.out.println("▶ 일정 상세");
    System.out.println();

    int inputDay;
    String inputContent;

    Calender detailCalender = null;
    inputDay = Prompt.inputInt(" 일 : ");
    inputContent = Prompt.inputString(" 내용 : " );
    for (int i = 0; i < calenderList.size(); i++) {
      if (calenderList.get(i).getDay() == inputDay &&
          calenderList.get(i).getCalenderContent().contains(inputContent)) {
        System.out.println();
        detailCalender = calenderList.get(i);
        System.out.printf(" >> 등록일 : %d월 %d일 %s요일\n",
            calenderList.get(i).getMonth(),
            calenderList.get(i).getDay(),
            calenderList.get(i).getDayOftheWeek());
        System.out.printf(" >> 종료일 : %s\n", calenderList.get(i).getEndDay());
        System.out.printf(" >> 내  용 : %s\n", calenderList.get(i).getCalenderContent());
        System.out.printf(" >> 중요도 : %s\n", 
            calenderList.get(i).getImportanceCalender());
        System.out.println();
      }
    }
    if (detailCalender == null) {
      System.out.printf(" '%d일'에 일정이 없습니다.\n", inputDay);
      listCalender(study);
      return;
    }

    System.out.println("---------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 취소");

    int selectNo = Prompt.inputInt("선택> ");
    System.out.println();
    switch (selectNo) {
      case 1: updateCalender(detailCalender); break;
      case 2: deleteCalender(detailCalender, study); break;
      case 3: return;
      default : return;
    }
  }

  private void updateCalender(Calender calender) {
    System.out.println("▶ 일정 수정");
    System.out.println();

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

    String updateImportant = stateImportant();

    if (updateImportant == null) {
      System.out.println(" 입력 오류입니다.");
      return;
    }

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
    calender.setImportanceCalender(updateImportant);
    calender.setEndDay(updateEndDay);

    System.out.println(" >> 일정을 변경하였습니다.\n");
  }

  private void deleteCalender(Calender calender, Study study) {
    System.out.println();
    System.out.println("▶ 일정 삭제");

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 일정 삭제를 취소하였습니다.\n");
      return;
    }
    calenderList.remove(calender);
    study.getMyStudyCalender().remove(calender);
    System.out.println(" >> 일정이 삭제되었습니다.");
    listCalender(study);
  }

  // 등록,상세,재입력 카테고리 선택 메서드
  private boolean selectCategory(List<Calender> calenderList, Study study) {
    if (calenderList.isEmpty()) {
      System.out.println();
      System.out.println(" 등록된 일정이 없습니다.");
      System.out.println("---------------------");
      System.out.println("1. 다른 월");
      System.out.println("2. 등록");
      System.out.println("3. 취소");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : return true;
        case 2 : addCalender(study); break;
        case 3 : return false;
        default : return false;
      }
    }

    if (!calenderList.isEmpty()) {
      System.out.println("---------------------");
      System.out.println("1. 상세");
      System.out.println("2. 다른 월");
      System.out.println("3. 등록");
      System.out.println("4. 취소");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : detailCalender(calenderList, study); break;
        case 2 : return true;
        case 3 : addCalender(study); break;
        case 4 : return false;
        default : return false;
      }
    }
    return true;
  }

  // 일정 중요도 상태 입력 메서드
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
      case 2 : return "★★★★";
      case 3 : return "★★★";
      case 4 : return "★★";
      case 5 : return "★";
    }
    return null;
  }

}


