package com.ogong.pms;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import com.ogong.pms.table.JsonDataTable;
import com.ogong.pms.table.MemberTable;
import com.ogong.server.DataProcessor;
import com.ogong.server.RequestProcessor;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    System.out.println("[오늘의 공부 서버]");

    System.out.println("서버 실행 중....");

    ServerSocket serverSocket = new ServerSocket(5050);
    Socket socket = serverSocket.accept();

    System.out.println("클라이언트 접속");

    HashMap<String,DataProcessor> dataProcessorMap = new HashMap<String,DataProcessor>();
    dataProcessorMap.put("member.", new MemberTable());

    RequestProcessor requestProcessor = new RequestProcessor(socket, dataProcessorMap);

    requestProcessor.service();
    requestProcessor.close();

    Collection<DataProcessor> dataProcessors = dataProcessorMap.values();
    for (DataProcessor dataProcessor : dataProcessors) {
      if (dataProcessor instanceof JsonDataTable) {
        ((JsonDataTable<?>)dataProcessor).save();
      }
    }


    System.out.println("서버 종료");
    serverSocket.close();

  }

}