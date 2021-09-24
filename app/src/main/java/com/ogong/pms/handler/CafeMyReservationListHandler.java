package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class CafeMyReservationListHandler extends AbstractCafeHandler {

  List<CafeReservation> reserList;
  PromptPerMember promptPerMember;

  public CafeMyReservationListHandler (List<Cafe> cafeList,
      List<CafeReservation> reserList, PromptPerMember promptPerMember) {

    super (cafeList);
    this.reserList = reserList;
    this.promptPerMember = promptPerMember;

    //    CafeReservation reservation = new CafeReservation();
    //    reservation.setReservationNo(1);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-8-1"));
    //    reservation.setStartTime(LocalTime.of(10, 00));
    //    reservation.setUseTime(1);
    //    reservation.setUseMemberNumber(1);
    //    reservation.setTotalPrice(2000);
    //    reservation.setWirteReview(false);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
    //
    //    reservation = new CafeReservation();
    //    reservation.setReservationNo(2);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-10-10"));
    //    reservation.setStartTime(LocalTime.of(10, 00));
    //    reservation.setUseTime(3);
    //    reservation.setUseMemberNumber(0);
    //    reservation.setTotalPrice(45000);
    //    reservation.setWirteReview(false);
    //    reservation.setRoomNo(4);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
    //
    //    reservation = new CafeReservation();
    //    reservation.setReservationNo(3);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-10-10"));
    //    reservation.setStartTime(LocalTime.of(15, 00));
    //    reservation.setUseTime(2);
    //    reservation.setUseMemberNumber(0);
    //    reservation.setTotalPrice(30000);
    //    reservation.setWirteReview(false);
    //    reservation.setRoomNo(4);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 예약 내역 보기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser(); 

    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 볼 수 있습니다.");
      return;
    }
    int reservationCount = 0;

    for (CafeReservation myReservationList : reserList) {
      if (myReservationList.getMember().getPerNo() == member.getPerNo()) {
        System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n 결제금액 : %d원\n 상태 : %s\n"
            , myReservationList.getReservationNo(), myReservationList.getReservationDate(), 
            myReservationList.getCafe().getName(), myReservationList.getTotalPrice(),
            getReservationStatus(myReservationList.getReservationStatus()));
        System.out.println();
        reservationCount++;
      }
    }

    if (reservationCount == 0) {
      System.out.println(" >> 예약 내역이 존재하지 않습니다.");
      return;
    }

    System.out.println("----------------------");
    System.out.println("1. 상세");
    System.out.println("0. 이전");
    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/cafeReservation/detail").forward(request); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
