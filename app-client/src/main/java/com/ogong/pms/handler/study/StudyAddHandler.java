package com.ogong.pms.handler.study;

import java.util.ArrayList;
import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyAddHandler implements Command {

  StudyDao studyDao;

  public StudyAddHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 등록");
    System.out.println();

    Study study = new Study();

    List<Study> arrayStudy = studyDao.findAll();

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
    System.out.println();
    while (true) {
      try {
        nop = Prompt.inputInt(" 인원수(1~30) : ");
        if ((nop == 0) || (nop > 30)) {
          System.out.println(" >> 인원수는 1명 이상 30명 이하로만 입력 가능합니다.");
          System.out.println();
          continue;
        }
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.");
        System.out.println();
        continue;
      }
      break;
    }
    study.setNumberOfPeple(nop);

    // 대면,비대면
    String face;
    System.out.println();
    while (true) {
      face = Prompt.inputString(" 대면 , 비대면 , 대면/비대면 : ");
      if ((face.length() == 3 && face.equals("비대면")) ||
          (face.length() == 2 && face.equals("대면")) ||
          (face.length() == 6 && face.equals("대면/비대면"))) {
        break;
      }
      System.out.println(" >> 대면/비대면 중에 입력하세요.");
      System.out.println();
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

    // 마지막 스터디 번호 찾아서 새 스터디 등록시 +1 되도록 기능 구현
    Study lastStudy = null;
    if (!arrayStudy.isEmpty()) {
      lastStudy = arrayStudy.get(arrayStudy.size() - 1);
      study.setStudyNo(lastStudy.getStudyNo() + 1);
    } else {
      study.setStudyNo(1);
    }

    // 작성자,구성원,캘린더,자유게시판
    study.setOwner(AuthPerMemberLoginHandler.getLoginUser());
    study.setMembers(new ArrayList<>());
    study.setWatingMember(new ArrayList<>());
    study.setMyStudyCalender(new ArrayList<>());
    study.setMyStudyFreeBoard(new ArrayList<>());
    study.setMyStudyToDo(new ArrayList<>());

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      return;
    }
    // 고유번호
    studyDao.insert(study);
    System.out.println(" >> 스터디가 등록되었습니다.");
  }
}