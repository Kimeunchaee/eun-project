package com.ogong.pms.handler.admin;

import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminCeoMemberDeleteHandler implements Command {

  CeoMemberDao ceoMemberDao;

  public AdminCeoMemberDeleteHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  //관리자용
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업회원 탈퇴");
    System.out.println();

    int deleteNo = (int) request.getAttribute("inputCeoNo");

    CeoMember ceoMember = ceoMemberDao.findByNo(deleteNo);

    String input = Prompt.inputString(" 정말 탈퇴시키겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 기업 회원 삭제를 취소하였습니다.");
      request.getRequestDispatcher("/adminCeoMember/list").forward(request);
      return;
    }

    if (input.equals("네")) {
      ceoMember.setCeoStatus(CeoMember.OUTUSER);
      ceoMember.setCeoBossName("탈퇴된 회원: (" + ceoMember.getCeoBossName() + ")");
      ceoMember.setCeoEmail("Deleted Email");
      ceoMember.setCeoPassword("Deleted Password");
      ceoMember.setCeoPhoto("Deleted Photo");
      ceoMember.setCeoLicenseNo("Deleted LicenseNo");
    }

    // 회원 삭제
    ceoMemberDao.update(ceoMember);

    // 카페삭제
    //    requestAgent.request("cafe.selectList", null);
    //
    //    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      System.out.println(" >> 스터디카페가 없습니다.");
    //      return;
    //    }
    //
    //    Collection<Cafe> cafeCollection = requestAgent.getObjects(Cafe.class);
    //    List<Cafe> cafeList = new ArrayList<>(cafeCollection);
    //
    //    for (int i = cafeList.size() -1; i >= 0; i--) {
    //      if (cafeList.get(i).getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
    //
    //        if (ceoMember.getCeoBossName().contains("탈퇴")) {
    //          cafeList.get(i).setCafeStatus(Cafe.DELETE);
    //        }
    //
    //        requestAgent.request("cafe.update", cafeList.get(i));
    //
    //        if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //          System.out.println(" >> 스터디카페 삭제가 실패되었습니다.");
    //          return;
    //        }
    //      }
    //    }

    System.out.println(" >> 기업 회원이 삭제되었습니다.");
    request.getRequestDispatcher("/adminCeoMember/list").forward(request);
    return;
  }

}
