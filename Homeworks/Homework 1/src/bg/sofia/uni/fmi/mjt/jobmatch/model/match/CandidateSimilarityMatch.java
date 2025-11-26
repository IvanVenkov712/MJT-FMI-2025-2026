package bg.sofia.uni.fmi.mjt.jobmatch.model.match;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;

import java.util.Objects;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;
import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validateSimilarityScore;

public class CandidateSimilarityMatch {
    private Candidate targetCandidate;
    private Candidate similarCandidate;
    private double similarityScore;

    public CandidateSimilarityMatch(Candidate targetCandidate, Candidate similarCandidate, double similarityScore) {
        validate(targetCandidate, "targetCandidate");
        validate(similarCandidate, "similarCandidate");
        validateSimilarityScore(similarityScore);

        this.targetCandidate = targetCandidate;
        this.similarCandidate = similarCandidate;
        this.similarityScore = similarityScore;
    }

    public Candidate getTargetCandidate() {
        return targetCandidate;
    }

    public Candidate getSimilarCandidate() {
        return similarCandidate;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CandidateSimilarityMatch that = (CandidateSimilarityMatch) o;
        return MathUtils.compareDouble(similarityScore, that.similarityScore) == 0 &&
            Objects.equals(targetCandidate, that.targetCandidate) &&
            Objects.equals(similarCandidate, that.similarCandidate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(targetCandidate);
        result = 31 * result + Objects.hashCode(similarCandidate);
        result = 31 * result + Double.hashCode(similarityScore);
        return result;
    }
}
