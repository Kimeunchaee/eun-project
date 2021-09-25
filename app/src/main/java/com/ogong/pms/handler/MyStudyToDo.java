package com.ogong.pms.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.util.Prompt;

public class MyStudyToDo {

  int ToDoNo = 5;
  List<Study> studyList;
  List<ToDo> toDoList;
  Study study;

  public MyStudyToDo(List<ToDo> toDoList, List<Study> studyList) {
    this.toDoList = toDoList;
    this.studyList = studyList;

    //    ToDo test = new ToDo();
    //    test.setTodoNo(1);
    //    test.setTodoContent("문제집 주문하기");
    //    test.setTodoRemark("자바의 정석");
    //    test.setTodoDate(new Date(System.currentTimeMillis()));
    //    test.setTodoStatus(1);
    //    toDoList.add(test);
    //    studyList.get(0).getMyStudyToDo().add(test);
    //
    //    test = new ToDo();
    //    test.setTodoNo(2);
    //    test.setTodoContent("다음달 시험 일정 확인하기");
    //    test.setTodoRemark("자격증");
    //    test.setTodoDate(new Date(System.currentTimeMillis()));
    //    test.setTodoStatus(1);
    //    toDoList.add(test);
    //    studyList.get(0).getMyStudyToDo().add(test);
    //
    //    test = new ToDo();
    //    test.setTodoNo(3);
    //    test.setTodoContent("장소 예약 리뷰 적어주기");
    //    test.setTodoRemark("에이스터디카페");
    //    test.setTodoDate(new Date(System.currentTimeMillis()));
    //    test.setTodoStatus(1);
    //    toDoList.add(test);
    //    studyList.get(1).getMyStudyToDo().add(test);
    //
    //    test = new ToDo();
    //    test.setTodoNo(4);
    //    test.setTodoContent("내일 점심 샌드위치 사먹기");
    //    test.setTodoRemark("배고프다");
    //    test.setTodoDate(new Date(System.currentTimeMillis()));
    //    test.setTodoStatus(1);
    //    toDoList.add(test);
    //    studyList.get(1).getMyStudyToDo().add(test);

  }

  //등록
  protected void addToDo(Study study) {
    System.out.println();
    System.out.println("▶ To-Do List 등록");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null ) {
      System.out.println(" >> 로그인 한 회원만 조회 가능합니다.");
      return;
    }

    if (study.getStudyTitle() == null) {
      System.out.println(" >> 가입된 스터디가 없습니다.");
      return;
    }

    ToDo todo = new ToDo();

    todo.setTodoContent(Prompt.inputString(" 내용: "));
    todo.setTodoRemark(Prompt.inputString(" 비고: "));
    todo.setTodoStatus(1);
    todo.setTodoDate(new Date(System.currentTimeMillis()));

    String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오)");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록을 취소하였습니다.");
      listToDo(study);
      return;
    }

    todo.setTodoNo(ToDoNo++);
    toDoList.add(todo);
    study.getMyStudyToDo().add(todo);

    System.out.println(" >> 할 일이 등록되었습니다.");
  }

  // 목록
  protected void listToDo(Study study) {
    System.out.println();
    System.out.println("▶ To-Do List 목록");
    System.out.println();

    // 메서드 안에서 선언할때는 초기화해야한다 (null 쓰지 마세요)
    List<ToDo> toDoArrayList = new ArrayList<>(); 

    System.out.println(" >> 진행 중 ");

    int count1 = 0;

    for (ToDo todo : study.getMyStudyToDo()) {
      if (todo.getTodoStatus() == 1) {
        System.out.printf(" %s | %s | 내용 : %s | 비고 : %s | DATE : %s\n", 
            getStatusToDo(todo.getTodoStatus()),
            todo.getTodoNo(),
            todo.getTodoContent(),
            todo.getTodoRemark(),
            todo.getTodoDate());
        System.out.println();
        count1++;
      }
      toDoArrayList.add(todo);
    }

    if (count1 == 0) {
      System.out.println(" ○ | 진행 중인 To-Do List가 없습니다.\n");
    }

    System.out.println(" >> 완료 ");

    int count2 = 0;

    for (ToDo todo : study.getMyStudyToDo()) {
      if (todo.getTodoStatus() == 2) {
        System.out.printf(" %s | %s | 내용 : %s | 비고 : %s | DATE : %s\n", 
            getStatusToDo(todo.getTodoStatus()),
            todo.getTodoNo(),
            todo.getTodoContent(),
            todo.getTodoRemark(),
            todo.getTodoDate());
        System.out.println();
        count2++;
      }
    }

    if (count2 == 0) {
      System.out.println(" ✔ | 완료된 To-Do List가 없습니다.\n");
    }

    System.out.println("---------------------");
    System.out.println("1. 상세");
    System.out.println("2. 등록");
    System.out.println("0. 이전");
    int selete = Prompt.inputInt("선택> ");
    switch (selete) {
      case 1 : detailToDo(toDoArrayList, study); break;
      case 2 : addToDo(study); break;
      default : return;
    }
  }

  //상세
  protected void detailToDo(List<ToDo> toDoArrayList, Study study) {
    System.out.println();
    System.out.println("▶ To-Do List 상세보기");
    System.out.println();

    //ToDo detailArrayToDo = new ToDo();

    while (true) {
      int inputToDoNo = Prompt.inputInt(" 번호 : ");
      System.out.println();

      ToDo toDo = findBytodoNo(inputToDoNo, toDoArrayList);

      if (toDo == null) {
        System.out.println(" >> 해당 번호의 To-Do가 없습니다.");
        System.out.println();
        return;
      }

      System.out.printf(" >> 내용 : %s\n", toDo.getTodoContent());
      System.out.printf(" >> 비고 : %s\n", toDo.getTodoRemark());
      if (toDo.getTodoStatus() == 1) {
        System.out.println(" >> 상태 : 진행 중");
      } else if (toDo.getTodoStatus() == 2) {
        System.out.println(" >> 상태 : 완료");
      }
      System.out.printf(" >> DATE : %s\n", toDo.getTodoDate());

      System.out.println("\n----------------------");
      System.out.println("1. 수정");
      System.out.println("2. 삭제");
      System.out.println("0. 이전");
      int selete = Prompt.inputInt("선택> ");
      switch (selete) {
        case 1 : updateToDo(toDo, study); return;
        case 2 : deleteToDo(toDo, study); return;
        case 0 : listToDo(study); return;
        default : return;
      }
    }
  }

  // 수정
  protected void updateToDo(ToDo toDo, Study study) {
    System.out.println();
    System.out.println("▶ To-Do List 변경");
    System.out.println();

    List<ToDo> todoComplete = new ArrayList<>();

    String todoContent = Prompt.inputString(String.format(" 내용(%s) : ", toDo.getTodoContent()));
    String todoRemark = Prompt.inputString(String.format(" 비고(%s) : ", toDo.getTodoRemark()));
    int todoStatus = promptStatus(toDo.getTodoStatus());


    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경을 취소하였습니다.");
      return;
    }

    toDo.setTodoContent(todoContent);
    toDo.setTodoStatus(todoStatus);
    toDo.setTodoRemark(todoRemark);

    todoComplete.add(toDo);

    System.out.println(" >> 할 일이 변경되었습니다.");
  }


  //삭제
  protected void deleteToDo(ToDo toDo, Study study) {
    System.out.println();
    System.out.println("▶ To-Do List 삭제");
    System.out.println();

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오)");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소하였습니다.");
      return;
    }

    toDoList.remove(toDo);
    study.getMyStudyToDo().remove(toDo);

    System.out.println(" >> To-Do을 삭제하였습니다.");
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
      System.out.printf(" 진행 상황(%s):", getStatusToDo(todoStatus));
    }
    System.out.println();
    System.out.println(" 1: 진행 중");
    System.out.println(" 2: 완료");
    return Prompt.inputInt(" 선택> ");
  }



  //상태 꺼내기
  private String getStatusToDo(int todoStatus) {
    switch (todoStatus) {
      case 1: return "○";
      case 2: return "✔";
      default: return null;
    }
  }

  //번호로 객체 찾기
  private ToDo findBytodoNo(int todoNo, List<ToDo> toDoArrayList) {
    for (ToDo todo : toDoArrayList) {
      if (todo.getTodoNo() == todoNo) {
        return todo;
      }
    }
    return null;
  }
}



