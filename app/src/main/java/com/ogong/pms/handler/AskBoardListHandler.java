package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;

public class AskBoardListHandler extends AbstractAskBoardHandler {

  public AskBoardListHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Reply> replyList) {
    super(askBoardList, replyList, memberList, ceoMemberList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 문의사항 목록");
    System.out.println();

    if (askBoardList.isEmpty()) {
      System.out.println(" >> 등록된 글이 없습니다.");
      return;
    }

    for (AskBoard askList : askBoardList) {

      // 기업
      if (askList.getAskMemberWriter().getPerNickname() == null) {

        if (askList.getAskStatus() == 1) {
          System.out.printf("\n (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askList.getAskNo(), 
              askList.getAskTitle(), 
              askList.getAskCeoWriter().getCeoBossName(),
              askList.getAskRegisteredDate(),
              askList.getAskVeiwCount());
        }

        else if (askList.getAskStatus() == 2) {
          System.out.printf("\n (%d)\n 작성자 : %s\n", 
              askList.getAskNo(), 
              askList.getAskCeoWriter().getCeoBossName());
          System.out.println(" 비밀글입니다.");
        }
      }

      // 개인
      else if (askList.getAskCeoWriter().getCeoBossName() == null) {

        if (askList.getAskStatus() == 1) {
          System.out.printf("\n (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askList.getAskNo(), 
              askList.getAskTitle(), 
              askList.getAskMemberWriter().getPerNickname(),
              askList.getAskRegisteredDate(),
              askList.getAskVeiwCount());
        }

        else if (askList.getAskStatus() == 2) {
          System.out.printf("\n (%d)\n 작성자 : %s\n", 
              askList.getAskNo(), 
              askList.getAskMemberWriter().getPerNickname());
          System.out.println();
          System.out.println(" 비밀글입니다.");
        }
      }

    }
  }
}
