package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Converter;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

public class CandidateJobMatchSameCandidateConverter implements Converter<JobPosting, CandidateJobMatch> {
    private final Candidate candidate;
    private final SimilarityStrategy strategy;

    public CandidateJobMatchSameCandidateConverter(Candidate candidate, SimilarityStrategy strategy) {
        this.candidate = candidate;
        this.strategy = strategy;
    }

    @Override
    public CandidateJobMatch convert(JobPosting arg) {
        return new CandidateJobMatch(
            candidate, arg, strategy.calculateSimilarity(candidate.getSkills(), arg.getRequiredSkills())
        );
    }
}
