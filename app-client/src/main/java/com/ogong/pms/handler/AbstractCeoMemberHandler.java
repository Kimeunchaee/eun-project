package com.ogong.pms.handler;

import static com.ogong.pms.domain.Cafe.DELETE;
import static com.ogong.pms.domain.Cafe.GENERAL;
import static com.ogong.pms.domain.Cafe.STOP;
import static com.ogong.pms.domain.Cafe.WAIT;
import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public abstract class AbstractCeoMemberHandler implements Command {

  protected List<CeoMember> ceoMemberList;

  public AbstractCeoMemberHandler(List<CeoMember> ceoMemberList) {
    this.ceoMemberList = ceoMemberList;
  }

  protected static String getCafeStatusLabel(int status) {
    switch (status) {
      case WAIT: return "승인대기";
      case GENERAL: return "운영중";
      case STOP: return "운영중단";
      case DELETE: return "삭제";
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

      if (input != GENERAL && input != STOP) {
        System.out.println(" >> 번호를 잘못 선택하셨습니다.\n    다시 입력해 주세요.\n");
      }
    } while (input != GENERAL && input != STOP);

    return input;
  }
}







