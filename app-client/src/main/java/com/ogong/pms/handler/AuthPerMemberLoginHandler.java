package com.ogong.pms.handler;

import java.util.HashMap;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.Member;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class AuthPerMemberLoginHandler extends AbstractLoginHandler implements Command {

  RequestAgent requestAgent;

  public static Member loginUser;
  public static Member getLoginUser() {
    return loginUser;
  }

  public AuthPerMemberLoginHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = Prompt.inputString(" 비밀번호 : ");

    HashMap<String,String> params = new HashMap<>();
    params.put("email", inputEmail);
    params.put("password", inputPassword);

    requestAgent.request("member.selectOneByEmailPassword", params);

    if (requestAgent.getStatus().equals(RequestAgent.SUCCESS)) {
      Member member = requestAgent.getObject(Member.class);
      System.out.printf("%s님 환영합니다!\n", member.getPerNickname());
      loginUser = member;
      accessLevel = Menu.PER_LOGIN;

    } else {
      System.out.println("이메일과 암호가 일치하는 회원을 찾을 수 없습니다.");
    }
  } 
}
