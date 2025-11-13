package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.api.JobMatchAPI;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.CandidateNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.JobPostingNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.PlatformStatistics;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByJobTitleComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables.CandidateJobMatchSameCandidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables.CandidateJobMatchSameJobPosting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class JobMatch implements JobMatchAPI {
    private final Map<String, Candidate> candidates;
    private final Map<String, Employer> employers;
    private final Map<String, JobPosting> jobPostings;

    public JobMatch() {
        candidates = new HashMap<>();
        employers = new HashMap<>();
        jobPostings = new HashMap<>();
    }

    @Override
    public Candidate registerCandidate(Candidate candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("candidate was null!");
        }
        if (candidates.containsKey(candidate.getEmail())) {
            throw new UserAlreadyExistsException("User with this email is already registered in the system!");
        }
        candidates.put(candidate.getEmail(), candidate);
        return candidate;
    }

    @Override
    public Employer registerEmployer(Employer employer) {
        if (employer == null) {
            throw new IllegalArgumentException("candidate was null!");
        }
        if (employers.containsKey(employer.email())) {
            throw new UserAlreadyExistsException("User with this email is already registered in the system!");
        }
        employers.put(employer.email(), employer);
        return employer;
    }

    @Override
    public JobPosting postJobPosting(JobPosting jobPosting) {
        if (jobPosting == null) {
            throw new IllegalArgumentException("jobPosting was null!");
        }
        if (!employers.containsKey(jobPosting.getEmployerEmail())) {
            throw new UserNotFoundException("The employer is not registered!");
        }
        jobPostings.put(jobPosting.getId(), jobPosting);
        return jobPosting;
    }

    private List<CandidateJobMatch> findTopN(Iterable<CandidateJobMatch> matches,
                                             Comparator<CandidateJobMatch> criterion, int limit) {

        Queue<CandidateJobMatch> topCandidates = new PriorityQueue<>(criterion);

        for (CandidateJobMatch match : matches) {
            if (!MathUtils.equalsDouble(match.getSimilarityScore(), 0)) {
                topCandidates.add(match);
                if (topCandidates.size() > limit) {
                    topCandidates.poll();
                }
            }
        }

        List<CandidateJobMatch> res = new ArrayList<>();

        while (!topCandidates.isEmpty()) {
            res.add(topCandidates.poll());
        }

        return List.copyOf(res.reversed());
    }

    @Override
    public List<CandidateJobMatch> findTopNCandidatesForJob(String jobPostingId, int limit,
                                                            SimilarityStrategy strategy) {
        if (jobPostingId == null || jobPostingId.isBlank()) {
            throw new IllegalArgumentException("Invalid jobPostingId");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be non-negative!");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("strategy is null!");
        }
        if (!jobPostings.containsKey(jobPostingId)) {
            throw new JobPostingNotFoundException("Job Posting with this id was not found!");
        }
        return findTopN(
            new CandidateJobMatchSameJobPosting(candidates.values(), jobPostings.get(jobPostingId), strategy),
            new CandidateJobMatchByScoreComparator()
                .thenComparing(new CandidateJobMatchByCandidateNameComparator().reversed()),
            limit
        );
    }

    @Override
    public List<CandidateJobMatch> findTopNJobsForCandidate(String candidateEmail, int limit,
                                                            SimilarityStrategy strategy) {

        if (candidateEmail == null || candidateEmail.isBlank()) {
            throw new IllegalArgumentException("Invalid jobPostingId");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be non-negative!");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("strategy is null!");
        }
        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }
        return findTopN(
            new CandidateJobMatchSameCandidate(jobPostings.values(), candidates.get(candidateEmail), strategy),
            new CandidateJobMatchByScoreComparator()
                .thenComparing(new CandidateJobMatchByJobTitleComparator().reversed()),
            limit
        );
    }

    @Override
    public List<CandidateSimilarityMatch> findSimilarCandidates(String candidateEmail, int limit,
                                                                SimilarityStrategy strategy) {

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
