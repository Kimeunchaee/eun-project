package com.ogong.pms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;

public class ListCafeDao implements CafeDao {

  List<Cafe> cafeList = new ArrayList<>();
  List<CafeReview> cafeReviewList = new ArrayList<>();
  List<CafeRoom> cafeRoomList = new ArrayList<>();
  List<CafeReservation> cafeReservationList = new ArrayList<>();

  //-----------------------Cafe--------------------------------------

  @Override
  public List<Cafe> getCafeList() throws Exception {
    return cafeList;
  }

  @Override
  public List<Cafe> getCafeListByMember() throws Exception {
    ArrayList<Cafe> listByMember = new ArrayList<>();
    for(Cafe cafe : cafeList) {
      if (cafe.getCafeStatus() != Cafe.WAIT && cafe.getCafeStatus() != Cafe.DELETE) {
        listByMember.add(cafe);
      }
    }
    return listByMember;
  }

  @Override
  public List<Cafe> getCafeListByCeoMember(int ceoNo) throws Exception {
    ArrayList<Cafe> listByCeoMember = new ArrayList<>();
    for(Cafe cafe : cafeList) {
      if (cafe.getCeoMember().getCeoNo() == ceoNo && cafe.getCafeStatus() != Cafe.DELETE) {
        listByCeoMember.add(cafe);
      }
    }
    return listByCeoMember;
  }

  @Override
  public List<Cafe> findCafeListByLocation(String input) throws Exception {
    ArrayList<Cafe> listByLocation = new ArrayList<>();
    for(Cafe cafe : cafeList) {
      if (cafe.getCafeStatus() != Cafe.WAIT && 
          cafe.getCafeStatus() != Cafe.DELETE &&
          cafe.getLocation().contains(input)) {
        listByLocation.add(cafe);
      }
    }
    return listByLocation;
  }

  @Override
  public Cafe findByCafeNo(int cafeNo) throws Exception {
    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == cafeNo) {
        return cafe;
      }
    }
    return null;
  }

  @Override
  public Cafe findByCafeNoMember(int cafeNo) throws Exception {
    for (Cafe cafe : cafeList) {
      if (cafe.getNo() == cafeNo && 
          cafe.getCafeStatus() != Cafe.WAIT && 
          cafe.getCafeStatus() != Cafe.DELETE) {
        return cafe;
      }
    }
    return null;
  }

  @Override
  public void insertCafe(Cafe cafe) throws Exception {
    cafeList.add(cafe);
  }

  @Override
  public void updateCafe(Cafe cafe) throws Exception {
    for (int i = 0; i < cafeList.size(); i++) {
      if (cafeList.get(i).getNo() == cafe.getNo()) {
        cafeList.set(i, cafe);
        return;
      }
    }
  }

  @Override
  public void deleteCafe(Cafe cafe) throws Exception {
    for (int i = 0; i < cafeList.size(); i++) {
      if (cafeList.get(i).getNo() == cafe.getNo()) {
        cafeList.set(i, cafe);
        return;
      }
    }
  }

  //-----------------------CafeReview--------------------------------------

  @Override
  public List<CafeReview> getCafeReviewList() throws Exception {
    return cafeReviewList;
  }

  @Override
  public List<CafeReview> findReviewListByCafeNo(int cafeNo) throws Exception {
    ArrayList<CafeReview> listByMember = new ArrayList<>();
    for(CafeReview cafeReview : cafeReviewList) {
      if (cafeReview.getCafe().getNo() == cafeNo) {
        listByMember.add(cafeReview);
      }
    }
    return listByMember;
  }

  @Override
  public List<CafeReview> findReviewListByMember(int memberNo) throws Exception {
    ArrayList<CafeReview> listByMember = new ArrayList<>();
    for(CafeReview cafeReview : cafeReviewList) {
      if (cafeReview.getMember().getPerNo() == memberNo) {
        listByMember.add(cafeReview);
      }
    }
    return listByMember;
  }

  @Override
  public CafeReview findByReviewNo(int reviewNo) throws Exception {
    for (CafeReview cafeReview : cafeReviewList) {
      if (cafeReview.getReviewNo() == reviewNo) {
        return cafeReview;
      }
    }
    return null;
  }

  @Override
  public void insertCafeReview(CafeReview cafeReview) throws Exception {
    cafeReviewList.add(cafeReview);
  }

  @Override
  public void deleteCafeReview(int reviewNo) throws Exception {
    for (int i = 0; i < cafeReviewList.size(); i++) {
      if (cafeReviewList.get(i).getReviewNo() == reviewNo) {
        CafeReview cafeReview = cafeReviewList.get(i);
        cafeReview.setReviewStatus(1);
        cafeReviewList.set(i, cafeReview);
        return;
      }
    }
  }

  //-----------------------CafeRoom--------------------------------------

  @Override
  public List<CafeRoom> getCafeRoomList() throws Exception {
    return cafeRoomList;
  }

  @Override
  public List<CafeRoom> findCafeRoomListByCafe(int cafeNo) throws Exception {
    ArrayList<CafeRoom> listByCafeNo = new ArrayList<>();
    for(CafeRoom cafeRoom : cafeRoomList) {
      if (cafeRoom.getCafe().getNo() == cafeNo) {
        listByCafeNo.add(cafeRoom);
      }
    }
    return listByCafeNo;
  }

  @Override
  public CafeRoom findByRoomNo(int roomNo) throws Exception {
    for (CafeRoom cafeRoom : cafeRoomList) {
      if (cafeRoom.getRoomNo() == roomNo) {
        return cafeRoom;
      }
    }
    return null;
  }

  @Override
  public void insertCafeRoom(CafeRoom cafeRoom) throws Exception {
    cafeRoomList.add(cafeRoom);
  }

  @Override
  public void updateCafeRoom(CafeRoom cafeRoom) throws Exception {
    for (int i = 0; i < cafeRoomList.size(); i++) {
      if (cafeRoomList.get(i).getRoomNo() == cafeRoom.getRoomNo()) {
        cafeRoomList.set(i, cafeRoom);
        return;
      }
    }
  }

  @Override
  public void deleteCafeRoom(int roomNo) throws Exception {
    for (int i = 0; i < cafeRoomList.size(); i++) {
      if (cafeRoomList.get(i).getRoomNo() == roomNo) {
        cafeRoomList.remove(i);
        return;
      }
    }
  }

  //-----------------------CafeReservation--------------------------------------

  @Override
  public List<CafeReservation> getCafeReservationList() throws Exception {
    return cafeReservationList;
  }

  @Override
  public List<CafeReservation> findReservationListByMember(int memberNo) throws Exception {
    ArrayList<CafeReservation> listByMember = new ArrayList<>();
    for(CafeReservation cafeReservation : cafeReservationList) {
      if (cafeReservation.getMember().getPerNo() == memberNo) {
        listByMember.add(cafeReservation);
      }
    }
    return listByMember;
  }

  @Override
  public List<CafeReservation> findReservationListByCeoMember(int ceoNo, int cafeNo)
      throws Exception {
    ArrayList<CafeReservation> listByCeoMember = new ArrayList<>();
    for(CafeReservation cafeReservation : cafeReservationList) {
      if (cafeReservation.getCafe().getCeoMember().getCeoNo() == ceoNo &&
          cafeReservation.getCafe().getNo() == cafeNo) {
        listByCeoMember.add(cafeReservation);
      }
    }
    return listByCeoMember;
  }

  @Override
  public CafeReservation findReservationByMember(int memberNo, int reserNo) throws Exception {
    for (CafeReservation reservation : cafeReservationList) {
      if (reservation.getMember().getPerNo() == memberNo &&
          reservation.getReservationNo() == reserNo) {
        return reservation;
      }
    }
    return null;
  }

  @Override
  public void insertReservation(CafeReservation cafeReservation) throws Exception {
    cafeReservationList.add(cafeReservation);
  }

  @Override
  public void updateWirteReview(int reservationNo) throws Exception {
    for (int i = 0; i < cafeReservationList.size(); i++) {
      if (cafeReservationList.get(i).getReservationNo() == reservationNo) {
        CafeReservation reservation = cafeReservationList.get(i);
        reservation.setWirteReview(true);
        cafeReservationList.set(i, reservation);
        return;
      }
    }
  }

  @Override
  public void deleteReservation(CafeReservation cafeReservation, int status) throws Exception {
    for (int i = 0; i < cafeReservationList.size(); i++) {
      if (cafeReservationList.get(i).getReservationNo() == cafeReservation.getReservationNo()) {
        CafeReservation reservation = cafeReservationList.get(i);
        reservation.setReservationStatus(status);
        cafeReservationList.set(i, reservation);
        return;
      }
    }
  }
}
