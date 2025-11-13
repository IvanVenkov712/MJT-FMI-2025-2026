package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

import java.util.Comparator;

public class CandidateJobMatchByScoreComparator implements Comparator<CandidateJobMatch> {
    @Override
    public int compare(CandidateJobMatch o1, CandidateJobMatch o2) {
        return Double.compare(o1.getSimilarityScore(), o2.getSimilarityScore());
    }
}
