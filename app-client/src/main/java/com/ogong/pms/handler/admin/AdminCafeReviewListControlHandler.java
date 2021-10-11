package com.ogong.pms.handler.admin;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminCafeReviewListControlHandler implements Command{

  CafeDao cafeDao;

  public AdminCafeReviewListControlHandler(CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 후기 목록");
    System.out.println();

    List<CafeReview> reviewList = cafeDao.getCafeReviewList();

    if (reviewList.isEmpty()) {
      System.out.println(" >> 리뷰 내역이 존재하지 않습니다.");
      return;
    }

    for (CafeReview cafeReview : reviewList) {
      if (cafeReview.getReviewStatus() == 1) {
        System.out.printf(" \n (%s)", cafeReview.getReviewNo());
        System.out.println(" >> 삭제한 리뷰입니다.\n");
        continue;
      }

      Cafe cafe = cafeDao.findByCafeNo(cafeReview.getCafe().getNo());
      System.out.printf(" (%d)\n [%s]\n 별점 : %d\n 내용 : %s\n 등록일 : %s\n",
          cafeReview.getReviewNo(), 
          cafe.getName(), 
          cafeReview.getGrade(),
          cafeReview.getContent(), 
          cafeReview.getRegisteredDate());
      System.out.println();
    }

    System.out.println("----------------------");
    System.out.println("1. 리뷰 삭제");
    System.out.println("0. 뒤로 가기");

    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/cafe/reviewListDelete").forward(request); return;
      case 0: return;
      default :
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }
}
