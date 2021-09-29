package com.ogong.pms.handler.mystudy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.util.Prompt;

public class MyStudyFreeBoard {

  int freeBoardNo = 4;
  List<FreeBoard> freeBoardList;
  List<Comment> commentList;
  List<Member> memberList;
  List<Study> studyList;
  Study study;

  public MyStudyFreeBoard(List<FreeBoard> freeBoardList, List<Comment> commentList,
      List<Member> memberList, List<Study> studyList) {
    this.freeBoardList = freeBoardList;
    this.commentList = commentList;
    this.memberList = memberList;
    this.studyList = studyList;

    //    FreeBoard test = new FreeBoard();
    //    test.setFreeBoardNo(1);
    //    test.setFreeBoardTitle("게시글1");
    //    test.setFreeBoardContent("5월 10일에 만나요");
    //    test.setFreeBoardAtcFile("지도");
    //    test.setFreeBoardWriter(memberList.get(0));
    //    test.setFreeBoardViewcount(test.getFreeBoardViewcount());
    //    test.setFreeBoardRegisteredDate(new Date(System.currentTimeMillis()));
    //    test.setComment(new ArrayList<>());
    //    freeBoardList.add(test);
    //    studyList.get(0).getMyStudyFreeBoard().add(test);
    //
    //    test = new FreeBoard();
    //    test.setFreeBoardNo(2);
    //    test.setFreeBoardTitle("게시글2");
    //    test.setFreeBoardContent("아주아주 잘하고 있습니다");
    //    test.setFreeBoardAtcFile("jpg");
    //    test.setFreeBoardWriter(memberList.get(0));
    //    test.setFreeBoardViewcount(test.getFreeBoardViewcount());
    //    test.setFreeBoardRegisteredDate(new Date(System.currentTimeMillis()));
    //    test.setComment(new ArrayList<>());
    //    freeBoardList.add(test);
    //    studyList.get(0).getMyStudyFreeBoard().add(test);
    //
    //    test = new FreeBoard();
    //    test.setFreeBoardNo(3);
    //    test.setFreeBoardTitle("게시글3");
    //    test.setFreeBoardContent("159p 이상합니다");
    //    test.setFreeBoardAtcFile("문제집");
    //    test.setFreeBoardWriter(memberList.get(0));
    //    test.setFreeBoardViewcount(test.getFreeBoardViewcount());
    //    test.setFreeBoardRegisteredDate(new Date(System.currentTimeMillis()));
    //    test.setComment(new ArrayList<>());
    //    freeBoardList.add(test);
    //    studyList.get(0).getMyStudyFreeBoard().add(test);
  }

  // 등록
  private void addFreeBoard(List<Comment> commentList, Study study) {
    System.out.println();
    System.out.println("▶ 게시글 작성");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();
    FreeBoard freeBoard = new FreeBoard();

    freeBoard.setFreeBoardTitle(Prompt.inputString(" 제목 : "));
    freeBoard.setFreeBoardContent(Prompt.inputString(" 내용 : "));
    freeBoard.setFreeBoardAtcFile(Prompt.inputString(" 첨부파일 : "));
    freeBoard.setFreeBoardWriter(member);
    freeBoard.setFreeBoardViewcount(freeBoard.getFreeBoardViewcount());
    freeBoard.setComment(new ArrayList<>());
    freeBoard.setFreeBoardRegisteredDate(new Date(System.currentTimeMillis()));

    String input = Prompt.inputString(" 게시글을 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 게시글 등록을 취소하였습니다.");
      return;
    }

    freeBoard.setFreeBoardNo(freeBoardNo++);
    freeBoardList.add(freeBoard);
    study.getMyStudyFreeBoard().add(freeBoard);

    System.out.println(" >> 게시글이 등록되었습니다.");
  }

  // 목록
  public void listFreeBoard(List<Comment> commentList, Study study) {
    System.out.println();
    System.out.println("▶ 게시글 목록");
    System.out.println();

    List<FreeBoard> freeBoardArrayList = new ArrayList<>();

    for (FreeBoard freeBoard : study.getMyStudyFreeBoard()) {
      System.out.printf(
          " (%d)\n 제목 : %s\n 내용 : %s\n 첨부파일 : %s\n 작성자 : %s\n 조회수 : %s\n 작성일 : %s\n",
          freeBoard.getFreeBoardNo(), 
          freeBoard.getFreeBoardTitle(),
          freeBoard.getFreeBoardContent(),
          freeBoard.getFreeBoardAtcFile(),
          freeBoard.getFreeBoardWriter().getPerNickname(),
          freeBoard.getFreeBoardViewcount(),
          freeBoard.getFreeBoardRegisteredDate());
      System.out.println();

      freeBoardArrayList.add(freeBoard); 
    }

    if (!freeBoardArrayList.isEmpty()) {
      System.out.println("---------------------");
      System.out.println("1. 상세");
      System.out.println("2. 등록");
      System.out.println("0. 이전");
      int selete = Prompt.inputInt("선택> ");
      switch (selete) {
        case 1 : detailFreeBoard(freeBoardArrayList, commentList, study); break;
        case 2 : addFreeBoard(commentList, study); break;
        default : return;
      }
    }

    if (freeBoardArrayList.isEmpty()) {
      System.out.println(" >> 등록된 게시글이 없습니다");
      System.out.println("---------------------");
      System.out.println("1. 등록");
      System.out.println("0. 이전");
      int selete = Prompt.inputInt("선택> ");
      switch (selete) {
        case 1 : addFreeBoard(commentList, study); break;
        default : return;
      }
    }

  }

  // 상세
  private void detailFreeBoard(List<FreeBoard> freeBoardArrayList,
      List<Comment> commentList, Study study) {
    System.out.println();
    System.out.println("▶ 게시글 상세보기");
    System.out.println();

    int inputNo;
    FreeBoard free = null;

    while (true) {
      inputNo = Prompt.inputInt(" 번호 : ");
      System.out.println();

      free = findByNo(inputNo, freeBoardArrayList);

      if (free == null) {
        System.out.println(" >> 해당 번호의 게시글이 없습니다.\n");
        return;
      }

      if (free.getFreeBoardNo() == inputNo) {
        System.out.printf(" [%s]\n", free.getFreeBoardTitle());
        System.out.printf(" >> 내용 : %s\n", free.getFreeBoardContent());
        System.out.printf(" >> 첨부파일 : %s\n", free.getFreeBoardAtcFile());
        System.out.printf(" >> 작성자 : %s\n", free.getFreeBoardWriter().getPerNickname());
        System.out.printf(" >> 등록일 : %s\n", free.getFreeBoardRegisteredDate());
        free.setFreeBoardViewcount(free.getFreeBoardViewcount() + 1);
        System.out.printf(" >> 조회수 : %d\n", free.getFreeBoardViewcount());

        listComment(free); // 댓글호출
      }
      break;
    }

    System.out.println("\n----------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 댓글 등록");
    System.out.println("4. 댓글 수정");
    System.out.println("5. 댓글 삭제");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : updateFreeBoard(free, commentList, study); break;
      case 2 : deleteFreeBoard(free, study); break;
      case 3 : addComment(free, commentList); break;
      case 4 : updateComment(); break;
      case 5 : deleteComment(free); break;
      case 0 : listFreeBoard(commentList, study); break;
      default : 
    }
  }

  // 수정
  private void updateFreeBoard(FreeBoard detailFreeBoard, List<Comment> commentList, Study study) {
    System.out.println();
    System.out.println("▶ 게시글 수정");
    System.out.println();

    if (detailFreeBoard.getFreeBoardWriter().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 수정 권한이 없습니다.");
      return;
    }

    String freeBoardTitle = Prompt.inputString(" 제목(" + detailFreeBoard.getFreeBoardTitle()  + ") : ");
    String freeBoardContent = Prompt.inputString(" 내용(" + detailFreeBoard.getFreeBoardContent() + ") : ");
    String freeBoardAtcFile = Prompt.inputString(" 첨부파일(" + detailFreeBoard.getFreeBoardAtcFile() + ") : ");

    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경을 취소되었습니다.");
      return;
    }

    detailFreeBoard.setFreeBoardTitle(freeBoardTitle);
    detailFreeBoard.setFreeBoardContent(freeBoardContent);
    detailFreeBoard.setFreeBoardAtcFile(freeBoardAtcFile);

    System.out.println(" >> 게시글을 변경하였습니다.");
  }

  // 삭제
  private void deleteFreeBoard(FreeBoard detailFreeBoard, Study study) {
    System.out.println();
    System.out.println("▶ 게시글 삭제");
    System.out.println();

    if (detailFreeBoard.getFreeBoardWriter().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 삭제 권한이 없습니다.");
      return;
    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 삭제를 취소하였습니다.");
      return;
    }

    freeBoardList.remove(detailFreeBoard);
    study.getMyStudyFreeBoard().remove(detailFreeBoard);

    System.out.println(" >> 게시글이 삭제되었습니다.");
  }

  //--------------------------댓글--------------------------

  int commentNo = 1;

  // 댓글 등록
  private void addComment(FreeBoard freeBoard, List<Comment> commentList) {
    System.out.println();
    System.out.println("▶ 댓글 작성");

    if (AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 로그인 한 회원만 등록 가능합니다.");
    } else {

      Comment comment = new Comment();

      comment.setCommentNo(commentNo++);
      comment.setCommentText(Prompt.inputString(" 댓글 내용 : "));
      comment.setCommentWiter(AuthPerMemberLoginHandler.getLoginUser());
      comment.setCommentRegisteredDate(new Date(System.currentTimeMillis()));

      String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 댓글 등록을 취소하였습니다.");
      }

      commentList.add(comment);
      freeBoard.getComment().add(comment);

      System.out.println(" >> 댓글이 등록되었습니다.");
    }
  }

  // 댓글 출력
  private void listComment(FreeBoard freeBoard) {
    System.out.println();
    System.out.println("============= 댓글 =============");

    int countFreeBoard = 0;

    for (Comment comment : freeBoard.getComment()) {
      System.out.printf(" (%d) | 내용 : %s | 작성자 : %s | 등록일 : %s\n",
          comment.getCommentNo(),
          comment.getCommentText(),
          comment.getCommentWiter().getPerNickname(),
          comment.getCommentRegisteredDate());
      countFreeBoard++;
    }

    if (countFreeBoard == 0) {
      System.out.println(" >> 등록된 댓글이 없습니다.");
    }
  }

  // 댓글 수정
  static int commentNo1;

  private void updateComment() {
    System.out.println();
    System.out.println("▶ 댓글 수정");
    System.out.println();

    if (AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 변경 권한이 없습니다.");

    } else  {

      try {
        commentNo1 = Prompt.inputInt(" 번호 : ");
      } catch (Exception e){
        System.out.println(" >> 번호를 입력해 주세요.");
      }

      Comment comment = findByComment(commentNo1);

      String commentTitle = Prompt.inputString(
          "댓글 내용(" +  comment.getCommentText() + ") : ");
      System.out.println();

      String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
      if (!input.equalsIgnoreCase("네")) {
        System.out.println(" >> 댓글 변경이 취소되었습니다.");
        return;
      }

      comment.setCommentText(commentTitle);

      System.out.println(" >> 댓글을 변경하였습니다.");
    }
  }

  // 댓글 삭제
  private void deleteComment(FreeBoard freeBoard) {
    System.out.println();
    System.out.println("▶ 댓글 삭제");
    System.out.println();

    if (AuthPerMemberLoginHandler.getLoginUser() == null) {
      System.out.println(" >> 삭제 권한이 없습니다.");
    } else  {

      try {
        commentNo1 = Prompt.inputInt(" 번호 : ");
      } catch (Exception e) {
        System.out.println(" >> 번호를 입력해 주세요");
      }

      Comment comment = findByComment(commentNo1);

      System.out.println();
      String inputno = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
      if (!inputno.equalsIgnoreCase("네")) {
        System.out.println(" >> 댓글 삭제를 취소하였습니다.");
        return;
      }

      commentList.remove(comment);
      freeBoard.getComment().remove(comment);

      System.out.println(" >> 댓글이 삭제되었습니다.");
    }
  }

  //게시글 번호로 찾기
  private FreeBoard findByNo(int inputNo, List<FreeBoard> freeBoardArrayList) {
    for (FreeBoard freeBoard : freeBoardArrayList) {
      if (freeBoard.getFreeBoardNo() == inputNo) {
        return freeBoard;
      }
    }
    return null;
  }

  //댓글 번호로 찾기
  private Comment findByComment (int commentNo) {
    for (Comment comment : commentList) {
      if (comment.getCommentNo() == commentNo) {
        return comment;
      }
    }
    return null;
  }
}

// 번호 입력
//    int inputTitle = Prompt.inputInt("번호 : ");
//    System.out.println();
//
//    FreeBoard free = findByNo(inputTitle);
//
//    if (free == null) {
//      System.out.println("해당 번호의 게시글이 없습니다.");
//      return;
//    }
//
//    if (free.getFreeBoardWriter().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
//      System.out.println("삭제 권한이 없습니다.");
//      return;
//    }


