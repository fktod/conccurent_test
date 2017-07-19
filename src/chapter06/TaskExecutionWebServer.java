package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 任务调度策略
 * 线程池异步执行任务
 */
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        while (true){
            Socket connection = server.accept();
            /**
             * 匿名内部类实现方式
             */
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            });

            /**
             * lamdba表达式实现方式
             */
            exec.execute(() -> handleRequest(connection));
        }
    }

    private static void handleRequest(Socket connection){

    }

    /**
     * 修改任务调度策略
     * 多个线程并行执行单个任务
     */
    public class ThreadPerTaskExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    /**
     * 修改任务调度策略
     * 单个线程串行执行多个任务
     */
    public class WithInThreadExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
