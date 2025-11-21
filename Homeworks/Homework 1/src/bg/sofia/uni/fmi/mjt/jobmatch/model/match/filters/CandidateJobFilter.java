package bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Filter;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

public class CandidateJobFilter implements Filter<CandidateJobMatch> {
    @Override
    public boolean matches(CandidateJobMatch candidateJobMatch) {
        return !MathUtils.equalsDouble(candidateJobMatch.getSimilarityScore(), 0);
    }
}
