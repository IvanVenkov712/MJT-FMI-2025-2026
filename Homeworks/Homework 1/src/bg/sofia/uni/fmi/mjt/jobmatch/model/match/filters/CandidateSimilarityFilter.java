package bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Filter;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

public class CandidateSimilarityFilter implements Filter<CandidateSimilarityMatch> {
    @Override
    public boolean matches(CandidateSimilarityMatch candidateSimilarityMatch) {
        if (candidateSimilarityMatch.getTargetCandidate().equals(candidateSimilarityMatch.getSimilarCandidate())) {
            return false;
        }
        return !MathUtils.equalsDouble(candidateSimilarityMatch.getSimilarityScore(), 0);
    }
}
