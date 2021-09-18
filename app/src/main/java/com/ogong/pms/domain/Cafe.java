package com.ogong.pms.domain;

import java.time.LocalTime;

public class Cafe {

  private int no; // 카페 번호
  private CeoMember ceoMember; // 사장
  private String cafeLicenseNo; // 사업자등록번호
  private String name; // 상호명
  private String mainImg; //카페 썸네일 이미지
  private String info; // 소개글
  private String location; // 주소
  private String phone; // 전화번호
  private LocalTime openTime; // 오픈시간
  private LocalTime closeTime; // 마감시간
  private String holiday; // 휴무일
  private int bookable; // 예약가능인원(개인좌석)
  private int timePrice; // 시간당금액(개인좌석)
  private int cafeStatus; // 0 : 승인대기 , 1 : 운영중 , 2 : 운영중단 3 : 삭제 -> cafeStatus 관리자, 사장 한테만 보임

  @Override
  public String toString() {
    return "Cafe [no=" + no + ", ceoMember=" + ceoMember + ", cafeLicenseNo=" + cafeLicenseNo
        + ", name=" + name + ", mainImg=" + mainImg + ", info=" + info + ", location=" + location
        + ", phone=" + phone + ", openTime=" + openTime + ", closeTime=" + closeTime + ", holiday="
        + holiday + ", bookable=" + bookable + ", timePrice=" + timePrice + ", cafeStatus="
        + cafeStatus + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public CeoMember getCeoMember() {
    return ceoMember;
  }

  public void setCeoMember(CeoMember ceoMember) {
    this.ceoMember = ceoMember;
  }

  public String getCafeLicenseNo() {
    return cafeLicenseNo;
  }

  public void setCafeLicenseNo(String cafeLicenseNo) {
    this.cafeLicenseNo = cafeLicenseNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMainImg() {
    return mainImg;
  }

  public void setMainImg(String mainImg) {
    this.mainImg = mainImg;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalTime getOpenTime() {
    return openTime;
  }

  public void setOpenTime(LocalTime openTime) {
    this.openTime = openTime;
  }

  public LocalTime getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(LocalTime closeTime) {
    this.closeTime = closeTime;
  }

  public String getHoliday() {
    return holiday;
  }

  public void setHoliday(String holiday) {
    this.holiday = holiday;
  }

  public int getBookable() {
    return bookable;
  }

  public void setBookable(int bookable) {
    this.bookable = bookable;
  }

  public int getTimePrice() {
    return timePrice;
  }

  public void setTimePrice(int timePrice) {
    this.timePrice = timePrice;
  }

  public int getCafeStatus() {
    return cafeStatus;
  }

  public void setCafeStatus(int cafeStatus) {
    this.cafeStatus = cafeStatus;
  }

}
