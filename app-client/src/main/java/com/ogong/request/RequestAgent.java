package com.ogong.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

//역할
//- 통신 프로토콜에 맞춰 서버에게 요청을 전달하고 응답을 받는 일을 한다.
//

public class RequestAgent implements AutoCloseable {

  public static final String SUCCESS = "success";
  public static final String FAIL = "fail";

  Socket socket;
  PrintWriter out;
  BufferedReader in;

  String status;
  String jsonData;

  // 서버와 연결 준비
  public RequestAgent(String ip, int port) throws Exception {
    socket = new Socket(ip, port);
    out = new PrintWriter(socket.getOutputStream());
    in = new BufferedReader(new InputStreamReader (socket.getInputStream()));
  }

  // 서버쪽으로 데이터를 보냄
  public void request(String command, Object value) throws Exception {
    out.println(command);

    if (command != null) {
      out.println(new Gson().toJson(value));
    } else {
      out.println();
    }
    out.flush();

    status = in.readLine();     // 서버의 응답 (success 아니면 fail 이다)
    jsonData = in.readLine();
  }

  public String getStatus() {
    return status;
  }

  public <T> T getObject(Class<T> type) {
    return new Gson().fromJson(jsonData, type);
  }

  @Override
  public void close() {
    try {out.close();} catch (Exception e) {}
    try {in.close();} catch (Exception e) {}
    try {socket.close();} catch (Exception e) {}
  }

}
