package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LuminosityGrayscaleTest {
    @Test
    void testProcess(@TempDir Path tempDir) {
        Path imagePath = tempDir.resolve("image.png");
        LocalFileSystemImageManager manager = new LocalFileSystemImageManager();
        try{
            manager.saveImage(new BufferedImage(12, 12, BufferedImage.TYPE_INT_RGB),imagePath.toFile());
            new LuminosityGrayscale().process(manager.loadImage(imagePath.toFile()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}