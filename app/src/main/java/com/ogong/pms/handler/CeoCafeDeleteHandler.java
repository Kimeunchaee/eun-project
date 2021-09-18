package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.util.Prompt;

public class CeoCafeDeleteHandler extends AbstractCafeHandler {

  PromptCafe promptcafe;

  public CeoCafeDeleteHandler (List<Cafe> cafeList, PromptCafe promptcafe) {
    super (cafeList);
    this.promptcafe = promptcafe;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 장소 삭제");

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 장소 삭제를 취소하였습니다.");
      return;
    }

    //    cafe.setName("");
    //    cafe.setMainImg("");
    //    cafe.setInfo("");
    //    cafe.setLocation("");
    //    cafe.setPhone("");
    //    cafe.setOpenTime(LocalTime.of(00, 00));
    //    cafe.setCloseTime(LocalTime.of(00, 00));
    //    cafe.setHoliday("");
    //    cafe.setBookable(0);
    //    cafe.setTimePrice(0);
    //    cafe.setCafeStatus(3);

    cafeList.remove(request.getAttribute("cafe"));


    System.out.println(" >> 장소를 삭제하였습니다.");
  }


}
