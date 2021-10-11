package com.ogong.pms.handler.ceoCafe;

import static com.ogong.pms.domain.Cafe.WAIT;
import java.time.LocalTime;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Address;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.AddressSearchApi;
import com.ogong.util.Prompt;

public class CeoCafeAddHandler implements Command {

  CafeDao cafeDao;

  public CeoCafeAddHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 등록");
    System.out.println();

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    Cafe cafe = new Cafe();

    cafe.setCeoMember(ceoMember);
    cafe.setName(Prompt.inputString(" 상호명 : "));
    cafe.setCafeLicenseNo(Prompt.inputString(" 사업자 등록번호 : "));
    cafe.setMainImg(Prompt.inputString(" 대표사진 : "));
    cafe.setInfo(Prompt.inputString(" 소개글 : "));

    AddressSearchApi api = new AddressSearchApi();
    Address address = api.searchAddress();
    String addressString = address.getLnmAdres();
    System.out.println(" 기본 주소 : " + addressString);
    cafe.setLocation(addressString + " " + Prompt.inputString(" 상세 주소 : "));

    cafe.setPhone(Prompt.inputString(" 전화번호 : "));
    cafe.setOpenTime(LocalTime.parse(Prompt.inputString(" 오픈시간 (예시 > 09:00) : ")));
    cafe.setCloseTime(LocalTime.parse(Prompt.inputString(" 마감시간 (예시 > 21:00) : ")));
    cafe.setHoliday(Prompt.inputString(" 휴무일 : "));
    cafe.setBookable(Prompt.inputInt(" 예약가능인원 : "));
    cafe.setTimePrice(Prompt.inputInt(" 시간당금액 : "));
    cafe.setCafeStatus(WAIT); // 0 : 승인대기

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }

    // 고유번호 + 1
    List<Cafe> cafeList = cafeDao.getCafeList();
    if (!cafeList.isEmpty()) {
      cafe.setNo(cafeList.get(cafeList.size() -1).getNo() + 1);
    } else {
      cafe.setNo(1);
    }

    cafeDao.insertCafe(cafe);
  }

}
