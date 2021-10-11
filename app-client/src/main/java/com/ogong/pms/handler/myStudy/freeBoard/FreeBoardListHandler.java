package com.ogong.pms.handler.myStudy.freeBoard;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class FreeBoardListHandler implements Command {

  RequestAgent requestAgent;

  public FreeBoardListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 게시글 목록");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo",String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    List<FreeBoard> freeBoardList = myStudy.getMyStudyFreeBoard();

    for (FreeBoard freeBoard : freeBoardList) {
      System.out.printf(
          " (%d)\n 제목 : %s\n 내용 : %s\n 첨부파일 : %s\n 작성자 : %s\n 조회수 : %s\n 작성일 : %s\n"
              + " 댓글수 : %d\n",
              freeBoard.getFreeBoardNo(), 
              freeBoard.getFreeBoardTitle(),
              freeBoard.getFreeBoardContent(),
              freeBoard.getFreeBoardAtcFile(),
              freeBoard.getFreeBoardWriter().getPerNickname(),
              freeBoard.getFreeBoardViewcount(),
              freeBoard.getFreeBoardRegisteredDate(),
              freeBoard.getComment().size());
      System.out.println();
    }

    if (!freeBoardList.isEmpty()) {
      System.out.println("---------------------");
      System.out.println("1. 상세");
      System.out.println("2. 등록");
      System.out.println("0. 이전");
      int selete = Prompt.inputInt("선택> ");
      switch (selete) {
        case 1 :request.getRequestDispatcher("/myStudy/freeBoardDetail").forward(request); return;
        case 2 : request.getRequestDispatcher("/myStudy/freeBoardAdd").forward(request); return;
        default : return;
        //        default :request.getRequestDispatcher("/myStudy/detail").forward(request); return;
      }
    }

    if (freeBoardList.isEmpty()) {
      System.out.println(" >> 등록된 게시글이 없습니다");
      System.out.println("---------------------");
      System.out.println("1. 등록");
      System.out.println("0. 이전");
      int selete = Prompt.inputInt("선택> ");
      switch (selete) {
        case 1 : request.getRequestDispatcher("/myStudy/freeBoardAdd").forward(request); return;
        default : return;
        //default :request.getRequestDispatcher("/myStudy/detail").forward(request); return;
      }
    }
  }
}


