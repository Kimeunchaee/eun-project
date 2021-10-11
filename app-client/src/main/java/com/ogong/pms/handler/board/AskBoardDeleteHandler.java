package com.ogong.pms.handler.board;

import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AskBoardDeleteHandler implements Command {

  AskBoardDao askBoardDao;

  public AskBoardDeleteHandler(AskBoardDao askBoardDao) {
    this.askBoardDao = askBoardDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 문의사항 삭제");
    System.out.println();

    int askNo = (int) request.getAttribute("askNo"); 

    AskBoard askBoard = askBoardDao.findByNo(askNo);

    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      if (AuthPerMemberLoginHandler.getLoginUser().getPerNo() !=
          askBoard.getAskMemberWriter().getPerNo()) {
        System.out.println(" >> 삭제 권한이 없습니다.");
        return;
      }
    }

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      if (AuthCeoMemberLoginHandler.getLoginCeoMember().getCeoNo() !=
          askBoard.getAskCeoWriter().getCeoNo()) {
        System.out.println(" >> 삭제 권한이 없습니다.");
        return;
      }

    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 문의글 삭제를 취소하였습니다.");
      return;
    }

    askBoardDao.delete(askNo);
    System.out.println(" >> 문의글을 삭제하였습니다.");
  }

}







