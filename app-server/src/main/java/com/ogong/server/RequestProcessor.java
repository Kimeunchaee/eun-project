package com.ogong.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

// 역할
//- 클라이언트와 통신하는 일을 담당한다.
//- 클라이언트 요청이 들어오면 그 요청을 처리할 객체를 찾아 실행하는 일을 한다.
//- 클라이언트 요청 정보를 객체에 보관하고, 응답 기능을 수행할 객체를 만드는 일을 한다.
//

public class RequestProcessor implements AutoCloseable {

  Socket socket; 
  PrintWriter out;
  BufferedReader in;

  Map<String,DataProcessor> dataProcessorMap;

  public RequestProcessor(Socket socket, Map<String,DataProcessor> dataProcessorMap) throws Exception {
    this.socket = socket;
    this.dataProcessorMap = dataProcessorMap;
    out = new PrintWriter(socket.getOutputStream());
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  // 아니 개발자님 얘는 왜 에러를 안 던져요?
  // why?
  // 내 선에서 해결할게~
  @Override
  public void close() {
    try {out.close();} catch (Exception e) {}
    try {in.close();} catch (Exception e) {}
    try {socket.close();} catch (Exception e) {}

  }

  public void service() throws Exception {

    Set<String> dataProcessorNames = dataProcessorMap.keySet();

    while (true) {
      String command = in.readLine();
      Request request = new Request(command, in.readLine());
      Response response = new Response();

      if (command.equalsIgnoreCase("quit")) {
        in.readLine();
        out.println("success");
        out.println("goodbye");
        out.flush();
        break;
      }

      DataProcessor dataProcessor = null;

      for (String dataProcessorName : dataProcessorNames) {
        if (command.startsWith(dataProcessorName)) {
          dataProcessor = dataProcessorMap.get(dataProcessorName);
          break;
        }
      }

      if (dataProcessor != null) {
        dataProcessor.execute(request, response);

      } else {
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령어를 처리할 수 없습니다.");
      }

      sendResult(response);
    }
  }

  private void sendResult(Response response) {
    out.println(response.status);

    if (response.getValue() != null) {
      out.println(new Gson().toJson(response.getValue()));

    } else {
      out.println();
    }
    out.flush();
  }

}
