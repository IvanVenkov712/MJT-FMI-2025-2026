package bg.sofia.uni.fmi.mjt.steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ImageHelper {
    public static BufferedImage loadImage(Path imagePath) {
        try {
            return ImageIO.read(imagePath.toFile());
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("Failed to load image %s", imagePath.toString()), e);
        }
    }

    public static void saveImage(BufferedImage image, String outputDir, String name) {
        try {
            ImageIO.write(image, "png", new File(outputDir, name));
            System.out.println("Saved " + name + " to " + outputDir);
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("While saving image %s", name), e);
        }
    }

    public static int getPixelCount(BufferedImage img) {
        return img.getWidth() * img.getHeight();
    }

    public static List<Path> getPNGs(Path sourceDir) {
        try (Stream<Path> files = Files.list(sourceDir)) {
            return files.filter(path -> path.toString().endsWith(".png"))
                .sorted(Comparator.comparing(Path::getFileName))
                .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }
}
