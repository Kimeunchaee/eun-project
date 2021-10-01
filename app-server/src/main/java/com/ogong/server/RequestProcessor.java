package com.ogong.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

public class RequestProcessor implements AutoCloseable{

  Socket socket;
  PrintWriter out;
  BufferedReader in;

  Map<String,DataProcessor> dataProcessorMap;

  public RequestProcessor(Socket socket,  Map<String,DataProcessor> dataProcessorMap) throws Exception {
    this.socket = socket;
    this.dataProcessorMap = dataProcessorMap;
    out = new PrintWriter(socket.getOutputStream());
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  @Override
  public void close() throws Exception {
    try {out.close();} catch (Exception e) {}
    try {in.close();} catch (Exception e) {}
    try {socket.close();} catch (Exception e) {}
  }

  public void service() throws Exception {

    // 데이터 처리 담당자의 이름 목록 가져오기
    Set<String> dataProcessorNames = dataProcessorMap.keySet();

    while (true) {
      String command = in.readLine();
      Request request = new Request(command, in.readLine());
      Response response = new Response();

      if (command.equals("quit")) {
        //in.readLine(); // new Request 할때 넘겨줌 

        //out.println("success");
        response.setStatus(Response.SUCCESS);

        //out.println("goodbye");
        response.setValue("goodbye");

        //out.flush();
        // 실행 결과 보내기 (flush()포함되어 있음)
        sendResult(response);

        break;
      }

      // 19-e
      // Response 객체에 보관된 실행 결과를 클라이언트에게 보내는 방법에서
      // 명령어에 해당되는 데이터를 처리하는 객체를 사용해서 클라이언트에게 보내는걸로 수정(???)
      //      } else if (command.startsWith("/member/")) {
      //        Request request = new Request(command, in.readLine());    // 클라이언트의 명령과 json데이터 보관
      //        Response response = new Response();     // 응답할 정보를 보관

      // 0929 수정
      // 명령어에 해당하는 데이터 처리 담당자를 찾는다.
      DataProcessor dataProcessor = null;
      for (String dataProcessorName : dataProcessorNames) {
        if (command.startsWith(dataProcessorName)) {
          dataProcessor = dataProcessorMap.get(dataProcessorName);
          break;
        }
      }

      if (dataProcessor != null) { // 명령어에 해당하는 데이터 처리 담당자가 있으면
        dataProcessor.execute(request, response);

      } else {
        response.setStatus(Response.FAIL);
        response.setValue("해당 명령어를 처리할 수 없습니다.");
      }

      sendResult(response); // 클라이언트에게 실행 결과를 보낸다.
    }
  }

  private void sendResult(Response response) throws Exception {
    // Response 객체에 보관된 실행 결과를 클라이언트에게 보낸다.
    out.println(response.status);
    if (response.getValue() != null) {
      out.println(new Gson().toJson(response.getValue()));
    } else {
      out.println();
    }
    out.flush();
  }
}