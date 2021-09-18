package com.ogong.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.util.Prompt;


public class MenuGroup extends Menu {

  static Stack<Menu> breadCrumb = new Stack<>();

  ArrayList<Menu> childs = new ArrayList<>();

  boolean disablePrevMenu;
  String prevMenuTitle = "이전 메뉴";
  static public boolean successLogin = false;

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

  public void add(Menu child) {
    childs.add(child);
  }

  public Menu remove(Menu child) {
    if (childs.remove(child)) 
      return child;
    return null;
  }

  @Override 
  public void execute() {

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
    if (AuthPerMemberLoginHandler.getLoginUser() != null) {
      path += " - " + AuthPerMemberLoginHandler.getLoginUser().getPerNickname();
    }

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
      if ((menu.accessScope & 
          AuthAdminLoginHandler.getUserAccessLevel()) > 0 ) {
        menuList.add(menu);
      } 
      else if ((menu.accessScope & 
          AuthPerMemberLoginHandler.getUserAccessLevel()) > 0 ) {
        menuList.add(menu);
      }
      else if ((menu.accessScope & 
          AuthCeoMemberLoginHandler.getUserAccessLevel()) > 0 ) {
        menuList.add(menu);
      }
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



