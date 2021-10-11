package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeMyReviewListHandler implements Command {

  CafeDao cafeDao;

  public CafeMyReviewListHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내가 쓴 후기 보기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 볼 수 있습니다.");
      return;
    }

    List<CafeReview> reviewList = cafeDao.findReviewListByMember(member.getPerNo());

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

      System.out.printf(" (%d)\n [%s]\n 별점 : %s (%d)\n 내용 : %s\n 등록일 : %s\n",
          cafeReview.getReviewNo(), 
          cafe.getName(), 
          CafeHandlerHelper.getReviewGradeStatusLabel(cafeReview.getGrade()), 
          cafeReview.getGrade(), 
          cafeReview.getContent(), 
          cafeReview.getRegisteredDate());
      System.out.println();
    }

    System.out.println("----------------------");
    System.out.println("1. 삭제");
    System.out.println("0. 이전");

    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/cafe/myReviewDelete").forward(request); return;
      default : return;
    }
  }
}
