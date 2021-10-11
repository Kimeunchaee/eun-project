package com.ogong.pms.handler.ceoCafe;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.cafe.CafeHandlerHelper;
import com.ogong.util.Prompt;

public class CeoReservationDetailHandler implements Command {

  CafeDao cafeDao;

  public CeoReservationDetailHandler(CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 예약 내역");
    System.out.println();

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();
    int cafeNo = (int) request.getAttribute("cafeNo");
    List<CafeReservation> cafeReservationList = printMyCafeReserDetail(ceoMember, cafeNo);
    if (cafeReservationList.size() == 0) {
      System.out.println(" >> 예약 내역이 없습니다.");
      return;
    }

    request.setAttribute("cafeReservationList", cafeReservationList);

    System.out.println("----------------------");
    System.out.println("1. 예약 거절");
    System.out.println("0. 이전");
    while (true) {
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1: 
          request.getRequestDispatcher("/ceoMember/ReservationReject").forward(request); 
          return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }

  private List<CafeReservation> printMyCafeReserDetail(CeoMember ceoMember, int input) throws Exception {
    List<CafeReservation> myCafeReserList = new ArrayList<>();
    List<CafeReservation> reserList = 
        cafeDao.findReservationListByCeoMember(ceoMember.getCeoNo(), input);

    for (CafeReservation cafeReser : reserList) {
      myCafeReserList.add(cafeReser);
      Cafe cafeReserCafe = cafeDao.findByCafeNo(cafeReser.getCafe().getNo());
      CafeRoom cafeRoom = cafeDao.findByRoomNo(cafeReser.getRoomNo());
      String reserStatusLable = 
          CafeHandlerHelper.getReservationStatus(cafeReser.getReservationStatus());

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
    return myCafeReserList;
  }
}
