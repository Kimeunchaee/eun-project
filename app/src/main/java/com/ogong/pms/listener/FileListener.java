package com.ogong.pms.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogong.context.ApplicationContextListener;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;

public class FileListener implements ApplicationContextListener {

  @SuppressWarnings("unchecked")
  @Override
  public void contextInitialized(Map<String, Object> params) {

    List<Member> memberList = (List<Member>) params.get("memberList");
    List<CeoMember> ceoMemberList = (List<CeoMember>) params.get("ceoMemberList");
    List<Admin> adminList = (List<Admin>) params.get("adminList");
    List<AdminNotice> adminNoticeList = (List<AdminNotice>) params.get("adminNoticeList");
    List<AskBoard> askBoardList = (List<AskBoard>) params.get("askBoardList");
    List<Cafe> cafeList = (List<Cafe>) params.get("cafeList");
    List<CafeReservation> cafeReservationList = (List<CafeReservation>) params.get("cafeReservationList");
    List<CafeReview> cafeReviewList = (List<CafeReview>) params.get("cafeReviewList");
    List<CafeRoom> cafeRoomList = (List<CafeRoom>) params.get("cafeRoomList");
    List<Study> studyList = (List<Study>) params.get("studyList");
    List<ToDo> toDoList = (List<ToDo>) params.get("toDoList");
    List<Calender> calenderList = (List<Calender>) params.get("calenderList");
    List<FreeBoard> freeBoardList = (List<FreeBoard>) params.get("freeBoardList");

    loadObjects("member.json", memberList, Member.class);
    loadObjects("ceoMember.json", ceoMemberList, CeoMember.class);
    loadObjects("admin.json", adminList, Admin.class);
    loadObjects("adminNotice.json" , adminNoticeList, AdminNotice.class);
    loadObjects("askBoard.json", askBoardList, AskBoard.class);
    loadObjects("cafe.json", cafeList, Cafe.class);
    loadObjects("cafeReservation.json", cafeReservationList, CafeReservation.class);
    loadObjects("cafeReview.json", cafeReviewList, CafeReview.class);
    loadObjects("cafeRoom.json", cafeRoomList, CafeRoom.class);
    loadObjects("study.json", studyList, Study.class);
    loadObjects("toDo.json", toDoList, ToDo.class);
    loadObjects("calender.json", calenderList, Calender.class);
    loadObjects("freeBoard.json", freeBoardList, FreeBoard.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void contextDestroyed(Map<String, Object> params) {

    List<Member> memberList = (List<Member>) params.get("memberList");
    List<CeoMember> ceoMemberList = (List<CeoMember>) params.get("ceoMemberList");
    List<Admin> adminList = (List<Admin>) params.get("adminList");
    List<AdminNotice> adminNoticeList = (List<AdminNotice>) params.get("adminNoticeList");
    List<AskBoard> askBoardList = (List<AskBoard>) params.get("askBoardList");
    List<Cafe> cafeList = (List<Cafe>) params.get("cafeList");
    List<CafeReservation> cafeReservationList = (List<CafeReservation>) params.get("cafeReservationList");
    List<CafeReview> cafeReviewList = (List<CafeReview>) params.get("cafeReviewList");
    List<CafeRoom> cafeRoomList = (List<CafeRoom>) params.get("cafeRoomList");
    List<Study> studyList = (List<Study>) params.get("studyList");
    List<ToDo> toDoList = (List<ToDo>) params.get("toDoList");
    List<Calender> calenderList = (List<Calender>) params.get("calenderList");
    List<FreeBoard> freeBoardList = (List<FreeBoard>) params.get("freeBoardList");

    //    saveObjects("member.json", memberList);  // MemberAddHandler
    //    saveObjects("askBoard.json", askBoardList);  // AskBoardAddHandler
    //    saveObjects("study.json", studyList);   // StudyAddHandler
    //    saveObjects("freeBoard.json", freeBoardList); // MyStudyFreeBoard
    //    saveObjects("ceoMember.json", ceoMemberList);   // CeoAddHandler
    //    saveObjects("admin.json", adminList); // AuthAdminLoginHandler
    //    saveObjects("adminNotice.json" , adminNoticeList);  // AdminNoticeAddHandler
    //    saveObjects("cafe.json", cafeList); // CeoCafeAddHandler
    //    saveObjects("cafeReservation.json", cafeReservationList);  // CafeMyReservationListHandler
    //    saveObjects("cafeReview.json", cafeReviewList);
    //    saveObjects("cafeRoom.json", cafeRoomList); // CafeDetailHandler 테스트값 : 2021-10-10
    //    saveObjects("toDo.json", toDoList);  // MyStudyToDo
    //    saveObjects("calender.json", calenderList); // MyStudyCalender
  }

  //JSON 형식으로 저장된 데이터를 읽어서 객체로 만든다.
  @SuppressWarnings("unused")
  private <E> void loadObjects(
      String filepath, // 데이터를 읽어 올 파일 경로 
      List<E> list, // 로딩한 데이터를 객체로 만든 후 저장할 목록 
      Class<E> domainType // 생성할 객체의 타입정보
      ) {

    // CSV 형식으로 저장된 게시글 데이터를 파일에서 읽어 객체에 담는다. 
    try (BufferedReader in = new BufferedReader(
        new FileReader(filepath, Charset.forName("UTF-8")))) {

      StringBuilder strBuilder = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) { // 파일 전체를 읽는다.
        strBuilder.append(str);
      }

      // *StringBuilder로 읽어온 JSON 문자열을 객체로 바꾼다.
      Type type = TypeToken.getParameterized(Collection.class, domainType).getType();
      Collection<E> collection = new Gson().fromJson(strBuilder.toString(), type);

      // JSON 데이터로 읽어온 목록을 파라미터로 받은 List 에 저장한다.
      list.addAll(collection);

      System.out.printf("%s 데이터 로딩 완료!\n", filepath);

    } catch (Exception e) {
      System.out.printf("%s 데이터 로딩 오류!\n", filepath);
    }
  }

  // 객체를 JSON 형식으로 저장한다.
  @SuppressWarnings("unused")
  private void saveObjects(String filepath, List<?> list) {
    try (PrintWriter out = new PrintWriter(
        new BufferedWriter(
            new FileWriter(filepath, Charset.forName("UTF-8"))))) {

      out.print(new Gson().toJson(list));

      System.out.printf("%s 데이터 출력 완료!\n", filepath);

    } catch (Exception e) {
      System.out.printf("%s 데이터 출력 오류!\n", filepath);
      e.printStackTrace();
    }
  }
}
