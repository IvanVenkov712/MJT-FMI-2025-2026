package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

import java.util.Comparator;

public class CandidateSimilarityMatchByCandidateNameComparator implements Comparator<CandidateSimilarityMatch> {
    @Override
    public int compare(CandidateSimilarityMatch o1, CandidateSimilarityMatch o2) {
        return o1.getSimilarCandidate().getName().compareTo(o2.getSimilarCandidate().getName());
    }
}
