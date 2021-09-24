package com.ogong.pms.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoReservationListHandler extends AbstractCeoMemberHandler {

  List<CafeReservation> reserList;
  List<Cafe> cafeList;
  List<CafeRoom> cafeRoomList;

  public CeoReservationListHandler(List<CeoMember> ceoMemberList, List<CafeReservation> reserList,
      List<Cafe> cafeList, List<CafeRoom> cafeRoomList) {
    super(ceoMemberList);
    this.reserList = reserList;
    this.cafeList = cafeList;
    this.cafeRoomList = cafeRoomList;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 카페 선택");

    int cafeListCount = 0;
    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    for (Cafe cafe : cafeList) {

      if (cafe.getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        System.out.printf("\n (%s)\n [%s]\n" , cafe.getNo(), cafe.getName());
        cafeListCount++;
      }
    }

    if (cafeListCount == 0) {
      System.out.println(" >> 등록된 카페가 없습니다. ");
      return;
    }

    System.out.println();
    int cafeNo = Prompt.inputInt(" 번호 : ");

    myCafeReservationList(cafeNo);
  }

  private void myCafeReservationList(int cafeNo) {
    System.out.println();
    System.out.println("▶ 예약 내역");
    System.out.println();

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    List<CafeReservation> cafeReservationList = printMyCafeReserDetail(ceoMember, cafeNo);
    if (cafeReservationList.size() == 0) {
      System.out.println(" >> 예약 내역이 없습니다.");
      return;
    }

    System.out.println("----------------------");
    System.out.println("1. 예약 거절");
    System.out.println("0. 이전");
    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: rejectReservation(cafeReservationList); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }

  private void rejectReservation(List<CafeReservation> cafeReservationList) {
    System.out.println();
    System.out.println("▶ 예약 거절");
    System.out.println();

    int reservationNo = Prompt.inputInt(" 번호 : ");
    for (CafeReservation cafeReservation : cafeReservationList) {
      if (cafeReservation.getReservationNo() == reservationNo) {
        Date today = new Date(System.currentTimeMillis());
        Date reserDate = cafeReservation.getReservationDate();

        if (cafeReservation.getReservationStatus() == 2) {
          System.out.println(" >> 이미 취소 된 예약입니다.");
          return;
        } else if (cafeReservation.getReservationStatus() == 4) {
          System.out.println(" >> 이미 거절 한 예약입니다.");
          return;
        }

        if (reserDate.toLocalDate().compareTo(today.toLocalDate()) > 0) {

          String input = Prompt.inputString(" 정말 예약 거절 하시겠습니까? (네 / 아니오) ");

          if (!input.equalsIgnoreCase("네")) {
            System.out.println(" >> 예약 거절을 취소합니다.");
            return;
          }
          cafeReservation.setReservationStatus(4);
          //      reserList.remove(myReservation);
          System.out.println(" >> 예약을 거절하였습니다.");
          return;
        } else if (reserDate.toLocalDate().compareTo(today.toLocalDate()) == 0) {
          System.out.println(" >> 당일 예약은 거절 불가능합니다.");
          return;
        } else {
          System.out.println(" >> 지난 예약은 선택할 수 없습니다.");
          return;
        }
      }
    }
    System.out.println(" >> 예약 번호를 잘못 선택하셨습니다.");
    return;
  }

  private List<CafeReservation> printMyCafeReserDetail(CeoMember ceoMember, int input) {
    List<CafeReservation> myCafeReserList = new ArrayList<>();
    for (CafeReservation cafeReser : reserList) {

      if (cafeReser.getMember().getPerNo() == ceoMember.getCeoNo() &&
          cafeReser.getCafe().getNo() == input) {
        myCafeReserList.add(cafeReser);
        Cafe cafeReserCafe = cafeReser.getCafe();
        CafeRoom cafeRoom = getCafeRoom(cafeReser.getRoomNo());
        String reserStatusLable = getReservationStatus(cafeReser.getReservationStatus());

        if (cafeReser.getUseMemberNumber() == 0) {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 스터디룸 : %s\n"
              + " 결제금액 : %d원\n 상태 : %s\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeRoom.getRoomName()  
              , cafeReser.getTotalPrice(), reserStatusLable);
          System.out.println();  
        } else {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 사용인원 : %d명\n"
              + " 결제금액 : %d원\n 상태 : %s\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeReser.getUseMemberNumber()   
              , cafeReser.getTotalPrice(), reserStatusLable);
          System.out.println();
        }
      } 
    }
    return myCafeReserList;
  }

  private CafeRoom getCafeRoom(int roomNo) {
    for (CafeRoom cafeRoom : cafeRoomList) {
      if (cafeRoom.getRoomNo() == roomNo) {
        return cafeRoom;
      }
    }
    return null;
  }

  private String getReservationStatus(int status) {
    switch (status) {
      case 0: return "예약완료(현장결제)";
      case 1: return "결제완료";
      case 2: return "예약취소";
      case 3: return "결제취소";
      case 4: return "예약거절";
      case 5: return "결제거절";
      default: return "오류";
    }
  }
}
