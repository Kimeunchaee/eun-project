package com.ogong.pms.handler.myStudy;

import com.ogong.chat.MySocketClient;
import com.ogong.chat.MySocketServer;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class MyStudyChat  implements Command  {
  RequestAgent requestAgent;

  public MyStudyChat(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }


  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 실시간 채팅");
    System.out.println();

    System.out.println("1. 채팅 개최");
    System.out.println("2. 채팅 참여");

    int selectNo = Prompt.inputInt("선택> "); 
    switch (selectNo) {
      case 1: MySocketServer.startchat(); return;
      case 2: MySocketClient.execute(); return;
      default : return;
    }
  }
}