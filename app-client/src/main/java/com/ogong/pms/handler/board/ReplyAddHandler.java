package com.ogong.pms.handler.board;

import java.sql.Date;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.Reply;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class ReplyAddHandler implements Command {

  AskBoardDao askBoardDao;

  public ReplyAddHandler(AskBoardDao askBoardDao) {
    this.askBoardDao = askBoardDao;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println(" ▶ 답변 등록");
    System.out.println();

    int askNo = (int) request.getAttribute("askNo");

    AskBoard askBoard = askBoardDao.findByNo(askNo);

    if (askBoard.getReply() != null) {
      System.out.println(" >> 이미 등록된 답변이 있습니다.");
      return;
    }

    Reply reply = new Reply();
    reply.setReplyTitle(Prompt.inputString(" 제목: "));
    reply.setReplyContent(Prompt.inputString(" 내용: "));
    reply.setReplyAdminWiter(AuthAdminLoginHandler.getLoginAdmin());
    reply.setReplyRegisteredDate(new Date(System.currentTimeMillis()));

    String input = Prompt.inputString("\n 정말 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equals("네")) {
      System.out.println(" >> 등록을 취소하였습니다.");
      return;
    }

    reply.setReplyNo(1);
    askBoard.setReply(reply);

    askBoardDao.update(askBoard);
    System.out.println(" >> 답글이 등록되었습니다.");
  }
}
