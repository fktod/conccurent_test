package chapter06;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务调度策略
 * 单线程执行文本，图像下载和绘制
 */
public class SingleThreadRenderer {
    void renderPage(CharSequence source){
        renderText(source);
        List<ImageData> imageDatas = new ArrayList<>();
        for(ImageInfo info:scanForImageInfo(source)){
            imageDatas.add(info.downloadImage());
        }
        for(ImageData data:imageDatas){
            renderImage(data);
        }
    }

    List<ImageInfo> scanForImageInfo(CharSequence source){
        return new ArrayList<ImageInfo>();
    }

    void renderImage(ImageData data){}

    void renderText(CharSequence source){}

    class ImageData{}

    class ImageInfo{
        ImageData downloadImage(){
            return new ImageData();
        }
    }
}
