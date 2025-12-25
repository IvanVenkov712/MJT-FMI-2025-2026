package bg.sofia.uni.fmi.mjt.steganography;

import bg.sofia.uni.fmi.mjt.steganography.embed.EmbedMultithreadProcessor;
import bg.sofia.uni.fmi.mjt.steganography.extract.ExtractMultithreadProcessor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        Path outputDir = Path.of(outputDirectory);
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        new EmbedMultithreadProcessor(
            Path.of(coverSourceDirectory),
            Path.of(secretSourceDirectory),
            outputDir,
            countConsumers
        ).embedPNGImages();
    }

    @Override
    public void extractPNGImages(String sourceDirectory, String outputDirectory) {
        Path outputDir = Path.of(outputDirectory);
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        new ExtractMultithreadProcessor(Path.of(sourceDirectory), outputDir, countConsumers).extractPNGImages();
    }
}
