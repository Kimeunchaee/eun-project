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
import com.ogong.pms.handler.AbstractLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLogoutHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.member.MemberAddHandler;
import com.ogong.pms.handler.member.MemberDetailHandler;
import com.ogong.pms.handler.member.MemberUpdateHandler;
import com.ogong.pms.listener.AppInitListener;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

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
    requestAgent = new RequestAgent("127.0.0.1", 5050);

    commandMap.put("/member/login", new AuthPerMemberLoginHandler(requestAgent));
    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());

    commandMap.put("/member/add", new MemberAddHandler(requestAgent));
    commandMap.put("/member/detail", new MemberDetailHandler(requestAgent));
    //commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(requestAgent));
    commandMap.put("/member/update", new MemberUpdateHandler(requestAgent));
    //commandMap.put("/member/detail", new MemberDetailHandler(requestAgent));
  }  

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
    MenuGroup adminMenuGroup = new MenuGroup("관리자");

    adminMenuGroup.add(new MenuItem("로그인", LOGOUT, "/admin/login"));
    adminMenuGroup.add(new MenuItem("로그아웃", ADMIN_LOGIN, "/admin/logout"));
    adminMenuGroup.add(new MenuItem("마이 페이지", ADMIN_LOGIN, "/admin/detail"));

    adminMenuGroup.add(createControlMemberMenu());  // 회원 관리
    adminMenuGroup.add(createControlStudyMenu());   // 스터디 관리
    adminMenuGroup.add(createControlReviewMenu());  // 장소 관리
    adminMenuGroup.add(createAdminCSMenu());        // 고객센터 관리

    return adminMenuGroup;
  }

  // 관리자 하위 메뉴2 - 회원 관리
  private Menu createControlMemberMenu() {
    MenuGroup adminUserMenu = new MenuGroup("회원 관리", ADMIN_LOGIN); 

    adminUserMenu.add(new MenuItem("개인 회원 조회", "/adminMember/list"));
    adminUserMenu.add(new MenuItem("개인 회원 상세", "/adminMember/detail"));
    adminUserMenu.add(new MenuItem("사장 회원 조회", "/adminCeoMember/list"));
    adminUserMenu.add(new MenuItem("사장 회원 상세", "/adminCeoMember/detail"));

    return adminUserMenu;
  }

  // 관리자 하위 메뉴3 - 스터디 관리
  private Menu createControlStudyMenu() {
    MenuGroup adminStudyMenu = new MenuGroup("스터디 관리", ADMIN_LOGIN); 

    adminStudyMenu.add(new MenuItem("목록","/study/list"));
    adminStudyMenu.add(new MenuItem("삭제","/study/delete"));
    return adminStudyMenu;
  }

  // 관리자 하위 메뉴4 - 장소 후기 관리
  private Menu createControlReviewMenu() {
    MenuGroup adminCafeReviewMenu = new MenuGroup("장소 관리", ADMIN_LOGIN); 

    adminCafeReviewMenu.add(new MenuItem("장소 게시글 관리","/cafe/control"));
    adminCafeReviewMenu.add(new MenuItem("장소 리뷰 관리","/cafe/reviewList")); 

    return adminCafeReviewMenu;
  }

  //관리자 하위 메뉴5 - 고객센터 관리
  private Menu createAdminCSMenu() {
    MenuGroup csMenu = new MenuGroup("고객센터 관리", ADMIN_LOGIN);

    csMenu.add(createAdminNoticeMenu());
    csMenu.add(createAdminAskMenu());

    return csMenu;
  }

  // 5-1
  private Menu createAdminNoticeMenu() {
    MenuGroup adminNoticeMenu = new MenuGroup("공지사항"); 

    adminNoticeMenu.add(new MenuItem("등록", "/adminNotice/add"));
    adminNoticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    adminNoticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return adminNoticeMenu;
  }

  // 5-2
  private Menu createAdminAskMenu() {
    MenuGroup adminaskMenu = new MenuGroup("문의사항");

    adminaskMenu.add(new MenuItem("목록", "/askBoard/list"));
    adminaskMenu.add(new MenuItem("상세", "/askBoard/detail"));

    return adminaskMenu;
  }

  // -----------------------------------------------------------------------------------------------
  // 개인 회원 메인
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("오늘의 공부"); 
    userMenuGroup.setMenuFilter(menuFilter);
    userMenuGroup.add(new MenuItem("회원가입", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("로그아웃", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("로그인", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("ID/PW 찾기", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // 마이페이지
    userMenuGroup.add(createStudyMenu());       // 스터디 찾기

    userMenuGroup.add(createMystudyMenu());     // 내 스터디

    userMenuGroup.add(createCafeMenu());        // 장소 예약하기
    userMenuGroup.add(createMemberCSMenu());          // 고객센터

    return userMenuGroup;
  }

  // 개인 하위 메뉴2 - 마이페이지 (로그인 했을때)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("마이 페이지", PER_LOGIN); 
    myPageMenu.setMenuFilter(menuFilter);
    myPageMenu.add(new MenuItem("개인정보", "/member/detail"));
    myPageMenu.add(new MenuItem("문의내역", "/askBoard/myList"));
    myPageMenu.add(new MenuItem("예약내역", "/cafeReservation/list"));
    myPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));
    return myPageMenu;
  }

  //개인 하위 메뉴3 - 스터디 찾기
  private Menu createStudyMenu() {
    MenuGroup allStudyMenu = new MenuGroup("스터디 찾기"); 
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
    MenuGroup myStudyMenu = new MenuGroup("내 스터디", PER_LOGIN);
    myStudyMenu.setMenuFilter(menuFilter);
    myStudyMenu.add(new MenuItem("목록", "/myStudy/list"));
    myStudyMenu.add(new MenuItem("상세", "/myStudy/detail"));

    return myStudyMenu; 
  }

  //개인 하위 메뉴5 - 스터디 장소
  private Menu createCafeMenu() {
    MenuGroup cafeMenu = new MenuGroup("장소 예약"); 
    cafeMenu.setMenuFilter(menuFilter);
    cafeMenu.add(new MenuItem("목록", "/cafe/list"));
    cafeMenu.add(new MenuItem("검색", "/cafe/search"));
    cafeMenu.add(new MenuItem("상세", "/cafe/detail"));

    return cafeMenu;
  }

  //개인 하위 메뉴6 - 고객센터
  private Menu createMemberCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("고객센터");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createMemberNoticeMenu());
    memberCSMenu.add(createMemberAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createMemberNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("공지사항"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 회원 권한
  private Menu createMemberAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("문의사항");
    askBoardMenu.setMenuFilter(menuFilter);
    askBoardMenu.add(new MenuItem("등록", PER_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("목록", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("상세", "/askBoard/detail"));

    return askBoardMenu;
  }

  //-----------------------------------------------------------------------------------------------

  // 기업
  Menu createCeoMenu() {
    MenuGroup ceoMemberMenuGroup = new MenuGroup("오늘의 공부 - 사장님");
    ceoMemberMenuGroup.setMenuFilter(menuFilter);
    ceoMemberMenuGroup.add(new MenuItem("회원가입", LOGOUT, "/ceoMember/add"));
    ceoMemberMenuGroup.add(new MenuItem("로그인", LOGOUT, "/ceoMember/login"));
    ceoMemberMenuGroup.add(new MenuItem("ID/PW 찾기", LOGOUT, "/ceoMember/findIdPw"));
    ceoMemberMenuGroup.add(new MenuItem("로그아웃", CEO_LOGIN, "/ceoMember/logout"));

    ceoMemberMenuGroup.add(createCeoPageMenu());      // 마이페이지

    ceoMemberMenuGroup.add(createCeoCSMenu());          // 고객센터

    return ceoMemberMenuGroup;
  }

  // 기업 정보 >> 로그인하라고 뜸
  private Menu createCeoPageMenu() {
    MenuGroup ceoPageMenu = new MenuGroup("마이 페이지", CEO_LOGIN); 
    ceoPageMenu.setMenuFilter(menuFilter);
    ceoPageMenu.add(new MenuItem("기업 프로필", "/ceoMember/detail"));
    //ceoPageMenu.add(new MenuItem("카페 등록", "/cafe/add"));
    ceoPageMenu.add(new MenuItem("카페 목록", "/ceoMember/myCafeList"));
    ceoPageMenu.add(new MenuItem("문의내역", "/askBoard/myList"));
    ceoPageMenu.add(new MenuItem("예약내역", "/ceoMember/ReservationList"));
    //    ceoPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));
    //    ceoPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));

    return ceoPageMenu;
  }


  //기업 하위 메뉴6 - 고객센터
  private Menu createCeoCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("고객센터");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createCeoNoticeMenu());
    memberCSMenu.add(createCeoAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createCeoNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("공지사항"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 기업 권한
  private Menu createCeoAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("문의사항");
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
    //requestAgent.request("quit", null);
    Prompt.close();

    notifyOnApplicationEnded();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("[오늘의 공부 클라이언트]");
    ClientApp app = new ClientApp(); 

    app.addApplicationContextListener(new AppInitListener());
    //app.addApplicationContextListener(new FileListener());

    app.welcomeservice();
    Prompt.close();
  }

}