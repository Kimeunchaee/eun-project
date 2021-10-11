package com.ogong.pms.handler.ceoCafe;

import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoCafeRoomDeleteHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeRoomDeleteHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디룸 삭제");
    System.out.println();

    CafeRoom cafeRoom = (CafeRoom) request.getAttribute("cafeRoom");

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디룸 삭제를 취소하였습니다.");
      return;
    }

    cafeDao.deleteCafeRoom(cafeRoom.getRoomNo());
  }

}
