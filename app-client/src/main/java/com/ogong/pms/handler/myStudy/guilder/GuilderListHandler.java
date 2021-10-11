package com.ogong.pms.handler.myStudy.guilder;

import java.util.HashMap;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class GuilderListHandler implements Command {

  //  MyStudyGuilderList myStudyGuilderList; // 구성원 목록
  //  MyStudyGuilderDelete myStudyGuilderDelete; // 구성원 탈퇴
  //  MyStudyGuilderEntrust myStudyGuilderEntrust; // 조장 위임
  //
  //  public MyStudyGuilder(MyStudyGuilderList myStudyGuilderList, 
  //      MyStudyGuilderDelete myStudyGuilderDelete, MyStudyGuilderEntrust myStudyGuilderEntrust) {
  //    this.myStudyGuilderList = myStudyGuilderList;
  //    this.myStudyGuilderDelete = myStudyGuilderDelete;
  //    this.myStudyGuilderEntrust = myStudyGuilderEntrust;
  //  }

  RequestAgent requestAgent;

  public GuilderListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  // 스터디 구성원 목록
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 구성원");
    System.out.println();

    HashMap<String,String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(request.getAttribute("inputNo")));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println(" >> 스터디 상세 오류.");
      return;
    }

    Study myStudy = requestAgent.getObject(Study.class);

    System.out.printf(" >> 스터디 구성원 (%s/%s명)\n" , myStudy.getMembers().size() + 1,
        myStudy.getNumberOfPeple());
    System.out.println(" 조  장 : " + myStudy.getOwner().getPerNickname());
    System.out.println(" 구성원 : " + myStudy.getMemberNames());

    // 조장만 보임
    if (!AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname())) {
      return;
    }

    if(AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname()) && !myStudy.getWatingMemberNames().isEmpty()) {
      System.out.printf("\n ★ > 승인 대기 중인 회원이 %d명 있습니다.", myStudy.getWatingMember().size());
    } 

    else if(AuthPerMemberLoginHandler.getLoginUser().getPerNickname().equals(
        myStudy.getOwner().getPerNickname()) && myStudy.getWatingMemberNames().isEmpty()) {
      System.out.println("\n ☆ > 승인 대기 중인 회원이 없습니다.");
    }

    request.setAttribute("inputNo", myStudy.getStudyNo());

    System.out.println("\n----------------------");
    System.out.println();
    System.out.println("1. 승인 대기 목록");
    System.out.println("2. 조장 권한 위임");
    System.out.println("3. 구성원 탈퇴시키기");
    System.out.println("0. 뒤로 돌아가기");
    System.out.println();

    int inputGuilerNo = Prompt.inputInt("선택> ");
    switch (inputGuilerNo) {
      //case 1: myStudyGuilderList.listGuilder(myStudy); break;
      //case 2: myStudyGuilderEntrust.entrustGuilder(myStudy); return;
      //case 3: myStudyGuilderDelete.guilderDelete(myStudy); break;
      case 1: request.getRequestDispatcher("/myStudy/listGuilder").forward(request); return;
      case 2: request.getRequestDispatcher("/myStudy/entrustGuilder").forward(request); return;
      case 3: request.getRequestDispatcher("/myStudy/deleteGuilder").forward(request); return;
      default: return;
    }
  }

}
