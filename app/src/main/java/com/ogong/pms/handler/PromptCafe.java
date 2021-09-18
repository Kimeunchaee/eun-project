package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;

public class PromptCafe {

  List<Cafe> cafeList;
  List<CafeReview> cafeReviewList;

  public PromptCafe (List<Cafe> cafeList, List<CafeReview> cafeReviewList) {
    this.cafeList = cafeList;
    this.cafeReviewList = cafeReviewList;
  }

  public Cafe findByCafeNo(int no) {
    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == no && cafe.getCafeStatus() != 0 && cafe.getCafeStatus() != 3) {
        return cafe;
      }
    }
    return null;
  }

  public Cafe findByCafeName(String name) {
    for (Cafe cafe : cafeList) {
      if (cafe.getName().equals(name) && cafe.getCafeStatus() != 0 && cafe.getCafeStatus() != 3) {
        return cafe;
      }
    }
    return null;
  }

  public CafeReview findByCafeReview (int reviewNo) {
    for (CafeReview cafeReview : cafeReviewList) {
      if (cafeReview.getReviewNo() == reviewNo) {
        return cafeReview;
      }
    }
    return null;
  }

}
