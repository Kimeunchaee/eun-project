package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminCafeReviewListDeleteHandler implements Command{

  CafeDao cafeDao;

  public AdminCafeReviewListDeleteHandler(CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 리뷰 삭제하기");
    System.out.println();

    int userReviewNo = Prompt.inputInt(" 번호 : ");

    CafeReview cafeReview = cafeDao.findByReviewNo(userReviewNo);

    if (cafeReview == null) {
      return;
    }

    if (cafeReview.getReviewStatus() == 1) {
      System.out.println(" >> 이미 삭제된 리뷰입니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 /아니오) ");
    System.out.println();
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소합니다.");
      return;
    }

    cafeDao.deleteCafeReview(userReviewNo);
  }
}
