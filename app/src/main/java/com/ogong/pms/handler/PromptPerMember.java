package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;

public class PromptPerMember {

  List<Member> memberList;

  public PromptPerMember(List<Member> memberList) {
    this.memberList = memberList;
  }

  //닉네임을 통해 멤버를 찾아서 리턴한다.
  public Member findByMemberNick(String inputNick) {
    for (Member member : memberList) {
      if (inputNick.equals(member.getPerNickname())) {
        return member;
      }
    }
    return null;
  }

  //이메일을 통해 멤버를 찾아서 리턴한다.
  public Member findByMemberEmail(String inputEmail) {
    for (Member member : memberList) {
      if (member.getPerEmail().equals(inputEmail)) {
        return member;
      }
    }
    return null;
  }

  //비밀번호를 통해 멤버를 찾아서 리턴한다.
  public Member findByMemberPW(String inputPW) {
    for (Member member : memberList) {
      if (inputPW.equals(member.getPerPassword())) {
        return member;
      }
    }
    return null;
  }

  //번호를 통해 멤버를 찾아서 리턴한다.
  public Member findByMemberNo(int inputPerNo) {
    for (Member member : memberList) {
      if (inputPerNo == member.getPerNo()) {
        return member;
      }
    }
    return null;
  }
}
