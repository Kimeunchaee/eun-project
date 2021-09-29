package com.ogong.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;
import com.google.gson.Gson;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    System.out.println("[PMS 서버]");

    System.out.println("1) 서버 소캣 준비");
    ServerSocket serverSocket = new ServerSocket(8888);

    System.out.println("2) 클라이언트의 접속을 기다림");
    Socket socket = serverSocket.accept();

    System.out.println("3) 클라이언트가 접속했음");

    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream());

    System.out.println("클라이언트가 보낸 데이터 읽기");
    while (true) {
      String command = in.readLine();
      System.out.println(command);

      if (command.equalsIgnoreCase("quit")) {
        out.println("goodbye");
        out.flush();
        break;        

      } else if (command.equalsIgnoreCase("/member/add")) {
        addMember(out, in);

      } else if (command.equalsIgnoreCase("/member/detial")) {
        detailMember(out, in);

      } else {
        out.println("command");
        out.flush();
      }
    }

    System.out.println("4) 클라이언트와의 접속을 끊음");
    in.close();
    out.close();
    socket.close();

    System.out.println("5) 서버 소켓 종료");
    serverSocket.close();
  }

  private static void addMember(PrintWriter out, BufferedReader in) throws Exception {
    String jsonStr = in.readLine();
    Member member = new Gson().fromJson(jsonStr, Member.class);

    System.out.println(member);

    out.println("success");
    out.flush();
  }

  @SuppressWarnings("unchecked")
  private static void detailMember(PrintWriter out, BufferedReader in) throws Exception {
    String jsonStr = in.readLine();

    HashMap<String,Object> mapMember = new Gson().fromJson(jsonStr, HashMap.class);
    System.out.println(mapMember);

    Member m = new Member();
    m.setPerNo(1);
    m.setPerNickname("초보초보쌩초보");
    m.setPerEmail("naver");
    m.setPerPassword("1111");
    m.setPerPhoto("jpg");
    m.setPerRegisteredDate(new Date(System.currentTimeMillis()));

    out.println(new Gson().toJson(m));
    out.flush();
  }
}