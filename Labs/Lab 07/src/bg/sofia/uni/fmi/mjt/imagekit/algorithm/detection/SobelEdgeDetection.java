package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private static final int MIN_WIDTH = 3;
    private static final int MIN_HEIGHT = 3;
    private static final int COLOR_MAX = 255;

    private static final int[][] GX = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };

    private static final int[][] GY = {
        {-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}
    };

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        if (grayscaleAlgorithm == null) {
            throw new IllegalArgumentException("grayscaleAlgorithm is null");
        }
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    // TODO: Decide what to do in the edges and the corners
    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
        image = grayscaleAlgorithm.process(image);
        if (image.getWidth() < MIN_WIDTH || image.getHeight() < MIN_HEIGHT) {
            return image.getSubimage(0, 0, image.getWidth(), image.getHeight());
        }
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage output = new BufferedImage(width, height, image.getType());

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int edgeColor = applyForPixel(image, x, y);
                output.setRGB(x, y, edgeColor);
            }
        }
        return output;
    }

    private static int applyForPixel(BufferedImage image, int x, int y) {
        int pixelX = 0;
        int pixelY = 0;

        // Apply convolution
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                int rgb = image.getRGB(x + j, y + i);
                int gray = new Color(rgb).getRed();

                pixelX += gray * GX[i + 1][j + 1];
                pixelY += gray * GY[i + 1][j + 1];
            }
        }
        // Magnitude
        int magnitude = (int) Math.min(COLOR_MAX, Math.sqrt(pixelX * pixelX + pixelY * pixelY));
        return new Color(magnitude, magnitude, magnitude).getRGB();
    }
}
