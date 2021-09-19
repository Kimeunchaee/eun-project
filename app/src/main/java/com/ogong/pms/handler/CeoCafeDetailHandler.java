package com.ogong.pms.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.CeoMember;
import com.ogong.util.Prompt;

public class CeoCafeDetailHandler extends AbstractCeoMemberHandler {

  List<CeoMember> ceoMemberList;
  List<Cafe> cafeList;
  List<CafeReview> reviewList;
  List<CafeRoom> roomList;
  int roomNo = 5;

  public CeoCafeDetailHandler (List<CeoMember> ceoMemberList, List<Cafe> cafeList,
      List<CafeReview> reviewList, List<CafeRoom> roomList) {
    super (ceoMemberList);
    this.cafeList = cafeList;
    this.reviewList = reviewList;
    this.roomList = roomList;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 장소 상세보기");
    System.out.println();

    Cafe cafe = findByNo(Prompt.inputInt(" 번호 : "));
    System.out.println();
    if (cafe == null) {
      System.out.println(" >> 번호를 다시 선택하세요.");
      return;
    }
    System.out.printf(" (%s)\n", cafe.getNo());
    System.out.printf(" [%s]\n", cafe.getName());
    System.out.printf(" >> 사업자 등록번호 : %s\n", cafe.getCafeLicenseNo());
    System.out.printf(" >> 대표이미지 : %s\n", cafe.getMainImg());
    System.out.printf(" >> 소개글 : %s\n", cafe.getInfo());
    System.out.printf(" >> 주소 : %s\n", cafe.getLocation());
    System.out.printf(" >> 번호 : %s\n", cafe.getPhone());
    System.out.printf(" >> 오픈시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 휴무일 : %s\n", cafe.getHoliday());
    System.out.printf(" >> 예약가능 인원 : %d\n", cafe.getBookable());
    System.out.printf(" >> 시간당 금액 : %d원\n", cafe.getTimePrice());
    System.out.printf(" >> 상태 : %s\n", getCafeStatusLabel(cafe.getCafeStatus()));
    System.out.println();
    System.out.println("============= 리뷰 =============");
    int reviewSize = 0;
    for (CafeReview review : reviewList) {
      if (review.getCafe().getNo() == cafe.getNo()) {
        String nickname = review.getMember().getPerNickname();
        System.out.printf(" 닉네임 : %s | 별점 : %d | 내용 : %s | 등록일 : %s\n",
            nickname, review.getGrade(), review.getContent(), review.getRegisteredDate());
        reviewSize++;
      }
    }
    if (reviewSize == 0) {
      System.out.println(" >> 등록된 리뷰가 없습니다.");
    }

    request.setAttribute("cafe", cafe);

    System.out.println();
    System.out.println("----------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 스터디룸 관리");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/ceoMember/cafeUpdate").forward(request); return;
      case 2: request.getRequestDispatcher("/ceoMember/cafeDelete").forward(request); return;
      case 3: controlStudyRoom(cafe);
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void controlStudyRoom(Cafe cafe) {
    System.out.println();
    System.out.println("▶ 스터디룸 관리");
    System.out.println();
    // 구현중

    List<CafeRoom> cafeRoom = myCafeStudyRoomList(cafe);

    if (cafeRoom.isEmpty()) {
      System.out.println(" >> 등록된 스터디룸이 없습니다.");
      System.out.println("\n----------------------");
      System.out.println("1. 등록");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt(" 선택> ");
      switch (selectNo) {
        case 1: StudyRoomAdd(cafe); return;
        case 0: return;
        default : 
          System.out.println(" >> 번호를 다시 선택해 주세요."); return;
      }
    }

    int i = 1;
    HashMap<Integer, Integer> selectRoomNo = new HashMap<>();
    for (CafeRoom myCafeRoom : cafeRoom) {
      if (cafe.getNo() == myCafeRoom.getCafe().getNo()) {
        System.out.println(" " + i + ". " + myCafeRoom.getRoomName());
        selectRoomNo.put(i, myCafeRoom.getRoomNo());
        i++;
      }
    }

    System.out.println("\n----------------------");
    System.out.println("1. 등록");
    System.out.println("2. 상세");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: StudyRoomAdd(cafe); return;
      case 2: myCafeStudyRoomDetail(selectRoomNo, cafe); return;
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void StudyRoomAdd(Cafe cafe) {
    System.out.println();
    System.out.println("▶ 스터디룸 등록");
    System.out.println();

    CafeRoom cafeRoom = new CafeRoom();
    cafeRoom.setRoomNo(roomNo++);
    cafeRoom.setCafe(cafe);
    cafeRoom.setRoomName(Prompt.inputString(" 스터디룸 이름 : "));
    cafeRoom.setRoomImg(Prompt.inputString(" 스터디룸 사진 : "));
    cafeRoom.setRoomInfo(Prompt.inputString(" 스터디룸 설명 : "));
    cafeRoom.setStartTime(cafe.getOpenTime());
    cafeRoom.setEndTime(cafe.getCloseTime());
    cafeRoom.setRoomPrice(Prompt.inputInt(" 스터디룸 시간당금액 : "));

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }
    System.out.println(" >> 등록되었습니다.");
    roomList.add(cafeRoom);
  }

  private void myCafeStudyRoomDetail(HashMap<Integer, Integer> selectRoomNo, Cafe cafe) {
    System.out.println();
    System.out.println("▶ 스터디룸 상세");

    CafeRoom cafeRoom = null;
    int input;

    while (true) {
      System.out.println();
      input = Prompt.inputInt(" 번호 : ");
      try {
        cafeRoom = getCafeRoomByNo(selectRoomNo.get(input), cafe); 

        if (cafeRoom != null) {
          break;
        }
      } catch (NullPointerException e) {
        System.out.println(" >> 번호를 다시 선택해 주세요.");
        continue;
      }
    }

    System.out.println();
    System.out.printf(" (%s)\n", cafeRoom.getRoomNo());
    System.out.printf(" [%s - %s]\n", cafeRoom.getCafe().getName(), cafeRoom.getRoomName());
    System.out.printf(" >> 대표이미지 : %s\n", cafeRoom.getRoomImg());
    System.out.printf(" >> 소개글 : %s\n", cafeRoom.getRoomInfo());
    System.out.printf(" >> 시작시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 시간당 금액 : %d원\n", cafeRoom.getRoomPrice());

    System.out.println("\n----------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: studyRoomUpdate(cafeRoom); return;
      case 2: studyRoomDelete(cafeRoom); return;
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void studyRoomUpdate(CafeRoom cafeRoom) {
    System.out.println();
    System.out.println("▶ 스터디룸 수정");
    System.out.println();

    String name = Prompt.inputString(String.format(" 스터디룸 이름(%s) : ", cafeRoom.getRoomName()));
    String mainImg = Prompt.inputString(String.format(" 스터디룸 사진(%s) : ", cafeRoom.getRoomImg()));
    String Info = Prompt.inputString(String.format(" 스터디룸 설명(%s) : ", cafeRoom.getRoomInfo()));
    int timePrice = Prompt.inputInt(String.format(" 스터디룸 시간당금액(%d) : ", cafeRoom.getRoomPrice()));

    String input = Prompt.inputString(" 정말 수정하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 수정을 취소하였습니다.");
      return;
    }

    cafeRoom.setRoomName(name);
    cafeRoom.setRoomImg(mainImg);
    cafeRoom.setRoomInfo(Info);
    cafeRoom.setRoomPrice(timePrice);

    System.out.println(" >> 수정이 완료 되었습니다.");
  }

  public void studyRoomDelete(CafeRoom cafeRoom) {
    System.out.println();
    System.out.println("▶ 스터디룸 삭제");

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디룸 삭제를 취소하였습니다.");
      return;
    }

    roomList.remove(cafeRoom);

    System.out.println(" >> 스터디룸을 삭제하였습니다.");
  }

  private List<CafeRoom> myCafeStudyRoomList(Cafe cafe) {
    List<CafeRoom> myCafeStudyRoomList = new ArrayList<>();
    for (CafeRoom cafeRoom : roomList) {
      if (cafeRoom.getCafe().getNo() == cafe.getNo()) {
        myCafeStudyRoomList.add(cafeRoom);
      }
    }
    return myCafeStudyRoomList;
  } 

  private CafeRoom getCafeRoomByNo(int roomNo, Cafe cafe) {
    for (CafeRoom cafeRoom : roomList) {
      if (cafeRoom.getRoomNo() == roomNo && cafeRoom.getCafe().getNo() == cafe.getNo()) {
        return cafeRoom;
      }
    }
    return null;
  }

  private Cafe findByNo(int cafeNo) {
    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();
    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == cafeNo && cafe.getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
        return cafe;
      }
    }
    return null;
  }
}
