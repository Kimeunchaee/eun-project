package com.ogong.pms.table;

import com.ogong.pms.domain.Admin;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

//역할 
//- 관리자 데이터를 저장하고 조회하는 일을 한다.
public class AdminTable extends JsonDataTable<Admin> implements DataProcessor {

  public AdminTable() {
    super("admin.json", Admin.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch (request.getCommand()) {
      case "admin.update" : update(request, response); break;
      case "admin.selectOne" : selectOne(request, response); break;
      case "admin.selectOneByEmailPassword" : selectOneByEmailPassword(request, response); break;
      default : 
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("adminNo"));
    Admin admin = findByAdminNo(no);

    if (admin != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(admin);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 관리자를 찾을 수 없습니다.");
    }
  }

  private void selectOneByEmailPassword(Request request, Response response) throws Exception {
    String email = request.getParameter("adminEmail");
    String password = request.getParameter("adminPassword");

    Admin admin = null;
    for (Admin ad : list) {
      if (ad.getMasterEmail().equals(email) && ad.getMasterPassword().equals(password)) {
        admin = ad;
        break;
      }
    }

    if (admin != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(admin);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 이메일과 암호를 가진 관리자를 찾을 수 없습니다.");
    }
  }

  private void update(Request request, Response response) throws Exception {
    Admin admin = request.getObject(Admin.class);

    int index = indexOf(admin.getMasterNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 관리자를 찾을 수 없습니다.");
      return;
    }

    list.set(index, admin);
    response.setStatus(Response.SUCCESS);
  }



  private Admin findByAdminNo(int no) {
    for (Admin admin : list) {
      if (admin.getMasterNo() == no) {
        return admin;
      }
    }
    return null;
  }

  private int indexOf(int adminNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getMasterNo() == adminNo) {
        return i;
      }
    }
    return -1;
  }
}