package bg.sofia.uni.fmi.mjt.jobmatch.math;

public class MathUtils {
    public static final double EPSILON = 0.000000000001;

    public static boolean equalsDouble(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
}
