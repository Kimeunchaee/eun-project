package com.ogong.pms.handler;

import java.util.List;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.util.Prompt;

public class AuthPerMemberLoginHandler extends AbstractLoginHandler {

  PromptPerMember promptPerMember;
  List<Member> memberList;
  List<Study> studyList;

  static Member loginUser;
  public static Member getLoginUser() {
    return loginUser;
  }

  public static int getUserAccessLevel() {
    return accessLevel;
  }

  public AuthPerMemberLoginHandler(PromptPerMember promptPerMember, List<Member> memberList,
      List<Study> studyList) {
    this.promptPerMember = promptPerMember;
    this.memberList = memberList;
    this.studyList = studyList;
  }

  @Override
  public void execute(CommandRequest request) {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = "";
    Member member = promptPerMember.findByMemberEmail(inputEmail);

    if (member == null) {
      System.out.println(" >> 등록된 회원이 아닙니다.");
      return;
    }

    if (member.getPerStatus() == Member.BLOCK) {
      System.out.println(" >> 차단된 회원은 로그인할 수 없습니다. 고객센터-문의사항에 요청하세요.");
      return;
    }

    //    if (member.getPerStatus() == Member.BLOCK) {
    //      while (member != null) {
    //        inputPassword = Prompt.inputString(" 비밀번호 : ");
    //
    //        if (member.getPerPassword().equals(inputPassword)) {
    //          member.setPerEmail(inputEmail);
    //          member.setPerPassword(inputPassword);
    //          System.out.println();
    //          System.out.printf(" >> '%s'님 환영합니다!\n", member.getPerNickname());
    //
    //          // 변경하면 상태도 바꿔줄지 로그인하는 순간 상태를 바꿔줄지 고민
    //          member.setPerStatus(Member.GENERAL);
    //
    //          for (Study myStudy : studyList) {
    //            if (myStudy.getOwner().getPerNo() == member.getPerNo()) {
    //              myStudy.setOwner(member);
    //              myStudy.setStudyTitle(myStudy.getTempStudyTitle());
    //              myStudy.setTempStudyTitle(null);
    //            }
    //          }
    //
    //          loginUser = member;
    //          accessLevel = Menu.PER_LOGIN;
    //          return;
    //        }
    //        System.out.println(" >> 비밀번호를 다시 입력하세요.\n");
    //        return;
    //      }
    //    }

    while (member != null) {
      inputPassword = Prompt.inputString(" 비밀번호 : ");

      if (member.getPerPassword().equals(inputPassword)) {
        member.setPerEmail(inputEmail);
        member.setPerPassword(inputPassword);

        if (member.getPerNickname().contains("차단")) {
          System.out.println(" >> 차단되었던 회원은 비밀번호를 변경해주세요.");

          member.setPerStatus(Member.GENERAL);

          String str = member.getPerNickname().replace("차단된 회원/", "");
          member.setPerNickname(str);

          for (Study myStudy : studyList) {
            if (myStudy.getOwner().getPerNo() == member.getPerNo()) {
              myStudy.setStudyTitle(myStudy.getTempStudyTitle());
              myStudy.setTempStudyTitle(null);
            }
          }
        }
        System.out.println();
        System.out.printf(" >> '%s'님 환영합니다!\n", member.getPerNickname());

        loginUser = member;
        accessLevel = Menu.PER_LOGIN;

        return;
      }

      System.out.println(" >> 비밀번호를 다시 입력하세요.\n");
      return;
    }
  } 
}
