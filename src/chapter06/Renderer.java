package chapter06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 任务调度策略
 * 线程池异步执行同构任务
 */
public class Renderer {
    private final ExecutorService exec;

    public Renderer(ExecutorService exec) {
        this.exec = exec;
    }

    void renderPage(CharSequence source) throws Exception {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> service = new ExecutorCompletionService<ImageData>(exec);
        for (final ImageInfo info : imageInfos) {
            service.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return info.downloadImage();
                }
            });
        }
        renderText(source);

        try {
            for (int i = 0; i < imageInfos.size(); i++) {
                Future<ImageData> future = service.take();
                ImageData imageData = future.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    Exception launderThrowable(Throwable e) {
        return new Exception(e);
    }

    void renderImage(ImageData imageData) {
    }

    void renderText(CharSequence source) {
    }

    List<ImageInfo> scanForImageInfo(CharSequence source) {
        return new ArrayList<>();
    }

    class ImageInfo {
        ImageData downloadImage() {
            return new ImageData();
        }
    }

    class ImageData {
    }

}
