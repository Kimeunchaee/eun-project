package com.ogong.pms.handler;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class CafeMyReservationDetailHandler extends AbstractCafeHandler {

  List<CafeReview> reviewList;
  List<CafeReservation> reserList;
  List<CafeRoom> roomList;
  int reviewNo = 1; // 리뷰번호

  public CafeMyReservationDetailHandler (List<Cafe> cafeList, List<CafeReview> reviewList, 
      List<CafeReservation> reserList, List<CafeRoom> roomList) {

    super (cafeList);
    this.reviewList = reviewList;
    this.reserList = reserList;
    this.roomList = roomList;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 내 예약 내역 상세 보기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser(); 

    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 볼 수 있습니다.");
      return;
    }

    int input = Prompt.inputInt(" 번호 : ");
    System.out.println();

    CafeReservation myReservation = printMyReserDetail(member, input);
    if (myReservation == null) {
      System.out.println(" >> 번호를 다시 선택하세요. ");
      return;
    }

    if (myReservation.getReservationStatus() != 0 && myReservation.getReservationStatus() != 1) {
      System.out.println("----------------------");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 0: return;
      }
    }

    System.out.println("----------------------");
    System.out.println("1. 리뷰 등록");
    System.out.println("2. 예약 취소");
    System.out.println("0. 이전");

    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1: goToAddReview(myReservation); return;
      case 2: cancelReservation(myReservation); return;
      case 0: return;
      default :
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void goToAddReview(CafeReservation myReservation) {
    System.out.println();

    if (myReservation == null) {
      System.out.println(" >> 예약번호를 잘못 선택하셨습니다.");
      return;
    }

    Date today = new Date(System.currentTimeMillis());
    Date reserDate = myReservation.getReservationDate();

    if (reserDate.toLocalDate().compareTo(today.toLocalDate()) < 0) {
      if (!myReservation.getWirteReview()) {
        System.out.println(" >> 리뷰 작성 화면으로 이동합니다.");
        addReview(myReservation);
      } else {
        System.out.println(" >> 이미 리뷰를 작성한 예약입니다.");
        return;
      }
    } else {
      System.out.println(" >> 이용 후 다음 날부터 작성 가능합니다.");
      return;
    }
  }

  protected void addReview(CafeReservation myReservation) {
    System.out.println();
    System.out.println("▶ 리뷰 등록하기");

    if (AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 로그인 한 회원만 등록 가능합니다.");
    } else {

      Cafe cafe = myReservation.getCafe();

      CafeReview cafeReview = new CafeReview();

      String content = Prompt.inputString(" 리뷰 내용 : ");
      int grade = Prompt.inputInt(" 별점(0~5점) : ");
      while (grade < 0 || grade > 5) {
        System.out.println(" 별점을 다시 입력해 주세요.");
        grade = Prompt.inputInt(" 별점(0~5점) : ");
      }
      Member member = AuthPerMemberLoginHandler.getLoginUser();
      Date registeredDate = new Date(System.currentTimeMillis());

      String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 리뷰 등록을 취소하였습니다.");
        return;
      }

      cafeReview.setReviewNo(reviewNo++);
      cafeReview.setContent(content);
      cafeReview.setGrade(grade);
      cafeReview.setCafe(cafe);
      cafeReview.setMember(member);
      cafeReview.setRegisteredDate(registeredDate);
      cafeReview.setReviewStatus(0);

      reviewList.add(cafeReview);
      myReservation.setWirteReview(true);

      System.out.println(" >> 리뷰가 등록되었습니다.");
    }
  }

  private void cancelReservation(CafeReservation myReservation) {
    System.out.println();

    if (myReservation == null) {
      System.out.println(" >> 예약 번호를 잘못 선택하셨습니다.");
      return;
    }

    Date today = new Date(System.currentTimeMillis());
    Date reserDate = myReservation.getReservationDate();

    if (reserDate.toLocalDate().compareTo(today.toLocalDate()) > 0) {

      String input = Prompt.inputString(" 정말 예약 취소 하시겠습니까? (네 / 아니오) ");

      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 예약 취소를 취소합니다.");
        return;
      }
      myReservation.setReservationStatus(2);
      //      reserList.remove(myReservation);
      System.out.println(" >> 예약이 취소되었습니다.");
    } else if (reserDate.toLocalDate().compareTo(today.toLocalDate()) == 0) {
      System.out.println(" >> 당일 예약은 취소 불가능합니다.");
      return;
    } else {
      System.out.println(" >> 지난 예약은 선택할 수 없습니다.");
      return;
    }
  }

  private CafeReservation printMyReserDetail(Member member, int input) {

    for (CafeReservation cafeReser : reserList) {

      if (cafeReser.getMember().getPerNo() == member.getPerNo() &&
          cafeReser.getReservationNo() == input) {
        Cafe cafeReserCafe = cafeReser.getCafe();
        CafeRoom cafeRoom = getCafeRoom(cafeReser.getRoomNo());
        String reviewStatusLable = getReviewStatusLabel(String.valueOf(cafeReser.getWirteReview()));
        String reserStatusLable = getReservationStatus(cafeReser.getReservationStatus());

        if (cafeReser.getReservationStatus() != 0 && cafeReser.getReservationStatus() != 1) {
          reviewStatusLable = "작성불가";
        }

        if (cafeReser.getUseMemberNumber() == 0) {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 스터디룸 : %s\n"
              + " 결제금액 : %d원\n 리뷰작성여부 : %s\n 상태 : %s\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeRoom.getRoomName()  
              , cafeReser.getTotalPrice() , reviewStatusLable , reserStatusLable);
          System.out.println();  
          return cafeReser;
        } else {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 사용인원 : %d명\n"
              + " 결제금액 : %d원\n 리뷰작성여부 : %s\n 상태 : %s\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeReser.getUseMemberNumber()   
              , cafeReser.getTotalPrice() , reviewStatusLable , reserStatusLable);
          System.out.println();
          return cafeReser;
        }
      } 
    }
    return null;
  }

  private CafeRoom getCafeRoom(int roomNo) {
    for (CafeRoom cafeRoom : roomList) {
      if (cafeRoom.getRoomNo() == roomNo) {
        return cafeRoom;
      }
    }
    return null;
  }
}
