package com.ogong.pms.handler;

import java.time.LocalTime;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoCafeUpdateHandler extends AbstractCeoMemberHandler {

  List<CeoMember> ceoMemberList;

  public CeoCafeUpdateHandler (List<CeoMember> ceoMemberList) {
    super (ceoMemberList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 장소 수정");

    Cafe cafe =(Cafe) request.getAttribute("cafe");

    String name = Prompt.inputString(String.format(" 상호명(%s) : ", cafe.getName()));
    String cafeLicenseNo = Prompt.inputString(String.format(" 사업자 등록번호(%s) : ", cafe.getCafeLicenseNo()));
    String mainImg = Prompt.inputString(String.format(" 대표이미지(%s) : ", cafe.getMainImg()));
    String Info = Prompt.inputString(String.format(" 소개글(%s) : ", cafe.getInfo()));
    String location = Prompt.inputString(String.format(" 주소(%s) : ", cafe.getLocation()));
    String phone = Prompt.inputString(String.format(" 전화번호(%s) : ", cafe.getPhone()));
    String openTime = Prompt.inputString(String.format(" 오픈시간(%s) : ", cafe.getOpenTime()));
    String closeTime = Prompt.inputString(String.format(" 마감시간(%s) : ", cafe.getCloseTime()));
    String holiday = Prompt.inputString(String.format(" 휴무일(%s) : ", cafe.getHoliday()));
    int bookable = Prompt.inputInt(String.format(" 예약가능인원(%d) : ", cafe.getBookable()));
    int timePrice = Prompt.inputInt(String.format(" 시간당금액(%d) : ", cafe.getTimePrice()));
    int cafeStatus = 0;
    if (cafe.getCafeStatus() != 0) {
      cafeStatus = promptCafeStatus(cafe.getCafeStatus());
    }

    String input = Prompt.inputString(" 정말 수정하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 수정을 취소하였습니다.");
      return;
    }

    cafe.setName(name);
    cafe.setCafeLicenseNo(cafeLicenseNo);
    cafe.setMainImg(mainImg);
    cafe.setInfo(Info);
    cafe.setLocation(location);
    cafe.setPhone(phone);
    cafe.setOpenTime(LocalTime.parse(openTime));
    cafe.setCloseTime(LocalTime.parse(closeTime));
    cafe.setHoliday(holiday);
    cafe.setBookable(bookable);
    cafe.setTimePrice(timePrice);
    cafe.setCafeStatus(cafeStatus);


    System.out.println(" >> 수정이 완료 되었습니다.");
  }


}
