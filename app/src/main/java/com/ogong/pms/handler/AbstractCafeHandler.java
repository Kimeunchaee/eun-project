package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;

public abstract class AbstractCafeHandler implements Command {

  List<Cafe> cafeList;

  public AbstractCafeHandler (List<Cafe> cafeList) {
    this.cafeList = cafeList;
  }

  protected String getReviewStatusLabel(String status) {
    switch (status) {
      case "true": return "작성완료";
      case "false": return "작성대기";
      default: return "오류";
    }
  }

  protected String getReviewGradeStatusLabel(int status) {
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

  protected String getReservationStatus(int status) {
    switch (status) {
      case 0: return "결제대기";
      case 1: return "결제완료";
      case 2: return "예약취소";
      case 3: return "결제취소";
      default: return "오류";
    }
  }

}
