package bg.sofia.uni.fmi.mjt.steganography.extract;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

@SuppressWarnings("CheckStyle")
public class ExtractConsumer implements Runnable {
    private final ExtractImageInfo imageInfo;
    private final Path outputDir;

    public ExtractConsumer(ExtractImageInfo imageInfo, Path outputDir) {
        this.imageInfo = imageInfo;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        BufferedImage res = extract();
        ImageHelper.saveImage(res, outputDir.toString(), imageInfo.imageName());
    }

    public BufferedImage extract() {
        BufferedImage embedded = imageInfo.image();
        int width = embedded.getWidth();
        int height = embedded.getHeight();
        int totalPixels = width * height;

        int pixelIndex = 0;

        // 1) Extract secret width
        int secretWidth = extract12Bits(embedded, pixelIndex);
        pixelIndex += 4;

        // 2) Extract secret height
        int secretHeight = extract12Bits(embedded, pixelIndex);
        pixelIndex += 4;

        if (secretWidth <= 0 || secretHeight <= 0) {
            throw new IllegalStateException("Invalid extracted secret dimensions.");
        }

        if (secretWidth * secretHeight + 8 > totalPixels) {
            throw new IllegalStateException("Embedded image is corrupted or incomplete.");
        }

        BufferedImage secret = new BufferedImage(
            secretWidth,
            secretHeight,
            BufferedImage.TYPE_INT_ARGB
        );

        // 3) Extract secret pixels
        for (int y = 0; y < secretHeight; y++) {
            for (int x = 0; x < secretWidth; x++) {
                int cx = pixelIndex % width;
                int cy = pixelIndex / width;

                int rgb = embedded.getRGB(cx, cy);

                int bits = extract3BitsFromPixel(rgb);

                // Restore MSB position (xxx00000)
                int gray = bits << 5;

                int pixel =
                    (0xFF << 24) |
                        (gray << 16) |
                        (gray << 8) |
                        gray;

                secret.setRGB(x, y, pixel);
                pixelIndex++;
            }
        }

        return secret;
    }

    /**
     * Extracts a 12-bit integer from 4 pixels (3 bits per pixel)
     */
    private static int extract12Bits(BufferedImage img, int startIndex) {
        int value = 0;
        int width = img.getWidth();

        for (int i = 0; i < 4; i++) {
            int index = startIndex + i;
            int x = index % width;
            int y = index / width;

            int rgb = img.getRGB(x, y);
            int bits = extract3BitsFromPixel(rgb);

            value = (value << 3) | bits;
        }

        return value;
    }

    /**
     * Extracts 3 LSB bits from R, G, B
     */
    private static int extract3BitsFromPixel(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        int bitR = r & 1;
        int bitG = g & 1;
        int bitB = b & 1;

        return (bitR << 2) | (bitG << 1) | bitB;
    }
}
