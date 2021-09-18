package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.Member;

public class AskBoardMyListHandler extends AbstractAskBoardHandler {

  public AskBoardMyListHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Comment> commentList) {
    super(askBoardList, commentList, memberList, ceoMemberList);
  }

  // 마이페이지 - 내가 쓴 문의내역
  @Override
  public void execute(CommandRequest request){
    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      // 개인이 쓴 문의글
      int count = 0;
      for (AskBoard askList : askBoardList) {
        String member = AuthPerMemberLoginHandler.getLoginUser().getPerNickname();
        if(member.equals(askList.getAskMemberWriter().getPerNickname())) {
          System.out.println();
          System.out.printf(" (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askList.getAskNo(), 
              askList.getAskTitle(), 
              askList.getAskMemberWriter().getPerNickname(),
              askList.getAskRegisteredDate(),
              askList.getAskVeiwCount());
          count++;
        } 
      }

      if (count == 0) {
        System.out.println("\n >> 내가 등록한 문의글이 없습니다.");
      }
    } 

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      // 기업이 쓴 문의글
      int count = 0;
      for (AskBoard askList : askBoardList) {
        String ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember().getCeoBossName();
        if(ceoMember.equals(askList.getAskCeoWriter().getCeoBossName())) {
          System.out.println();
          System.out.printf(" (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askList.getAskNo(), 
              askList.getAskTitle(), 
              askList.getAskCeoWriter().getCeoBossName(),
              askList.getAskRegisteredDate(),
              askList.getAskVeiwCount());
          count++;
        } 
      }

      if (count == 0) {
        System.out.println("\n >> 내가 등록한 문의글이 없습니다.");
      }
    }

    else {
      System.out.println();
      System.out.println(" >> 로그인 하세요.");
      return;
    }
  }

}
