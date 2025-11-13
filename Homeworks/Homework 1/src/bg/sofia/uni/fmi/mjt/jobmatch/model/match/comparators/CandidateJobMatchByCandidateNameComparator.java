package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

import java.util.Comparator;

public class CandidateJobMatchByCandidateNameComparator implements Comparator<CandidateJobMatch> {
    @Override
    public int compare(CandidateJobMatch o1, CandidateJobMatch o2) {
        return o1.getCandidate().getName().compareTo(o2.getCandidate().getName());
    }
}
