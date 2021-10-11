package com.ogong.pms.handler.myStudy.calender;

import java.util.HashMap;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class CalenderDeleteHandler implements Command {

  RequestAgent requestAgent;

  public CalenderDeleteHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 삭제");

    int[] arry = (int[]) request.getAttribute("studyNocalNo");

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(arry[0]));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    Calender calender = myStudy.getMyStudyCalender().get(arry[1]);

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 일정 삭제를 취소하였습니다.\n");
      return;
    }
    myStudy.getMyStudyCalender().remove(calender);

    requestAgent.request("study.update", myStudy);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" 일정 삭제 오류");
      return;
    }

    System.out.println(" >> 일정이 삭제되었습니다.");
  }

}
