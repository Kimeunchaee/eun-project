package com.ogong.pms.handler;

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
    // 구현중

    int cafeListCount = 0;
    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    for (Cafe cafe : cafeList) {

      if (cafe.getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        System.out.printf("\n (%s)\n 이름 : %s\n" , cafe.getNo(), cafe.getName());
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

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    List<CafeReservation> cafeReservationList = printMyCafeReserDetail(ceoMember, cafeNo);
    if (cafeReservationList.size() == 0) {
      System.out.println(" >> 예약 내역이 없습니다.");
      return;
    }
  }

  private List<CafeReservation> printMyCafeReserDetail(CeoMember ceoMember, int input) {
    List<CafeReservation> myCafeReserList = new ArrayList<>();
    for (CafeReservation cafeReser : reserList) {

      if (cafeReser.getMember().getPerNo() == ceoMember.getCeoNo() &&
          cafeReser.getCafe().getNo() == input) {
        myCafeReserList.add(cafeReser);
        Cafe cafeReserCafe = cafeReser.getCafe();
        CafeRoom cafeRoom = getCafeRoom(cafeReser.getRoomNo());
        if (cafeReser.getUseMemberNumber() == 0) {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 스터디룸 : %s\n"
              + " 결제금액 : %d원\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeRoom.getRoomName()  
              , cafeReser.getTotalPrice());
          System.out.println();  
        } else {
          System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n"
              + " 시작시간 : %s\n 이용시간 : %s시간\n 사용인원 : %d명\n"
              + " 결제금액 : %d원\n"
              , cafeReser.getReservationNo(), cafeReser.getReservationDate(), cafeReserCafe.getName()
              , cafeReser.getStartTime(), cafeReser.getUseTime(), cafeReser.getUseMemberNumber()   
              , cafeReser.getTotalPrice());
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
}
