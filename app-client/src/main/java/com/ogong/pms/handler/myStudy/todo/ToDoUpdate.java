package com.ogong.pms.handler.myStudy.todo;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class ToDoUpdate implements Command {

  StudyDao studyDao;

  public ToDoUpdate(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  //삭제
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 변경");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyTodoNo");

    Study myStudy = studyDao.findByNo(arry[0]);
    ToDo todo = myStudy.getMyStudyToDo().get(arry[1]);

    String todoContent = Prompt.inputString(String.format(" 내용(%s) : ", todo.getTodoContent()));
    String todoRemark = Prompt.inputString(String.format(" 비고(%s) : ", todo.getTodoRemark()));
    int todoStatus = promptStatus(todo.getTodoStatus());


    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경을 취소하였습니다.");
      return;
    }

    todo.setTodoContent(todoContent);
    todo.setTodoStatus(todoStatus);
    todo.setTodoRemark(todoRemark);

    studyDao.update(myStudy);

    System.out.println(" >> 할 일이 변경되었습니다.");
    request.getRequestDispatcher("/myStudy/todoList").forward(request);
  }

  //상태 선택
  private int promptStatus() {
    return promptStatus(-1);
  }

  private int promptStatus(int todoStatus) {
    if (todoStatus == -1) {
      System.out.println();
      System.out.println(" 진행 상황: ");
      System.out.println(" 1: 진행 중");
      System.out.println();

      try{
        return Prompt.inputInt("선택> ");
      } catch(Exception e) {
        System.out.println(" >> 잘못 입력하셨습니다.");
      }

    } else {
      System.out.printf(" 진행 상황(%s) : " , todoStatus);
    }
    return Prompt.inputInt("1. 진행 중 / 2. 완료 > ");
  }
}



