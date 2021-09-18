package com.ogong.pms.handler;

import java.time.LocalTime;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoCafeAddHandler extends AbstractCafeHandler {

  List<CeoMember> ceoMemberList;
  int cafeNo = 5;

  public CeoCafeAddHandler (List<Cafe> cafeList, List<CeoMember> ceoMemberList) {
    super (cafeList);
    this.ceoMemberList = ceoMemberList;

    //    Cafe cafe = new Cafe();
    //    cafe.setNo(1);
    //    cafe.setCeoMember(ceoMemberList.get(0));
    //    cafe.setCafeLicenseNo("159-75-45678");
    //    cafe.setName("에이스터디카페");
    //    cafe.setMainImg("a.jpg");
    //    cafe.setInfo("testtest");
    //    cafe.setLocation("서울 강남");
    //    cafe.setPhone("02-111-1111");
    //    cafe.setOpenTime(LocalTime.of(8, 00));
    //    cafe.setCloseTime(LocalTime.of(21, 00));
    //    cafe.setHoliday("매주 일요일");
    //    cafe.setBookable(30);
    //    cafe.setTimePrice(2000);
    //    cafe.setCafeStatus(1);
    //    cafeList.add(cafe);
    //
    //    cafe = new Cafe();
    //    cafe.setNo(2);
    //    cafe.setCeoMember(ceoMemberList.get(0));
    //    cafe.setCafeLicenseNo("456-78-12345");
    //    cafe.setName("해피해피스터디카페");
    //    cafe.setMainImg("bbb.jpg");
    //    cafe.setInfo("test~~~!");
    //    cafe.setLocation("서울 잠실");
    //    cafe.setPhone("010-1234-1234");
    //    cafe.setOpenTime(LocalTime.of(7, 00));
    //    cafe.setCloseTime(LocalTime.of(22, 00));
    //    cafe.setHoliday("휴무일 없음");
    //    cafe.setBookable(24);
    //    cafe.setTimePrice(1500);
    //    cafe.setCafeStatus(1);
    //    cafeList.add(cafe);
    //
    //    cafe = new Cafe();
    //    cafe.setNo(3);
    //    cafe.setCeoMember(ceoMemberList.get(1));
    //    cafe.setCafeLicenseNo("456-45-78945");
    //    cafe.setName("광명스터디카페");
    //    cafe.setMainImg("ccccc.jpg");
    //    cafe.setInfo("ㅎㅎㅎㅎㅎ");
    //    cafe.setLocation("경기도 광명");
    //    cafe.setPhone("010-5055-1111");
    //    cafe.setOpenTime(LocalTime.of(9, 00));
    //    cafe.setCloseTime(LocalTime.of(23, 00));
    //    cafe.setHoliday("휴무일 없음");
    //    cafe.setBookable(40);
    //    cafe.setTimePrice(3000);
    //    cafe.setCafeStatus(1);
    //    cafeList.add(cafe);
    //
    //    cafe = new Cafe();
    //    cafe.setNo(4);
    //    cafe.setCeoMember(ceoMemberList.get(2));
    //    cafe.setCafeLicenseNo("123-45-45612");
    //    cafe.setName("비트스터디카페");
    //    cafe.setMainImg("bit.jpg");
    //    cafe.setInfo("비트주세요");
    //    cafe.setLocation("경기도 용인");
    //    cafe.setPhone("010-1245-1111");
    //    cafe.setOpenTime(LocalTime.of(05, 00));
    //    cafe.setCloseTime(LocalTime.of(23, 00));
    //    cafe.setHoliday("휴무일 없음");
    //    cafe.setBookable(100);
    //    cafe.setTimePrice(3500);
    //    cafe.setCafeStatus(0);
    //    cafeList.add(cafe);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 장소 등록");
    System.out.println();

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();

    Cafe cafe = new Cafe();
    cafe.setNo(cafeNo++);
    cafe.setCeoMember(ceoMember);
    cafe.setName(Prompt.inputString(" 상호명 : "));
    cafe.setCafeLicenseNo(Prompt.inputString(" 사업자 등록번호 : "));
    cafe.setMainImg(Prompt.inputString(" 대표사진 : "));
    cafe.setInfo(Prompt.inputString(" 소개글 : "));
    cafe.setLocation(Prompt.inputString(" 주소 : "));
    cafe.setPhone(Prompt.inputString(" 전화번호 : "));
    cafe.setOpenTime(LocalTime.parse(Prompt.inputString(" 오픈시간 (예시 > 09:00) : ")));
    cafe.setCloseTime(LocalTime.parse(Prompt.inputString(" 마감시간 (예시 > 21:00) : ")));
    cafe.setHoliday(Prompt.inputString(" 휴무일 : "));
    cafe.setBookable(Prompt.inputInt(" 예약가능인원 : "));
    cafe.setTimePrice(Prompt.inputInt(" 시간당금액 : "));
    cafe.setCafeStatus(0); // 0 : 승인대기

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }
    System.out.println(" >> 등록되었습니다.");
    cafeList.add(cafe);
  }

}
