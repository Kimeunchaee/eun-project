package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeSearchHandler implements Command {

  CafeDao cafeDao;

  public CafeSearchHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 검색");
    System.out.println();

    String input = Prompt.inputString(" 지역 : ");

    List<Cafe> cafeList = cafeDao.findCafeListByLocation(input);

    if (!cafeList.isEmpty()) {
      for (Cafe cafe : cafeList) {
        System.out.printf("\n (%s)\n 이름 : %s\n 주소 : %s\n 예약가능인원 : %d\n", 
            cafe.getNo(), 
            cafe.getName(), 
            cafe.getLocation(), 
            cafe.getBookable());
      }
    } else {
      System.out.println(" >> 검색 결과가 존재하지 않습니다.");
    }
  }
}
