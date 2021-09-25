package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class MemberUpdateHandler extends AbstractMemberHandler {

  PromptPerMember promptPerMember;

  public MemberUpdateHandler(List<Member> memberList, PromptPerMember promptPerMember) {
    super(memberList);
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 프로필 수정");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");
    Member member = promptPerMember.findByMemberNo(inputNo);

    if (member == null) {
      System.out.println(" >> 해당 회원이 없습니다.");
    }

    member = AuthPerMemberLoginHandler.getLoginUser();

    System.out.println("1. 닉네임");
    System.out.println("2. 사진");
    System.out.println("3. 이메일");
    System.out.println("4. 비밀번호");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");

    String perNickName = member.getPerNickname();
    String perPhoto = member.getPerPhoto();
    String perEmail = member.getPerEmail();
    String perPassword = member.getPerPassword();

    switch (selectNo) {
      case 1: 
        perNickName = Prompt.inputString(" 닉네임(" + member.getPerNickname()  + ") : ");
        break;
      case 2: 
        perPhoto = Prompt.inputString(" 사  진(" + member.getPerPhoto() + ") : ");
        break;
      case 3:
        perEmail = Prompt.inputString(" 이메일(" + member.getPerEmail() + ") : ");
        break;
      case 4:
        perPassword = Prompt.inputString(" 비밀번호(" + member.getPerPassword() + ") : ");
        break;
      default : 
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 변경을 취소하였습니다.");
      return;
    }
    if (selectNo == 1) {
      member.setPerNickname(perNickName);
    } else if (selectNo == 2) {
      member.setPerPhoto(perPhoto);
    } else if (selectNo == 3) {
      member.setPerEmail(perEmail);
    } else if (selectNo == 4) {
      member.setPerPassword(perPassword);
    }

    System.out.println(" >> 회원 정보를 변경하였습니다.");

  }
}
