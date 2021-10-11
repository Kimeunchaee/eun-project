package com.ogong.pms.handler.myStudy.freeBoard;

import java.util.HashMap;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class FreeBoardDeleteHandler implements Command {

  RequestAgent requestAgent;

  public FreeBoardDeleteHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 게시글 삭제");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyNoFreeNo");
    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(arry[0]));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    FreeBoard freeBoard = myStudy.getMyStudyFreeBoard().get(arry[1]);

    if (freeBoard.getFreeBoardWriter().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 삭제 권한이 없습니다.");
      request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소하였습니다.");
      request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
      return;
    }

    myStudy.getMyStudyFreeBoard().remove(freeBoard);

    requestAgent.request("study.update", myStudy);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" 게시글 삭제 오류");
      return;
    }

    System.out.println(" >> 게시글이 삭제되었습니다.");
    request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
    return;
  }
}


