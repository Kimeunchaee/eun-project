package com.ogong.pms.table;

import com.ogong.pms.domain.AdminNotice;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

// 역할 
// - 회원 데이터를 저장하고 조회하는 일을 한다.
public class NoticeTable extends JsonDataTable<AdminNotice> implements DataProcessor {

  public NoticeTable() {
    super("adminNotice.json", AdminNotice.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch (request.getCommand()) {
      case "notice.insert" : insert(request, response); break;
      case "notice.selectOne" : selectOne(request, response); break;
      case "notice.selectList" : selectList(request, response); break;
      case "notice.update" : update(request, response); break;
      case "notice.delete" : delete(request, response); break;
      default : 
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void insert(Request request, Response response) throws Exception {
    AdminNotice notice = request.getObject(AdminNotice.class);
    list.add(notice);
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("noticeNo"));
    AdminNotice notice = findByNoticeNo(no);

    if (notice != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(notice);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디를 찾을 수 없습니다.");
    }
  }

  private void update(Request request, Response response) throws Exception {
    AdminNotice notice = request.getObject(AdminNotice.class);

    int index = indexOf(notice.getAdminNotiNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 공지글을 찾을 수 없습니다.");
      return;
    }

    list.set(index, notice);
    response.setStatus(Response.SUCCESS);
  }

  public AdminNotice findByNoticeNo(int inputNo) {
    for (AdminNotice notice : list) {
      if (notice.getAdminNotiNo() == inputNo) {
        return notice;
      }
    }
    return null;
  }

  private void delete(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("noticeNo"));
    int index = indexOf(no);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 공지글을 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  private int indexOf(int noticeNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAdminNotiNo() == noticeNo) {
        return i;
      }
    }
    return -1;
  }

}
