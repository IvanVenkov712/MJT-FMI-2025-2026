package bg.sofia.uni.fmi.mjt.steganography.embed;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class EmbedConsumer implements Runnable {
    private final BlockingQueue<EmbedImageInfo> workingQueue;
    private final Path outputDir;

    public EmbedConsumer(Path outputDir, BlockingQueue<EmbedImageInfo> workingQueue) {
        this.workingQueue = workingQueue;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        EmbedImageInfo curr;
        while (true) {
            try {
                curr = workingQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (curr == EmbedImageInfo.POISON_PILL) {
                break;
            }
            BufferedImage res = new ImageEmbedder(curr).embed();
            ImageHelper.saveImage(res, outputDir.toString(), curr.imageName());
        }
    }
}
