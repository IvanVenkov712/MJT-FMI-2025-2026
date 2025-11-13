package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CosineSimilarity extends AbstractSimilarityStrategy {

    private record SkillsSetPair(Set<Skill> candidateSkills, Set<Skill> jobSkills) {

    }

    private record VectorPair(MathVector candidateVector, MathVector jobVector) {

    }

    private class MathVector {
        private static final double EPSILON = 0.000000000001;
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
            if (Math.abs(norm1) < EPSILON || Math.abs(norm2) < EPSILON) {
                return 0;
            }
            double dot = dotProduct(other);
            return dot / (norm1 * norm2);
        }
    }


    @Override
    public double calculateSimilarity(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        validateParameters(candidateSkills, jobSkills);
        if (candidateSkills.isEmpty() && jobSkills.isEmpty()) {
            return 0;
        }

        return 0;
    }
}
