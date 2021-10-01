package com.ogong.pms.handler.mystudy;

import java.util.List;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AbstractStudyHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.util.Prompt;

public class MyStudyDetailHandler extends AbstractStudyHandler {

  MyStudyToDo myStudyToDo;
  MyStudyCalender myStudyCalender;
  MyStudyFreeBoard myStudyFreeBoard;
  MyStudyGuilder myStudyGuilder;
  MyStudyExit myStudyExit;
  List<Comment> commentList;
  PromptStudy promptStudy;

  public MyStudyDetailHandler(List<Study> studyList, MyStudyToDo myStudyToDo, 
      MyStudyCalender myStudyCalender, MyStudyFreeBoard myStudyFreeBoard,
      MyStudyGuilder myStudyGuilder, MyStudyExit myStudyExit, List<Comment> commentList, 
      PromptStudy promptStudy) {
    super(studyList);
    this.myStudyToDo = myStudyToDo;
    this.myStudyCalender = myStudyCalender;
    this.myStudyFreeBoard = myStudyFreeBoard;
    this.myStudyGuilder = myStudyGuilder;
    this.myStudyExit = myStudyExit;
    this.commentList = commentList;
    this.promptStudy = promptStudy;
  }


  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 스터디 상세");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    int myStudyCount = 0;

    for (int i = 0; i < studyList.size(); i++) {
      if (studyList.get(i).getMemberNames().contains(member.getPerNickname()) ||
          studyList.get(i).getOwner().getPerNickname().equals(member.getPerNickname())) {
        myStudyCount++;
      }
    }

    if(myStudyCount == 0) {
      System.out.println(" >> 가입된 스터디가 없습니다.");
      return;
    }

    int inputNo = Prompt.inputInt(" 번호  : ");

    Study myStudy = promptStudy.findByMyStudyNo(inputNo); 

    if(myStudy == null) {
      System.out.println();
      System.out.println(" >> 본인의 스터디 번호를 입력하세요");
      return;
    }

    else if (member.getPerNickname().equals(myStudy.getOwner().getPerNickname()) || 
        myStudy.getMemberNames().contains(member.getPerNickname())) {

      System.out.printf(" \n(%s)\n", myStudy.getStudyNo());
      System.out.printf(" [%s]\n", myStudy.getStudyTitle());
      System.out.printf(" >> 조장 : %s\n", myStudy.getOwner().getPerNickname());
      System.out.printf(" >> 분야 : %s\n", myStudy.getSubject());
      System.out.printf(" >> 지역 : %s\n", myStudy.getArea());
      System.out.printf(" >> 인원수 : %s/%s명\n",
          myStudy.getMembers().size() + 1, myStudy.getNumberOfPeple());
      System.out.printf(" >> 대면 : %s\n", myStudy.getFace());
      System.out.printf(" >> 소개글 : %s\n", myStudy.getIntroduction());

      request.setAttribute("inputNo", inputNo);

      if (AuthPerMemberLoginHandler.loginUser != null) {

        if (myStudy.getMemberNames().contains(
            AuthPerMemberLoginHandler.loginUser.getPerNickname()) || 
            myStudy.getOwner().getPerNickname().equals(
                AuthPerMemberLoginHandler.loginUser.getPerNickname())) {

          System.out.println();
          while (true) {
            System.out.println("\n----------------------");

            System.out.println("1. 구성원");
            // 대기중인 회원 거절하기
            // 대기중인 회원 승인할때 회원 고유번호로 입력하도록
            // 조장 : 권한 넘겨주기, 탈퇴시키기 까지

            System.out.println("2. 캘린더");
            System.out.println("3. To-do");
            System.out.println("4. 자유게시판");
            System.out.println("5. 화상미팅");
            System.out.println("6. 탈퇴하기");  // 메서드
            // 스터디 삭제는 되는데 탈퇴하기가 없음
            // 구성원은 탈퇴만 해야함, 조장은 스터디삭제도 할수 있음

            if (myStudy.getOwner().getPerNickname().equals(
                AuthPerMemberLoginHandler.loginUser.getPerNickname())) {
              System.out.println("7. 스터디 수정");
              System.out.println("8. 스터디 삭제");
            }

            System.out.println("0. 뒤로 가기");

            int selectNo = Prompt.inputInt("선택> "); 
            switch (selectNo) {
              case 1: myStudyGuilder.ownerGuilder(myStudy); break;
              case 2: myStudyCalender.listCalender(myStudy); break;
              case 3: myStudyToDo.listToDo(myStudy); break;
              case 4: myStudyFreeBoard.listFreeBoard(commentList, myStudy); break;
              case 5:  MyStudyCheating.cheat() ;  // 임시로 넣었음
              case 6: myStudyExit.exitMyStudy(myStudy); return;  
              case 7: request.getRequestDispatcher("/myStudy/update").forward(request); return;
              case 8: request.getRequestDispatcher("/myStudy/delete").forward(request); return;
              default : return;
            }
          }
        }

        else if (!member.getPerNickname().equals(myStudy.getOwner().getPerNickname())) {
          System.out.println();
          System.out.println(" >> 볼 수 없는 페이지입니다.");
          System.out.println();
          System.out.println(" >> 다시 선택해 주세요.");
          return;
        }
      }

    }
  }
}
