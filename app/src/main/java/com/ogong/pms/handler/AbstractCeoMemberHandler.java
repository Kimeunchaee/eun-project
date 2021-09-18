package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public abstract class AbstractCeoMemberHandler implements Command {

  List<CeoMember> ceoMemberList;

  public AbstractCeoMemberHandler(List<CeoMember> ceoMemberList) {
    this.ceoMemberList = ceoMemberList;
  }

  protected static String getCafeStatusLabel(int status) {
    switch (status) {
      case 0: return "승인대기";
      case 1: return "운영중";
      case 2: return "운영중단";
      case 3: return "삭제";
      default: return "오류";
    }
  }

  protected static int promptCafeStatus(int status) {

    System.out.printf(" 상태(%s) : \n", getCafeStatusLabel(status));
    int input = 0;
    do {
      System.out.println(" 1: 운영중");
      System.out.println(" 2: 운영중단");
      input = Prompt.inputInt(" > ");

      if (input != 1 && input != 2) {
        System.out.println(" >> 번호를 잘못 선택하셨습니다.\n    다시 입력해 주세요.\n");
      }
    } while (input != 1 && input != 2);

    return input;
  }
}







