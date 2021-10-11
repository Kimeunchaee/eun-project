package com.ogong.pms.handler.myStudy.calender;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class CalenderDetailHandler implements Command {

  RequestAgent requestAgent;

  public CalenderDetailHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 상세");
    System.out.println();


    int[] arry = (int[]) request.getAttribute("inputNo");

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(arry[0]));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    List<Calender> calenderList = myStudy.getMyStudyCalender();

    int inputNo = Prompt.inputInt(" 번호 > ");

    for (int i = 0; i < calenderList.size(); i++) {
      if (calenderList.get(i).getCalenderNo() == inputNo && 
          calenderList.get(i).getMonth() == arry[1]) {
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
        arry[1] = i;
        inputNo = 0;
        System.out.println();
      }
    }
    if (inputNo != 0) {
      System.out.println(" >> 해당 월에 정확한 번호를 입력해주세요");
      return;
    }

    System.out.println("---------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 취소");

    request.setAttribute("studyNocalNo", arry);

    int selectNo = Prompt.inputInt("선택> ");
    System.out.println();
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/myStudy/calenderUpdate").forward(request); return;
      case 2: request.getRequestDispatcher("/myStudy/calenderDelete").forward(request); return;
      case 3: return;
      default : return;
    }
  }

}
