package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.CandidateJobPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.CandidateJobMatchSameJobPostingConverter;

import java.util.Collection;

public class CandidateJobMatchSameJobPosting extends TopElementFinderImpl<CandidateJobMatch> {

    public CandidateJobMatchSameJobPosting(Collection<Candidate> candidates, JobPosting posting,
                                           SimilarityStrategy strategy) {
        super(
            new IterableView<>(candidates, new CandidateJobMatchSameJobPostingConverter(posting, strategy)),
            new CandidateJobMatchByScoreComparator().thenComparing(
                new CandidateJobMatchByCandidateNameComparator().reversed()
            ), new CandidateJobPredicate()
        );
    }
}

