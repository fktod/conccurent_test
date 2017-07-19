package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static com.sun.activation.registries.LogSupport.log;

/**
 * 任务调度策略
 * 线程池异步执行任务
 */
public class LifeCycleWebServer {
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(80);
        while (true){
            try {
                Socket connection = server.accept();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(connection);
                    }
                });
            }catch(RejectedExecutionException e){
                /**
                 * 此处为何要判断线程池的生命周期
                 */
                if(!exec.isShutdown()){
                    log("task submission rejected", e);
                }
            }
        }
    }

    public void stop(){
        exec.shutdown();
    }

    private void handleRequest(Socket connection){
        Request req= readRequest(connection);
        if(isShutdownnRequest(req)){
            stop();
        }else{
            dispatchRequest(req);
        }
    }

    private Request readRequest(Socket connection){
        return new Request();
    }

    private boolean isShutdownnRequest(Request req){
        return true;
    }

    private void dispatchRequest(Request req){

    }

    public class Request{

    }
}
