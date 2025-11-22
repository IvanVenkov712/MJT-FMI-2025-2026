package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    private static final float DEFAULT_RED_COEF = 0.21f;
    private static final float DEFAULT_GREEN_COEF = 0.72f;
    private static final float DEFAULT_BLUE_COEF = 0.07f;

    @Override
    public BufferedImage process(BufferedImage image) {
        // BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        // return op.filter(image, null);
        int x = image.getMinX();
        int y = image.getMinY();
        int w = image.getWidth();
        int h = image.getHeight();
        ColorModel model = ColorModel.getRGBdefault();
        BufferedImage res = new BufferedImage(w, h, image.getType());
        for (; x < w; ++x) {
            for (; y < h; ++y) {
                int pixel = image.getRGB(x, y);
                int r = model.getRed(pixel);
                int g = model.getGreen(pixel);
                int b = model.getBlue(pixel);
                Color c = new Color(DEFAULT_RED_COEF * r, DEFAULT_GREEN_COEF * g , DEFAULT_BLUE_COEF * b);
                res.setRGB(x, y, c.getRGB());
            }
        }
        return res;
    }
}
