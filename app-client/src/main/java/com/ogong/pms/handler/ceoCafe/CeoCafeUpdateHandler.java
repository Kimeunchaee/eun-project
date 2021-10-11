package com.ogong.pms.handler.ceoCafe;

import java.time.LocalTime;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Address;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.cafe.CafeHandlerHelper;
import com.ogong.util.AddressSearchApi;
import com.ogong.util.Prompt;

public class CeoCafeUpdateHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeUpdateHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 수정");
    System.out.println();

    Cafe cafe = cafeDao.findByCafeNo((int) request.getAttribute("cafeNo"));

    String name = Prompt.inputString(String.format(" 상호명(%s) : ", cafe.getName()));
    String cafeLicenseNo = Prompt.inputString(String.format(" 사업자 등록번호(%s) : ", cafe.getCafeLicenseNo()));
    String mainImg = Prompt.inputString(String.format(" 대표이미지(%s) : ", cafe.getMainImg()));
    String Info = Prompt.inputString(String.format(" 소개글(%s) : ", cafe.getInfo()));
    System.out.printf(" 기존 주소 : %s\n", cafe.getLocation());

    AddressSearchApi api = new AddressSearchApi();
    Address address = api.searchAddress();
    String addressString = address.getLnmAdres();
    System.out.println(" 바꿀 주소 : " + addressString);
    addressString += " " + Prompt.inputString(" 상세 주소 : ");

    String phone = Prompt.inputString(String.format(" 전화번호(%s) : ", cafe.getPhone()));
    String openTime = Prompt.inputString(String.format(" 오픈시간(%s) : ", cafe.getOpenTime()));
    String closeTime = Prompt.inputString(String.format(" 마감시간(%s) : ", cafe.getCloseTime()));
    String holiday = Prompt.inputString(String.format(" 휴무일(%s) : ", cafe.getHoliday()));
    int bookable = Prompt.inputInt(String.format(" 예약가능인원(%d) : ", cafe.getBookable()));
    int timePrice = Prompt.inputInt(String.format(" 시간당금액(%d) : ", cafe.getTimePrice()));
    int cafeStatus = 0;
    if (cafe.getCafeStatus() != 0) {
      cafeStatus = CafeHandlerHelper.promptCafeStatus(cafe.getCafeStatus());
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
    cafe.setLocation(addressString);
    cafe.setPhone(phone);
    cafe.setOpenTime(LocalTime.parse(openTime));
    cafe.setCloseTime(LocalTime.parse(closeTime));
    cafe.setHoliday(holiday);
    cafe.setBookable(bookable);
    cafe.setTimePrice(timePrice);
    cafe.setCafeStatus(cafeStatus);

    cafeDao.updateCafe(cafe);
    System.out.println(" >> 수정이 완료 되었습니다.");
  }


}
