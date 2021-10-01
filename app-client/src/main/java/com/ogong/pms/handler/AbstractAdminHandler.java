package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Admin;

public abstract class AbstractAdminHandler implements Command {

  List<Admin> adminList;

  static Admin loginAdmin;
  public static Admin getLoginAdmin() {
    return loginAdmin;
  }

  public AbstractAdminHandler(List<Admin> adminList) {
    this.adminList = adminList;
  }



}
