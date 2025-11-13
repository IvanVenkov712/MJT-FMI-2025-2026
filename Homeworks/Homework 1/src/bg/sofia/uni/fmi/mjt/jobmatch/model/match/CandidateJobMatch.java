package bg.sofia.uni.fmi.mjt.jobmatch.model.match;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;
import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validateSimilarityScore;

public class CandidateJobMatch {
    private Candidate candidate;
    private JobPosting jobPosting;
    private double similarityScore;

    public CandidateJobMatch(Candidate candidate, JobPosting jobPosting,  double similarityScore) {
        validate(candidate, "candidate");
        validate(jobPosting, "jobPosting");
        validateSimilarityScore(similarityScore);

        this.jobPosting = jobPosting;
        this.candidate = candidate;
        this.similarityScore = similarityScore;
    }
}
