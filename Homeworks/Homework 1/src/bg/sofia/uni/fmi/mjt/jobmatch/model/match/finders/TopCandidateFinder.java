package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.CollectionConverterImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters.CandidateJobFilter;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters.CandidateJobMatchSameJobPostingConverter;

import java.util.Collection;

public class TopCandidateFinder extends TopElementFinderImpl<CandidateJobMatch> {

    public TopCandidateFinder(Collection<Candidate> candidates, JobPosting posting,
                              SimilarityStrategy strategy) {
        super(
            new CollectionConverterImpl<>(new CandidateJobMatchSameJobPostingConverter(posting, strategy)).convert(
                candidates),
            new CandidateJobMatchByScoreComparator().thenComparing(
                new CandidateJobMatchByCandidateNameComparator().reversed()
            ), new CandidateJobFilter()
        );
    }
}

