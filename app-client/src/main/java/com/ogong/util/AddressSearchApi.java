package com.ogong.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ogong.pms.domain.Address;

public class AddressSearchApi {

  public Address searchAddress() throws IOException {
    Address address = null;

    while (true) {
      System.out.println();
      System.out.println(" > 주소 검색");
      String searchKeyword = Prompt.inputString(" 예) 판교역로 235, 분당 주공, 삼평동 681 > ");
      System.out.println();

      StringBuilder urlBuilder = 
          new StringBuilder("http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll"); /*URL*/
      urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=jdTdDJnUw0kp/YUP/kTSolkPIS45XusfzTcL5a15e394LlBKFhZCsp6N/fnYBUjrHHfu7rHSkVgljiBOQtjQNA=="); /*Service Key*/
      urlBuilder.append("&" + URLEncoder.encode("srchwrd","UTF-8") + "=" + URLEncoder.encode(searchKeyword, "UTF-8")); /*검색어*/
      urlBuilder.append("&" + URLEncoder.encode("countPerPage","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지당 출력될 개수를 지정(최대50)*/
      urlBuilder.append("&" + URLEncoder.encode("currentPage","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*출력될 페이지 번호*/
      //      System.out.println(urlBuilder.toString());

      address = xmlParsing(urlBuilder.toString());

      if (address == null) {
        continue;
      } else {
        break;
      }
    }
    return address;
  }

  private Address xmlParsing(String url) {
    Map<Integer,Address> addressMapper = new HashMap<>();
    int inputNo = 0;

    try {
      DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
      Document doc = dBuilder.parse(url);

      // root tag 
      doc.getDocumentElement().normalize();

      // 파싱할 tag
      NodeList nList = doc.getElementsByTagName("newAddressListAreaCdSearchAll");
      //      System.out.println("파싱할 리스트 수 : "+ nList.getLength());

      if (nList.getLength() == 0) {
        System.out.println(" 검색 결과가 존재하지 않습니다.");
      }

      for (int i = 0; i < nList.getLength(); i++) {
        Address address = new Address();
        Node nNode = nList.item(i);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          System.out.println(" ("+(i+1)+")");
          System.out.println(" 우편번호  : " + getTagValue("zipNo", eElement));
          System.out.println(" 도로명주소  : " + getTagValue("lnmAdres", eElement));
          System.out.println(" 지번주소  : " + getTagValue("rnAdres", eElement));
          address.setZipNo(getTagValue("zipNo", eElement));
          address.setLnmAdres(getTagValue("lnmAdres", eElement));
          address.setRnAdres(getTagValue("rnAdres", eElement));
          addressMapper.put((i+1), address);
        }
      }

      while (true) {
        System.out.println();
        inputNo = Prompt.inputInt(" 주소 선택(다시 검색(0)) > ");

        if (inputNo < 0 || inputNo > addressMapper.size()) {
          System.out.println(" >> 잘못된 번호입니다. 다시 입력해 주세요.");
          continue;
        } else {
          break;
        } 
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (inputNo == 0) {
      return null;
    }

    System.out.println();
    return addressMapper.get(inputNo);
  }

  //tag값의 정보를 가져오는 메소드
  private static String getTagValue(String tag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
    Node nValue = nlList.item(0);
    if(nValue == null) 
      return null;
    return nValue.getNodeValue();
  }

}
