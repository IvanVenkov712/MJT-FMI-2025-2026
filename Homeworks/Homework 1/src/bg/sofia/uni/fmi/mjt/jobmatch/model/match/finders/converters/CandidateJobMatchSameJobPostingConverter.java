package bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Converter;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

public class CandidateJobMatchSameJobPostingConverter implements Converter<Candidate, CandidateJobMatch> {
    private final JobPosting posting;
    private final SimilarityStrategy strategy;

    public CandidateJobMatchSameJobPostingConverter(JobPosting posting, SimilarityStrategy strategy) {
        this.posting = posting;
        this.strategy = strategy;
    }

    @Override
    public CandidateJobMatch convert(Candidate arg) {
        return new CandidateJobMatch(arg, posting,
            strategy.calculateSimilarity(arg.getSkills(), posting.getRequiredSkills())
        );
    }
}
