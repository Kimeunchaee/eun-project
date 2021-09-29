package com.ogong.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;
import com.google.gson.Gson;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class ClientApp {

  public static void main(String[] args) throws Exception {
    System.out.println("[PMS 클라이언트]");

    System.out.println("1) 서버와 연결 중");
    Socket socket = new Socket("127.0.0.1", 8888);

    System.out.println("2) 서버와 연결되었음");

    PrintWriter out = new PrintWriter(socket.getOutputStream());
    BufferedReader in = new BufferedReader(new InputStreamReader (socket.getInputStream()));

    while (true) {
      String input = Prompt.inputString("명령> ");

      if (input.equals("/member/add")) {
        addMember(out, in);
      } else if (input.equals("/member/detail")){
        detailMember(out, in);
      } else {
        out.println(input);
        out.flush();

        String result = in.readLine();  // 서버에서 한 줄의 문자열을 보낼 때까지 기다린다.
        System.out.println(">>> " + result);
      }

      if (input.equalsIgnoreCase("quit")) {
        break;
      }
    }

    System.out.println("3) 서버와의 접속을 끊음");
    out.close();
    in.close();
    Prompt.close();
  }

  private static void addMember(PrintWriter out, BufferedReader in) throws Exception {
    out.print("/member/add");

    Member testMember = new Member();
    testMember.setPerNo(1);
    testMember.setPerNickname("초보초보쌩초보");
    testMember.setPerEmail("naver");
    testMember.setPerPassword("1111");
    testMember.setPerPhoto("jpg");
    testMember.setPerRegisteredDate(new Date(System.currentTimeMillis()));

    out.println(new Gson().toJson(testMember));
    out.flush();

    String result = in.readLine();
    System.out.println(">>> " + result);
  }

  private static void detailMember(PrintWriter out, BufferedReader in) throws Exception {
    out.print("/member/detail");

    HashMap<String, Object> map = new HashMap<>();
    map.put("no", 1);

    String jsonStr = new Gson().toJson(map);
    System.out.println(jsonStr);

    out.println(jsonStr);
    out.flush();

    System.out.println(in.readLine());

    String result = in.readLine();
    Member member = new Gson().fromJson(result, Member.class);
    System.out.println(member);
  }
}