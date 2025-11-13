package bg.sofia.uni.fmi.mjt.jobmatch.model.match.verifiers;

import bg.sofia.uni.fmi.mjt.jobmatch.ItemVerifier;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

public class CandidateSimilarityVerifier implements ItemVerifier<CandidateSimilarityMatch> {
    @Override
    public boolean isValid(CandidateSimilarityMatch candidateSimilarityMatch) {
        return !MathUtils.equalsDouble(candidateSimilarityMatch.getSimilarityScore(), 0);
    }
}
