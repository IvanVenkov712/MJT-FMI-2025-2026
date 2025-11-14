package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.ElementConverter;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

public class CandidateSimilarityMatchSameCandidateConvertor implements
    ElementConverter<Candidate, CandidateSimilarityMatch> {
    private final Candidate target;
    private final SimilarityStrategy strategy;

    public CandidateSimilarityMatchSameCandidateConvertor(Candidate target, SimilarityStrategy strategy) {
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