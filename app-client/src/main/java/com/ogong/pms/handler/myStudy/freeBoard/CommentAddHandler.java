package com.ogong.pms.handler.myStudy.freeBoard;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class CommentAddHandler implements Command {

  RequestAgent requestAgent;

  public CommentAddHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 댓글 작성");

    if (AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 로그인 한 회원만 등록 가능합니다.");
    } else {

      int[] arry = (int[]) request.getAttribute("studyNoFreeNo");

      HashMap<String,String> params = new HashMap<>();
      params.put("studyNo", String.valueOf(arry[0]));

      requestAgent.request("study.selectOne", params);

      if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
        System.out.println(" >> 스터디 상세 오류.");
        return;
      }

      Study myStudy = requestAgent.getObject(Study.class);
      List<FreeBoard> freeBoardList = myStudy.getMyStudyFreeBoard();
      FreeBoard freeBoard = freeBoardList.get(arry[1]);

      List<Comment> arrayComment = new ArrayList<>(freeBoard.getComment());

      Comment comment = new Comment();

      // 마지막 댓글 번호 찾아서 새 댓글 등록시 +1 되도록 기능 구현
      Comment lastComment = null;
      if (!arrayComment.isEmpty()) {
        lastComment = arrayComment.get(arrayComment.size() -1);
        comment.setCommentNo(lastComment.getCommentNo() + 1);
      } else {
        comment.setCommentNo(1);
      }
      comment.setCommentText(Prompt.inputString(" 댓글 내용 : "));
      comment.setCommentWiter(AuthPerMemberLoginHandler.getLoginUser());
      comment.setCommentRegisteredDate(new Date(System.currentTimeMillis()));

      String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 댓글 등록을 취소하였습니다.");
        request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
        return;
      }

      freeBoard.getComment().add(comment);
      freeBoardList.set(arry[1], freeBoard);
      myStudy.setMyStudyFreeBoard(freeBoardList);

      requestAgent.request("study.update", myStudy);

      if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
        System.out.println("댓글 등록 실패!");
        request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
        return;
      }

      System.out.println(" >> 댓글이 등록되었습니다.");
      request.getRequestDispatcher("/myStudy/freeBoardDetail").forward(request);
      return;
    }
  }
}

