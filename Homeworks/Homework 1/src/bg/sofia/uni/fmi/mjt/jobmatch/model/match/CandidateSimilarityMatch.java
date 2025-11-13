package bg.sofia.uni.fmi.mjt.jobmatch.model.match;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;

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
}
