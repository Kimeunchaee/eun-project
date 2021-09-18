package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoMyCafeListHandler extends AbstractCeoMemberHandler {

  List<Cafe> cafeList;
  List<CafeReview> reviewList;
  PromptPerMember promptPerMember;

  public CeoMyCafeListHandler(List<CeoMember> ceoMemberList, List<Cafe> cafeList
      , List<CafeReview> reviewList, PromptPerMember promptPerMember) {
    super(ceoMemberList);
    this.cafeList = cafeList;
    this.reviewList = reviewList;
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 카페 목록");

    try {
      CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

      for (Cafe cafe : cafeList) {
        //        if (cafe.getCafeStatus() == 3) {
        //          System.out.printf(" \n (%s)\n", cafe.getNo());
        //          System.out.println(" 삭제 된 장소입니다.");
        //          continue;
        //        }
        //        if (cafe.getCafeStatus() == 3) {
        //          continue;
        //        }
        if (cafe.getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
          System.out.printf("\n (%s)\n 이름 : %s\n 주소 : %s\n 예약가능인원 : %d\n"
              , cafe.getNo(), cafe.getName(), cafe.getLocation(), cafe.getBookable());
          if (cafe.getCafeStatus() == 0) {
            System.out.println(" * 승인 대기중인 카페입니다.");
          }
        }
      }

    } catch (NullPointerException e) {
      System.out.println(" >> 로그인 하세요.");
    }

    System.out.println("\n----------------------");
    System.out.println("1. 상세");
    System.out.println("2. 등록");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : request.getRequestDispatcher("/ceoMember/myCafeDetail").forward(request); break;
      case 2 : request.getRequestDispatcher("/ceoMember/cafeAdd").forward(request); break;
      default : return;
    }
  }
}
