package com.ogong.pms.handler;

import java.sql.Date;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class CafeDetailHandler extends AbstractCafeHandler {

  List<CafeReview> reviewList;
  List<CafeReservation> reserList;
  List<CafeRoom> roomList;
  PromptCafe promptcafe;
  PromptPerMember promptPerMember;
  int reservationNo = 4; // 예약번호

  public CafeDetailHandler (List<Cafe> cafeList, List<CafeReview> reviewList
      , List<CafeReservation> reserList, PromptPerMember promptPerMember,
      List<CafeRoom> roomList,  PromptCafe promptcafe) {

    super (cafeList);
    this.reviewList = reviewList;
    this.reserList = reserList;
    this.roomList = roomList;
    this.promptcafe = promptcafe;

    //    CafeRoom cafeRoom = new CafeRoom();
    //    cafeRoom.setRoomNo(0);
    //    cafeRoom.setCafe(cafeList.get(0));
    //    cafeRoom.setRoomName("A타입(2~3인)");
    //    cafeRoom.setRoomImg("a_room.jpg");
    //    cafeRoom.setRoomInfo("2~3인이 이용할 수 있는 스터디룸 입니다.\n기본설비 : 화이트보드, 무선인터넷");
    //    cafeRoom.setStartTime(cafeList.get(0).getOpenTime());
    //    cafeRoom.setEndTime(cafeList.get(0).getCloseTime());
    //    cafeRoom.setRoomPrice(6000);
    //    cafeRoom.setRoomStatus(0);
    //    roomList.add(cafeRoom);
    //
    //    cafeRoom = new CafeRoom();
    //    cafeRoom.setRoomNo(1);
    //    cafeRoom.setCafe(cafeList.get(1));
    //    cafeRoom.setRoomName("A타입(2인)");
    //    cafeRoom.setRoomImg("a_room.jpg");
    //    cafeRoom.setRoomInfo("최대 2인이 이용할 수 있는 스터디룸 입니다.\n기본설비 : 화이트보드, 무선인터넷");
    //    cafeRoom.setStartTime(cafeList.get(1).getOpenTime());
    //    cafeRoom.setEndTime(cafeList.get(1).getCloseTime());
    //    cafeRoom.setRoomPrice(6000);
    //    cafeRoom.setRoomStatus(0);
    //    roomList.add(cafeRoom);
    //
    //    cafeRoom = new CafeRoom();
    //    cafeRoom.setRoomNo(2);
    //    cafeRoom.setCafe(cafeList.get(0));
    //    cafeRoom.setRoomName("B타입(3~4인)");
    //    cafeRoom.setRoomImg("b_room.jpg");
    //    cafeRoom.setRoomInfo("3~4인이 이용할 수 있는 스터디룸 입니다.\n기본설비 : 화이트보드, 무선인터넷");
    //    cafeRoom.setStartTime(cafeList.get(0).getOpenTime());
    //    cafeRoom.setEndTime(cafeList.get(0).getCloseTime());
    //    cafeRoom.setRoomPrice(9000);
    //    cafeRoom.setRoomStatus(0);
    //    roomList.add(cafeRoom);
    //
    //    cafeRoom = new CafeRoom();
    //    cafeRoom.setRoomNo(3);
    //    cafeRoom.setCafe(cafeList.get(1));
    //    cafeRoom.setRoomName("B타입(4인)");
    //    cafeRoom.setRoomImg("b_room.jpg");
    //    cafeRoom.setRoomInfo("최대 4인이 이용할 수 있는 스터디룸 입니다.\n기본설비 : 화이트보드, 무선인터넷");
    //    cafeRoom.setStartTime(cafeList.get(1).getOpenTime());
    //    cafeRoom.setEndTime(cafeList.get(1).getCloseTime());
    //    cafeRoom.setRoomPrice(9000);
    //    cafeRoom.setRoomStatus(0);
    //    roomList.add(cafeRoom);
    //
    //    cafeRoom = new CafeRoom();
    //    cafeRoom.setRoomNo(4);
    //    cafeRoom.setCafe(cafeList.get(0));
    //    cafeRoom.setRoomName("C타입(5~6인)");
    //    cafeRoom.setRoomImg("c_room.jpg");
    //    cafeRoom.setRoomInfo("5~6인이 이용할 수 있는 스터디룸 입니다.\n기본설비 : 화이트보드, 무선인터넷");
    //    cafeRoom.setStartTime(cafeList.get(0).getOpenTime());
    //    cafeRoom.setEndTime(cafeList.get(0).getCloseTime());
    //    cafeRoom.setRoomPrice(15000);
    //    cafeRoom.setRoomStatus(0);
    //    roomList.add(cafeRoom);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 장소 상세보기");
    Cafe cafe = promptcafe.findByCafeNo(Prompt.inputInt(" 번호 : "));
    System.out.println();
    if (cafe == null) {
      System.out.println(" >> 해당 번호의 장소가 존재하지 않습니다.");
      return;
    }
    System.out.printf(" (%s)\n", cafe.getNo());
    System.out.printf(" [%s]\n", cafe.getName());
    System.out.printf(" >> 대표이미지 : %s\n", cafe.getMainImg());
    System.out.printf(" >> 소개글 : %s\n", cafe.getInfo());
    System.out.printf(" >> 주소 : %s\n", cafe.getLocation());
    System.out.printf(" >> 번호 : %s\n", cafe.getPhone());
    System.out.printf(" >> 오픈시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 휴무일 : %s\n", cafe.getHoliday());
    System.out.printf(" >> 예약가능 인원 : %d\n", cafe.getBookable());
    System.out.printf(" >> 시간당 금액 : %d원\n", cafe.getTimePrice());
    getStarRatingAverage(cafe);
    listReview(cafe);
    System.out.println();

    if (cafe.getCafeStatus() == 2) {
      return;
    }

    int roomCount = 0;
    for (CafeRoom cafeRoom : roomList) {
      if (cafeRoom.getCafe().getNo() == cafe.getNo()) {
        roomCount++;
      }
    }

    if (roomCount != 0) {
      System.out.println("----------------------");
      System.out.println("1. 일반 예약");
      System.out.println("2. 스터디룸 예약");
      System.out.println("0. 이전");

      int selectNo = Prompt.inputInt(" 선택> ");
      switch (selectNo) {
        case 1 : addReservation(cafe); return;
        case 2 : addRoomReservation(cafe); return;
        case 0 : return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      } 
    } else {
      System.out.println("----------------------");
      System.out.println("1. 일반 예약");
      System.out.println("0. 이전");

      int selectNo = Prompt.inputInt(" 선택> ");
      switch (selectNo) {
        case 1 : addReservation(cafe); return;
        case 0 : return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      } 
    }       
  }

  private void getStarRatingAverage(Cafe cafe) {
    int starRating = 0;
    int starRatingCount = 0;
    double starRatingAverage = 0;

    for (CafeReview review : reviewList) {
      if (review.getCafe().getNo() == cafe.getNo()) {
        if (review.getReviewStatus() == 1) {
          continue;
        }
        starRating += review.getGrade();
        starRatingAverage =(double) starRating / ++starRatingCount;
      }
    }
    System.out.printf(" >> 리뷰평점 : ★ %.1f(%d)\n" , starRatingAverage , starRatingCount);
  }

  private void listReview(Cafe cafe) {
    int i = 1;
    System.out.println();
    System.out.println("============= 리뷰 =============");
    int reviewSize = 0;
    for (CafeReview review : reviewList) {
      if (review.getCafe().getNo() == cafe.getNo()) {
        if (review.getReviewStatus() == 1) {
          //System.out.printf(" \n (%s)\n", review.getReviewNo());
          System.out.printf(" (%d) | 삭제 된 리뷰입니다. |\n", i++);
          reviewSize++;
          continue;
        }
        String nickname = review.getMember().getPerNickname();
        System.out.printf(" (%d) 닉네임 : %s | 별점 : %s | 내용 : %s | 등록일 : %s\n",
            i++, nickname, getReviewGradeStatusLabel(review.getGrade()), review.getContent()
            , review.getRegisteredDate());
        reviewSize++;
      }
    }

    if (reviewSize == 0) {
      System.out.println(" >> 등록된 리뷰가 없습니다.");
    }
  }

  protected void addReservation(Cafe cafe) {
    System.out.println();
    System.out.println("▶ 예약하기");

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 예약 가능합니다.");
      return;
    }

    CafeReservation reservation = new CafeReservation();

    Date today = new Date(System.currentTimeMillis());
    Date reservationDate = Prompt.inputDate(" 예약 날짜 : ");
    while (today.toLocalDate().compareTo(reservationDate.toLocalDate()) > 0) {
      System.out.println(" >> 이전 날짜는 예약 불가능합니다.");
      System.out.println("    날짜를 다시 입력해 주세요.\n");
      reservationDate = Prompt.inputDate(" 예약 날짜 : ");
    }

    String openTime = cafe.getOpenTime().toString();    
    String closeTime = cafe.getCloseTime().toString();
    String closeTimeMinus1 = cafe.getCloseTime().minusHours(1).toString();

    LocalTime startTime;
    while (true) {
      startTime = LocalTime.parse(Prompt.inputString(
          String.format(" 시작 시간 (%s~%s) : ", openTime, closeTimeMinus1)));
      if (startTime.isBefore(cafe.getOpenTime())) {
        System.out.println(" >> 오픈 시간 전입니다.");
        System.out.println("    시작 시간을 다시 입력해 주세요.\n");
        continue;
      } else if (startTime.isAfter(cafe.getCloseTime().minusHours(1))) {
        System.out.println(" >> " + closeTimeMinus1 + " 까지만 가능합니다.");
        System.out.println("    시작 시간을 다시 입력해 주세요.\n");
        continue;
      }
      break;
    }

    int useTime = Prompt.inputInt(String.format(" 이용 시간 (%s 마감) : ", closeTime));
    int availableTime = (int) ChronoUnit.HOURS.between(startTime, cafe.getCloseTime());
    while (useTime > availableTime) {
      System.out.printf(" >> 마감 시간(%s)을 초과하여 예약할 수 없습니다.\n", closeTime);
      System.out.println("    이용 시간을 다시 입력해 주세요.\n");
      useTime = Prompt.inputInt(String.format(" 이용할 시간 (%s 마감) : ", closeTime));
    }

    int useMemberNumber = Prompt.inputInt(" 사용 인원 : ");
    int totalPrice = useTime * useMemberNumber * cafe.getTimePrice();
    System.out.printf(" 총 금액 : %d원\n" , totalPrice);

    String input = Prompt.inputString(" 정말 예약하시겠습니까? (네 / 아니오) ");

    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 장소 예약을 취소하였습니다.");
      return;
    }

    reservation.setReservationNo(reservationNo++);
    reservation.setMember(member);
    reservation.setCafe(cafe);
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(startTime);
    reservation.setUseTime(useTime);
    reservation.setUseMemberNumber(useMemberNumber);
    reservation.setTotalPrice(totalPrice);
    reservation.setWirteReview(false);

    cafe.setBookable(cafe.getBookable() - 1);

    reserList.add(reservation);

    System.out.println();
    System.out.println("*** 예약 되었습니다 ***");
  }

  private void addRoomReservation(Cafe cafe) {
    System.out.println();
    System.out.println("▶ 스터디룸 예약하기");

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 예약 가능합니다.");
      return;
    }

    int i = 1;
    HashMap<Integer, Integer> selectRoomNo = new HashMap<>();
    for (CafeRoom cafeRoom : roomList) {
      if (cafe.getNo() == cafeRoom.getCafe().getNo()) {
        System.out.println(" " + i + ". " + cafeRoom.getRoomName());
        selectRoomNo.put(i, cafeRoom.getRoomNo());
        i++;
      }
    }

    if (i == 0) {
      System.out.println(" >> 등록된 스터디룸이 없습니다.");
      return;
    }

    int selectNo = Prompt.inputInt(" 선택> ");
    CafeRoom cafeRoom = null;
    try {
      cafeRoom = getCafeRoomByNo(selectRoomNo.get(selectNo), cafe);      
    } catch (NullPointerException e) {
      System.out.println(" >> 번호를 잘못 선택하셨습니다.");
      return;
    }

    detailRoomReservation(cafe, selectRoomNo.get(selectNo), cafeRoom);
  }

  private void detailRoomReservation(Cafe cafe, Integer selectNo, CafeRoom cafeRoom) {
    int roomNo = selectNo; // room 고유번호

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    Date today = new Date(System.currentTimeMillis());
    Date reservationDate = Prompt.inputDate("\n 예약 날짜(당일 예약 불가) : ");
    while (today.toLocalDate().compareTo(reservationDate.toLocalDate()) > 0) {
      System.out.println(" >> 이전 날짜는 예약 불가능 합니다.");
      System.out.println("    날짜를 다시 입력해 주세요.\n");
      reservationDate = Prompt.inputDate(" 예약 날짜 : ");
    }

    List<CafeReservation> todayReserList = getCafeReserList(cafe, roomNo, reservationDate);
    ArrayList<LocalTime> useTimeList = new ArrayList<>();

    try {
      if (!todayReserList.isEmpty()) {
        for (CafeReservation cafeReser : todayReserList) {
          for (int i = 0; i < cafeReser.getUseTime(); i++) {
            if (i == 0) {
              useTimeList.add(cafeReser.getStartTime().plusHours(1));
              continue;
            } else if (i > 0) {
              LocalTime tempTime = useTimeList.get(useTimeList.size()-1).plusHours(1);
              useTimeList.add(tempTime);
            }
          }
        }
      }
    } catch (NullPointerException e) {

    }

    LocalTime startTime = cafeRoom.getStartTime();
    LocalTime endTime = cafeRoom.getEndTime();
    LocalTime tempEndTime = LocalTime.parse("00:00");

    int availableTime = (int) ChronoUnit.HOURS.between(startTime, endTime);
    HashMap<Integer, String> statusOfNumber = new HashMap<>();

    //System.out.println("스터디룸 시작 시간 : " + startTime + ", 끝 시간 : " + endTime);

    System.out.println("\n [ 예약 현황 ]");
    for (int i = 0; i < availableTime; i++) {
      if (i == 0) {
        tempEndTime = startTime.plusHours(1);
      } else {
        startTime = tempEndTime;
        tempEndTime = startTime.plusHours(1);
      }

      String status = "예약 가능";

      if (useTimeList.size() != 0) {
        for (int j = 0; j < useTimeList.size(); j++) {
          if (useTimeList.get(j).compareTo(tempEndTime) == 0) {
            status = "예약 불가";
          }
        }
      }
      System.out.printf(" %d. %s ~ %s : %s\n", i+1, startTime, tempEndTime, status);
      statusOfNumber.put(i+1, startTime + "," + tempEndTime + "," +status);
    }

    ArrayList<Integer> selectStatusOfNumber = new ArrayList<>();
    while (true) {
      int number = Prompt.inputInt("\n 예악하고 싶은 시간의 번호를 입력하세요 (종료:0) : ");
      if (number == 0) {
        break;
      }
      if (!ReservationStatus(statusOfNumber, number)) {
        System.out.println(" >> 예약 가능한 시간이 아닙니다. 다시 선택해 주세요.");
        continue;
      } else if (ReservationStatus(statusOfNumber, number)) {
        if (selectStatusOfNumber.size() == 0) {
          selectStatusOfNumber.add(number);
          continue;
        } else {
          if (number == selectStatusOfNumber.get(selectStatusOfNumber.size()-1)+1) {
            selectStatusOfNumber.add(number);
            continue;
          } else {
            System.out.println(" >> 연속된 시간만 선택 가능합니다. 다시 선택해 주세요.");
            continue;
          }
        }
      }
    }

    System.out.println("\n [ 예약 시간 ]");
    for (int i = 0; i < selectStatusOfNumber.size(); i++) {
      String[] reserTimeInfo = statusOfNumber.get(selectStatusOfNumber.get(i)).split(","); 
      System.out.printf(" %d. %s ~ %s\n", i+1, reserTimeInfo[0], reserTimeInfo[1]);
    }
    System.out.println(" 총 이용 시간 : " + selectStatusOfNumber.size() +"시간");

    if (selectStatusOfNumber.size() == 0) {
      System.out.println(" >> 종료");
      return;
    }

    String input = Prompt.inputString("\n 정말 예약하시겠습니까? (네 / 아니오) ");

    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디룸 예약을 취소하였습니다.");
      return;
    }

    String[] realReserTime = statusOfNumber.get(selectStatusOfNumber.get(0)).split(",");
    //String[] stringStartTime = realReserTime[0].split(":");
    LocalTime realStartTime = LocalTime.parse(realReserTime[0]);
    int totalPrice = cafeRoom.getRoomPrice() * selectStatusOfNumber.size();

    CafeReservation reservation = new CafeReservation();

    reservation.setReservationNo(reservationNo++);
    reservation.setMember(member);
    reservation.setCafe(cafe);
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(realStartTime);
    reservation.setUseTime(selectStatusOfNumber.size());
    reservation.setUseMemberNumber(0);
    reservation.setTotalPrice(totalPrice);
    reservation.setWirteReview(false);
    reservation.setRoomNo(roomNo);

    reserList.add(reservation);

    System.out.println();
    System.out.println("*** 예약되었습니다 ***");
  }

  private CafeRoom getCafeRoomByNo(int roomNo, Cafe cafe) {
    for (CafeRoom cafeRoom : roomList) {
      if (cafeRoom.getRoomNo() == roomNo && cafeRoom.getCafe().getNo() == cafe.getNo()) {
        return cafeRoom;
      }
    }
    return null;
  }

  private List<CafeReservation> getCafeReserList(Cafe cafe, int roomNo, Date reservationDate) {
    List<CafeReservation> todayReserList = new ArrayList<>();
    for (CafeReservation cafeReser : reserList) {
      if (reservationDate.toLocalDate().compareTo(cafeReser.getReservationDate().toLocalDate()) == 0 &&
          cafeReser.getCafe().getNo() == cafe.getNo() &&
          cafeReser.getRoomNo() == roomNo) {
        todayReserList.add(cafeReser);
      }
    }
    return todayReserList;
  }

  private boolean ReservationStatus(HashMap<Integer, String> statusOfNumber, int number) {
    if (number > statusOfNumber.size()) {
      return false;
    }

    for (int i = 0; i < statusOfNumber.size(); i++) {
      if (i == number) {
        String[] reserInfo = statusOfNumber.get(i).split(",");
        if (reserInfo[2].equals("예약 불가")) {
          return false;
        }
      }
    }
    return true;
  }
}