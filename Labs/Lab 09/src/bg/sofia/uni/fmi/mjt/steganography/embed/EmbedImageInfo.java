package bg.sofia.uni.fmi.mjt.steganography.embed;

import java.awt.image.BufferedImage;

public record EmbedImageInfo(boolean toProcess, BufferedImage coverImage, BufferedImage secretImage, String imageName) {
    public EmbedImageInfo(BufferedImage coverImage, BufferedImage secretImage, String imageName) {
        this(true, coverImage, secretImage, imageName);
    }

    public static final EmbedImageInfo POISON_PILL = new EmbedImageInfo(false, null, null, null);
}
