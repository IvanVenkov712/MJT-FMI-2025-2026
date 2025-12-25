package bg.sofia.uni.fmi.mjt.steganography.embed;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

@SuppressWarnings("CheckStyle")
public class EmbedConsumer implements Runnable {
    private final EmbedImageInfo images;
    private final Path outputDir;

    public EmbedConsumer(EmbedImageInfo images, Path outputDir) {
        this.images = images;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        BufferedImage res = embed();
        ImageHelper.saveImage(res, outputDir.toString(), images.imageName());
    }

    public BufferedImage embed() {
        int coverWidth = images.coverImage().getWidth();
        int coverHeight = images.coverImage().getHeight();
        int secretWidth = images.secretImage().getWidth();
        int secretHeight = images.secretImage().getHeight();

        int coverPixels = coverWidth * coverHeight;

        BufferedImage result = new BufferedImage(
            coverWidth,
            coverHeight,
            BufferedImage.TYPE_INT_ARGB
        );

        int coverIndex = 0;

        // 1) Embed secret width (12 bits) into first 4 pixels
        coverIndex = embed12Bits(
            result, images.coverImage(), coverIndex, secretWidth
        );

        // 2) Embed secret height (12 bits) into next 4 pixels
        coverIndex = embed12Bits(
            result, images.coverImage(), coverIndex, secretHeight
        );

        // 3) Embed secret image pixels
        for (int y = 0; y < secretHeight; y++) {
            for (int x = 0; x < secretWidth; x++) {
                int secretRGB = images.secretImage().getRGB(x, y);

                int r = (secretRGB >> 16) & 0xFF;
                int g = (secretRGB >> 8) & 0xFF;
                int b = secretRGB & 0xFF;

                // Average RGB, floor
                int avg = (r + g + b) / 3;

                // Take 3 MSB
                int msb3 = (avg >> 5) & 0b111;

                int cx = coverIndex % coverWidth;
                int cy = coverIndex / coverWidth;

                int coverRGB = images.coverImage().getRGB(cx, cy);
                int embedded = embed3BitsIntoPixel(coverRGB, msb3);

                result.setRGB(cx, cy, embedded);
                coverIndex++;
            }
        }

        // 4) Copy remaining cover pixels unchanged
        while (coverIndex < coverPixels) {
            int cx = coverIndex % coverWidth;
            int cy = coverIndex / coverWidth;
            result.setRGB(cx, cy, images.coverImage().getRGB(cx, cy));
            coverIndex++;
        }

        return result;
    }

    /**
     * Embeds a 12-bit value into 4 pixels (3 bits per pixel)
     */
    private static int embed12Bits(
        BufferedImage result,
        BufferedImage cover,
        int startIndex,
        int value
    ) {
        int bitIndex = 11; // start from MSB

        int width = cover.getWidth();

        for (int i = 0; i < 4; i++) {
            int bits = (value >> (bitIndex - 2)) & 0b111;
            bitIndex -= 3;

            int cx = startIndex % width;
            int cy = startIndex / width;

            int coverRGB = cover.getRGB(cx, cy);
            int embedded = embed3BitsIntoPixel(coverRGB, bits);

            result.setRGB(cx, cy, embedded);
            startIndex++;
        }

        return startIndex;
    }

    /**
     * Embeds 3 bits into the LSB of R, G, B
     */
    private static int embed3BitsIntoPixel(int rgb, int bits) {
        int a = (rgb >> 24) & 0xFF;
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        r = (r & 0xFE) | ((bits >> 2) & 1);
        g = (g & 0xFE) | ((bits >> 1) & 1);
        b = (b & 0xFE) | (bits & 1);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

}
