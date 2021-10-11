package com.ogong.pms.table;

import java.util.ArrayList;
import com.ogong.pms.domain.CafeReview;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

public class CafeReviewTable extends JsonDataTable<CafeReview> implements DataProcessor {

  public CafeReviewTable() {
    super("cafeReview.json", CafeReview.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch(request.getCommand()) {
      case "cafeReview.selectList" : selectList(request, response); break;
      case "cafeReview.selectListByCafeNo" : selectListByCafeNo(request, response); break;
      case "cafeReview.selectListByMember" : selectListByMember(request, response); break;
      case "cafeReview.selectOne" : selectOne(request, response); break;
      case "cafeReview.insert" : insert(request, response); break;
      case "cafeReview.updateReviewStatus" : updateReviewStatus(request, response); break;
      default:
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectListByCafeNo(Request request, Response response) throws Exception {
    int cafeNo = Integer.parseInt(request.getParameter("cafeNo"));
    ArrayList<CafeReview> listByMember = new ArrayList<>();
    for(CafeReview cafeReview : list) {
      if (cafeReview.getCafe().getNo() == cafeNo) {
        listByMember.add(cafeReview);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByMember);
  }

  private void selectListByMember(Request request, Response response) throws Exception {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    ArrayList<CafeReview> listByMember = new ArrayList<>();
    for(CafeReview cafeReview : list) {
      if (cafeReview.getMember().getPerNo() == memberNo) {
        listByMember.add(cafeReview);
      }
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(listByMember);
  }

  private void insert(Request request, Response response) throws Exception {
    CafeReview cafeReview = request.getObject(CafeReview.class);
    list.add(cafeReview);
    response.setStatus(Response.SUCCESS);
  }

  private void selectOne(Request request, Response response) throws Exception {
    int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
    CafeReview cafeReview = findByNo(reviewNo);

    if (cafeReview != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(cafeReview);
    } else {
      response.setStatus(Response.FAIL);
    }
  }

  private void updateReviewStatus(Request request, Response response) throws Exception {
    int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
    CafeReview cafeReview = findByNo(reviewNo);

    if (cafeReview == null) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 예약을 찾을 수 없습니다.");
      return;
    }

    cafeReview.setReviewStatus(1);
    response.setStatus(Response.SUCCESS);
  }

  private CafeReview findByNo(int reviewNo) {
    for (CafeReview cafeReview : list) {
      if (cafeReview.getReviewNo() == reviewNo) {
        return cafeReview;
      }
    }
    return null;
  }
}
