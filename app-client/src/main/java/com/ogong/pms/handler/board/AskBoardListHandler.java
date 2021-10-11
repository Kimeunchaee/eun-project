package com.ogong.pms.handler.board;

import java.util.List;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class AskBoardListHandler implements Command {

  AskBoardDao askBoardDao;

  public AskBoardListHandler(AskBoardDao askBoardDao) {
    this.askBoardDao = askBoardDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 문의사항 목록");
    System.out.println();

    List<AskBoard> askBoardList = askBoardDao.findAll();

    if (askBoardList == null) {
      System.out.println(" >> 등록된 글이 없습니다.");
      return;
    }

    for (AskBoard askBoard : askBoardList) {

      // 기업
      if (askBoard.getAskMemberWriter().getPerNickname() == null) {

        if (askBoard.getAskStatus() == 1) {
          System.out.printf("\n (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askBoard.getAskNo(), 
              askBoard.getAskTitle(), 
              askBoard.getAskCeoWriter().getCeoBossName(),
              askBoard.getAskRegisteredDate(),
              askBoard.getAskVeiwCount());
        }

        else if (askBoard.getAskStatus() == 2) {
          System.out.printf("\n (%d)\n 작성자 : %s\n", 
              askBoard.getAskNo(), 
              askBoard.getAskCeoWriter().getCeoBossName());
          System.out.println(" 비밀글입니다.");
        }

        if (askBoard.getReply() != null) {
          System.out.println(" 📖 > 등록된 답변이 있습니다.");
        } else {
          System.out.println(" 📕 > 등록된 답변이 없습니다.");
        }
      }

      // 개인
      else if (askBoard.getAskCeoWriter().getCeoBossName() == null) {

        if (askBoard.getAskStatus() == 1) {
          System.out.printf("\n (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askBoard.getAskNo(), 
              askBoard.getAskTitle(), 
              askBoard.getAskMemberWriter().getPerNickname(),
              askBoard.getAskRegisteredDate(),
              askBoard.getAskVeiwCount());
        }

        else if (askBoard.getAskStatus() == 2) {
          System.out.printf("\n (%d)\n 작성자 : %s\n", 
              askBoard.getAskNo(), 
              askBoard.getAskMemberWriter().getPerNickname());
          System.out.println();
          System.out.println(" 비밀글입니다.");
        }

        if (askBoard.getReply() != null) {
          System.out.println(" 📖 > 등록된 답변이 있습니다.");
        } else {
          System.out.println(" 📕 > 등록된 답변이 없습니다.");
        }
      }

    }
  }
}
