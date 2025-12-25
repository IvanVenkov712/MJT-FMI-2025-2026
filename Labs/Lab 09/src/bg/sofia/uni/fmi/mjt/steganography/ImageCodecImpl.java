package bg.sofia.uni.fmi.mjt.steganography;

import bg.sofia.uni.fmi.mjt.steganography.embed.EmbedImageInfo;
import bg.sofia.uni.fmi.mjt.steganography.extract.ExtractImageInfo;

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
        BlockingQueue<EmbedImageInfo> workingQueue = new LinkedBlockingQueue<>();

        //DirectoryStream<>
    }

    @Override
    public void extractPNGImages(String sourceDirectory, String outputDirectory) {
        BlockingQueue<ExtractImageInfo> workingQueue = new LinkedBlockingQueue<>();
    }
}
