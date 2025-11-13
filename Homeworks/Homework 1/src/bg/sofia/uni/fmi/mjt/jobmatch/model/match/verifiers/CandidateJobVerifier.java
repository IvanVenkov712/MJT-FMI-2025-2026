package bg.sofia.uni.fmi.mjt.jobmatch.model.match.verifiers;

import bg.sofia.uni.fmi.mjt.jobmatch.ItemVerifier;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

public class CandidateJobVerifier implements ItemVerifier<CandidateJobMatch> {
    @Override
    public boolean isValid(CandidateJobMatch candidateJobMatch) {
        return !MathUtils.equalsDouble(candidateJobMatch.getSimilarityScore(), 0);
    }
}
