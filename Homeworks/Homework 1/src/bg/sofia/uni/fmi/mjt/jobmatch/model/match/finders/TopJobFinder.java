package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.CollectionConverterImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByJobTitleComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters.CandidateJobFilter;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters.CandidateJobMatchSameCandidateConverter;

import java.util.Collection;

public class TopJobFinder extends TopElementFinderImpl<CandidateJobMatch> {

    public TopJobFinder(Collection<JobPosting> original, Candidate candidate,
                        SimilarityStrategy strategy) {

        super(
            new CollectionConverterImpl<>(new CandidateJobMatchSameCandidateConverter(candidate, strategy)).convert(
                original),
            new CandidateJobMatchByScoreComparator().thenComparing(
                new CandidateJobMatchByJobTitleComparator().reversed()
            ), new CandidateJobFilter()
        );
    }
}
