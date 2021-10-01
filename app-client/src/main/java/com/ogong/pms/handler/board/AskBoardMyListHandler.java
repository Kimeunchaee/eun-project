package com.ogong.pms.handler.board;

import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;
import com.ogong.pms.handler.AbstractAskBoardHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;

public class AskBoardMyListHandler extends AbstractAskBoardHandler {

  ReplyDetailHandler replyDetailHandler;

  public AskBoardMyListHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Reply> replyList, ReplyDetailHandler replyDetailHandler) {
    super(askBoardList, replyList, memberList, ceoMemberList);
    this.replyDetailHandler = replyDetailHandler;
  }

  // 마이페이지 - 내가 쓴 문의내역
  @Override
  public void execute(CommandRequest request){
    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      // 개인이 쓴 문의글
      int count = 0;
      AskBoard myAskBoard = new AskBoard();
      for (AskBoard askBoard : askBoardList) {
        String member = AuthPerMemberLoginHandler.getLoginUser().getPerNickname();
        if(member.equals(askBoard.getAskMemberWriter().getPerNickname())) {
          System.out.println();
          System.out.printf(" (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askBoard.getAskNo(), 
              askBoard.getAskTitle(), 
              askBoard.getAskMemberWriter().getPerNickname(),
              askBoard.getAskRegisteredDate(),
              askBoard.getAskVeiwCount());
          myAskBoard = askBoard;
          count++;

        } 

      }
      System.out.println("---------------------");

      if (count == 0) {
        System.out.println("\n >> 내가 등록한 문의글이 없습니다.");
        return;
      }

      if (myAskBoard.getReply() == null) {
        System.out.println();
        System.out.println(" >> 등록된 답변이 없습니다.");
      }
      else if (myAskBoard.getReply() != null) {
        replyDetailHandler.detailReply(myAskBoard);  // 답변 호출
      }

    } 

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      // 기업이 쓴 문의글
      int count = 0;
      AskBoard ceoAskBoard = new AskBoard();
      for (AskBoard askBoard : askBoardList) {
        String ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember().getCeoBossName();
        if(ceoMember.equals(askBoard.getAskCeoWriter().getCeoBossName())) {
          System.out.println();
          System.out.printf(" (%d)\n 제목 : %s\n 작성자 : %s\n 작성일 : %s\n 조회수 : %d\n", 
              askBoard.getAskNo(), 
              askBoard.getAskTitle(), 
              askBoard.getAskCeoWriter().getCeoBossName(),
              askBoard.getAskRegisteredDate(),
              askBoard.getAskVeiwCount());
          ceoAskBoard = askBoard;
          count++;
        } 
      }

      System.out.println("---------------------");

      if (count == 0) {
        System.out.println("\n >> 내가 등록한 문의글이 없습니다.");
        return;
      }

      if (ceoAskBoard.getReply() == null) {
        System.out.println();
        System.out.println(" >> 등록된 답변이 없습니다.");
      }
      else if (ceoAskBoard.getReply() != null) {
        replyDetailHandler.detailReply(ceoAskBoard);  // 답변 호출
      }

    }

    else {
      System.out.println();
      System.out.println(" >> 로그인 하세요.");
      return;
    }
  }

}
