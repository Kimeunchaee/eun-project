package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.handler.AbstractCafeHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeSearchHandler extends AbstractCafeHandler {

  public CafeSearchHandler (List<Cafe> cafeList) {
    super (cafeList);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 검색");
    System.out.println();
    int count = 0;
    String input = Prompt.inputString(" 지역 : ");
    for (Cafe cafe : cafeList) {
      if (cafe.getLocation().contains(input)) {
        System.out.printf("\n (%s)\n 이름 : %s\n 주소 : %s\n 예약가능인원 : %d\n"
            , cafe.getNo(), cafe.getName(), cafe.getLocation(), cafe.getBookable());
        count++;
      }
    }
    if (count == 0) {
      System.out.println(" >> 검색 결과가 존재하지 않습니다.");
      //      System.out.println("\n----------------------");
      //      System.out.println("1. 검색");
      //      System.out.println("0. 이전");
      //      while (true) {
      //        int selectInput = Prompt.inputInt("선택> ");
      //        switch (selectInput) {
      //          case 1: request.getRequestDispatcher("/cafe/search").forward(request); return;
      //          case 0: return;
      //          default : System.out.println(" >> 번호를 다시 선택해 주세요.");
      //        }
      //      }
      //    } else {
      //      System.out.println("\n----------------------");
      //      System.out.println("1. 상세");
      //      System.out.println("0. 이전");
      //      while(true) {
      //        int selectInput = Prompt.inputInt("선택> ");
      //        switch (selectInput) {
      //          case 1: request.getRequestDispatcher("/cafe/detail").forward(request); return;
      //          case 0: return;
      //          default : 
      //            System.out.println(" >> 번호를 다시 선택해 주세요.");
      //        }
      //      }
    }
  }
}
