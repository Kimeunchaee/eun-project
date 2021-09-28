package com.ogong.pms.handler;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;

public class AskBoardAddHandler extends AbstractAskBoardHandler {

  int askNo = 7;
  RandomPw randomPw;
  PromptPerMember promptPerMember;
  List<Study> studyList;

  public AskBoardAddHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Reply> replyList, RandomPw randomPw,
      PromptPerMember promptPerMember, List<Study> studyList) {
    super(askBoardList, replyList, memberList, ceoMemberList);
    this.randomPw = randomPw;
    this.promptPerMember = promptPerMember;
    this.studyList = studyList;

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

    // 비회원일때
    if (AuthPerMemberLoginHandler.getLoginUser() == null) {

      System.out.println(" 비회원은 차단 해제 문의글 등록만 가능합니다.");
      System.out.println(" 가입시 입력한 이메일로 문의글 작성 후 임시비밀번호를 발급해드립니다.\n");

      String input = Prompt.inputString(" 차단 해제 요청글을 작성하시겠습니까? (네 /아니오) ");

      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 작성을 취소하였습니다.");
        return;
      }

      System.out.println();
      System.out.println(" 제목 : 차단회원 해제 요청");
      askList.setAskTitle("차단회원 해제 요청");

      String nonMemberNickName = Prompt.inputString(" 요청 닉네임 : ");
      askList.setAskNonMemberWriter(nonMemberNickName);

      String inputNonMemberEmail;
      Member nonMember;
      while (true) {
        inputNonMemberEmail = Prompt.inputString(" 기존 이메일 : ");
        nonMember = promptPerMember.findByBlockMemberEmail(inputNonMemberEmail);

        if (nonMember == null) {
          System.out.println(" >> 차단된 회원 중 해당 이메일이 존재하지 않습니다.\n");
          String inputX = Prompt.inputString(" 요청을 취소하시겠습니까? (네 / 아니오) ");
          System.out.println();
          if (!inputX.equalsIgnoreCase("네")) {
            continue;
          }
          System.out.println(" >> 차단회원 해제 요청을 취소하였습니다.");
          return;
        }
        break;
      }

      askList.setAskContent(inputNonMemberEmail);
      askList.setAskVeiwCount(askList.getAskVeiwCount());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));
      askList.setAskStatus(2);

      System.out.println();
      String inputY = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
      if (!inputY.equalsIgnoreCase("네")) {
        System.out.println(" >> 문의글 등록을 취소하였습니다.");
        return;
      }

      askList.setAskNo(askNo++);
      askBoardList.add(askList);
      System.out.println(" >> 문의글이 등록되었습니다.\n");

      SendMail sendMail = new SendMail();

      for (Member member : memberList) {
        if (member.getPerEmail().equals(inputNonMemberEmail)) {
          String pw = randomPw.randomPw();
          member.setPerPassword(pw);
          System.out.println(" >> 메일 발송 처리중입니다. 잠시만 기다려 주세요.");
          sendMail.sendMail(inputNonMemberEmail, pw);
          member.setPerStatus(Member.GENERAL);
          System.out.println();
          System.out.printf(" '%s'님의 임시 비밀번호가 메일로 전송되었습니다.\n", nonMemberNickName);
          System.out.println(" >> 로그인 후 비밀번호를 변경해 주세요.");
        }
      }

      //      nonMember.setPerNickname(nonMember.getPerNickname());
      //      nonMember.setPerStatus(Member.GENERAL);
      //
      //      for (Study myStudy : studyList) {
      //        if (myStudy.getOwner().getPerNo() == nonMember.getPerNo()) {
      //          myStudy.setOwner(nonMember);
      //          myStudy.setStudyTitle(myStudy.getTempStudyTitle());
      //          myStudy.setTempStudyTitle(null);
      //        }
      //      }
    }


    // 개인일때
    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskMemberWriter(AuthPerMemberLoginHandler.getLoginUser());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt(" 1: 공개 / 2: 비공개 > ");
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

    // 기업일때
    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      askList.setAskTitle(Prompt.inputString(" 제목 : "));
      askList.setAskContent(Prompt.inputString(" 내용 : "));
      askList.setAskCeoWriter(AuthCeoMemberLoginHandler.getLoginCeoMember());
      askList.setAskRegisteredDate(new Date(System.currentTimeMillis()));

      while (true) {

        try {
          statusNo = Prompt.inputInt(" 1: 공개 / 2: 비공개 > ");
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
