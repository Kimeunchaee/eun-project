package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeMyReviewDeleteHandler implements Command {

  CafeDao cafeDao;

  public CafeMyReviewDeleteHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    int inputNo = Prompt.inputInt(" 삭제할 리뷰 번호 : ");

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 삭제할 수 있습니다.");
      return;
    }

    CafeReview myReviewByNo = getMyReviewByNo(member, inputNo);

    if (myReviewByNo == null) {
      System.out.println(" >> 리뷰 번호를 잘못 선택하셨습니다.");
      return;
    }

    if (myReviewByNo.getReviewStatus() == 1) {
      System.out.println(" >> 이미 삭제한 리뷰입니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 /아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소합니다.");
      return;
    }

    cafeDao.deleteCafeReview(myReviewByNo.getReviewNo());
  }

  private CafeReview getMyReviewByNo(Member member, int reviewNo) throws Exception {
    List<CafeReview> reviewList = cafeDao.getCafeReviewList();
    for (CafeReview cafeReview : reviewList) {
      if (cafeReview.getMember().getPerNo() == member.getPerNo() &&
          cafeReview.getReviewNo() == reviewNo) {
        return cafeReview;
      }
    }
    return null;
  }
}
