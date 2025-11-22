package bg.sofia.uni.fmi.mjt.jobmatch.model.match;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;

import java.util.Objects;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;
import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validateSimilarityScore;

public class CandidateJobMatch {
    private final Candidate candidate;
    private final JobPosting jobPosting;
    private final double similarityScore;

    public CandidateJobMatch(Candidate candidate, JobPosting jobPosting,  double similarityScore) {
        validate(candidate, "candidate");
        validate(jobPosting, "jobPosting");
        validateSimilarityScore(similarityScore);

        this.jobPosting = jobPosting;
        this.candidate = candidate;
        this.similarityScore = similarityScore;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CandidateJobMatch that = (CandidateJobMatch) o;
        return Double.compare(similarityScore, that.similarityScore) == 0 &&
            Objects.equals(candidate, that.candidate) && Objects.equals(jobPosting, that.jobPosting);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(candidate);
        result = 31 * result + Objects.hashCode(jobPosting);
        result = 31 * result + Double.hashCode(similarityScore);
        return result;
    }
}
