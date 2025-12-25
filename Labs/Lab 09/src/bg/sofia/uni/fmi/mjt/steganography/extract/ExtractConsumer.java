package bg.sofia.uni.fmi.mjt.steganography.extract;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class ExtractConsumer implements Runnable {
    private final BlockingQueue<ExtractImageInfo> workingQueue;
    private final Path outputDir;

    public ExtractConsumer( Path outputDir, BlockingQueue<ExtractImageInfo> workingQueue) {
        this.workingQueue = workingQueue;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        ExtractImageInfo curr;
        while (true) {
            try {
                curr = workingQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (curr == ExtractImageInfo.POISON_PILL) {
                break;
            }
            BufferedImage res = new ImageExtractor(curr).extract();
            ImageHelper.saveImage(res, outputDir.toString(), curr.imageName());
        }
    }
}
