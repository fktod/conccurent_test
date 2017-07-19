package chapter06;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Timer的缺陷
 * 1.只有单一线程执行任务，执行时间过长会对后续任务产生影响
 * 2.不能处理运行时异常
 */
public class OutOfTime {

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(1);
        timer.schedule(new ThrowTask(),1);
        TimeUnit.SECONDS.sleep(5);

    }

    static class ThrowTask extends TimerTask{

        @Override
        public void run() {
            System.out.println("定时任务运行。。。");
            throw new RuntimeException();
        }
    }
}
