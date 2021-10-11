package com.ogong.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.ogong.util.Prompt;


public class MenuGroup extends Menu {

  static Stack<Menu> breadCrumb = new Stack<>();

  ArrayList<Menu> childs = new ArrayList<>();

  boolean disablePrevMenu;
  String prevMenuTitle = "👈 이전 메뉴";

  // 메뉴 목록을 출력할 때 출력 여부를 결정할 객체를 보관한다.
  MenuFilter menuFilter;

  static public boolean successLogin = false;

  // 이전으로 이동시키는 메뉴를 표현하기 위해 만든 클래스
  private static class PrevMenu extends Menu {
    public PrevMenu() {
      super("");
    }
    @Override
    public void execute() {
    }
  }
  static PrevMenu prevMenu = new PrevMenu();

  public MenuGroup(String title) {
    super(title);
  }
  public MenuGroup(String title, int accessScope) {
    super(title, accessScope);
  }

  public MenuGroup(String title, boolean disablePrevMenu) {
    super(title);
    this.disablePrevMenu = disablePrevMenu;
  }

  public MenuGroup(String title, boolean disablePrevMenu, int accessScope) {
    super(title);
    this.accessScope = accessScope;
  }

  public void setPrevMenuTitle(String prevMenuTitle) {
    this.prevMenuTitle = prevMenuTitle;
  }

  public void setMenuFilter(MenuFilter menuFilter) {
    this.menuFilter = menuFilter;
  }

  //MenuGroup이 포함하는 하위 Menu를 다룰 수 있도록 메서드를 정의한다.
  public void add(Menu child) {
    childs.add(child);
  }

  // 배열에 들어 있는 Menu 객체를 찾아 제거한다.
  public Menu remove(Menu child) {
    if (childs.remove(child)) 
      return child;
    return null;
  }

  @Override 
  public void execute() {

    // 현재 실행하는 메뉴를 스택에 보관한다.
    breadCrumb.push(this);

    while (true) {

      if (successLogin) {
        successLogin = false;
        breadCrumb.pop();
        break;
      }

      printBreadCrumbMenuTitle();
      List<Menu> menuList = getMenuList();
      printMenuList(menuList);

      try {
        Menu menu = selectMenu(menuList);
        if (menu == null) {
          System.out.println("무효한 메뉴 번호입니다.");
          continue;
        }
        if (menu instanceof PrevMenu) {
          breadCrumb.pop();
          return;
        }

        menu.execute();

      } catch (Exception e) {
        System.out.println("-----------------------------------------");
        System.out.printf ("오류 발생: %s\n", e.getClass().getName());
        e.printStackTrace();   // 왜 에러 떳는지 알려준다.
        System.out.println("-----------------------------------------");
      }
    }
  }


  private String getBreadCrumb() {
    String path = "";

    for (int i = 0; i < breadCrumb.size(); i++) {
      if (path.length() > 0) {
        path += " / ";
      }
      Menu menu = breadCrumb.get(i); 
      path += menu.title;
    }

    // 메인, 관리자 메뉴는 안보이게 (로그아웃 전에 메인, 관리자를 못가게해야함)
    //    if (AuthPerMemberLoginHandler.getLoginUser() != null) {
    //      path += " - " + AuthPerMemberLoginHandler.getLoginUser().getPerNickname();
    //    }

    return path;
  }

  // 출력될 메뉴 목록 준비
  // 왜?
  // - 메뉴 출력 속도를 빠르게 하기 위함.
  // - 메뉴를 출력할 때 출력할 메뉴와 출력하지 말아야 할 메뉴를 구분하는
  //   시간을 줄이기 위함.
  private List<Menu> getMenuList() {
    ArrayList<Menu> menuList = new ArrayList<>();
    for (Menu menu : childs) {
      if (menuFilter != null && !menuFilter.accept(menu)) {
        // 메뉴 필터가 있을 때, 그 메뉴 필터에서 승인하지 않는다면
        // 출력할 메뉴에 포함시키지 않는다.
        continue;
      }
      menuList.add(menu);
      //      if ((menu.accessScope & 
      //          AuthAdminLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      } 
      //      else if ((menu.accessScope & 
      //          AuthPerMemberLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      }
      //      else if ((menu.accessScope & 
      //          AuthCeoMemberLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      }
    }


    return menuList;
  }


  private void printBreadCrumbMenuTitle() {
    System.out.printf("\n[%s]\n", getBreadCrumb());
  }

  private void printMenuList(List<Menu> menuList) {
    int i = 1;
    for (Menu menu : menuList) {
      System.out.printf("%d. %-20s\n", i++ , menu.title);
    }

    if (!disablePrevMenu) {
      System.out.printf("0. %s\n", this.prevMenuTitle);
    }
  }

  private Menu selectMenu(List<Menu> menuList)  {
    int menuNo = Prompt.inputInt("선택> ");

    if (menuNo < 0 || menuNo > menuList.size()) {
      return null;
    }

    if (menuNo == 0 && !disablePrevMenu) {
      return prevMenu;   // 호출한 쪽에 '이전 메뉴' 선택을 알리기 위해
    } 

    return menuList.get(menuNo - 1);
  }

}



