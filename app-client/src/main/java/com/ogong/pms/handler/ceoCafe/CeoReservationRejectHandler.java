package com.ogong.pms.handler.ceoCafe;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoReservationRejectHandler implements Command {

  CafeDao cafeDao;

  public CeoReservationRejectHandler(CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 예약 거절");
    System.out.println();

    List<CafeReservation> cafeReservationList = cafeDao.getCafeReservationList(); 

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
          //          cafeReservation.setReservationStatus(4);
          //      reserList.remove(myReservation);
          cafeDao.deleteReservation(cafeReservation, 4);
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
}
