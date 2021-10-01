package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;

public abstract class AbstractAskBoardHandler implements Command {

  protected List<AskBoard> askBoardList;
  List<Reply> replyList;
  List<Member> memberList;
  List<CeoMember> ceoMemberList;
  int replyCount = 1;

  public AbstractAskBoardHandler(List<AskBoard> askBoardList, List<Reply> replyList, 
      List<Member> memberList, List<CeoMember> ceoMemberList) {
    this.askBoardList = askBoardList;
    this.replyList = replyList;
    this.memberList = memberList;
    this.ceoMemberList = ceoMemberList;
  }

  protected AskBoard findByAskBoardNo(int askNo) {
    for (AskBoard askList : askBoardList) {
      if (askList.getAskNo() == askNo) {
        return askList;
      }
    }
    return null;
  }
}
