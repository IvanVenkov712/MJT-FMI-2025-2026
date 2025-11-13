package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.api.JobMatchAPI;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.PlatformStatistics;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.List;

public class JobMatch implements JobMatchAPI {
    public JobMatch() {

    }

    @Override
    public Candidate registerCandidate(Candidate candidate) {
        return null;
    }

    @Override
    public Employer registerEmployer(Employer employer) {
        return null;
    }

    @Override
    public JobPosting postJobPosting(JobPosting jobPosting) {
        return null;
    }

    @Override
    public List<CandidateJobMatch> findTopNCandidatesForJob(String jobPostingId, int limit,
                                                            SimilarityStrategy strategy) {
        return List.of();
    }

    @Override
    public List<CandidateJobMatch> findTopNJobsForCandidate(String candidateEmail, int limit,
                                                            SimilarityStrategy strategy) {
        return List.of();
    }

    @Override
    public List<CandidateSimilarityMatch> findSimilarCandidates(String candidateEmail, int limit,
                                                                SimilarityStrategy strategy) {
        return List.of();
    }

    @Override
    public List<SkillRecommendation> getSkillRecommendationsForCandidate(String candidateEmail, int limit) {
        return List.of();
    }

    @Override
    public PlatformStatistics getPlatformStatistics() {
        return null;
    }
}
