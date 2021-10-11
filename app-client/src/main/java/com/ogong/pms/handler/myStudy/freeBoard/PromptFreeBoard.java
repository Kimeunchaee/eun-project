package com.ogong.pms.handler.myStudy.freeBoard;

import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.request.RequestAgent;

public class PromptFreeBoard {

  protected RequestAgent requestAgent;

  public PromptFreeBoard(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  public void printComments(FreeBoard freeComment) throws Exception {
    System.out.println();
    System.out.println("============= 댓글 =============");

    int countFreeBoard = 0;

    for (Comment comment : freeComment.getComment()) {
      System.out.printf(" (%d) | 내용 : %s | 작성자 : %s | 등록일 : %s\n",
          comment.getCommentNo(),
          comment.getCommentText(),
          comment.getCommentWiter().getPerNickname(),
          comment.getCommentRegisteredDate());
      countFreeBoard++;
    }

    if (countFreeBoard == 0) {
      System.out.println(" >> 등록된 댓글이 없습니다.");
    }
  }

}