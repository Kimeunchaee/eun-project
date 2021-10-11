package com.ogong.pms.table;

import com.ogong.pms.domain.CeoMember;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

// 역할 
// - 기업 회원 데이터를 저장하고 조회하는 일을 한다.
public class CeoMemberTable extends JsonDataTable<CeoMember> implements DataProcessor {

  public CeoMemberTable() {
    super("ceoMember.json", CeoMember.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch (request.getCommand()) {
      case "ceoMember.insert" : insert(request, response); break;
      case "ceoMember.selectList" : selectList(request, response); break;
      case "ceoMember.selectOneByEmailPassword" : selectOneByEmailPassword(request, response); break;
      case "ceoMember.selectOneByNickname" : selectOneByNickname(request, response); break;
      case "ceoMember.selectOneByEmail" : selectOneByEmail(request, response); break;
      case "ceoMember.selectOne" : selectOne(request, response); break;
      case "ceoMember.update" : update(request, response); break;
      case "ceoMember.delete" : delete(request, response); break;
      default : 
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void insert(Request request, Response response) throws Exception {
    CeoMember ceoMember = request.getObject(CeoMember.class);
    list.add(ceoMember);
    response.setStatus(Response.SUCCESS);
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectOneByEmailPassword(Request request, Response response) throws Exception {
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    CeoMember ceoMember = null;
    for (CeoMember m : list) {
      if (m.getCeoEmail().equals(email) && m.getCeoPassword().equals(password)) {
        ceoMember = m;
        break;
      }
    }

    if (ceoMember != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(ceoMember);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 이메일과 암호를 가진 회원을 찾을 수 없습니다.");
    }
  }

  private void selectOneByNickname(Request request, Response response) throws Exception {
    String nick = request.getParameter("ceoMemberNick");

    CeoMember ceoMember = null;
    for (CeoMember m : list) {
      if (m.getCeoBossName().equals(nick)) {
        ceoMember = m;
        break;
      }
    }

    if (ceoMember != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(ceoMember);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 이메일과 암호를 가진 회원을 찾을 수 없습니다.");
    }
  }

  private void selectOneByEmail(Request request, Response response) throws Exception {
    String email = request.getParameter("ceoMemberEmail");

    CeoMember ceoMember = null;
    for (CeoMember m : list) {
      if (m.getCeoEmail().equals(email)) {
        ceoMember = m;
        break;
      }
    }

    if (ceoMember != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(ceoMember);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 이메일과 암호를 가진 회원을 찾을 수 없습니다.");
    }
  }

  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("ceoMemberNo"));
    CeoMember m = findByNo(no);

    if (m != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(m);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 회원을 찾을 수 없습니다.");
    }
  }

  private void update(Request request, Response response) throws Exception {
    CeoMember ceoMember = request.getObject(CeoMember.class);

    int index = indexOf(ceoMember.getCeoNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 회원을 찾을 수 없습니다.");
      return;
    }

    list.set(index, ceoMember);
    response.setStatus(Response.SUCCESS);
  }

  private void delete(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("ceoMemberNo"));
    int index = indexOf(no);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 회원을 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  private CeoMember findByNo(int no) {
    for (CeoMember ceoMember : list) {
      if (ceoMember.getCeoNo() == no) {
        return ceoMember;
      }
    }
    return null;
  }

  private int indexOf(int memberNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getCeoNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

}
