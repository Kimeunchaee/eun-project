package com.ogong.pms.handler.board;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;
import com.ogong.pms.handler.AbstractAskBoardHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AskBoardAddHandler extends AbstractAskBoardHandler {

  int askNo = 7;

  public AskBoardAddHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Reply> replyList) {
    super(askBoardList, replyList, memberList, ceoMemberList);

    //    AskBoard askList = new AskBoard();
    //    askList.setAskNo(1);
    //    askList.setAskTitle("문의합니다.");
    //    askList.setAskContent("예약 방법에 대해 알고 싶습니다.");
    //    askList.setAskMemberWriter(memberList.get(0));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(2);
    //    askList.setAskTitle("가게 등록..");
    //    askList.setAskContent("가게 승인 요청 어떻게 하나요?");
    //    askList.setAskCeoWriter(ceoMemberList.get(0));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(3);
    //    askList.setAskTitle("이런이런!");
    //    askList.setAskContent("개발자님!! 이 부분 좀 고쳐 주세요!");
    //    askList.setAskMemberWriter(memberList.get(1));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(4);
    //    askList.setAskTitle("헐! 헐! 헐!");
    //    askList.setAskContent("예약 내역이 안 보여요 ㅠㅠ");
    //    askList.setAskCeoWriter(ceoMemberList.get(1));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(5);
    //    askList.setAskTitle("질문있어요.");
    //    askList.setAskContent("스터디 참여 방법이 궁금해요.");
    //    askList.setAskMemberWriter(memberList.get(2));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(6);
    //    askList.setAskTitle("고객 예약 관련 문의");
    //    askList.setAskContent("실수로 고객 예약 내역을 삭제했어요..");
    //    askList.setAskCeoWriter(ceoMemberList.get(2));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAskStatus(1);
    //    askBoardList.add(askList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 문의사항");
    System.out.println();

    AskBoard askList = new AskBoard();

    int statusNo = 0;

    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskMemberWriter(AuthPerMemberLoginHandler.getLoginUser());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt("\n 1: 공개 / 2: 비공개 > ");
          System.out.println();
          if (statusNo >= 3) {
            System.out.println(" >> 번호를 다시 입력하세요.\n");
            continue;
          }
          else if ((statusNo > 0) && (statusNo < 3)) {
            String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
            if (!input.equalsIgnoreCase("네")) {
              System.out.println(" >> 문의글 등록을 취소하였습니다.");
              return;
            }    
            askList.setAskNo(askNo++);
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(" >> 번호만 입력 가능합니다.\n");
          continue;
        }
        break;
      } 

      askList.setAskStatus(statusNo);
    }

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskCeoWriter(AuthCeoMemberLoginHandler.getLoginCeoMember());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt("\n 1: 공개 / 2: 비공개 > ");
          System.out.println();
          if (statusNo >= 3) {
            System.out.println(" >> 번호를 다시 입력하세요.\n");
            continue;
          }
          else if ((statusNo > 0) && (statusNo < 3)) {
            String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
            if (!input.equalsIgnoreCase("네")) {
              System.out.println(" >> 문의글 등록을 취소하였습니다.");
              return;
            }     
            askList.setAskNo(askNo++);
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(" >> 번호만 입력 가능합니다.\n");
          continue;
        }
        break;
      } 

      askList.setAskStatus(statusNo);
    }

    if (statusNo == 0) {
      System.out.println(" >> 이전 화면으로 돌아갑니다.");
      return;
    } 

    else if ((statusNo > 0) && (statusNo < 3)) {
      System.out.println(" >> 문의글이 등록되었습니다.");
      askBoardList.add(askList);
    }
  }

}
