package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoCafeDetailHandler extends AbstractCeoMemberHandler {

  List<CeoMember> ceoMemberList;
  List<Cafe> cafeList;
  List<CafeReview> reviewList;

  public CeoCafeDetailHandler (List<CeoMember> ceoMemberList, List<Cafe> cafeList,
      List<CafeReview> reviewList) {
    super (ceoMemberList);
    this.cafeList = cafeList;
    this.reviewList = reviewList;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 상세보기");
    System.out.println();

    Cafe cafe = findByNo(Prompt.inputInt(" 번호 : "));
    System.out.println();
    if (cafe == null) {
      System.out.println(" >> 번호를 다시 선택하세요.");
      return;
    }
    System.out.printf(" (%s)\n", cafe.getNo());
    System.out.printf(" [%s]\n", cafe.getName());
    System.out.printf(" >> 사업자 등록번호 : %s\n", cafe.getCafeLicenseNo());
    System.out.printf(" >> 대표이미지 : %s\n", cafe.getMainImg());
    System.out.printf(" >> 소개글 : %s\n", cafe.getInfo());
    System.out.printf(" >> 주소 : %s\n", cafe.getLocation());
    System.out.printf(" >> 번호 : %s\n", cafe.getPhone());
    System.out.printf(" >> 오픈시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 휴무일 : %s\n", cafe.getHoliday());
    System.out.printf(" >> 예약가능 인원 : %d\n", cafe.getBookable());
    System.out.printf(" >> 시간당 금액 : %d원\n", cafe.getTimePrice());
    System.out.printf(" >> 상태 : %s\n", getCafeStatusLabel(cafe.getCafeStatus()));
    System.out.println();
    System.out.println("============= 리뷰 =============");
    int reviewSize = 0;
    for (CafeReview review : reviewList) {
      if (review.getCafe().getNo() == cafe.getNo()) {
        String nickname = review.getMember().getPerNickname();
        System.out.printf(" 닉네임 : %s | 별점 : %d | 내용 : %s | 등록일 : %s\n",
            nickname, review.getGrade(), review.getContent(), review.getRegisteredDate());
        reviewSize++;
      }
    }
    if (reviewSize == 0) {
      System.out.println(" >> 등록된 리뷰가 없습니다.");
    }

    request.setAttribute("cafe", cafe);

    System.out.println();
    System.out.println("----------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 스터디룸 관리");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/ceoMember/cafeUpdate").forward(request); return;
      case 2: request.getRequestDispatcher("/ceoMember/cafeDelete").forward(request); return;
      case 3: controlStudyRoom(cafe);
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void controlStudyRoom(Cafe cafe) {
    System.out.println();
    System.out.println("▶ 스터디룸 관리");
    System.out.println();
    // 구현중

    System.out.println("1. 등록");
    System.out.println("2. 목록");
    System.out.println("3. 수정");
    System.out.println("4. 삭제");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: return;
      case 2: return;
      case 3: return;
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private Cafe findByNo(int cafeNo) {
    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == cafeNo && cafe.getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        return cafe;
      }
    }
    return null;
  }
}
