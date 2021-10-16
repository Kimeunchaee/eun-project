package com.ogong.pms.handler.myStudy.todo;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class ToDoAdd implements Command {

  StudyDao studyDao;

  public ToDoAdd(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  //등록
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 등록");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    Study myStudy = studyDao.findByNo(inputNo);

    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 조회 가능합니다.");
      return;
    }

    if (myStudy.getStudyTitle() == null) {
      System.out.println(" >> 가입된 스터디가 없습니다.");
      return;
    }

    List <ToDo> todoList = myStudy.getMyStudyToDo();
    ToDo todo = new ToDo();

    todo.setTodoContent(Prompt.inputString(" 내용: "));
    todo.setTodoRemark(Prompt.inputString(" 비고: "));
    todo.setTodoStatus(1);
    todo.setTodoDate(new Date(System.currentTimeMillis()));

    String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록을 취소하였습니다.");
      request.getRequestDispatcher("/myStudy/todoList").forward(request);
      return;
    }

    // 마지막 Todo 번호 찾아서 새 Todo 등록시 +1 되도록 기능 구현
    ToDo lastTodo = null;
    if (!todoList.isEmpty()) {
      lastTodo = todoList.get(todoList.size() - 1);
      todo.setTodoNo(lastTodo.getTodoNo() + 1);
    } else {
      todo.setTodoNo(1);
    }
    todoList.add(todo);
    myStudy.setMyStudyToDo(todoList);

    studyDao.update(myStudy);

    System.out.println(" >> 할 일이 등록되었습니다.");
    request.getRequestDispatcher("/myStudy/todoList").forward(request);
  }
}



