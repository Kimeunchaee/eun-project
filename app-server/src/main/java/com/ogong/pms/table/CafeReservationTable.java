package com.ogong.pms.table;

import java.util.ArrayList;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

public class CafeReservationTable extends JsonDataTable<CafeReservation> implements DataProcessor {

  public CafeReservationTable() {
    super("cafeReservation.json", CafeReservation.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch(request.getCommand()) {
      case "cafeReservation.selectList" : selectList(request, response); break;
      case "cafeReservation.selectListByMember" : selectListByMember(request, response); break;
      case "cafeReservation.selectListByCeoMember" : selectListByCeoMember(request, response); break;
      case "cafeReservation.selectOneByMember" : selectOneByMember(request, response); break;
      case "cafeReservation.insert" : insert(request, response); break;
      case "cafeReservation.updateWirteReview" : updateWirteReview(request, response); break;
      case "cafeReservation.updateReservationStatus" : updateReservationStatus(request, response); break;
      default:
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectListByMember(Request request, Response response) throws Exception {
    ArrayList<CafeReservation> listByMember = new ArrayList<>();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));

    for(CafeReservation cafeReservation : list) {
      if (cafeReservation.getMember().getPerNo() == memberNo) {
        listByMember.add(cafeReservation);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByMember);
  }

  private void selectListByCeoMember(Request request, Response response) throws Exception {
    ArrayList<CafeReservation> listByCeoMember = new ArrayList<>();
    int ceoNo = Integer.parseInt(request.getParameter("ceoNo"));
    int cafeNo = Integer.parseInt(request.getParameter("cafeNo"));

    for(CafeReservation cafeReservation : list) {
      if (cafeReservation.getCafe().getCeoMember().getCeoNo() == ceoNo &&
          cafeReservation.getCafe().getNo() == cafeNo) {
        listByCeoMember.add(cafeReservation);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByCeoMember);
  }

  private void selectOneByMember(Request request, Response response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    int reservationNo = Integer.parseInt(request.getParameter("reservationNo"));
    CafeReservation reservation = findByMemberNoReservationNo(memberNo, reservationNo);

    if (reservation != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(reservation);
    } else {
      response.setStatus(Response.FAIL);
    }

  }

  private void insert(Request request, Response response) throws Exception {
    CafeReservation cafeReservation = request.getObject(CafeReservation.class);
    list.add(cafeReservation);
    response.setStatus(Response.SUCCESS);
  }

  private CafeReservation findByMemberNoReservationNo(int memberNo, int reservationNo) {
    for (CafeReservation reservation : list) {
      if (reservation.getMember().getPerNo() == memberNo &&
          reservation.getReservationNo() == reservationNo) {
        return reservation;
      }
    }
    return null;
  }

  private void updateWirteReview(Request request, Response response) throws Exception {
    int cafeReservationNo = Integer.parseInt(request.getParameter("reservationNo"));
    CafeReservation cafeReservation = findByNo(cafeReservationNo);

    if (cafeReservation == null) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 예약을 찾을 수 없습니다.");
      return;
    }

    cafeReservation.setWirteReview(true);
    response.setStatus(Response.SUCCESS);
  }

  private void updateReservationStatus(Request request, Response response) throws Exception {
    int cafeReservationNo = Integer.parseInt(request.getParameter("reservationNo"));
    int status = Integer.parseInt(request.getParameter("status"));
    CafeReservation cafeReservation = findByNo(cafeReservationNo);

    if (cafeReservation == null) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 예약을 찾을 수 없습니다.");
      return;
    }

    cafeReservation.setReservationStatus(status);
    response.setStatus(Response.SUCCESS);
  }

  private CafeReservation findByNo(int cafeReservationNo) {
    for (CafeReservation reservation : list) {
      if (reservation.getReservationNo() == cafeReservationNo) {
        return reservation;
      }
    }
    return null;
  }

}
