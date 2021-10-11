package com.ogong.pms.handler.ceoCafe;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoCafeRoomListHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeRoomListHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디룸 관리");
    System.out.println();

    int cafeNo = (int) request.getAttribute("cafeNo");

    List<CafeRoom> cafeRoom = cafeDao.findCafeRoomListByCafe(cafeNo);

    if (cafeRoom.isEmpty()) {
      System.out.println(" >> 등록된 스터디룸이 없습니다.");
      System.out.println("\n----------------------");
      System.out.println("1. 등록");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt(" 선택> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/ceoMember/cafeRoomAdd").forward(request); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요."); return;
      }
    }

    int i = 1;
    HashMap<Integer, Integer> selectRoomNo = new HashMap<>();
    for (CafeRoom myCafeRoom : cafeRoom) {
      System.out.println(" " + i + ". " + myCafeRoom.getRoomName());
      selectRoomNo.put(i, myCafeRoom.getRoomNo());
      i++;
    }

    request.setAttribute("cafeNo", cafeNo);

    System.out.println("\n----------------------");
    System.out.println("1. 등록");
    System.out.println("2. 상세");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/ceoMember/cafeRoomAdd").forward(request); return;
      case 2: 
        request.setAttribute("selectRoomNo", selectRoomNo);
        request.getRequestDispatcher("/ceoMember/cafeRoomDetail").forward(request); return;
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }
}
