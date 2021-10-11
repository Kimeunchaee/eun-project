package com.ogong.pms.handler.myStudy.todo;

import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class ToDoDelete implements Command {

  RequestAgent requestAgent;

  public ToDoDelete(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  //삭제
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 삭제");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyTodoNo");
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(arry[0]));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 출력 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);
    ToDo todo = myStudy.getMyStudyToDo().get(arry[1]);

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오)");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소하였습니다.");
      request.getRequestDispatcher("/myStudy/todoList").forward(request);
      return;
    }

    myStudy.getMyStudyToDo().remove(todo);

    requestAgent.request("study.update", myStudy);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 해당 스터디에서 삭제 오류");
      return;
    }

    System.out.println(" >> To-Do를 삭제하였습니다.");
    request.getRequestDispatcher("/myStudy/todoList").forward(request);
  }
}



