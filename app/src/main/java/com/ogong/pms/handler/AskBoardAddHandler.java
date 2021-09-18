package com.ogong.pms.handler;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class AskBoardAddHandler extends AbstractAskBoardHandler {

  int askNo = 7;

  public AskBoardAddHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Comment> commentList) {
    super(askBoardList, commentList, memberList, ceoMemberList);

    //    AskBoard askList = new AskBoard();
    //    askList.setAskNo(1);
    //    askList.setAskTitle("문의합니다.");
    //    askList.setAskContent("예약 방법에 대해 알고 싶습니다.");
    //    askList.setAskMemberWriter(memberList.get(0));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("예약 방법에 대해..");
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(2);
    //    askList.setAskTitle("가게 등록..");
    //    askList.setAskContent("가게 승인 요청 어떻게 하나요?");
    //    askList.setAskCeoWriter(ceoMemberList.get(0));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("가게 승인 요청 방법은..");
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(3);
    //    askList.setAskTitle("이런이런!");
    //    askList.setAskContent("개발자님!! 이 부분 좀 고쳐 주세요!");
    //    askList.setAskMemberWriter(memberList.get(1));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("오호라! 알겠습니다!");
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(4);
    //    askList.setAskTitle("헐! 헐! 헐!");
    //    askList.setAskContent("예약 내역이 안 보여요 ㅠㅠ");
    //    askList.setAskCeoWriter(ceoMemberList.get(1));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("수정하겠습니다.");
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(5);
    //    askList.setAskTitle("질문있어요.");
    //    askList.setAskContent("스터디 참여 방법이 궁금해요.");
    //    askList.setAskMemberWriter(memberList.get(2));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("스터디 참여 방법은..");
    //    askBoardList.add(askList);
    //
    //    askList = new AskBoard();
    //    askList.setAskNo(6);
    //    askList.setAskTitle("고객 예약 관련 문의");
    //    askList.setAskContent("실수로 고객 예약 내역을 삭제했어요..");
    //    askList.setAskCeoWriter(ceoMemberList.get(2));
    //    askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
    //    askList.setAskVeiwCount(askList.getAskVeiwCount());
    //    askList.setAnswer("헐! 헐! 헐!");
    //    askBoardList.add(askList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 문의사항");
    System.out.println();

    AskBoard askList = new AskBoard();

    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      askList.setAskNo(askNo++);
      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskMemberWriter(AuthPerMemberLoginHandler.getLoginUser());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
      // 0917 관리자 댓글 수정 필요!
      //      askList.setAdminComment(new ArrayList<>());

    }

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      askList.setAskNo(askNo++);
      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskCeoWriter(AuthCeoMemberLoginHandler.getLoginCeoMember());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
      // 0917 관리자 댓글 수정 필요!
      //      askList.setAdminComment(new ArrayList<>());

    }

    String input = Prompt.inputString(" 문의글을 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 문의글 등록을 취소하였습니다.");
      return;
    }

    askBoardList.add(askList);
  }
}







