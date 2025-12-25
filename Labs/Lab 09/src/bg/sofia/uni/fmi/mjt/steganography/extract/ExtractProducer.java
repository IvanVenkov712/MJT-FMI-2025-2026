package bg.sofia.uni.fmi.mjt.steganography.extract;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class ExtractProducer implements Runnable {
    private final BlockingQueue<ExtractImageInfo> workingQueue;
    private final Path imgPath;
    private final String imgName;

    public ExtractProducer(BlockingQueue<ExtractImageInfo> workingQueue, Path imgPath) {
        this.workingQueue = workingQueue;
        this.imgPath = imgPath;
        this.imgName = imgPath.getFileName().toString();
    }

    @Override
    public void run() {
        BufferedImage img = ImageHelper.loadImage(imgPath);
        try {
            workingQueue.put(new ExtractImageInfo(img, imgName));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
