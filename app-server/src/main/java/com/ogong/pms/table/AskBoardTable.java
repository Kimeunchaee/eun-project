package com.ogong.pms.table;

import com.ogong.pms.domain.AskBoard;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

// 역할
// - 문의사항 데이터를 저장하고 조회하는 일을 한다.
public class AskBoardTable extends JsonDataTable<AskBoard> implements DataProcessor {

  public AskBoardTable() {
    super("askBoard.json", AskBoard.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch (request.getCommand()) {
      case "askBoard.insert" : insert(request, response); break;
      case "askBoard.selectList" : selectList(request, response); break;
      case "askBoard.selectOne" : selectOne(request, response); break;
      case "askBoard.update" : update(request, response); break;
      case "askBoard.delete" : delete(request, response); break;
      case "askBoard.perMy.selectOne" : selectOnePerMyAskBoard(request, response); break;
      case "askBoard.ceoMy.selectOne" : selectOneCeoMyAskBoard(request, response); break;
      default : 
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void insert(Request request, Response response) throws Exception {
    AskBoard askBoard = request.getObject(AskBoard.class);
    list.add(askBoard);
    response.setStatus(Response.SUCCESS);
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    AskBoard askBoard = findByNo(no);

    if (askBoard != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(askBoard);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 문의글을 찾을 수 없습니다.");
    }
  }

  private void update(Request request, Response response) throws Exception {
    AskBoard askBoard = request.getObject(AskBoard.class);

    int index = indexOf(askBoard.getAskNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 문의글을 찾을 수 없습니다.");
      return;
    }

    list.set(index, askBoard);
    response.setStatus(Response.SUCCESS);
  }

  private void delete(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    int index = indexOf(no);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 문의글을 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  private void selectOnePerMyAskBoard(Request request, Response response) {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    int askNo = Integer.parseInt(request.getParameter("askNo"));

    AskBoard askBoard = null;

    for (AskBoard a : list) {
      if (a.getAskMemberWriter().getPerNo() == memberNo) {
        if (a.getAskNo() == askNo) {
          askBoard = a;
          break;
        }
      }
    }

    if (askBoard != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(askBoard);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 문의글을 찾을 수 없습니다.");
    }
  }

  private void selectOneCeoMyAskBoard(Request request, Response response) {
    int ceoMemberNo = Integer.parseInt(request.getParameter("ceoMemberNo"));
    int askNo = Integer.parseInt(request.getParameter("askNo"));

    AskBoard askBoard = null;

    for (AskBoard a : list) {
      if (a.getAskCeoWriter().getCeoNo() == ceoMemberNo) {
        if (a.getAskNo() == askNo) {
          askBoard = a;
          break;
        }
      }
    }

    if (askBoard != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(askBoard);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 문의글을 찾을 수 없습니다.");
    }
  }

  private AskBoard findByNo(int no) {
    for (AskBoard askBoard : list) {
      if (askBoard.getAskNo() == no) {
        return askBoard;
      }
    }
    return null;
  }

  private int indexOf(int askNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAskNo() == askNo) {
        return i;
      }
    } 
    return -1;
  }

}
