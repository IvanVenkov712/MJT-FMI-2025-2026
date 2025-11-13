package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

import java.util.Comparator;

public class CandidateSimilarityMatchByScoreComparator implements Comparator<CandidateSimilarityMatch> {
    @Override
    public int compare(CandidateSimilarityMatch o1, CandidateSimilarityMatch o2) {
        return Double.compare(o1.getSimilarityScore(), o2.getSimilarityScore());
    }
}
