package bg.sofia.uni.fmi.mjt.steganography.extract;

import java.awt.image.BufferedImage;

public record ExtractImageInfo(boolean toProcess, BufferedImage image, String imageName) {
    public ExtractImageInfo(BufferedImage image, String imageName) {
        this(true, image, imageName);
    }

    public static final ExtractImageInfo POISON_PILL = new ExtractImageInfo(false, null, null);
}
