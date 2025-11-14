package bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

public class CandidateJobPredicate implements MyPredicate<CandidateJobMatch> {
    @Override
    public boolean test(CandidateJobMatch candidateJobMatch) {
        return !MathUtils.equalsDouble(candidateJobMatch.getSimilarityScore(), 0);
    }
}
