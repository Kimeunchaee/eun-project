package com.ogong.pms.handler.ceoCafe;

import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoCafeRoomUpdateHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeRoomUpdateHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디룸 수정");
    System.out.println();

    CafeRoom cafeRoom = (CafeRoom) request.getAttribute("cafeRoom");

    String name = Prompt.inputString(String.format(" 스터디룸 이름(%s) : ", cafeRoom.getRoomName()));
    String mainImg = Prompt.inputString(String.format(" 스터디룸 사진(%s) : ", cafeRoom.getRoomImg()));
    String Info = Prompt.inputString(String.format(" 스터디룸 설명(%s) : ", cafeRoom.getRoomInfo()));
    int timePrice = Prompt.inputInt(String.format(" 스터디룸 시간당금액(%d) : ", cafeRoom.getRoomPrice()));

    System.out.println();
    String input = Prompt.inputString(" 정말 수정하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 수정을 취소하였습니다.");
      return;
    }

    cafeRoom.setRoomName(name);
    cafeRoom.setRoomImg(mainImg);
    cafeRoom.setRoomInfo(Info);
    cafeRoom.setRoomPrice(timePrice);

    cafeDao.updateCafeRoom(cafeRoom);
  }
}
