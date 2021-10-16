package com.ogong.pms.handler.myStudy.todo;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class ToDoDelete implements Command {

  StudyDao studyDao;

  public ToDoDelete(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  //삭제
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 삭제");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyTodoNo");

    Study myStudy = studyDao.findByNo(arry[0]);
    ToDo todo = myStudy.getMyStudyToDo().get(arry[1]);

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오)");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소하였습니다.");
      request.getRequestDispatcher("/myStudy/todoList").forward(request);
      return;
    }

    myStudy.getMyStudyToDo().remove(todo);

    studyDao.update(myStudy);

    System.out.println(" >> To-Do를 삭제하였습니다.");
    request.getRequestDispatcher("/myStudy/todoList").forward(request);
  }
}



