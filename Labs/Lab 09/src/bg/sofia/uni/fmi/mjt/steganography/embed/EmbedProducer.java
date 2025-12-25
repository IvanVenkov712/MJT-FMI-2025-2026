package bg.sofia.uni.fmi.mjt.steganography.embed;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EmbedProducer implements Runnable {
    private static final int META_INFO = 8;
    private final Path coverImagePath;
    private final Path secretImagePath;
    private final BlockingQueue<EmbedImageInfo> workingQueue;
    private final Path imageName;

    public EmbedProducer(Path coverImagePath, Path secretImagePath, BlockingQueue<EmbedImageInfo> workingQueue) {
        this.coverImagePath = coverImagePath;
        this.secretImagePath = secretImagePath;
        this.workingQueue = workingQueue;
        this.imageName = coverImagePath.getFileName();
    }

    @Override
    public void run() {
        BufferedImage coverImage = ImageHelper.loadImage(coverImagePath);
        BufferedImage secretImage = ImageHelper.loadImage(secretImagePath);
        int x = ImageHelper.getPixelCount(coverImage);
        int y = ImageHelper.getPixelCount(secretImage);
        if (x >= y + META_INFO) {
            try {
                workingQueue.put(new EmbedImageInfo(coverImage, secretImage, imageName.toString()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
