package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 任务调度策略
 * 单个线程串行执行任务
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        while(true){
            Socket connection = server.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection){
        /**
         * 请求处理代码
         */
    }
}
