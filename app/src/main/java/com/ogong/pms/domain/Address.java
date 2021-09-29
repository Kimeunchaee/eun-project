package com.ogong.pms.domain;

public class Address {

  private String zipNo; // 우편번호
  private String lnmAdres; // 도로명주소
  private String rnAdres; // 지번주소

  @Override
  public String toString() {
    return "Address [zipNo=" + zipNo + ", lnmAdres=" + lnmAdres + ", rnAdres=" + rnAdres + "]";
  }

  public String getZipNo() {
    return zipNo;
  }

  public void setZipNo(String zipNo) {
    this.zipNo = zipNo;
  }

  public String getLnmAdres() {
    return lnmAdres;
  }

  public void setLnmAdres(String lnmAdres) {
    this.lnmAdres = lnmAdres;
  }

  public String getRnAdres() {
    return rnAdres;
  }

  public void setRnAdres(String rnAdres) {
    this.rnAdres = rnAdres;
  }

}
