package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.util.Prompt;

public class AdminCafeControlHandler extends AbstractCafeHandler {

  List<CafeReview> reviewList;
  PromptPerMember promptPerMember;
  PromptCafe promptcafe;

  public AdminCafeControlHandler (List<Cafe> cafeList, List<CafeReview> reviewList,
      PromptPerMember promptPerMember, PromptCafe promptcafe) {
    super (cafeList);
    this.reviewList = reviewList;
    this.promptPerMember = promptPerMember;
    this.promptcafe = promptcafe;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 장소 목록");

    if (cafeList.isEmpty()) {
      System.out.println(" >> 등록된 장소가 없습니다.");
      return;
    }

    for(Cafe cafe : cafeList) {
      if (cafe.getCafeStatus() == 3) {
        System.out.printf(" \n (%s)\n", cafe.getNo());
        System.out.println(" 삭제 된 장소입니다.");
        continue;
      }
      System.out.printf(" \n (%s)\n 이름 : %s\n 주소 : %s\n 예약가능인원 : %d\n"
          , cafe.getNo(), cafe.getName(), cafe.getLocation(), cafe.getBookable());
      if (cafe.getCafeStatus() == 0) {
        System.out.println(" * 승인 대기중인 카페입니다.");
      }
    }

    selectCafeDetailMenu();
  }

  private void selectCafeDetailMenu() {
    System.out.println("\n----------------------");
    System.out.println("1. 상세");
    System.out.println("2. 승인");
    System.out.println("0. 이전");
    int input = Prompt.inputInt("선택> ");
    switch (input) {
      case 1: detail(); break;
      case 2: approvalCafe(); break;
      default : return;
    }
  }

  public void detail() {
    System.out.println();
    System.out.println("▶ 장소 상세");
    System.out.println();

    Cafe cafe = promptcafe.findByCafeNo(Prompt.inputInt(" 장소 번호 : "));
    System.out.println();

    if (cafe == null) {
      System.out.println(" >> 해당 번호의 장소가 존재하지 않습니다.");
      return;
    }
    System.out.printf(" (%s)\n", cafe.getNo());
    System.out.printf(" [%s]\n", cafe.getName());
    System.out.printf(" >> 대표 이미지 : %s\n", cafe.getMainImg());
    System.out.printf(" >> 소개글 : %s\n", cafe.getInfo());
    System.out.printf(" >> 주소 : %s\n", cafe.getLocation());
    System.out.printf(" >> 번호 : %s\n", cafe.getPhone());
    System.out.printf(" >> 오픈 시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감 시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 휴무일 : %s\n", cafe.getHoliday());
    System.out.printf(" >> 예약 가능 인원 : %d\n", cafe.getBookable());
    System.out.printf(" >> 시간당 금액 : %d원\n", cafe.getTimePrice());
    System.out.println();
    System.out.println("============= 리뷰 =============");
    int reviewSize = 0;
    for (CafeReview review : reviewList) {
      if (review.getCafe().getNo() == cafe.getNo()) {
        String nickname = promptPerMember.findByMemberNo(review.getMember().getPerNo()).getPerNickname();
        System.out.printf(" 닉네임 : %s | 별점 : %d | 내용 : %s | 등록일 : %s\n",
            nickname, review.getGrade(), review.getContent(), review.getRegisteredDate());
        reviewSize++;
      }
    }
    if (reviewSize == 0) {
      System.out.println(" >> 등록된 리뷰가 없습니다.");
    }

    System.out.println();

    System.out.println("1. 장소 삭제");
    System.out.println("0. 뒤로 가기");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : delete(); break;
      default : return;
    }
  }

  public void delete() {
    System.out.println();
    System.out.println("▶ 장소 삭제");
    System.out.println();

    Cafe cafe = promptcafe.findByCafeNo(Prompt.inputInt(" 장소 번호 : "));

    if (cafe == null) {
      System.out.println(" >> 해당 번호의 장소가 존재하지 않습니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    System.out.println();
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 장소 삭제를 취소하였습니다.");
      return;
    }

    cafeList.remove(cafe);

    System.out.println(" >> 장소를 삭제하였습니다.");
  }

  private void approvalCafe() {
    System.out.println();
    System.out.println("▶ 장소 승인");

    while (true) {
      System.out.println();
      int inputCafeNo = Prompt.inputInt(" 장소 번호 : ");

      Cafe cafe = findByCafeNo(inputCafeNo);

      if (cafe == null) {
        System.out.println(" >> 번호를 다시 선택하세요.");
        continue;
      } else if (cafe.getCafeStatus() != 0) {
        System.out.println(" >> 승인 대기 중인 카페가 아닙니다.\n    번호를 다시 선택하세요.");
        continue;
      } else if (cafe.getCafeStatus() == 0) {
        String input = Prompt.inputString(" 정말 승인하시겠습니까? (네 / 아니오) ");
        System.out.println();
        if (!input.equalsIgnoreCase("네")) {
          System.out.println(" >> 장소 승인을 취소하였습니다.");
          return;
        }
        cafe.setCafeStatus(1);
        System.out.printf(" >> '%s'를 승인하였습니다.\n", cafe.getName());
        return;
      }
    }
  }

  // PromptCafe에 있는 findByCafeNo랑 조건 다름!
  private Cafe findByCafeNo(int inputCafeNo) {
    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == inputCafeNo) {
        return cafe;
      }
    }
    return null;
  }
}
