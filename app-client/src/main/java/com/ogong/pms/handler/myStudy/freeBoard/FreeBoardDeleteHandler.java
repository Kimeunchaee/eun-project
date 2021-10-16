package com.ogong.pms.handler.myStudy.freeBoard;

import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class FreeBoardDeleteHandler implements Command {

  StudyDao studyDao;

  public FreeBoardDeleteHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 게시글 삭제");
    System.out.println();

    int[] arry = (int[]) request.getAttribute("studyNoFreeNo");

    Study myStudy = studyDao.findByNo(arry[0]);

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

    studyDao.update(myStudy);

    System.out.println(" >> 게시글이 삭제되었습니다.");
    request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
    return;
  }
}


