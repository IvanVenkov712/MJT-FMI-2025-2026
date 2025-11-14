package bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

public class CandidateSimilarityPredicate implements MyPredicate<CandidateSimilarityMatch> {
    @Override
    public boolean test(CandidateSimilarityMatch candidateSimilarityMatch) {
        return !MathUtils.equalsDouble(candidateSimilarityMatch.getSimilarityScore(), 0);
    }
}
