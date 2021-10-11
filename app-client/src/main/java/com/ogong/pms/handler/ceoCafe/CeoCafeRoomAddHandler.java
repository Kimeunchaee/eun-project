package com.ogong.pms.handler.ceoCafe;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoCafeRoomAddHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeRoomAddHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디룸 등록");
    System.out.println();

    Cafe cafe = cafeDao.findByCafeNo((int) request.getAttribute("cafeNo"));

    CafeRoom cafeRoom = new CafeRoom();
    cafeRoom.setCafe(cafe);
    cafeRoom.setRoomName(Prompt.inputString(" 스터디룸 이름 : "));
    cafeRoom.setRoomImg(Prompt.inputString(" 스터디룸 사진 : "));
    cafeRoom.setRoomInfo(Prompt.inputString(" 스터디룸 설명 : "));
    cafeRoom.setStartTime(cafe.getOpenTime());
    cafeRoom.setEndTime(cafe.getCloseTime());
    cafeRoom.setRoomPrice(Prompt.inputInt(" 스터디룸 시간당금액 : "));

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }

    // 고유번호 + 1
    List<CafeRoom> cafeRoomList = cafeDao.getCafeRoomList();
    if (!cafeRoomList.isEmpty()) {
      cafeRoom.setRoomNo(cafeRoomList.get(cafeRoomList.size() - 1).getRoomNo() + 1);
    } else {
      cafeRoom.setRoomNo(1);
    }

    cafeDao.insertCafeRoom(cafeRoom);
  }
}
