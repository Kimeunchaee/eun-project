package com.ogong.pms.handler.board;

import com.ogong.pms.domain.AskBoard;

public class ReplyDetailHandler {

  public void detailReply(AskBoard askBoard) {
    System.out.println();
    System.out.println(" ▶ 답변 ");
    System.out.println();

    System.out.printf(" (%d)\n", askBoard.getReply().getReplyNo());
    System.out.printf(" [%s]\n", askBoard.getReply().getReplyTitle());
    System.out.printf(" >> 내용 : %s\n", askBoard.getReply().getReplyContent());
    System.out.printf(" >> 작성자 : %s\n", 
        askBoard.getReply().getReplyAdminWiter().getMasterNickname());
    System.out.printf(" >> 작성일 : %s\n", askBoard.getReply().getReplyRegisteredDate());
  }
}
