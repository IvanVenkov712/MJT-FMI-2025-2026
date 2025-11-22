package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    private static final float DEFAULT_RED_COEF = 0.21f;
    private static final float DEFAULT_GREEN_COEF = 0.72f;
    private static final float DEFAULT_BLUE_COEF = 0.07f;

    public static int getGray(int r, int g, int b) {
        return (int) (DEFAULT_RED_COEF * r + DEFAULT_GREEN_COEF * g + DEFAULT_BLUE_COEF * b);
    }

    public static int getGray(int rgb) {
        Color c = new Color(rgb);

        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();

        return getGray(r, g, b);
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
        // BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        // return op.filter(image, null);

        int w = image.getWidth();
        int h = image.getHeight();
        ColorModel model = ColorModel.getRGBdefault();

        BufferedImage res = new BufferedImage(w, h, image.getType());
        for (int x = image.getMinX(); x < w; ++x) {
            for (int y = image.getMinY(); y < h; ++y) {
                int gray = getGray(image.getRGB(x, y));
                Color c = new Color(gray, gray, gray);
                res.setRGB(x, y, c.getRGB());
            }
        }
        return res;
    }
}
