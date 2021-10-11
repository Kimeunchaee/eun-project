package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeMyReservationListHandler implements Command {

  CafeDao cafeDao;

  public CafeMyReservationListHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
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

    List<CafeReservation> reserList = 
        cafeDao.findReservationListByMember(member.getPerNo());

    if (reserList.isEmpty()) {
      System.out.println(" >> 예약 내역이 존재하지 않습니다.");
      return;
    } else {
      for (CafeReservation myReservationList : reserList) {
        System.out.printf(" (%d)\n 예약날짜 : %s\n 예약장소 : %s\n 결제금액 : %d원\n 상태 : %s\n", 
            myReservationList.getReservationNo(), 
            myReservationList.getReservationDate(), 
            myReservationList.getCafe().getName(), 
            myReservationList.getTotalPrice(),
            CafeHandlerHelper.getReservationStatus(myReservationList.getReservationStatus()));
        System.out.println();
      }
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
