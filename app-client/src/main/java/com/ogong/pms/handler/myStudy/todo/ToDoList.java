package com.ogong.pms.handler.myStudy.todo;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class ToDoList implements Command {

  RequestAgent requestAgent;

  public ToDoList(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  //등록
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 목록");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 출력 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);
    List <ToDo> todoList = myStudy.getMyStudyToDo();

    if (todoList.isEmpty()) {
      System.out.println(" 등록된 TodoList가 없습니다.");
    }
    if (!todoList.isEmpty()) {
      System.out.println(" >> 진행 중 ");

      int countProgressing = 0;

      for (ToDo todo : todoList) {
        if (todo.getTodoStatus() == ToDo.PROGRESSING) {
          System.out.printf(" %s | %s | 내용 : %s | 비고 : %s | DATE : %s\n", 
              getStatusToDo(todo.getTodoStatus()),
              todo.getTodoNo(),
              todo.getTodoContent(),
              todo.getTodoRemark(),
              todo.getTodoDate());
          System.out.println();
          countProgressing++;
        }
      }

      if (countProgressing == 0) {
        System.out.println(" ○ | 진행 중인 To-Do List가 없습니다.\n");
      }

      System.out.println(" >> 완료 ");

      int countComplete = 0;

      for (ToDo todo : todoList) {
        if (todo.getTodoStatus() == ToDo.COMPLETE) {
          System.out.printf(" %s | %s | 내용 : %s | 비고 : %s | DATE : %s\n", 
              getStatusToDo(todo.getTodoStatus()),
              todo.getTodoNo(),
              todo.getTodoContent(),
              todo.getTodoRemark(),
              todo.getTodoDate());
          System.out.println();
          countComplete++;
        }
      }

      if (countComplete == 0) {
        System.out.println(" ✔ | 완료된 To-Do List가 없습니다.\n");
      }
    }

    System.out.println("---------------------");
    System.out.println("1. 상세");
    System.out.println("2. 등록");
    System.out.println("0. 이전");
    int selete = Prompt.inputInt("선택> ");
    switch (selete) {
      case 1 : request.getRequestDispatcher("/myStudy/todoDetail").forward(request); return;
      case 2 : request.getRequestDispatcher("/myStudy/todoAdd").forward(request); return;
      default : return;
    }
  }

  //상태 꺼내기
  private String getStatusToDo(int todoStatus) {
    switch (todoStatus) {
      case 1: return "○";
      case 2: return "✔";
      default: return null;
    }
  }
}

