package chapter06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 任务调度策略
 * 适用Future等待图像下载
 */
public class FutureRenderer {
    private final ExecutorService exec = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) throws Exception {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> imageDatas = new ArrayList<>();
                for (ImageInfo info : imageInfos) {
                    imageDatas.add(info.downloadImage());
                }
                return imageDatas;
            }
        };
        Future<List<ImageData>> future = exec.submit(task);
        renderText(source);
        try {
            List<ImageData> imageDatas = future.get();
            for(ImageData data:imageDatas){
                renderImage(data);
            }
        } catch (InterruptedException e) {
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
            //取消任务
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    Exception launderThrowable(Throwable e){
        return new Exception(e);
    }

    void renderImage(ImageData imageData){}

    void renderText(CharSequence source){}

    List<ImageInfo> scanForImageInfo(CharSequence source){
        return new ArrayList<>();
    }

    class ImageInfo{
        ImageData downloadImage(){
            return new ImageData();
        }
    }

    class ImageData{}
}
