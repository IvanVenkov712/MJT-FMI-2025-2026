package bg.sofia.uni.fmi.mjt.steganography;

import bg.sofia.uni.fmi.mjt.steganography.embed.EmbedMultithreadProcessor;
import bg.sofia.uni.fmi.mjt.steganography.extract.ExtractImageInfo;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ImageCodecImpl implements ImageCodec {

    private final int countConsumers;

    public ImageCodecImpl() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public ImageCodecImpl(int countConsumers) {
        this.countConsumers = countConsumers;
    }

    @Override
    public void embedPNGImages(String coverSourceDirectory, String secretSourceDirectory, String outputDirectory) {
        new EmbedMultithreadProcessor(
            Path.of(coverSourceDirectory),
            Path.of(secretSourceDirectory),
            Path.of(outputDirectory),
            countConsumers
        ).embedPNGImages();
    }

    @Override
    public void extractPNGImages(String sourceDirectory, String outputDirectory) {
        BlockingQueue<ExtractImageInfo> workingQueue = new LinkedBlockingQueue<>();
    }
}
