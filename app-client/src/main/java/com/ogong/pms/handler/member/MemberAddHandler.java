package com.ogong.pms.handler.member;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class MemberAddHandler implements Command {

  MemberDao memberDao;

  public MemberAddHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  // 개인
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 회원가입");
    System.out.println();

    List<Member> memberList = memberDao.findAll();
    Member member = new Member();

    String inputNewNick;
    inputNewNick = Prompt.inputString(" 닉네임 : ");
    for (Member comparisonMember : memberList) {
      if (inputNewNick.equals(comparisonMember.getPerNickname())) {
        System.out.println(" >> 이미 사용 중인 닉네임입니다.");
        return;
      }
    }

    member.setPerNickname(inputNewNick);
    member.setPerPhoto(Prompt.inputString(" 사  진 : "));

    String inputNewEmail;
    while (true) {
      inputNewEmail = Prompt.inputString(" 이메일 : ");
      if (!inputNewEmail.contains("@") ||
          !inputNewEmail.contains(".com") || inputNewEmail.length() < 6) {
        System.out.println(" >> 정확한 이메일 양식으로 입력해 주세요.");
        continue;
      }
      break;
    }
    member.setPerEmail(inputNewEmail);

    String inputNewPW;
    while (true) {
      inputNewPW = Prompt.inputString(" 비밀번호 : ");
      if (inputNewPW.length() < 8 || (!inputNewPW.contains("!") && !inputNewPW.contains("@")
          && !inputNewPW.contains("#") && !inputNewPW.contains("$")
          && !inputNewPW.contains("^") && !inputNewPW.contains("%")
          && !inputNewPW.contains("&") && !inputNewPW.contains("*"))) {
        System.out.println(" >> 8자 이상 특수문자를 포함시켜 주세요.");
        continue;
      }
      break;
    }
    member.setPerPassword(inputNewPW);

    while (true) {
      String pw =  Prompt.inputString(" 비밀번호 확인 : ");
      if (!pw.equals(member.getPerPassword())) {
        System.out.println("\n >> 확인 실패!\n");
        continue;
      } else {
        System.out.println("\n >> 확인 완료!\n");
      }
      break;
    }

    member.setPerRegisteredDate(new Date(System.currentTimeMillis()));

    // 마지막 회원번호 찾아서 신규회원 등록시 +1 되도록 기능 구현
    Member lastMember = null;
    if (!memberList.isEmpty()) {
      lastMember = memberList.get(memberList.size() - 1);
      member.setPerNo(lastMember.getPerNo() +1);
    } else {
      member.setPerNo(1);
    }
    member.setPerStatus(Member.INUSER);

    memberDao.insert(member);

    System.out.println(" >> 회원가입이 완료되었습니다.");
  }
}







