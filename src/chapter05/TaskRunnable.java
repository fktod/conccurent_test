package chapter05;

import java.util.concurrent.BlockingQueue;

/**
 * 对中断状态做出处理
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;
    @Override
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            /**
             * 恢复该线程中断状态
             */
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task){}
}

class Task{}
