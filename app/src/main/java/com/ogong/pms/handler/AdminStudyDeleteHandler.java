package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class AdminStudyDeleteHandler extends AbstractStudyHandler {

  PromptStudy promptStudy;

  public AdminStudyDeleteHandler(List<Study> newStudyList, PromptStudy promptStudy) {
    super(newStudyList);
    this.promptStudy = promptStudy;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 스터디 삭제");

    int inputNo = Prompt.inputInt(" 번호 : ");

    Study study = promptStudy.findByStudyNo(inputNo);

    if (study == null) {
      System.out.println(" >> 해당 제목의 스터디가 없습니다.");
      return;
    }

    if (study.getOwner().getPerNickname() 
        != AuthAdminLoginHandler.getLoginAdmin().getMasterNickname()) {

      System.out.println();
      String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 스터디 삭제를 취소하였습니다.");
        return;
      }
    }

    studyList.remove(study);

    System.out.println(" >> 스터디를 삭제하였습니다.");
  }
}