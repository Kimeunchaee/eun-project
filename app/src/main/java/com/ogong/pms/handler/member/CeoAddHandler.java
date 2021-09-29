package com.ogong.pms.handler.member;

import java.sql.Date;
import java.util.List;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AbstractCeoMemberHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoAddHandler extends AbstractCeoMemberHandler {

  int ceoMemberNo = 5;

  public CeoAddHandler(List<CeoMember> ceoMemberList) {
    super(ceoMemberList);

    //    CeoMember ceoMember = new CeoMember();
    //    ceoMember.setCeoNo(1);
    //    ceoMember.setCeoEmail("study1");
    //    ceoMember.setCeoPassword("1111");
    //    ceoMember.setCeoPhoto("a.jpg");
    //    ceoMember.setCeoLicenseNo("123-45-12345");
    //    ceoMember.setCeoBossName("김사장");
    //    ceoMember.setCeoregisteredDate(Date.valueOf("2020-12-10"));
    //    ceoMemberList.add(ceoMember);
    //
    //    ceoMember = new CeoMember();
    //    ceoMember.setCeoNo(2);
    //    ceoMember.setCeoEmail("study2");
    //    ceoMember.setCeoPassword("1111");
    //    ceoMember.setCeoPhoto("bbb.jpg");
    //    ceoMember.setCeoLicenseNo("123-45-67812");
    //    ceoMember.setCeoBossName("우사장");
    //    ceoMember.setCeoregisteredDate(Date.valueOf("2021-7-21"));
    //    ceoMemberList.add(ceoMember);
    //
    //    ceoMember = new CeoMember();
    //    ceoMember.setCeoNo(3);
    //    ceoMember.setCeoEmail("study3");
    //    ceoMember.setCeoPassword("1111");
    //    ceoMember.setCeoPhoto("ccc.jpg");
    //    ceoMember.setCeoLicenseNo("123-45-48751");
    //    ceoMember.setCeoBossName("조사장");
    //    ceoMember.setCeoregisteredDate(Date.valueOf("2021-5-1"));
    //    ceoMemberList.add(ceoMember);
    //
    //    ceoMember = new CeoMember();
    //    ceoMember.setCeoNo(4);
    //    ceoMember.setCeoEmail("study4");
    //    ceoMember.setCeoPassword("1111");
    //    ceoMember.setCeoPhoto("ddd.jpg");
    //    ceoMember.setCeoLicenseNo("789-56-12345");
    //    ceoMember.setCeoBossName("송사장");
    //    ceoMember.setCeoregisteredDate(Date.valueOf("2021-3-1"));
    //    ceoMemberList.add(ceoMember);
  }

  // 개인
  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 기업 회원가입");
    System.out.println();

    CeoMember ceoMember = new CeoMember();

    String inputNewEmail;
    while (true) {
      inputNewEmail = Prompt.inputString(" 이메일 : ");
      if (!inputNewEmail.contains("@") ||
          !inputNewEmail.contains(".com") ||
          inputNewEmail.length() < 6) {
        System.out.println(" >> 정확한 이메일 양식으로 입력해주세요.\n");
        continue;
      }
      break;
    }
    ceoMember.setCeoEmail(inputNewEmail);


    String inputNewPW;
    while (true) {
      System.out.println();
      inputNewPW = Prompt.inputString(" 비밀번호 : ");
      if (inputNewPW.length() < 8 || (!inputNewPW.contains("!") && !inputNewPW.contains("@")
          && !inputNewPW.contains("#") && !inputNewPW.contains("$")
          && !inputNewPW.contains("^") && !inputNewPW.contains("%")
          && !inputNewPW.contains("&") && !inputNewPW.contains("*"))) {
        System.out.println(" >> 8자 이상 특수문자를 포함시켜 주세요.");
        continue;
      }
      break;
    }
    ceoMember.setCeoPassword(inputNewPW);

    while (true) {
      System.out.println();
      String pw =  Prompt.inputString(" 비밀번호 확인 : ");
      if (!pw.equals(ceoMember.getCeoPassword())) {
        System.out.println(" >> 확인 실패!");
        continue;
      } else {
        System.out.println(" >> 확인 완료!");
      }
      break;
    }

    String inputLicenseNo;
    while (true) {
      System.out.println();
      inputLicenseNo = Prompt.inputString(" 사업자 등록번호 : ");
      if (inputLicenseNo.length() != 10) {
        System.out.println(" >> 10자리 숫자를 입력해주세요.");
        continue;
      }
      break;
    }
    ceoMember.setCeoLicenseNo(inputLicenseNo);
    System.out.println();
    ceoMember.setCeoBossName(Prompt.inputString(" 대표자명 : "));
    System.out.println();
    ceoMember.setCeoPhoto(Prompt.inputString(" 사진 : "));
    ceoMember.setCeoregisteredDate(new Date(System.currentTimeMillis()));

    ceoMember.setCeoNo(ceoMemberNo++);
    ceoMemberList.add(ceoMember);

    System.out.println(" >> 기업회원 가입이 완료되었습니다.");
  }
}







