package chapter06;

import java.util.concurrent.*;

public class AdRender {

    private final long TIME_BUGET = 1000;
    private final Ad DEFAULT_AD = new Ad();
    private ExecutorService exec = Executors.newFixedThreadPool(100);

    Page renderPageWithAd() throws InterruptedException{
        long endNanos = System.nanoTime() + TIME_BUGET;
        Future<Ad> future = exec.submit(new FetchAdTask());
        //生成页面
        Page page = renderPageBody();
        Ad ad;
        long timeLeft = endNanos - System.nanoTime();
        try {
            ad = future.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody(){
        return new Page();
    }

    class Page{
        void setAd(Ad ad){}
    }

    class Ad{}

    class FetchAdTask implements Callable<Ad>{
        @Override
        public Ad call() throws Exception {
            return new Ad();
        }
    }
}
