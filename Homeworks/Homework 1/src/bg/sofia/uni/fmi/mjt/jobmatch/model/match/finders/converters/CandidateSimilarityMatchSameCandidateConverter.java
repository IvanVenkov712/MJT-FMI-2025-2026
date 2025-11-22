package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Converter;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

public class CandidateSimilarityMatchSameCandidateConverter implements
    Converter<Candidate, CandidateSimilarityMatch> {
    private final Candidate target;
    private final SimilarityStrategy strategy;

    public CandidateSimilarityMatchSameCandidateConverter(Candidate target, SimilarityStrategy strategy) {
        this.target = target;
        this.strategy = strategy;
    }

    @Override
    public CandidateSimilarityMatch convert(Candidate arg) {
        return new CandidateSimilarityMatch(
            target, arg, strategy.calculateSimilarity(target.getSkills(), arg.getSkills())
        );
    }
}