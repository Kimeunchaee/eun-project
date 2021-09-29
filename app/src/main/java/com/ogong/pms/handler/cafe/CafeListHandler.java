package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.handler.AbstractCafeHandler;
import com.ogong.pms.handler.CommandRequest;

public class CafeListHandler extends AbstractCafeHandler {


  public CafeListHandler (List<Cafe> cafeList) {
    super (cafeList);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 목록");

    for(Cafe cafe : cafeList) {
      if (cafe.getCafeStatus() != 0 && cafe.getCafeStatus() != 3) {
        System.out.printf(" \n(%s)\n 이름 : %s\n 주소 : %s\n 예약가능인원 : %d\n"
            , cafe.getNo(), cafe.getName(), cafe.getLocation(), cafe.getBookable());
        if (cafe.getCafeStatus() == 2) {
          System.out.println(" * 운영 중단");
        }
      }
    }

    //    selectCafeDetailMenu(request);
  }

  //  private void selectCafeDetailMenu(CommandRequest request) throws Exception {
  //    System.out.println("\n----------------------");
  //    System.out.println("1. 상세");
  //    System.out.println("2. 검색");
  //    System.out.println("0. 이전");
  //    while (true) {
  //      int input = Prompt.inputInt(" 선택> ");
  //      switch (input) {
  //        case 1: request.getRequestDispatcher("/cafe/detail").forward(request); return;
  //        case 2: request.getRequestDispatcher("/cafe/search").forward(request); return;
  //        case 0: return;
  //        default : 
  //          System.out.println(" >> 번호를 다시 선택해 주세요.");
  //      }
  //    }
  //  }
}
