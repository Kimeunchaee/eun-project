package com.ogong.pms.handler.cafe;

import java.sql.Date;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeMyReservationDetailHandler implements Command {

  CafeDao cafeDao;

  public CafeMyReservationDetailHandler(CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
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
      case 1: goToAddReview(request, myReservation); return;
      case 2: 
        request.setAttribute("myReservation", myReservation);
        request.getRequestDispatcher("/cafeReservation/delete").forward(request);
        return;
      case 0: return;
      default :
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void goToAddReview(CommandRequest request,CafeReservation myReservation) throws Exception {
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
        request.setAttribute("reservationNo", myReservation.getReservationNo());
        request.setAttribute("cafeNo", myReservation.getCafe().getNo());
        request.getRequestDispatcher("/cafe/myReviewAdd").forward(request);
        return;

      } else {
        System.out.println(" >> 이미 리뷰를 작성한 예약입니다.");
        return;
      }
    } else {
      System.out.println(" >> 이용 후 다음 날부터 작성 가능합니다.");
      return;
    }
  }

  private CafeReservation printMyReserDetail(Member member, int input) throws Exception {
    CafeReservation cafeReser = 
        cafeDao.findReservationByMember(member.getPerNo(), input);

    if (cafeReser != null) {
      Cafe cafeReserCafe = cafeDao.findByCafeNo(cafeReser.getCafe().getNo());
      CafeRoom cafeRoom = cafeDao.findByRoomNo(cafeReser.getRoomNo());

      String reviewStatusLable = CafeHandlerHelper.getReviewStatusLabel(
          String.valueOf(cafeReser.getWirteReview()));
      String reserStatusLable = CafeHandlerHelper.getReservationStatus(
          cafeReser.getReservationStatus());

      if (cafeReser.getReservationStatus() != 0 && cafeReser.getReservationStatus() != 1) {
        reviewStatusLable = "작성불가";
      }

      if (cafeReser.getUseMemberNumber() == 0) {
        System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
            + " 이용시간 : %s ~ %s (%s시간)\n 스터디룸 : %s\n"
            + " 결제금액 : %d원\n 리뷰작성여부 : %s\n 상태 : %s\n"
            , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
            , cafeReser.getStartTime(), cafeReser.getStartTime().plusHours(cafeReser.getUseTime())
            , cafeReser.getUseTime(), cafeRoom.getRoomName(), cafeReser.getTotalPrice() 
            , reviewStatusLable , reserStatusLable);
        System.out.println();  
        return cafeReser;
      } else {
        System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
            + " 이용시간 : %s ~ %s (%s시간)\n 사용인원 : %d명\n"
            + " 결제금액 : %d원\n 리뷰작성여부 : %s\n 상태 : %s\n"
            , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
            , cafeReser.getStartTime(), cafeReser.getStartTime().plusHours(cafeReser.getUseTime())
            , cafeReser.getUseTime(), cafeReser.getUseMemberNumber(), cafeReser.getTotalPrice() 
            , reviewStatusLable, reserStatusLable);
        System.out.println();
        return cafeReser;
      }
    } else {
      return null;
    }
  }
}
