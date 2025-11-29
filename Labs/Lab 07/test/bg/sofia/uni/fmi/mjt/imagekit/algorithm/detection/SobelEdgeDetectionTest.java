package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class SobelEdgeDetectionTest {
    @Test
    void testProcess() {
        try {
            new SobelEdgeDetection(new LuminosityGrayscale()).process(
                new LocalFileSystemImageManager().loadImage(new File("test/car.jpg"))
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}