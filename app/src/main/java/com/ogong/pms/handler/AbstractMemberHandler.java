package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;

public abstract class AbstractMemberHandler implements Command {

  protected List<Member> memberList;

  public AbstractMemberHandler(List<Member> memberList) {
    this.memberList = memberList;
  }
}
