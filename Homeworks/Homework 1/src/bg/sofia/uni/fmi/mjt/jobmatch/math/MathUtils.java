package bg.sofia.uni.fmi.mjt.jobmatch.math;

public class MathUtils {
    public static final double EPSILON = 0.000000000001;

    public static boolean equalsDouble(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static int compareDouble(double a, double b) {
        double diff = a - b;
        if (Math.abs(diff) < EPSILON) {
            return 0;
        } else if (diff < -EPSILON) {
            return -1;
        } else {
            return 1;
        }

    }
}
