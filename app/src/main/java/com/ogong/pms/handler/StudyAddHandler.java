package com.ogong.pms.handler;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.util.Prompt;

public class StudyAddHandler extends AbstractStudyHandler {

  PromptPerMember promptPerMember;
  int studyNo = 6;
  int ToDoNo;
  List<ToDo> todoList;

  public StudyAddHandler(List<Study> studyList, List<ToDo> todoList,
      PromptPerMember promptPerMember) {
    super(studyList);
    this.promptPerMember = promptPerMember;
    this.todoList = todoList;

    //    Study testStudy = new Study();
    //    testStudy.setStudyNo(1);
    //    testStudy.setStudyTitle("삼성뿌셔");
    //    testStudy.setOwner(promptPerMember.memberList.get(0));
    //    testStudy.setSubject("취업");
    //    testStudy.setArea("서울");
    //    testStudy.setNumberOfPeple(5);
    //    testStudy.setFace("대면");
    //    testStudy.setIntroduction("취업 뿌셔뿌셔");
    //    testStudy.setMembers(new ArrayList<>());
    //    testStudy.setWatingMember(new ArrayList<>());
    //    testStudy.setMyStudyCalender(new ArrayList<>());
    //    testStudy.setMyStudyFreeBoard(new ArrayList<>());
    //    testStudy.setMyStudyToDo(new ArrayList<>());
    //    studyList.add(testStudy);
    //
    //    testStudy = new Study();
    //    testStudy.setStudyNo(2);
    //    testStudy.setStudyTitle("정처기준비");
    //    testStudy.setOwner(promptPerMember.memberList.get(0));
    //    testStudy.setSubject("자격증");
    //    testStudy.setArea("경기");
    //    testStudy.setNumberOfPeple(5);
    //    testStudy.setFace("비대면");
    //    testStudy.setIntroduction("한번에 붙자");
    //    testStudy.setMembers(new ArrayList<>());
    //    testStudy.setWatingMember(new ArrayList<>());
    //    testStudy.setMyStudyCalender(new ArrayList<>());
    //    testStudy.setMyStudyFreeBoard(new ArrayList<>());
    //    testStudy.setMyStudyToDo(new ArrayList<>());
    //    studyList.add(testStudy);
    //
    //    testStudy = new Study();
    //    testStudy.setStudyNo(3);
    //    testStudy.setStudyTitle("하반기 삼성 공모전");
    //    testStudy.setOwner(promptPerMember.memberList.get(1));
    //    testStudy.setSubject("공모전");
    //    testStudy.setArea("서울");
    //    testStudy.setNumberOfPeple(6);
    //    testStudy.setFace("대면/비대면");
    //    testStudy.setIntroduction("공모전 아자");
    //    testStudy.setMembers(new ArrayList<>());
    //    testStudy.setWatingMember(new ArrayList<>());
    //    testStudy.setMyStudyCalender(new ArrayList<>());
    //    testStudy.setMyStudyFreeBoard(new ArrayList<>());
    //    testStudy.setMyStudyToDo(new ArrayList<>());
    //    studyList.add(testStudy);
    //
    //    testStudy = new Study();
    //    testStudy.setStudyNo(4);
    //    testStudy.setStudyTitle("중앙대 컴공 기말고사");
    //    testStudy.setOwner(promptPerMember.memberList.get(2));
    //    testStudy.setSubject("기말고사");
    //    testStudy.setArea("서울");
    //    testStudy.setNumberOfPeple(3);
    //    testStudy.setFace("대면/비대면");
    //    testStudy.setIntroduction("시험 아자");
    //    testStudy.setMembers(new ArrayList<>());
    //    testStudy.setWatingMember(new ArrayList<>());
    //    testStudy.setMyStudyCalender(new ArrayList<>());
    //    testStudy.setMyStudyFreeBoard(new ArrayList<>());
    //    testStudy.setMyStudyToDo(new ArrayList<>());
    //    studyList.add(testStudy);
    //
    //    testStudy = new Study();
    //    testStudy.setStudyNo(5);
    //    testStudy.setStudyTitle("알고리즘 스터디");
    //    testStudy.setOwner(promptPerMember.memberList.get(3));
    //    testStudy.setSubject("IT");
    //    testStudy.setArea("서울");
    //    testStudy.setNumberOfPeple(2);
    //    testStudy.setFace("비대면");
    //    testStudy.setIntroduction("지옥같은 SI 탈출");
    //    testStudy.setMembers(new ArrayList<>());
    //    testStudy.setWatingMember(new ArrayList<>());
    //    testStudy.setMyStudyCalender(new ArrayList<>());
    //    testStudy.setMyStudyFreeBoard(new ArrayList<>());
    //    testStudy.setMyStudyToDo(new ArrayList<>());
    //    studyList.add(testStudy);

  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 스터디 등록");
    System.out.println();

    Study study = new Study();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    // 스터디명
    String studyTitle;
    while (true) {
      studyTitle = Prompt.inputString(" 스터디명 : ");
      if (studyTitle.length() == 0) {
        System.out.println(" >> 한 글자 이상 입력해 주세요.");
        System.out.println();
        continue;
      }
      break;
    }
    study.setStudyTitle(studyTitle);

    // 분야
    System.out.println();
    System.out.println(" [ Category ]");
    System.out.println(" 1. 어학");
    System.out.println(" 2. 자격증");
    System.out.println(" 3. 취업");
    System.out.println(" 4. IT");
    System.out.println(" 5. 예체능");
    System.out.println(" 6. 기타");
    System.out.println();

    while (true) {
      try {
        int subjectNo =Prompt.inputInt(" 분야 : ");
        switch (subjectNo) {
          case 1 : study.setSubject("어학"); break;
          case 2 : study.setSubject("자격증"); break;
          case 3 : study.setSubject("취업"); break;
          case 4 : study.setSubject("IT"); break;
          case 5 : study.setSubject("예체능"); break;
          case 6 : break;
          default : System.out.println(" >> 다시 선택하세요.\n"); continue;
        }

        if (subjectNo == 6) {
          while (true) {
            System.out.println();
            String input = Prompt.inputString(" 기타 분야 입력 : ");

            if (input.equals("어학") || input.equals("자격증") || input.equals("취업") ||
                input.equalsIgnoreCase("IT") || input.equals("예체능")) {
              System.out.println(" >> 이미 등록된 카테고리입니다.");
              continue;
            } else {
              System.out.println();
              String s = Prompt.inputString(" 해당 분야로 입력하시겠습니까? (네 / 아니오) ");
              if (!s.equals("네")) {
                System.out.println(" >> 다시 입력해주세요.");
                continue;
              }
              System.out.printf(" >> 분야가 '기타(%s)'로 등록되었습니다.\n", input);
              study.setSubject(input);
            }
            break;
          }
        }

      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.\n");
        continue;
      }
      break;
    }

    // 지역
    String area;
    while (true) {
      System.out.println();
      area = Prompt.inputString(" 지역 : ");
      if (area.contains("시") || area.contains("구") || area.contains("도")) {
        study.setArea(area);
        break;
      } System.out.println(" >> @@시 / @@도 / @@구 등을 입력해 주세요.");
    }

    // 인원수
    int nop = 0;
    while (true) {
      try {
        System.out.println();
        nop = Prompt.inputInt(" 인원수(1~30) : ");
        if ((nop == 0) || (nop > 30)) {
          System.out.println(" >> 인원수는 1명 이상 30명 이하로만 입력 가능합니다.");
          continue;
        }
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.\n");
        continue;
      }
      break;
    }
    study.setNumberOfPeple(nop);

    // 대면,비대면
    String face;
    while (true) {
      System.out.println();
      face = Prompt.inputString(" 대면 , 비대면 , 대면/비대면 : ");
      if ((face.length() == 3 && face.equals("비대면")) ||
          (face.length() == 2 && face.equals("대면")) ||
          (face.length() == 6 && face.equals("대면/비대면"))) {
        break;
      }
      System.out.println(" >> 대면/비대면 중에 입력하세요.");
    }
    study.setFace(face);

    // 소개글
    String introduction;
    while (true) {
      System.out.println();
      introduction = Prompt.inputString(" 소개글 : ");
      if (introduction.length() == 0) {
        System.out.println(" >> 한 글자 이상 입력해 주세요.");
        continue;
      }
      break;
    }
    study.setIntroduction(introduction);

    // 작성자,구성원,캘린더,자유게시판
    study.setOwner(member);
    study.setMembers(new ArrayList<>());
    study.setWatingMember(new ArrayList<>());
    study.setMyStudyCalender(new ArrayList<>());
    study.setMyStudyFreeBoard(new ArrayList<>());

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }
    // 고유번호
    study.setStudyNo(studyNo++); 
    studyList.add(study);
    System.out.println(" >> 스터디가 등록되었습니다.");

  }
}