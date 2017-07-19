package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 任务调度策略
 * 多个线程并行执行单个任务
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        while(true){
            Socket connection = server.accept();

            /**
             * 匿名函数写法
             */
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();

            /**
             * lamdba表达式写法
             */
            new Thread(() -> handleRequest(connection)).start();
        }
    }

    private static void handleRequest(Socket connection){

    }
}
