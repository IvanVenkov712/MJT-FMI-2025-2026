package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByJobTitleComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.CandidateJobPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.CandidateJobMatchSameCandidateConvertor;

public class TopJobFinder extends TopElementFinderImpl<CandidateJobMatch> {

    public TopJobFinder(Iterable<JobPosting> original, Candidate candidate,
                        SimilarityStrategy strategy) {

        super(
            new IterableView<>(original, new CandidateJobMatchSameCandidateConvertor(candidate, strategy)),
            new CandidateJobMatchByScoreComparator().thenComparing(
                new CandidateJobMatchByJobTitleComparator().reversed()
            ), new CandidateJobPredicate()
        );
    }
}
