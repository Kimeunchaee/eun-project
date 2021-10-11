package com.ogong.pms.handler.board;

import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AskBoardPerMyDetailHandler implements Command {

  AskBoardDao askBoardDao; 

  public AskBoardPerMyDetailHandler(AskBoardDao askBoardDao) {
    this.askBoardDao = askBoardDao;
  }

  @SuppressWarnings("unlikely-arg-type")
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 문의사항 상세");
    System.out.println();

    int askNo = Prompt.inputInt(" 번호 : ");

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    AskBoard  askBoard = askBoardDao.findByPerAskBoard(askNo, member.getPerNo());

    if (askBoard == null) {
      System.out.println(" >> 해당 번호의 문의글이 없습니다. ");
      return;
    }

    System.out.println();
    System.out.printf(" (%d)\n", askBoard.getAskNo());
    System.out.printf(" [%s]\n", askBoard.getAskTitle());
    System.out.printf(" >> 내용 : %s\n", askBoard.getAskContent());
    System.out.printf(" >> 작성자 : %s\n", askBoard.getAskMemberWriter().getPerNickname());
    System.out.printf(" >> 작성일 : %s\n", askBoard.getAskRegisteredDate());
    askBoard.setAskVeiwCount(askBoard.getAskVeiwCount() + 1);
    System.out.printf(" >> 조회수 : %d\n", askBoard.getAskVeiwCount());
    System.out.println("---------------------");
    if (askBoard.getReply() != null) {
      request.setAttribute("askNo", askNo);
      request.getRequestDispatcher("/reply/detail").forward(request); 

    } else {
      System.out.println(" >> 등록된 답변이 없습니다.");
    }

    request.setAttribute("askNo", askNo);
    System.out.println("\n---------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : request.getRequestDispatcher("/askBoard/update").forward(request); return;
      case 2 : request.getRequestDispatcher("/askBoard/delete").forward(request); return;
      default : return;
    }
  }
}
