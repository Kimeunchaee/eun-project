package com.ogong.pms.handler.mystudy;

import java.util.List;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AbstractStudyHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.util.Prompt;

public class MyStudyDeleteHandler extends AbstractStudyHandler {

  PromptStudy promptStudy;

  public MyStudyDeleteHandler(List<Study> newStudyList, PromptStudy promptstudy) {
    super(newStudyList);
    this.promptStudy = promptstudy;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 스터디 삭제");

    int inputNo = (int) request.getAttribute("inputNo");

    Study myStudy = promptStudy.findByMyStudyNo(inputNo);

    // 조건내용 조금 수정해서 '해당 번호의 스터디가 없습니다' 출력되도록 수정해야함
    if (myStudy == null) {
      return;
    }

    if (myStudy.getOwner().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 삭제 권한이 없습니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디 삭제를 취소하였습니다.");
      return;
    }

    studyList.remove(myStudy);

    System.out.println(" >> 스터디를 삭제하였습니다.");
  }
}