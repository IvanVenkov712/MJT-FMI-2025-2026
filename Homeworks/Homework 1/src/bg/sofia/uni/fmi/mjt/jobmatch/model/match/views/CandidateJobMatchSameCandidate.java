package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.CandidateJobMatchSameCandidateConvertor;

public class CandidateJobMatchSameCandidate extends IterableView<JobPosting, CandidateJobMatch> {

    public CandidateJobMatchSameCandidate(Iterable<JobPosting> original, Candidate candidate,
                                          SimilarityStrategy strategy) {

        super(original, new CandidateJobMatchSameCandidateConvertor(candidate, strategy));
    }
}
