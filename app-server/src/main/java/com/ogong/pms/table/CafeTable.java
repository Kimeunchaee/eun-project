package com.ogong.pms.table;

import java.util.ArrayList;
import com.ogong.pms.domain.Cafe;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

public class CafeTable extends JsonDataTable<Cafe> implements DataProcessor {

  public CafeTable() {
    super("cafe.json", Cafe.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch(request.getCommand()) {
      case "cafe.selectList" : selectList(request, response); break;
      case "cafe.selectListByMember" : selectListByMember(request, response); break;
      case "cafe.selectListByCeoMember" : selectListByCeoMember(request, response); break;
      case "cafe.selectListByLocation" : selectListByLocation(request, response); break;
      case "cafe.selectOne" : selectOne(request, response); break;
      case "cafe.selectOneByMember" : selectOneByMember(request, response); break;
      case "cafe.insert" : insert(request, response); break;
      case "cafe.update" : update(request, response); break;
      case "cafe.delete" : delete(request, response); break;
      default:
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  // 관리자용 전체리스트
  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  // 관리자 카페 삭제
  private void delete(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("cafeNo"));
    int index = indexOf(no);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 회원을 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  // 일반회원용 전체리스트
  private void selectListByMember(Request request, Response response) throws Exception {
    ArrayList<Cafe> listByMember = new ArrayList<>();
    for(Cafe cafe : list) {
      if (cafe.getCafeStatus() != Cafe.WAIT && cafe.getCafeStatus() != Cafe.DELETE) {
        listByMember.add(cafe);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByMember);
  }

  //사장용 카페리스트
  private void selectListByCeoMember(Request request, Response response) throws Exception {
    ArrayList<Cafe> listByCeoMember = new ArrayList<>();
    int ceoNo = Integer.parseInt(request.getParameter("ceoNo"));
    for(Cafe cafe : list) {
      if (cafe.getCeoMember().getCeoNo() == ceoNo && cafe.getCafeStatus() != Cafe.DELETE) {
        listByCeoMember.add(cafe);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByCeoMember);
  }

  private void selectListByLocation(Request request, Response response) throws Exception {
    ArrayList<Cafe> listByLocation = new ArrayList<>();
    String inputLocation = request.getParameter("inputLocation");

    for(Cafe cafe : list) {
      if (cafe.getCafeStatus() != Cafe.WAIT && 
          cafe.getCafeStatus() != Cafe.DELETE &&
          cafe.getLocation().contains(inputLocation)) {
        listByLocation.add(cafe);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByLocation);
  }

  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("cafeNo"));
    Cafe cafe = findByCafeNo(no);

    if (cafe != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(cafe);
    } else {
      response.setStatus(Response.FAIL);
    }
  }

  // 일반회원용
  private void selectOneByMember(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("cafeNo"));
    Cafe cafe = findByCafeNoByMember(no);

    if (cafe != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(cafe);
    } else {
      response.setStatus(Response.FAIL);
    }
  }

  private void insert(Request request, Response response) throws Exception {
    Cafe cafe = request.getObject(Cafe.class);
    list.add(cafe);
    response.setStatus(Response.SUCCESS);
  }

  private Cafe findByCafeNo(int no) {
    for (Cafe cafe : list) {
      if (cafe.getNo() == no) {
        return cafe;
      }
    }
    return null;
  }

  private void update(Request request, Response response) throws Exception {
    Cafe cafe = request.getObject(Cafe.class);

    int index = indexOf(cafe.getNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 카페를 찾을 수 없습니다.");
      return;
    }

    list.set(index, cafe);
    response.setStatus(Response.SUCCESS);
  }

  private Cafe findByCafeNoByMember(int no) {
    for (Cafe cafe : list) {
      if (cafe.getNo() == no && 
          cafe.getCafeStatus() != Cafe.WAIT && 
          cafe.getCafeStatus() != Cafe.DELETE) {
        return cafe;
      }
    }
    return null;
  }

  private int indexOf(int cafeNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == cafeNo) {
        return i;
      }
    }
    return -1;
  }
}
