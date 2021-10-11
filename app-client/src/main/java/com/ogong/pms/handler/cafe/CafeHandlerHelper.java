package com.ogong.pms.handler.cafe;

import static com.ogong.pms.domain.Cafe.DELETE;
import static com.ogong.pms.domain.Cafe.GENERAL;
import static com.ogong.pms.domain.Cafe.STOP;
import static com.ogong.pms.domain.Cafe.WAIT;
import com.ogong.util.Prompt;

public abstract class CafeHandlerHelper {

  public static String getReviewStatusLabel(String status) {
    switch (status) {
      case "true": return "작성완료";
      case "false": return "작성대기";
      default: return "오류";
    }
  }

  public static String getReviewGradeStatusLabel(int status) {
    switch (status) {
      case 0: return "☆☆☆☆☆";
      case 1: return "★☆☆☆☆";
      case 2: return "★★☆☆☆";
      case 3: return "★★★☆☆";
      case 4: return "★★★★☆";
      case 5: return "★★★★★";
      default: return "오류";
    }
  }

  public static String getReservationStatus(int status) {
    switch (status) {
      case 0: return "예약완료(현장결제)";
      case 1: return "결제완료";
      case 2: return "예약취소";
      case 3: return "결제취소";
      case 4: return "예약거절";
      case 5: return "결제거절";
      default: return "오류";
    }
  }

  public static String getCafeStatusLabel(int status) {
    switch (status) {
      case WAIT: return "승인대기";
      case GENERAL: return "운영중";
      case STOP: return "운영중단";
      case DELETE: return "삭제";
      default: return "오류";
    }
  }

  public static int promptCafeStatus(int status) {
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
