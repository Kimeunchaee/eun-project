package com.ogong.pms;

import static com.ogong.menu.Menu.ADMIN_LOGIN;
import static com.ogong.menu.Menu.CEO_LOGIN;
import static com.ogong.menu.Menu.LOGOUT;
import static com.ogong.menu.Menu.PER_LOGIN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.context.ApplicationContextListener;
import com.ogong.menu.Menu;
import com.ogong.menu.MenuFilter;
import com.ogong.menu.MenuGroup;
import com.ogong.pms.dao.impl.NetAdminDao;
import com.ogong.pms.dao.impl.NetAskBoardDao;
import com.ogong.pms.dao.impl.NetCafeDao;
import com.ogong.pms.dao.impl.NetCeoMemberDao;
import com.ogong.pms.dao.impl.NetMemberDao;
import com.ogong.pms.dao.impl.NetStudyDao;
import com.ogong.pms.handler.AbstractLoginHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.AuthAdminLogoutHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthCeoMemberLogoutHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLogoutHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.admin.AdminCafeApprovalHandler;
import com.ogong.pms.handler.admin.AdminCafeControlHandler;
import com.ogong.pms.handler.admin.AdminCafeDeleteHandler;
import com.ogong.pms.handler.admin.AdminCafeDetailHandler;
import com.ogong.pms.handler.admin.AdminCafeReviewListControlHandler;
import com.ogong.pms.handler.admin.AdminCafeReviewListDeleteHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberListHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberUpdateHandler;
import com.ogong.pms.handler.admin.AdminDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberListHandler;
import com.ogong.pms.handler.admin.AdminMemberUpdateHandler;
import com.ogong.pms.handler.admin.AdminNoticeAddHandler;
import com.ogong.pms.handler.admin.AdminNoticeDeleteHandler;
import com.ogong.pms.handler.admin.AdminNoticeDetailHandler;
import com.ogong.pms.handler.admin.AdminNoticeListHandler;
import com.ogong.pms.handler.admin.AdminNoticeUpdateHandler;
import com.ogong.pms.handler.admin.AdminStudyDeleteHandler;
import com.ogong.pms.handler.admin.AdminUpdateHandler;
import com.ogong.pms.handler.board.AskBoardAddHandler;
import com.ogong.pms.handler.board.AskBoardCeoMyDetailHandler;
import com.ogong.pms.handler.board.AskBoardCeoMyListHandler;
import com.ogong.pms.handler.board.AskBoardDeleteHandler;
import com.ogong.pms.handler.board.AskBoardDetailHandler;
import com.ogong.pms.handler.board.AskBoardListHandler;
import com.ogong.pms.handler.board.AskBoardPerMyDetailHandler;
import com.ogong.pms.handler.board.AskBoardPerMyListHandler;
import com.ogong.pms.handler.board.AskBoardUpdateHandler;
import com.ogong.pms.handler.board.ReplyAddHandler;
import com.ogong.pms.handler.board.ReplyDetailHandler;
import com.ogong.pms.handler.cafe.CafeDetailHandler;
import com.ogong.pms.handler.cafe.CafeListHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationDeleteHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationDetailHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationListHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewAddHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewDeleteHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewListHandler;
import com.ogong.pms.handler.cafe.CafeReservationHandler;
import com.ogong.pms.handler.cafe.CafeSearchHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeAddHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeDeleteHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeListHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomAddHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomDeleteHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomListHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomUpdateHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeUpdateHandler;
import com.ogong.pms.handler.ceoCafe.CeoReservationDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoReservationListHandler;
import com.ogong.pms.handler.ceoCafe.CeoReservationRejectHandler;
import com.ogong.pms.handler.member.CeoAddHandler;
import com.ogong.pms.handler.member.CeoDeleteHandler;
import com.ogong.pms.handler.member.CeoDetailHandler;
import com.ogong.pms.handler.member.CeoFindIdPwHandler;
import com.ogong.pms.handler.member.CeoUpdateHandler;
import com.ogong.pms.handler.member.MemberAddHandler;
import com.ogong.pms.handler.member.MemberDeleteHandler;
import com.ogong.pms.handler.member.MemberDetailHandler;
import com.ogong.pms.handler.member.MemberFindIdPwHandler;
import com.ogong.pms.handler.member.MemberUpdateHandler;
import com.ogong.pms.handler.myStudy.MyStudyDeleteHandler;
import com.ogong.pms.handler.myStudy.MyStudyDetailHandler;
import com.ogong.pms.handler.myStudy.MyStudyExitHandler;
import com.ogong.pms.handler.myStudy.MyStudyListHandler;
import com.ogong.pms.handler.myStudy.MyStudyUpdateHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderAddHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderDeleteHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderDetailHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderListHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentAddHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentDeleteHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardAddHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardDeleteHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardDetailHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardListHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.PromptFreeBoard;
import com.ogong.pms.handler.myStudy.guilder.GuilderDeleteHandler;
import com.ogong.pms.handler.myStudy.guilder.GuilderEntrustHandler;
import com.ogong.pms.handler.myStudy.guilder.GuilderListHandler;
import com.ogong.pms.handler.myStudy.guilder.WatingGuilderListHandler;
import com.ogong.pms.handler.myStudy.todo.ToDoAdd;
import com.ogong.pms.handler.myStudy.todo.ToDoDelete;
import com.ogong.pms.handler.myStudy.todo.ToDoDetail;
import com.ogong.pms.handler.myStudy.todo.ToDoList;
import com.ogong.pms.handler.myStudy.todo.ToDoUpdate;
import com.ogong.pms.handler.study.StudyAddHandler;
import com.ogong.pms.handler.study.StudyDetailHandler;
import com.ogong.pms.handler.study.StudyJoinHandler;
import com.ogong.pms.handler.study.StudyListHandler;
import com.ogong.pms.handler.study.StudySearchHandler;
import com.ogong.pms.listener.AppInitListener;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;

public class ClientApp {

  RequestAgent requestAgent;

  HashMap<String, Command> commandMap = new HashMap<>();

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

  private void notifyOnApplicationStarted() {
    HashMap<String,Object> params = new HashMap<>();

    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(params);
    }
  }

  private void notifyOnApplicationEnded() {
    HashMap<String,Object> params = new HashMap<>();

    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(params);
    }
  }       

  public ClientApp() throws Exception {
    // 로컬
    requestAgent = new RequestAgent("127.0.0.1", 5050);
    //requestAgent = new RequestAgent("192.168.0.92", 5050);
    //    requestAgent = new RequestAgent("192.168.0.68", 5050);

    // 데이터 관리를 담당할 DAO 객체를 준비한다.
    NetMemberDao memberDao = new NetMemberDao(requestAgent);
    NetAdminDao adminDao = new NetAdminDao(requestAgent);
    NetCeoMemberDao ceoMemberDao = new NetCeoMemberDao(requestAgent);
    NetAskBoardDao askBoardDao = new NetAskBoardDao(requestAgent);
    NetCafeDao cafeDao = new NetCafeDao(requestAgent);
    NetStudyDao studyDao = new NetStudyDao(requestAgent);

    System.out.println("서버에 접속 성공!"); // 접속 확인용

    RandomPw randomPw = new RandomPw();
    commandMap.put("/member/login", new AuthPerMemberLoginHandler(requestAgent));
    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());

    commandMap.put("/member/add", new MemberAddHandler(memberDao));
    commandMap.put("/member/detail", new MemberDetailHandler(memberDao));
    commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(randomPw, memberDao));
    commandMap.put("/member/update", new MemberUpdateHandler(memberDao));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberDao));

    commandMap.put("/askBoard/add", new AskBoardAddHandler(askBoardDao));
    commandMap.put("/askBoard/list", new AskBoardListHandler(askBoardDao));
    commandMap.put("/askBoard/detail", new AskBoardDetailHandler(askBoardDao));
    commandMap.put("/askBoard/update", new AskBoardUpdateHandler(askBoardDao));
    commandMap.put("/askBoard/delete", new AskBoardDeleteHandler(askBoardDao));
    commandMap.put("/askBoard/perMyList", new AskBoardPerMyListHandler(askBoardDao));
    commandMap.put("/askBoard/ceoMyList", new AskBoardCeoMyListHandler(askBoardDao));
    commandMap.put("/askBoard/PerMydetail", new AskBoardPerMyDetailHandler(askBoardDao));
    commandMap.put("/askBoard/CeoMydetail", new AskBoardCeoMyDetailHandler(askBoardDao));

    commandMap.put("/reply/add", new ReplyAddHandler(askBoardDao));
    commandMap.put("/reply/detail", new ReplyDetailHandler(askBoardDao));

    commandMap.put("/ceoMember/add", new CeoAddHandler(ceoMemberDao));
    commandMap.put("/ceoMember/detail", new CeoDetailHandler(ceoMemberDao));
    commandMap.put("/ceoMember/update", new CeoUpdateHandler(ceoMemberDao));
    commandMap.put("/ceoMember/delete", new CeoDeleteHandler(ceoMemberDao));
    commandMap.put("/ceoMember/login", new AuthCeoMemberLoginHandler(requestAgent));
    commandMap.put("/ceoMember/logout", new AuthCeoMemberLogoutHandler());
    commandMap.put("/ceoMember/findIdPw", new CeoFindIdPwHandler(randomPw, ceoMemberDao));

    commandMap.put("/admin/login", new AuthAdminLoginHandler(requestAgent));
    commandMap.put("/admin/logout", new AuthAdminLogoutHandler());

    commandMap.put("/admin/update", new AdminUpdateHandler(adminDao));
    commandMap.put("/admin/detail", new AdminDetailHandler(adminDao));

    commandMap.put("/adminCeoMember/list", new AdminCeoMemberListHandler(ceoMemberDao));
    commandMap.put("/adminCeoMember/detail", new AdminCeoMemberDetailHandler(ceoMemberDao));
    commandMap.put("/adminCeoMember/update", new AdminCeoMemberUpdateHandler(ceoMemberDao));
    commandMap.put("/adminCeoMember/delete", new AdminCeoMemberDeleteHandler(ceoMemberDao));

    commandMap.put("/adminMember/list", new AdminMemberListHandler(memberDao));
    commandMap.put("/adminMember/update", new AdminMemberUpdateHandler(memberDao));
    commandMap.put("/adminMember/detail", new AdminMemberDetailHandler(memberDao));
    commandMap.put("/adminMember/delete", new AdminMemberDeleteHandler(memberDao));

    commandMap.put("/adminNotice/add", new AdminNoticeAddHandler(adminDao));
    commandMap.put("/adminNotice/list", new AdminNoticeListHandler(adminDao));
    commandMap.put("/adminNotice/detail", new AdminNoticeDetailHandler(adminDao));
    commandMap.put("/adminNotice/update", new AdminNoticeUpdateHandler(adminDao));
    commandMap.put("/adminNotice/delete", new AdminNoticeDeleteHandler(adminDao));

    commandMap.put("/study/delete", new AdminStudyDeleteHandler(studyDao));

    commandMap.put("/study/add", new StudyAddHandler(studyDao));
    commandMap.put("/study/list", new StudyListHandler(studyDao));
    commandMap.put("/study/detail", new StudyDetailHandler(studyDao));
    commandMap.put("/study/search", new StudySearchHandler(studyDao));
    commandMap.put("/study/join", new StudyJoinHandler(studyDao));

    commandMap.put("/myStudy/list", new MyStudyListHandler(studyDao));
    commandMap.put("/myStudy/detail", new MyStudyDetailHandler(studyDao));
    commandMap.put("/myStudy/update", new MyStudyUpdateHandler(studyDao));
    commandMap.put("/myStudy/delete", new MyStudyDeleteHandler(studyDao));
    commandMap.put("/myStudy/exit", new MyStudyExitHandler(studyDao));
    commandMap.put("/myStudy/guilder", new GuilderListHandler(studyDao));

    commandMap.put("/myStudy/listGuilder", new WatingGuilderListHandler(studyDao));
    commandMap.put("/myStudy/entrustGuilder", new GuilderEntrustHandler(studyDao));
    commandMap.put("/myStudy/deleteGuilder", new GuilderDeleteHandler(studyDao));

    commandMap.put("/myStudy/calenderAdd", new CalenderAddHandler(studyDao));
    commandMap.put("/myStudy/calenderList", new CalenderListHandler(studyDao));
    commandMap.put("/myStudy/calenderDetail", new CalenderDetailHandler(studyDao));
    commandMap.put("/myStudy/calenderUpdate", new CalenderUpdateHandler(studyDao));
    commandMap.put("/myStudy/calenderDelete", new CalenderDeleteHandler(studyDao));

    PromptFreeBoard promptFreeBoard = new PromptFreeBoard(requestAgent);
    commandMap.put("/myStudy/freeBoardList", new FreeBoardListHandler(studyDao));
    commandMap.put("/myStudy/freeBoardAdd", new FreeBoardAddHandler(studyDao));
    commandMap.put("/myStudy/freeBoardDetail", new FreeBoardDetailHandler(studyDao, promptFreeBoard));
    commandMap.put("/myStudy/freeBoardUpdate", new FreeBoardUpdateHandler(studyDao));
    commandMap.put("/myStudy/freeBoardDelete", new FreeBoardDeleteHandler(studyDao));

    //Socket chatSocket = new Socket(); 
    //    commandMap.put("/myStudy/chat", new MyStudyChat(requestAgent));
    //commandMap.put("/myStudy/chatOpen", new MySocketServer(chatSocket, requestAgent));
    //commandMap.put("/myStudy/chatStart", new MySocketClient(requestAgent));

    commandMap.put("/myStudy/freeBoard/commentDelete", new CommentDeleteHandler(studyDao));
    commandMap.put("/myStudy/freeBoard/commentAdd", new CommentAddHandler(studyDao));
    commandMap.put("/myStudy/freeBoard/commentUpdate", new CommentUpdateHandler(studyDao));

    commandMap.put("/myStudy/todoAdd", new ToDoAdd(studyDao));
    commandMap.put("/myStudy/todoList", new ToDoList(studyDao));
    commandMap.put("/myStudy/todoDetail", new ToDoDetail(studyDao));
    commandMap.put("/myStudy/todoUpdate", new ToDoUpdate(studyDao));
    commandMap.put("/myStudy/todoDelete", new ToDoDelete(studyDao));

    commandMap.put("/cafe/list", new CafeListHandler(cafeDao));
    commandMap.put("/cafe/detail", new CafeDetailHandler(cafeDao));
    commandMap.put("/cafe/reservation", new CafeReservationHandler(cafeDao));
    commandMap.put("/cafe/search", new CafeSearchHandler(cafeDao));
    commandMap.put("/cafe/search", new CafeSearchHandler(cafeDao));

    commandMap.put("/cafeReservation/list", new CafeMyReservationListHandler(cafeDao));
    commandMap.put("/cafeReservation/detail", new CafeMyReservationDetailHandler(cafeDao));
    commandMap.put("/cafeReservation/delete", new CafeMyReservationDeleteHandler(cafeDao));

    commandMap.put("/cafe/myReviewList", new CafeMyReviewListHandler(cafeDao));
    commandMap.put("/cafe/myReviewAdd", new CafeMyReviewAddHandler(cafeDao));
    commandMap.put("/cafe/myReviewDelete", new CafeMyReviewDeleteHandler(cafeDao));

    commandMap.put("/ceoMember/myCafeList", new CeoCafeListHandler(cafeDao));
    commandMap.put("/ceoMember/myCafeDetail", new CeoCafeDetailHandler(cafeDao));
    commandMap.put("/ceoMember/cafeAdd", new CeoCafeAddHandler(cafeDao));
    commandMap.put("/ceoMember/cafeUpdate", new CeoCafeUpdateHandler(cafeDao));
    commandMap.put("/ceoMember/cafeDelete", new CeoCafeDeleteHandler(cafeDao));

    commandMap.put("/ceoMember/cafeRoomList", new CeoCafeRoomListHandler(cafeDao));
    commandMap.put("/ceoMember/cafeRoomDetail", new CeoCafeRoomDetailHandler(cafeDao));
    commandMap.put("/ceoMember/cafeRoomAdd", new CeoCafeRoomAddHandler(cafeDao));
    commandMap.put("/ceoMember/cafeRoomUpdate", new CeoCafeRoomUpdateHandler(cafeDao));
    commandMap.put("/ceoMember/cafeRoomDelete", new CeoCafeRoomDeleteHandler(cafeDao));

    commandMap.put("/ceoMember/ReservationList", new CeoReservationListHandler(cafeDao));
    commandMap.put("/ceoMember/ReservationDetail", new CeoReservationDetailHandler(cafeDao));
    commandMap.put("/ceoMember/ReservationReject", new CeoReservationRejectHandler(cafeDao));

    commandMap.put("/cafe/control", new AdminCafeControlHandler(cafeDao));
    commandMap.put("/cafe/controlDetail", new AdminCafeDetailHandler(cafeDao));
    commandMap.put("/cafe/controlApproval", new AdminCafeApprovalHandler(cafeDao));
    commandMap.put("/cafe/controlDelete", new AdminCafeDeleteHandler(cafeDao));

    commandMap.put("/cafe/reviewList", new AdminCafeReviewListControlHandler(cafeDao)); 
    commandMap.put("/cafe/reviewListDelete", new AdminCafeReviewListDeleteHandler(cafeDao)); 

  }  

  // 람다 문법 적용 전
  //  class MyFilter implements MenuFilter {
  //    @Override
  //    public boolean accept(Menu menu) {
  //      return (menu.getAccessScope() & AbstractLoginHandler.getUserAccessLevel()) > 0; 
  //    }
  //  }

  MenuFilter menuFilter = menu -> (menu.getAccessScope() & AbstractLoginHandler.getUserAccessLevel()) > 0;

  static Menu welcome() {
    MenuGroup welcomeMenuGroup = new MenuGroup("발표를 시작하겠습니다");
    welcomeMenuGroup.setPrevMenuTitle("시작");
    return welcomeMenuGroup;
  }

  //--------------------------------------------------------
  Menu createMenu() {

    MenuGroup mainMenuGroup = new MenuGroup("메인");
    mainMenuGroup.setPrevMenuTitle("종료");

    mainMenuGroup.add(createAdminMenu());
    mainMenuGroup.add(createMemberMenu());
    mainMenuGroup.add(createCeoMenu());

    return mainMenuGroup;
  }        

  // -----------------------------------------------------------------------------------------------
  // 관리자 메인
  Menu createAdminMenu() {
    MenuGroup adminMenuGroup = new MenuGroup("👑 관리자");
    adminMenuGroup.setMenuFilter(menuFilter);
    adminMenuGroup.add(new MenuItem("🖐 로그인", LOGOUT, "/admin/login"));
    adminMenuGroup.add(new MenuItem("🖐 로그아웃", ADMIN_LOGIN, "/admin/logout"));
    adminMenuGroup.add(new MenuItem("🙂 마이 페이지", ADMIN_LOGIN, "/admin/detail"));

    adminMenuGroup.add(createControlMemberMenu());  // 회원 관리
    adminMenuGroup.add(createControlStudyMenu());   // 스터디 관리
    adminMenuGroup.add(createControlReviewMenu());  // 장소 관리
    adminMenuGroup.add(createAdminCSMenu());        // 고객센터 관리

    return adminMenuGroup;
  }

  // 관리자 하위 메뉴2 - 회원 관리
  private Menu createControlMemberMenu() {
    MenuGroup adminUserMenu = new MenuGroup("📁 회원 관리", ADMIN_LOGIN); 
    adminUserMenu.setMenuFilter(menuFilter);
    adminUserMenu.add(new MenuItem("개인 회원 조회", "/adminMember/list"));
    adminUserMenu.add(new MenuItem("개인 회원 상세", "/adminMember/detail"));
    adminUserMenu.add(new MenuItem("사장 회원 조회", "/adminCeoMember/list"));
    adminUserMenu.add(new MenuItem("사장 회원 상세", "/adminCeoMember/detail"));

    return adminUserMenu;
  }

  // 관리자 하위 메뉴3 - 스터디 관리
  private Menu createControlStudyMenu() {
    MenuGroup adminStudyMenu = new MenuGroup("📖 스터디 관리", ADMIN_LOGIN); 
    adminStudyMenu.setMenuFilter(menuFilter);
    adminStudyMenu.add(new MenuItem("목록","/study/list"));
    adminStudyMenu.add(new MenuItem("삭제","/study/delete"));
    return adminStudyMenu;
  }

  // 관리자 하위 메뉴4 - 장소 후기 관리
  private Menu createControlReviewMenu() {
    MenuGroup adminCafeReviewMenu = new MenuGroup("🏘 장소 관리", ADMIN_LOGIN); 
    adminCafeReviewMenu.setMenuFilter(menuFilter);
    adminCafeReviewMenu.add(new MenuItem("장소 게시글 관리","/cafe/control"));
    adminCafeReviewMenu.add(new MenuItem("장소 리뷰 관리","/cafe/reviewList")); 

    return adminCafeReviewMenu;
  }

  //관리자 하위 메뉴5 - 고객센터 관리
  private Menu createAdminCSMenu() {
    MenuGroup csMenu = new MenuGroup("💌 고객센터 관리", ADMIN_LOGIN);
    csMenu.setMenuFilter(menuFilter);
    csMenu.add(createAdminNoticeMenu());
    csMenu.add(createAdminAskMenu());

    return csMenu;
  }

  // 5-1
  private Menu createAdminNoticeMenu() {
    MenuGroup adminNoticeMenu = new MenuGroup("📢 공지사항"); 
    adminNoticeMenu.setMenuFilter(menuFilter);
    adminNoticeMenu.add(new MenuItem("등록", "/adminNotice/add"));
    adminNoticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    adminNoticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return adminNoticeMenu;
  }

  // 5-2
  private Menu createAdminAskMenu() {
    MenuGroup adminaskMenu = new MenuGroup("💬 문의사항");
    adminaskMenu.setMenuFilter(menuFilter);
    adminaskMenu.add(new MenuItem("목록", "/askBoard/list"));
    adminaskMenu.add(new MenuItem("상세", "/askBoard/detail"));

    return adminaskMenu;
  }

  // -----------------------------------------------------------------------------------------------
  // 개인 회원 메인
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("🎓 오늘의 공부"); 
    userMenuGroup.setMenuFilter(menuFilter);
    userMenuGroup.add(new MenuItem("💕 회원가입", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("🖐 로그아웃", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("🖐 로그인", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("❔ ID/PW 찾기", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // 마이페이지
    userMenuGroup.add(createStudyMenu());       // 스터디 찾기

    userMenuGroup.add(createMystudyMenu());     // 내 스터디

    userMenuGroup.add(createCafeMenu());        // 장소 예약하기
    userMenuGroup.add(createMemberCSMenu());    // 고객센터

    return userMenuGroup;
  }

  // 개인 하위 메뉴2 - 마이페이지 (로그인 했을때)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("🔒 마이 페이지", PER_LOGIN); 
    myPageMenu.setMenuFilter(menuFilter);
    myPageMenu.add(new MenuItem("🙂 개인정보", "/member/detail"));
    myPageMenu.add(new MenuItem("💬 문의내역", "/askBoard/perMyList"));
    myPageMenu.add(new MenuItem("📞 예약내역", "/cafeReservation/list"));
    myPageMenu.add(new MenuItem("📝 후기내역", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("😢 탈퇴하기", "/member/delete"));
    return myPageMenu;
  }

  //개인 하위 메뉴3 - 스터디 찾기
  private Menu createStudyMenu() {
    MenuGroup allStudyMenu = new MenuGroup("📖 스터디 찾기"); 
    allStudyMenu.setMenuFilter(menuFilter);
    allStudyMenu.add(new MenuItem("등록", PER_LOGIN, "/study/add"));
    allStudyMenu.add(new MenuItem("목록","/study/list"));
    allStudyMenu.add(new MenuItem("검색","/study/search"));
    allStudyMenu.add(new MenuItem("상세","/study/detail"));

    return allStudyMenu; 
  }     

  // 이거 일단 다 보이게 하고 들어갔을 때 if문으로 필터하기 !!!!!!!
  // (조장 아니면 들어는 갈 수 있는데 if문으로 팅김)
  //개인 하위 메뉴4 - 내 스터디
  private Menu createMystudyMenu() {
    MenuGroup myStudyMenu = new MenuGroup("🖊 내 스터디", PER_LOGIN);
    myStudyMenu.setMenuFilter(menuFilter);
    myStudyMenu.add(new MenuItem("목록", "/myStudy/list"));
    myStudyMenu.add(new MenuItem("상세", "/myStudy/detail"));

    return myStudyMenu; 
  }

  //개인 하위 메뉴5 - 스터디 장소
  private Menu createCafeMenu() {
    MenuGroup cafeMenu = new MenuGroup("🏘 장소 예약"); 
    cafeMenu.setMenuFilter(menuFilter);
    cafeMenu.add(new MenuItem("목록", "/cafe/list"));
    cafeMenu.add(new MenuItem("검색", "/cafe/search"));
    cafeMenu.add(new MenuItem("상세", "/cafe/detail"));

    return cafeMenu;
  }

  //개인 하위 메뉴6 - 고객센터
  private Menu createMemberCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("💌 고객센터");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createMemberNoticeMenu());
    memberCSMenu.add(createMemberAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createMemberNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("📢 공지사항"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 회원 권한
  private Menu createMemberAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("💬 문의사항");
    askBoardMenu.setMenuFilter(menuFilter);
    askBoardMenu.add(new MenuItem("등록", PER_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("목록", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("상세", "/askBoard/detail"));

    return askBoardMenu;
  }

  //-----------------------------------------------------------------------------------------------

  // 기업
  Menu createCeoMenu() {
    MenuGroup ceoMemberMenuGroup = new MenuGroup("🏘 오늘의 공부 - 사장님");
    ceoMemberMenuGroup.setMenuFilter(menuFilter);
    ceoMemberMenuGroup.add(new MenuItem("💕 회원가입", LOGOUT, "/ceoMember/add"));
    ceoMemberMenuGroup.add(new MenuItem("🖐 로그인", LOGOUT, "/ceoMember/login"));
    ceoMemberMenuGroup.add(new MenuItem("❔ ID/PW 찾기", LOGOUT, "/ceoMember/findIdPw"));
    ceoMemberMenuGroup.add(new MenuItem("🖐 로그아웃", CEO_LOGIN, "/ceoMember/logout"));

    ceoMemberMenuGroup.add(createCeoPageMenu());      // 마이페이지

    ceoMemberMenuGroup.add(createCeoCSMenu());          // 고객센터

    return ceoMemberMenuGroup;
  }

  // 기업
  private Menu createCeoPageMenu() {
    MenuGroup ceoPageMenu = new MenuGroup("🔒 마이 페이지", CEO_LOGIN); 
    ceoPageMenu.setMenuFilter(menuFilter);
    ceoPageMenu.add(new MenuItem("🙂 기업 프로필", "/ceoMember/detail"));
    //ceoPageMenu.add(new MenuItem("카페 등록", "/cafe/add"));
    ceoPageMenu.add(new MenuItem("🏘 카페 목록", "/ceoMember/myCafeList"));
    ceoPageMenu.add(new MenuItem("💬 문의내역", "/askBoard/ceoMyList"));
    ceoPageMenu.add(new MenuItem("📞 예약내역", "/ceoMember/ReservationList"));
    //    ceoPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));

    // 이거 기업프로필에 있음 지우기~~~
    //    ceoPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));

    return ceoPageMenu;
  }


  //기업 하위 메뉴6 - 고객센터
  private Menu createCeoCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("💌 고객센터");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createCeoNoticeMenu());
    memberCSMenu.add(createCeoAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createCeoNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("📢 공지사항"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 기업 권한
  private Menu createCeoAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("💬 문의사항");
    askBoardMenu.setMenuFilter(menuFilter);
    askBoardMenu.add(new MenuItem("등록", CEO_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("목록", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("상세", "/askBoard/detail"));

    return askBoardMenu;
  }

  void welcomeservice() throws Exception {
    welcome().execute();
    service();
  }

  void service() throws Exception{
    notifyOnApplicationStarted();

    createMenu().execute(); 
    requestAgent.request("quit", null);
    Prompt.close();

    notifyOnApplicationEnded();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("[ 🎓 오늘의 공부 클라이언트 ]");
    ClientApp app = new ClientApp(); 

    app.addApplicationContextListener(new AppInitListener());
    //app.addApplicationContextListener(new FileListener());

    app.welcomeservice();
    Prompt.close();
  }

}