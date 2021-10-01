package com.ogong.pms;

import static com.ogong.menu.Menu.LOGOUT;
import static com.ogong.menu.Menu.PER_LOGIN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.ogong.context.ApplicationContextListener;
import com.ogong.menu.Menu;
import com.ogong.menu.MenuFilter;
import com.ogong.menu.MenuGroup;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.AbstractLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptCafe;
import com.ogong.pms.handler.PromptCeoMember;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.pms.listener.AppInitListener;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class ClientApp {

  static RequestAgent requestAgent;

  List<Study> studyList = new LinkedList<>();
  List<Member> memberList = new LinkedList<>();
  List<AdminNotice> adminNoticeList = new ArrayList<>();
  List<AskBoard> askBoardList = new ArrayList<>();
  List<Cafe> cafeList = new ArrayList<>();
  List<CafeReview> cafeReviewList = new ArrayList<>();
  List<CafeReservation> cafeReservationList = new ArrayList<>();
  List<ToDo> toDoList = new ArrayList<>();
  List<FreeBoard> freeBoardList = new ArrayList<>();
  List<Calender> calenderList = new ArrayList<>();
  List<Admin> adminList = new ArrayList<>();
  List<CeoMember> ceoMemberList = new ArrayList<>();
  List<Comment> commentList = new ArrayList<>();
  List<CafeRoom> cafeRoomList = new ArrayList<>();

  // 해시맵 추가(0904)
  HashMap<String, Command> commandMap = new HashMap<>();

  PromptPerMember promptPerMember = new PromptPerMember(memberList); 
  PromptCeoMember promptCeoMember = new PromptCeoMember(ceoMemberList);
  PromptCafe promptcafe = new PromptCafe(cafeList, cafeReviewList);
  PromptStudy promptStudy = new PromptStudy(studyList);

  class MenuItem extends Menu {
    String menuId;

    public MenuItem(String title, String menuId) {
      super(title);
      this.menuId = menuId;
    }

    public MenuItem(String title, int accessScope, String menuId) {
      super(title, accessScope);
      this.menuId = menuId;
    }

    @Override
    public void execute() {
      Command command = commandMap.get(menuId);
      try {
        command.execute(new CommandRequest(commandMap));
      } catch (Exception e) {
        System.out.printf("%s 명령을 실행하는 중 오류 발생!\n", menuId);
        e.printStackTrace();
      }
    }
  }

  //=> 옵저버(리스너) 목록
  List<ApplicationContextListener> listeners = new ArrayList<>();

  //=> 옵저버(리스너)를 등록하는 메서드
  public void addApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.add(listener);
  }

  // => 옵저버(리스너)를 제거하는 메서드
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.remove(listener);
  }

  // service()에서 사용
  private void notifyOnApplicationStarted() {
    HashMap<String,Object> params = new HashMap<>();
    params.put("memberList", memberList);
    params.put("ceoMemberList", ceoMemberList);
    params.put("adminList", adminList);
    params.put("adminNoticeList", adminNoticeList);
    params.put("askBoardList", askBoardList);
    params.put("cafeList", cafeList);
    params.put("cafeReservationList", cafeReservationList);
    params.put("cafeReviewList", cafeReviewList);
    params.put("cafeRoomList", cafeRoomList);
    params.put("studyList", studyList);
    params.put("toDoList", toDoList);
    params.put("calenderList", calenderList);
    params.put("freeBoardList", freeBoardList);

    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(params);
    }
  }

  private void notifyOnApplicationEnded() {
    HashMap<String,Object> params = new HashMap<>();
    params.put("memberList", memberList);
    params.put("ceoMemberList", ceoMemberList);
    params.put("adminList", adminList);
    params.put("adminNoticeList", adminNoticeList);
    params.put("askBoardList", askBoardList);
    params.put("cafeList", cafeList);
    params.put("cafeReservationList", cafeReservationList);
    params.put("cafeReviewList", cafeReviewList);
    params.put("cafeRoomList", cafeRoomList);
    params.put("studyList", studyList);
    params.put("toDoList", toDoList);
    params.put("calenderList", calenderList);
    params.put("freeBoardList", freeBoardList);

    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(params);
    }
  }

  public ClientApp() throws Exception {

    // 서버와 통신을 담당할 객체 준비
    requestAgent = new RequestAgent("127.0.0.1", 8888);

    //commandMap.put("/member/add", new MemberAddHandler(memberList));
    // commandMap.put("/member/detail", new MemberDetailHandler(memberList, commandMap));
    //commandMap.put("/member/update", new MemberUpdateHandler(memberList, promptPerMember));
    //commandMap.put("/member/delete", 
    //    new MemberDeleteHandler(memberList, promptPerMember, studyList));

    //    commandMap.put("/ceoMember/login", new AuthCeoMemberLoginHandler(ceoMemberList, promptCeoMember));
    //    commandMap.put("/ceoMember/logout", new AuthCeoMemberLogoutHandler());
    //    commandMap.put("/ceoMember/findIdPw", new CeoFindIdPwHandler(ceoMemberList, promptCeoMember));
    //    commandMap.put("/ceoMember/add", new CeoAddHandler(ceoMemberList));
    //    commandMap.put("/ceoMember/detail", new CeoDetailHandler(ceoMemberList));
    //    commandMap.put("/ceoMember/update", new CeoUpdateHandler(ceoMemberList, promptCeoMember));
    //    commandMap.put("/ceoMember/delete", 
    //        new CeoDeleteHandler(ceoMemberList, promptCeoMember, cafeList));
    //    commandMap.put("/ceoMember/myCafeList", 
    //        new CeoCafeListHandler(ceoMemberList, cafeList, cafeReviewList, promptPerMember));
    //    commandMap.put("/ceoMember/cafeAdd", new CeoCafeAddHandler(cafeList, ceoMemberList));
    //    commandMap.put("/ceoMember/cafeUpdate", new CeoCafeUpdateHandler(ceoMemberList));
    //    commandMap.put("/ceoMember/cafeDelete", new CeoCafeDeleteHandler(cafeList, promptcafe));
    //    commandMap.put("/ceoMember/myCafeDetail", 
    //        new CeoCafeDetailHandler(ceoMemberList, cafeList, cafeReviewList, cafeRoomList));
    //    commandMap.put("/ceoMember/ReservationList", 
    //        new CeoReservationListHandler(ceoMemberList, cafeReservationList, cafeList, cafeRoomList));
    //
    //    commandMap.put("/adminMember/detail", new AdminMemberDetailHandler(memberList, promptPerMember));
    //    commandMap.put("/adminMember/update", new AdminMemberUpdateHandler(memberList, promptPerMember));
    //    commandMap.put("/adminMember/delete", new AdminMemberDeleteHandler(memberList, promptPerMember, studyList));
    //    commandMap.put("/adminMember/list", new AdminMemberListHandler(memberList, commandMap));
    //
    //    commandMap.put("/adminCeoMember/detail", new AdminCeoMemberDetailHandler(ceoMemberList, promptCeoMember));
    //    commandMap.put("/adminCeoMember/update", new AdminCeoMemberUpdateHandler(ceoMemberList, promptCeoMember));
    //    commandMap.put("/adminCeoMember/delete",
    //        new AdminCeoMemberDeleteHandler(ceoMemberList, promptCeoMember, cafeList));
    //    commandMap.put("/adminCeoMember/list", new AdminCeoMemberListHandler(ceoMemberList, commandMap));
    //
    //    ReplyAddHandler replyAddHandler = new ReplyAddHandler();
    //    ReplyDetailHandler replyDetailHandler = new ReplyDetailHandler();
    //    List<Reply> replyList = new ArrayList<>();
    //    commandMap.put("/askBoard/add",  
    //        new AskBoardAddHandler(askBoardList, memberList, ceoMemberList, replyList));
    //    commandMap.put("/askBoard/list", 
    //        new AskBoardListHandler(askBoardList, memberList, ceoMemberList, replyList));
    //    commandMap.put("/askBoard/detail", 
    //        new AskBoardDetailHandler(
    //            askBoardList, memberList, ceoMemberList, replyList, replyAddHandler, replyDetailHandler));
    //    commandMap.put("/askBoard/update", 
    //        new AskBoardUpdateHandler(askBoardList, memberList, ceoMemberList, replyList));
    //    commandMap.put("/askBoard/delete", 
    //        new AskBoardDeleteHandler(askBoardList, memberList, ceoMemberList, replyList));
    //    commandMap.put("/askBoard/myList", 
    //        new AskBoardMyListHandler(askBoardList, memberList, ceoMemberList, replyList, replyDetailHandler));
    //
    //    commandMap.put("/cafe/list", new CafeListHandler(cafeList));
    //    commandMap.put("/cafe/detail", new CafeDetailHandler(cafeList, cafeReviewList, 
    //        cafeReservationList, promptPerMember, cafeRoomList, promptcafe));
    //    commandMap.put("/cafe/search", new CafeSearchHandler(cafeList));
    //    commandMap.put("/cafeReservation/list", new CafeMyReservationListHandler(cafeList, 
    //        cafeReservationList, promptPerMember));
    //    commandMap.put("/cafeReservation/detail", new CafeMyReservationDetailHandler(cafeList, 
    //        cafeReviewList, cafeReservationList, cafeRoomList));
    //    commandMap.put("/cafe/myReviewList", new CafeMyReviewListHandler(cafeList, cafeReviewList, promptcafe));
    //
    //    commandMap.put("/cafe/control", new AdminCafeControlHandler(cafeList, cafeReviewList, 
    //        promptPerMember, promptcafe));
    //    commandMap.put("/cafe/reviewList", new AdminCafeReviewListControlHandler(cafeList, 
    //        cafeReviewList, promptcafe)); 
    //
    //    commandMap.put("/adminNotice/add", new AdminNoticeAddHandler(adminNoticeList));
    //    commandMap.put("/adminNotice/list", new AdminNoticeListHandler(adminNoticeList));
    //    commandMap.put("/adminNotice/update", new AdminNoticeUpdateHandler(adminNoticeList));
    //    commandMap.put("/adminNotice/detail", new AdminNoticeDetailHandler(adminNoticeList));
    //    commandMap.put("/adminNotice/delete", new AdminNoticeDeleteHandler(adminNoticeList));
    //
    //    commandMap.put("/admin/login", new AuthAdminLoginHandler(adminList));
    //    commandMap.put("/admin/logout", new AuthAdminLogoutHandler());
    //
    //    RandomPw randomPw = new RandomPw();
    //    commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(promptPerMember, randomPw));
    //    commandMap.put("/member/login", new AuthPerMemberLoginHandler(promptPerMember, memberList));
    //    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());
    //
    //    commandMap.put("/admin/detail", new AdminDetailHandler(adminList));
    //    commandMap.put("/admin/update", new AdminUpdateHandler(adminList));
    //
    //    commandMap.put("/study/add", new StudyAddHandler(studyList, toDoList, promptPerMember));
    //    commandMap.put("/study/list", new StudyListHandler(studyList));
    //    commandMap.put("/study/detail", new StudyDetailHandler(studyList, promptStudy));
    //    commandMap.put("/study/search", new StudySearchHandler(studyList));
    //    commandMap.put("/study/delete", new AdminStudyDeleteHandler(studyList, promptStudy));

    //    // 내 스터디 하위
    //    MyStudyCalender myStudyCalender = new MyStudyCalender(calenderList, studyList);
    //    MyStudyToDo myStudyToDo = new MyStudyToDo(toDoList, studyList);
    //    MyStudyFreeBoard myStudyFreeBoard = new MyStudyFreeBoard(freeBoardList, commentList, memberList, studyList);
    //    MyStudyGuilderList myStudyGuilderList = new MyStudyGuilderList();
    //    MyStudyGuilderDelete myStudyGuilderDelete = new MyStudyGuilderDelete();
    //    MyStudyGuilderEntrust myStudyGuilderEntrust = new MyStudyGuilderEntrust();
    //    MyStudyGuilder myStudyGuilder = new MyStudyGuilder(myStudyGuilderList, myStudyGuilderDelete, myStudyGuilderEntrust);
    //    MyStudyExit myStudyExit = new MyStudyExit();
    //    // 내 스터디 
    //    commandMap.put("/myStudy/detail", new MyStudyDetailHandler(studyList, myStudyToDo,
    //        myStudyCalender, myStudyFreeBoard, myStudyGuilder, myStudyExit, commentList, promptStudy));
    //    commandMap.put("/myStudy/delete", new MyStudyDeleteHandler(studyList, promptStudy));
    //    commandMap.put("/myStudy/list", new MyStudyListHandler(studyList));
    //    commandMap.put("/myStudy/update", new MyStudyUpdateHandler(studyList, promptStudy));
  }

  //MenuGroup에서 사용할 필터를 정의한다.
  MenuFilter menuFilter = menu -> (menu.getAccessScope() & AbstractLoginHandler.getUserAccessLevel()) > 0;

  //
  //--------------------------------------------------------
  Menu createMenu() {

    MenuGroup mainMenuGroup = new MenuGroup("메인");
    mainMenuGroup.setPrevMenuTitle("종료");

    //mainMenuGroup.add(createAdminMenu());
    mainMenuGroup.add(createMemberMenu());
    //mainMenuGroup.add(createCeoMenu());

    return mainMenuGroup;
  }

  //-----------------------------------------------------------------------------------------------
  // 개인 회원 메인
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("오늘의 공부"); 
    userMenuGroup.setMenuFilter(menuFilter);
    userMenuGroup.add(new MenuItem("회원가입", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("로그아웃", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("로그인", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("ID/PW 찾기", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // 마이페이지
    //userMenuGroup.add(createStudyMenu());       // 스터디 찾기

    //userMenuGroup.add(createMystudyMenu());     // 내 스터디

    //userMenuGroup.add(createCafeMenu());        // 장소 예약하기
    //userMenuGroup.add(createMemberCSMenu());          // 고객센터

    return userMenuGroup;
  }

  // 개인 하위 메뉴2 - 마이페이지 (로그인 했을때)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("마이 페이지", PER_LOGIN);
    myPageMenu.setMenuFilter(menuFilter);
    myPageMenu.add(new MenuItem("개인정보", "/member/detail"));
    //myPageMenu.add(new MenuItem("문의내역", "/askBoard/myList"));
    //myPageMenu.add(new MenuItem("예약내역", "/cafeReservation/list"));
    //myPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));
    return myPageMenu;
  }

  //--------------------------------------------------
  void welcomeservice() throws Exception {
    welcome().execute();
    service();
  }

  void service() throws Exception {
    notifyOnApplicationStarted();

    createMenu().execute();
    Prompt.close();

    notifyOnApplicationEnded();
  }

  static Menu welcome() {
    MenuGroup welcomeMenuGroup = new MenuGroup("발표를 시작하겠습니다");
    welcomeMenuGroup.setPrevMenuTitle("시작");
    return welcomeMenuGroup;
  }

  public static void main(String[] args) throws Exception {
    System.out.println("[PMS 클라이언트]");

    ClientApp app = new ClientApp(); 
    app.addApplicationContextListener(new AppInitListener());
    app.welcomeservice();

    Prompt.close();

    //    requestAgent = new RequestAgent("127.0.0.1", 8888);
    //
    //    while (true) {
    //      String input = Prompt.inputString("명령> ");
    //
    //      if (input.equals("/member/add")) {
    //        addMember();
    //      } else if (input.equals("/member/detail")){
    //        detailMember();
    //      } else {
    //        //out.println(input);
    //        //out.flush();
    //        requestAgent.request(input, null);
    //
    //        if (requestAgent.getStatus().equals(RequestAgent.SUCCESS)) {
    //          String result = requestAgent.getObject(String.class);
    //          System.out.println(">>> " + result);
    //        } else {
    //          System.out.println("명령 요청 실패!");
    //        }
    //      }
    //
    //      if (input.equalsIgnoreCase("quit")) {
    //        break;
    //      }
    //    }
    //
    //    requestAgent.close();
    //
    //    Prompt.close();
  }
}