package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private static final int MIN_WIDTH = 0;
    private static final int MIN_HEIGHT = 0;
    private static final int COLOR_MAX = 255;
    private static final int MATRIX_SIZE = 3;
    private static final int BYTE_SIZE = 8;

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

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int edgeColor = getPixelColor(image, x, y);
                output.setRGB(x, y, edgeColor);
            }
        }
        return output;
    }

    private static int getPixelColor(BufferedImage image, int x, int y) {
        /*
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
        int pixelValue = Math.min(255, (int) Math.round(G));
        // Magnitude
        int magnitude = (int) Math.min(COLOR_MAX, Math.sqrt(pixelX * pixelX + pixelY * pixelY));
        return new Color(magnitude, magnitude, magnitude).getRGB();
         */
        int[][] pixelMatrix = getMatrixFromImage(image, x, y);
        int gx = applyConvolution(GX, pixelMatrix);
        int gy = applyConvolution(GY, pixelMatrix);
        double g = Math.sqrt(gx * gx + gy * gy);
        int pixelValue = Math.min(COLOR_MAX, (int) Math.round(g));

        return (pixelValue << BYTE_SIZE * 2) | (pixelValue << BYTE_SIZE) | pixelValue;
    }

    private static int applyConvolution(int[][] kernel, int[][] matrix) {
        if (kernel.length == 0 || kernel.length != matrix.length) {
            return 0;
        }
        if (kernel[0].length == 0 || kernel[0].length != matrix[0].length) {
            return 0;
        }
        int m = kernel.length;
        int n = kernel[0].length;
        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                res += kernel[i][j] * matrix[i][j];
            }
        }

        return res;
    }

    private static int[][] getMatrixFromImage(BufferedImage image, int x, int y) {
        int[][] res = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int px = x + dx;
                int py = y + dy;

                if (px < 0 || px >= image.getWidth() || py < 0 || py >= image.getHeight()) {
                    res[dy + 1][dx + 1] = 0;
                } else {
                    int rgb = image.getRGB(px, py);
                    res[dy + 1][dx + 1] = new Color(rgb).getRed();
                }
            }
        }
        return res;
    }
}
