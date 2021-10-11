package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;

public interface CafeDao {

  //-----------------------Cafe--------------------------------------

  List<Cafe> getCafeList() throws Exception;
  List<Cafe> getCafeListByMember() throws Exception;
  List<Cafe> getCafeListByCeoMember(int ceoNo) throws Exception;
  List<Cafe> findCafeListByLocation(String input) throws Exception;
  Cafe findByCafeNo(int cafeNo) throws Exception;
  Cafe findByCafeNoMember(int cafeNo) throws Exception;
  void insertCafe(Cafe cafe) throws Exception;
  void updateCafe(Cafe cafe) throws Exception;
  void deleteCafe(Cafe cafe) throws Exception;

  //-----------------------CafeReview--------------------------------------

  List<CafeReview> getCafeReviewList() throws Exception;
  List<CafeReview> findReviewListByCafeNo(int cafeNo) throws Exception;
  List<CafeReview> findReviewListByMember(int memberNo) throws Exception;
  CafeReview findByReviewNo(int reviewNo) throws Exception;
  void insertCafeReview(CafeReview cafeReview) throws Exception;
  void deleteCafeReview(int reviewNo) throws Exception;

  //-----------------------CafeRoom--------------------------------------

  List<CafeRoom> getCafeRoomList() throws Exception;
  List<CafeRoom> findCafeRoomListByCafe(int cafeNo) throws Exception;
  CafeRoom findByRoomNo(int roomNo) throws Exception;
  void insertCafeRoom(CafeRoom cafeRoom) throws Exception;
  void updateCafeRoom(CafeRoom cafeRoom) throws Exception;
  void deleteCafeRoom(int roomNo) throws Exception;

  //-----------------------CafeReservation--------------------------------------

  List<CafeReservation> getCafeReservationList() throws Exception;
  List<CafeReservation> findReservationListByMember(int memberNo) throws Exception;
  List<CafeReservation> findReservationListByCeoMember(int ceoNo, int cafeNo) throws Exception;
  CafeReservation findReservationByMember(int memberNo, int reserNo) throws Exception;
  void insertReservation(CafeReservation cafeReservation) throws Exception;
  void updateWirteReview(int reservationNo) throws Exception;
  void deleteReservation(CafeReservation cafeReservation, int status) throws Exception;

}
