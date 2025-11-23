package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.CollectionConverterImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateSimilarityMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateSimilarityMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters.CandidateSimilarityFilter;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters.CandidateSimilarityMatchSameCandidateConverter;

import java.util.Collection;

public class TopSimilarCandidatesFinder extends TopElementFinderImpl<CandidateSimilarityMatch> {

    public TopSimilarCandidatesFinder(Collection<Candidate> candidates, Candidate target,
                                      SimilarityStrategy strategy) {
        super(
            new CollectionConverterImpl<>(new CandidateSimilarityMatchSameCandidateConverter(target, strategy)).convert(
                candidates),
            new CandidateSimilarityMatchByScoreComparator().thenComparing(
                new CandidateSimilarityMatchByCandidateNameComparator().reversed()
            ), new CandidateSimilarityFilter()
        );
    }

}
