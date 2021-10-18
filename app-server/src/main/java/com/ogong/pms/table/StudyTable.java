package com.ogong.pms.table;

import java.util.ArrayList;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.server.DataProcessor;
import com.ogong.server.Request;
import com.ogong.server.Response;

// 역할 
// - 회원 데이터를 저장하고 조회하는 일을 한다.
public class StudyTable extends JsonDataTable<Study> implements DataProcessor {

  public StudyTable() {
    super("study.json", Study.class);
  }

  @Override
  public void execute(Request request, Response response) throws Exception {
    switch (request.getCommand()) {
      case "study.insert" : insert(request, response); break;
      case "study.selectOne" : selectOne(request, response); break;
      case "study.selectList" : selectList(request, response); break;
      case "study.update" : update(request, response); break;
      case "study.delete" : delete(request, response); break;
      case "study.selectByKeyword" : selectByKeyword(request, response); break;
      case "study.my.selectOne" : selectOneMyStudy(request, response); break;
      default :  
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령을 지원하지 않습니다.");
    }
  }

  private void insert(Request request, Response response) throws Exception {
    Study study = request.getObject(Study.class);
    list.add(study);
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }

  private void selectList(Request request, Response response) throws Exception {
    response.setStatus(Response.SUCCESS);
    response.setValue(list);
  }


  private void selectOne(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("studyNo"));
    Study study = findByStudyNo(no);

    if (study != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(study);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디를 찾을 수 없습니다.");
    }
  }

  private void selectByKeyword(Request request, Response response) throws Exception {
    String keyword = request.getParameter("keyword");

    ArrayList<Study> searchResult = new ArrayList<>();
    for (Study study : list) {
      if (!study.getArea().contains(keyword) &&
          !study.getSubject().contains(keyword) &&
          !study.getStudyTitle().contains(keyword)) {
        continue;
      }
      searchResult.add(study);
    }

    response.setStatus(Response.SUCCESS);
    response.setValue(searchResult);
  }

  private void update(Request request, Response response) throws Exception {
    Study study = request.getObject(Study.class);

    int index = indexOf(study.getStudyNo());
    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디를 찾을 수 없습니다.");
      return;
    }

    list.set(index, study);
    response.setStatus(Response.SUCCESS);
  }

  private void delete(Request request, Response response) throws Exception {
    int no = Integer.parseInt(request.getParameter("studyNo"));
    int index = indexOf(no);

    if (index == -1) {
      response.setStatus(Response.FAIL);
      response.setValue("해당 번호의 스터디를 찾을 수 없습니다.");
      return;
    }

    list.remove(index);
    response.setStatus(Response.SUCCESS);
  }

  // 내 스터디 상세보기
  private void selectOneMyStudy(Request request, Response response) {
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    int studyNo = Integer.parseInt(request.getParameter("studyNo"));

    Study study = null;

    for (Study s : list) {
      if (s.getStudyNo() == studyNo) {
        if (s.getOwner().getPerNo() == memberNo) {
          study = s;
          break;
        } else {
          for (Member m : s.getMembers()) {
            if(m.getPerNo() == memberNo) {
              study = s;
              break;
            }
          }
        }
      }
    }

    if (study != null) {
      response.setStatus(Response.SUCCESS);
      response.setValue(study);
    } else {
      response.setStatus(Response.FAIL);
      response.setValue("해당 스터디를 찾을 수 없습니다.");
    }
  }

  private int indexOf(int studyNo) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getStudyNo() == studyNo) {
        return i;
      }
    }
    return -1;
  }

  // 스터디 전체리스트에서 찾기
  public Study findByStudyNo(int studyinputNo) {
    for (Study study : list) {
      if (study.getStudyNo() == studyinputNo) {
        return study;
      }
    }
    return null;
  }

  // 자유게시판 번호로 찾기
  //  public FreeBoard findFreeBoardByNo(int inputFreeBoardNo, List<FreeBoard> freeBoardList) {
  //    for (FreeBoard freeBoard : freeBoardList) {
  //      if (freeBoard.getFreeBoardNo() == inputFreeBoardNo) {
  //        return freeBoard;
  //      }
  //    }
  //    return null;
  //  }
}