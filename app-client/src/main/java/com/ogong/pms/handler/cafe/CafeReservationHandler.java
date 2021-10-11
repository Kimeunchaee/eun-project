package com.ogong.pms.handler.cafe;

import java.sql.Date;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CafeReservationHandler implements Command {

  CafeDao cafeDao;

  public CafeReservationHandler (CafeDao cafeDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {

    int cafeNo = (int) request.getAttribute("cafeNo");
    String selectReservation = (String) request.getAttribute("selectReservation");

    Cafe cafe = cafeDao.findByCafeNoMember(cafeNo);

    if (cafe == null) {
      System.out.println(" >> 해당 번호의 장소가 존재하지 않습니다.");
      return;
    }

    if (selectReservation.equals("addReservation")) {
      addReservation(cafe);
    } else if (selectReservation.equals("addRoomReservation")) {
      addRoomReservation(cafe);
    }

  }

  private void addReservation(Cafe cafe) throws Exception {
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

    int useTime = Prompt.inputInt(String.format(" 이용 시간 (%s 마감) 예시 > 2 : ", closeTime));
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

    // 고유번호 + 1
    List<CafeReservation> cafeReservationList = cafeDao.getCafeReservationList();
    if (!cafeReservationList.isEmpty()) {
      reservation.setReservationNo(
          cafeReservationList.get(cafeReservationList.size() - 1).getReservationNo() + 1);
    } else {
      reservation.setReservationNo(1);
    }

    reservation.setMember(member);
    reservation.setCafe(cafe);
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(startTime);
    reservation.setUseTime(useTime);
    reservation.setUseMemberNumber(useMemberNumber);
    reservation.setTotalPrice(totalPrice);
    reservation.setWirteReview(false);

    cafe.setBookable(cafe.getBookable() - 1);

    cafeDao.insertReservation(reservation);
  }

  private void addRoomReservation(Cafe cafe) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디룸 예약하기");

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    if (member == null) {
      System.out.println(" >> 로그인 한 회원만 예약 가능합니다.");
      return;
    }

    int i = 1;
    HashMap<Integer, Integer> selectRoomNo = new HashMap<>();

    List<CafeRoom> roomList = cafeDao.findCafeRoomListByCafe(cafe.getNo());
    for (CafeRoom cafeRoom : roomList) {
      System.out.println(" " + i + ". " + cafeRoom.getRoomName());
      selectRoomNo.put(i, cafeRoom.getRoomNo());
      i++;
    }

    if (roomList.isEmpty()) {
      System.out.println(" >> 등록된 스터디룸이 없습니다.");
      return;
    }

    int selectNo = Prompt.inputInt(" 선택> ");
    CafeRoom cafeRoom = null;
    try {
      cafeRoom = cafeDao.findByRoomNo(selectRoomNo.get(selectNo));
    } catch (NullPointerException e) {
      System.out.println(" >> 스터디룸 번호를 잘못 선택하셨습니다.");
      return;
    }

    detailRoomReservation(cafe.getNo(), selectRoomNo.get(selectNo));
  }

  private void detailRoomReservation(int cafeNo, Integer selectNo) throws Exception {
    //    int roomNo = selectNo; // room 고유번호
    Cafe cafe = cafeDao.findByCafeNoMember(cafeNo);
    CafeRoom cafeRoom = cafeDao.findByRoomNo(selectNo);

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    Date today = new Date(System.currentTimeMillis());
    Date reservationDate = Prompt.inputDate("\n 예약 날짜(당일 예약 불가) : ");
    while (today.toLocalDate().compareTo(reservationDate.toLocalDate()) > 0) {
      System.out.println(" >> 이전 날짜는 예약 불가능 합니다.");
      System.out.println("    날짜를 다시 입력해 주세요.\n");
      reservationDate = Prompt.inputDate(" 예약 날짜 : ");
    }

    List<CafeReservation> todayReserList = getCafeReserList(cafeNo, selectNo, reservationDate);
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

    // 고유번호 + 1
    List<CafeReservation> cafeReservationList = cafeDao.getCafeReservationList();
    if (!cafeReservationList.isEmpty()) {
      reservation.setReservationNo(
          cafeReservationList.get(cafeReservationList.size() - 1).getReservationNo() + 1);
    } else {
      reservation.setReservationNo(1);
    }

    reservation.setMember(member);
    reservation.setCafe(cafe);
    reservation.setReservationDate(reservationDate);
    reservation.setStartTime(realStartTime);
    reservation.setUseTime(selectStatusOfNumber.size());
    reservation.setUseMemberNumber(0);
    reservation.setTotalPrice(totalPrice);
    reservation.setWirteReview(false);
    reservation.setRoomNo(selectNo);

    cafeDao.insertReservation(reservation);
  }

  private List<CafeReservation> getCafeReserList(
      int cafeNo, int roomNo, Date reservationDate) throws Exception {

    List<CafeReservation> reserList = cafeDao.getCafeReservationList();

    List<CafeReservation> todayReserList = new ArrayList<>();
    for (CafeReservation cafeReser : reserList) {

      if (reservationDate.toLocalDate().compareTo(cafeReser.getReservationDate().toLocalDate()) == 0 &&
          cafeReser.getCafe().getNo() == cafeNo && cafeReser.getRoomNo() == roomNo &&
          cafeReser.getReservationStatus() == 0 || cafeReser.getReservationStatus() == 1) {
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