package bg.sofia.uni.fmi.mjt.steganography.embed;

import java.awt.image.BufferedImage;

public record EmbedImageInfo(BufferedImage coverImage, BufferedImage secretImage, String imageName) {
}
