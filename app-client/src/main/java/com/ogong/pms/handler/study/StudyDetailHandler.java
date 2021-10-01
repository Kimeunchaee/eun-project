package com.ogong.pms.handler.study;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AbstractStudyHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.pms.handler.mystudy.MyStudyDetailHandler;
import com.ogong.util.Prompt;

public class StudyDetailHandler extends AbstractStudyHandler {

  MyStudyDetailHandler myStudyDetailHandler;
  PromptStudy promptStudy;

  public StudyDetailHandler(List<Study> studyList, PromptStudy promptStudy) {
    super(studyList);
    this.promptStudy = promptStudy;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 상세");
    System.out.println();

    int inputNo = Prompt.inputInt(" 번호 : ");

    Study study = promptStudy.findByStudyNo(inputNo);

    if (study == null) {
      System.out.println(" >> 해당 번호의 스터디가 없습니다.");
      return;
    }

    System.out.printf(" \n(%s)\n", study.getStudyNo());
    System.out.printf(" [%s]\n", study.getStudyTitle());
    System.out.printf(" >> 조장 : %s\n", study.getOwner().getPerNickname());
    System.out.printf(" >> 분야 : %s\n", study.getSubject());
    System.out.printf(" >> 지역 : %s\n", study.getArea());
    System.out.printf(" >> 인원수 : %s/%s명\n",
        study.getMembers().size() + 1, study.getNumberOfPeple());
    System.out.printf(" >> 대면 : %s\n", study.getFace());
    System.out.printf(" >> 소개글 : %s\n", study.getIntroduction());

    request.setAttribute("inputNo", inputNo);

    if (AuthPerMemberLoginHandler.loginUser != null) {

      if (study.getOwner().getPerNickname().equals(
          AuthPerMemberLoginHandler.loginUser.getPerNickname())) {
        System.out.println();
        System.out.println();
        System.out.println("1. 수정");
        System.out.println("2. 삭제");
        System.out.println("0. 이전");

        while (true) {
          int selectNo = Prompt.inputInt("선택> ");
          switch (selectNo) {
            case 1: request.getRequestDispatcher("/myStudy/update").forward(request); return;
            case 2: request.getRequestDispatcher("/myStudy/delete").forward(request); return;
            case 0: return;
            default : 
              System.out.println(" >> 번호를 다시 선택해 주세요.");
          }
        }
      } else {

        System.out.println("\n----------------------");
        System.out.println("1. 참여 신청하기");
        System.out.println("0. 이전");

        while (true) {
          int selectNo = Prompt.inputInt("선택> ");
          switch (selectNo) {
            case 1: joinStudy(study); return;
            case 0: return;
            default : 
              System.out.println(" >> 번호를 다시 선택해 주세요.");
          }
        }
      }
    }
  }

  //스터디 가입
  protected void joinStudy(Study study) {
    System.out.println();
    System.out.println("▶ 스터디 신청");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    for (Member pM : study.getMembers()) {
      if (pM.getPerNickname().equals(member.getPerNickname())) {
        System.out.println(" >> 이미 참여 중인 스터디입니다.");
        return;
      }
    }

    for (Member memberWating : study.getWatingMember()) {
      if (member.getPerNickname().equals(memberWating.getPerNickname())) {
        System.out.println(" >> 이미 승인 대기 중인 스터디입니다.");
        return;
      }
    }

    if(study.getMembers().size() == (study.getNumberOfPeple() - 1)) {
      System.out.println(" >> 참여 가능 인원수를 초과하였습니다.");
      return;
    }

    String input = Prompt.inputString(" 스터디에 참여하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 참여 신청이 취소되었습니다.");
      return;
    }
    study.getWatingMember().add(member);
    System.out.println();
    System.out.println(" >> 참여 신청이 완료되었습니다.\n   승인이 완료될 때까지 기다려 주세요.");
  }


}
