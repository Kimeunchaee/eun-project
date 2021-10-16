package com.ogong.pms.handler.myStudy.freeBoard;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class FreeBoardAddHandler implements Command {

  StudyDao studyDao;

  public FreeBoardAddHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 게시글 작성");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    Study myStudy = studyDao.findByNo(inputNo);

    List<FreeBoard> freeBoardList = myStudy.getMyStudyFreeBoard();

    FreeBoard freeBoard = new FreeBoard();

    freeBoard.setFreeBoardTitle(Prompt.inputString(" 제목 : "));
    freeBoard.setFreeBoardContent(Prompt.inputString(" 내용 : "));
    freeBoard.setFreeBoardAtcFile(Prompt.inputString(" 첨부파일 : "));
    freeBoard.setFreeBoardWriter(member);
    freeBoard.setFreeBoardViewcount(freeBoard.getFreeBoardViewcount());
    freeBoard.setComment(new ArrayList<>());
    freeBoard.setFreeBoardRegisteredDate(new Date(System.currentTimeMillis()));

    String input = Prompt.inputString(" 게시글을 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 게시글 등록을 취소하였습니다.");
      return;
    }

    // 마지막 자유게시판 번호 찾아서 새 게시글 등록시 +1 되도록 기능 구현
    FreeBoard lastFreeBoard = null;
    if (!freeBoardList.isEmpty()) {
      lastFreeBoard = freeBoardList.get(freeBoardList.size() - 1);
      freeBoard.setFreeBoardNo(lastFreeBoard.getFreeBoardNo() + 1);
    } else {
      freeBoard.setFreeBoardNo(1);
    }
    freeBoardList.add(freeBoard);
    myStudy.setMyStudyFreeBoard(freeBoardList);

    studyDao.update(myStudy);

    System.out.println(" >> 게시글이 등록되었습니다.");
    request.getRequestDispatcher("/myStudy/freeBoardList").forward(request);
    return;
  }
}

