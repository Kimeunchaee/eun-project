package com.ogong.pms.handler.board;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AskBoardAddHandler implements Command {

  AskBoardDao askBoardDao;

  public AskBoardAddHandler(AskBoardDao askBoardDao) {
    this.askBoardDao = askBoardDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 문의사항");
    System.out.println();

    AskBoard askBoard = new AskBoard();

    List<AskBoard> askBoardList = askBoardDao.findAll();

    int statusNo = 0;

    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      askBoard.setAskTitle(Prompt.inputString(" 제목 : "));
      askBoard.setAskContent(Prompt.inputString(" 내용 : "));
      askBoard.setAskMemberWriter(AuthPerMemberLoginHandler.getLoginUser());
      askBoard.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt("\n 1: 공개 / 2: 비공개 > ");
          System.out.println();
          if (statusNo >= 3) {
            System.out.println(" >> 번호를 다시 입력하세요.\n");
            continue;
          }
          else if ((statusNo > 0) && (statusNo < 3)) {
            String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
            if (!input.equalsIgnoreCase("네")) {
              System.out.println(" >> 문의글 등록을 취소하였습니다.");
              return;
            }  

            // 마지막 고유번호를 찾아서 신규 등록시 +1 되도록 기능 구현
            AskBoard lastAskBoard = null;
            if (!askBoardList.isEmpty()) {
              lastAskBoard = askBoardList.get(askBoardList.size() - 1);
              askBoard.setAskNo(lastAskBoard.getAskNo() + 1);
            } else {
              askBoard.setAskNo(1);
            }
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(" >> 번호만 입력 가능합니다.\n");
          continue;
        }
        break;
      } 

      askBoard.setAskStatus(statusNo);
    }

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      askBoard.setAskTitle(Prompt.inputString(" 제목 : "));
      askBoard.setAskContent(Prompt.inputString(" 내용 : "));
      askBoard.setAskCeoWriter(AuthCeoMemberLoginHandler.getLoginCeoMember());
      askBoard.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt("\n 1: 공개 / 2: 비공개 > ");
          System.out.println();
          if (statusNo >= 3) {
            System.out.println(" >> 번호를 다시 입력하세요.\n");
            continue;
          }
          else if ((statusNo > 0) && (statusNo < 3)) {
            String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
            if (!input.equalsIgnoreCase("네")) {
              System.out.println(" >> 문의글 등록을 취소하였습니다.");
              return;
            }     
            AskBoard lastAskBoard = null;
            if (!askBoardList.isEmpty()) {
              lastAskBoard = askBoardList.get(askBoardList.size() - 1);
              askBoard.setAskNo(lastAskBoard.getAskNo() + 1);
            } else {
              askBoard.setAskNo(1);
            }
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(" >> 번호만 입력 가능합니다.\n");
          continue;
        }
        break;
      } 

      askBoard.setAskStatus(statusNo);
    }

    if (statusNo == 0) {
      System.out.println(" >> 이전 화면으로 돌아갑니다.");
      return;
    } 

    else if ((statusNo > 0) && (statusNo < 3)) {
      askBoardDao.insert(askBoard);
      System.out.println(" >> 문의글이 등록되었습니다.");
    }
  }
}
