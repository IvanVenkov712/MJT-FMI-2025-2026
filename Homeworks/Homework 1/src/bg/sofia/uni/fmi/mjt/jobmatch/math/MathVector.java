package bg.sofia.uni.fmi.mjt.jobmatch.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MathVector {
    private final List<Double> arr;
    private final double norm;

    public MathVector(Collection<Double> arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr was null");
        }
        this.arr = new ArrayList<>(arr);
        double normTmp = 0;
        for (double x : this.arr) {
            normTmp += x * x;
        }
        norm = Math.sqrt(normTmp);
    }

    public int length() {
        return arr.size();
    }

    public double getNorm() {
        return norm;
    }

    public double dotProduct(MathVector other) {
        if (other == null || other.length() != length()) {
            throw new IllegalArgumentException("other is not a valid vector for a dot product");
        }
        double res = 0;
        for (int i = 0; i < length(); ++i) {
            res += arr.get(i) * other.arr.get(i);
        }
        return res;
    }

    public double getSimilarity(MathVector other) {
        if (other == null || other.length() != length()) {
            throw new IllegalArgumentException("other is not a valid vector for a similarity");
        }
        double norm1 = getNorm();
        double norm2 = other.getNorm();
        if (MathUtils.equalsDouble(norm1, 0.0) || MathUtils.equalsDouble(norm2, 0)) {
            return 0;
        }
        double dot = dotProduct(other);
        return dot / (norm1 * norm2);
    }
}
