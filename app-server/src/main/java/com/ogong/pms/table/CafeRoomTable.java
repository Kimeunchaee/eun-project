package com.ogong.pms.table;

import java.util.ArrayList;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

public class CafeRoomTable extends JsonDataTable<CafeRoom> implements DataProcessor {

  public CafeRoomTable() {
    super("cafeRoom.json", CafeRoom.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch(request.getCommand()) {
      case "cafeRoom.selectList" : selectList(request, response); break;
      case "cafeRoom.selectListByCafe" : selectListByCafe(request, response); break;
      case "cafeRoom.selectOne" : selectOne(request, response); break;
      case "cafeRoom.selectOneByRoomNoCafeNo" : selectOneByRoomNoCafeNo(request, response); break;
      case "cafeRoom.insert" : insert(request, response); break;
      case "cafeRoom.update" : update(request, response); break;
      case "cafeRoom.delete" : delete(request, response); break;
      default:
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectListByCafe(Request request, Response response) throws Exception {
    ArrayList<CafeRoom> listByCafeNo = new ArrayList<>();
    int cafeNo = Integer.parseInt(request.getParameter("cafeNo"));
    for(CafeRoom cafeRoom : list) {
      if (cafeRoom.getCafe().getNo() == cafeNo) {
        listByCafeNo.add(cafeRoom);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByCafeNo);
  }

  private void selectOne(Request request, Response response) throws Exception {
    int roomNo = Integer.parseInt(request.getParameter("roomNo"));
    CafeRoom cafeRoom = findByRoomNo(roomNo);

    if (cafeRoom != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(cafeRoom);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디룸을 찾을 수 없습니다.");
    }
  }

  private void selectOneByRoomNoCafeNo(Request request, Response response) throws Exception {
    int roomNo = Integer.parseInt(request.getParameter("roomNo"));
    int cafeNo = Integer.parseInt(request.getParameter("cafeNo"));
    CafeRoom cafeRoom = findByRoomNoCafeNo(roomNo, cafeNo);

    if (cafeRoom != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(cafeRoom);
    } else {
      response.setStatus(Response.FAIL);
    }
  }

  private void insert(Request request, Response response) throws Exception {
    CafeRoom cafeRoom = request.getObject(CafeRoom.class);
    list.add(cafeRoom);
    response.setStatus(Response.SUCCESS);
  }

  private void update(Request request, Response response) throws Exception {
    CafeRoom cafeRoom = request.getObject(CafeRoom.class);

    int index = indexOf(cafeRoom.getRoomNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디룸을 찾을 수 없습니다.");
      return;
    }

    list.set(index, cafeRoom);
    response.setStatus(Response.SUCCESS);
  }

  private void delete(Request request, Response response) throws Exception {
    int roomNo = Integer.parseInt(request.getParameter("roomNo"));
    int index = indexOf(roomNo);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 게시글을 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  private CafeRoom findByRoomNo(int roomNo) {
    for (CafeRoom cafeRoom : list) {
      if (cafeRoom.getRoomNo() == roomNo) {
        return cafeRoom;
      }
    }
    return null;
  }

  private CafeRoom findByRoomNoCafeNo(int roomNo, int cafeNo) {
    for (CafeRoom cafeRoom : list) {
      if (cafeRoom.getRoomNo() == roomNo && cafeRoom.getCafe().getNo() == cafeNo) {
        return cafeRoom;
      }
    }
    return null;
  }

  private int indexOf(int roomNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getRoomNo() == roomNo) {
        return i;
      }
    }
    return -1;
  }
}
