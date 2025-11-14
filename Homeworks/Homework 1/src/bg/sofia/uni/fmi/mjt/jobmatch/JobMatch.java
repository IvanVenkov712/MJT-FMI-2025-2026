package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.api.JobMatchAPI;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.CandidateNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.JobPostingNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.CosineSimilarity;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.PlatformStatistics;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByJobTitleComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateJobMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateSimilarityMatchByCandidateNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.CandidateSimilarityMatchByScoreComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByImprovementComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.CandidateJobMatchSameCandidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.CandidateJobMatchSameJobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.CandidateSimilarityMatchSameCandidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.SkillRecommendationIterable;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.CandidateJobPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.CandidateSimilarityPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.SkillRecommendationPredicate;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobMatch implements JobMatchAPI {
    private final Map<String, Candidate> candidates;
    private final Map<String, Employer> employers;
    private final Map<String, JobPosting> jobPostings;
    private final Map<String, Integer> skillFrequency;
    private String mostCommonSkillName;
    private JobPosting highestPaidJob;

    public JobMatch() {
        candidates = new HashMap<>();
        employers = new HashMap<>();
        jobPostings = new HashMap<>();
        skillFrequency = new HashMap<>();
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
        updateMostCommonSkillName(candidate.getSkills());
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
        updateHighestPaidJob(jobPosting);
        return jobPosting;
    }

    @Override
    public List<CandidateJobMatch> findTopNCandidatesForJob(String jobPostingId, int limit,
                                                            SimilarityStrategy strategy) {
        validateFindTopNParams(jobPostingId, limit, strategy);

        if (!jobPostings.containsKey(jobPostingId)) {
            throw new JobPostingNotFoundException("Job Posting with this id was not found!");
        }

        IterableView<Candidate, CandidateJobMatch> matches =
            new CandidateJobMatchSameJobPosting(candidates.values(), jobPostings.get(jobPostingId), strategy);

        Comparator<CandidateJobMatch> comparator = new CandidateJobMatchByScoreComparator().thenComparing(
            new CandidateJobMatchByCandidateNameComparator().reversed()
        );

        return matches.findTopN(comparator, new CandidateJobPredicate(), limit);
    }

    @Override
    public List<CandidateJobMatch> findTopNJobsForCandidate(String candidateEmail, int limit,
                                                            SimilarityStrategy strategy) {

        validateFindTopNParams(candidateEmail, limit, strategy);

        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }

        IterableView<JobPosting, CandidateJobMatch> matches = new CandidateJobMatchSameCandidate(
            jobPostings.values(), candidates.get(candidateEmail), strategy
        );
        Comparator<CandidateJobMatch> comparator = new CandidateJobMatchByScoreComparator().thenComparing(
            new CandidateJobMatchByJobTitleComparator().reversed()
        );

        return matches.findTopN(comparator, new CandidateJobPredicate(), limit);
    }

    @Override
    public List<CandidateSimilarityMatch> findSimilarCandidates(String candidateEmail, int limit,
                                                                SimilarityStrategy strategy) {
        validateFindTopNParams(candidateEmail, limit, strategy);
        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }

        Candidate target = candidates.get(candidateEmail);

        IterableView<Candidate, CandidateSimilarityMatch> matches =
            new CandidateSimilarityMatchSameCandidate(candidates.values(), target, strategy);

        Comparator<CandidateSimilarityMatch> comparator = new CandidateSimilarityMatchByScoreComparator().thenComparing(
            new CandidateSimilarityMatchByCandidateNameComparator().reversed()
        );

        return matches.findTopN(comparator, new CandidateSimilarityPredicate(), limit);
    }

    @Override
    public List<SkillRecommendation> getSkillRecommendationsForCandidate(String candidateEmail, int limit) {
        return getSkillRecommendationsForCandidate(candidateEmail, limit, new CosineSimilarity());
    }

    @Override
    public PlatformStatistics getPlatformStatistics() {
        return new PlatformStatistics(candidates.size(), employers.size(), jobPostings.size(), mostCommonSkillName,
            highestPaidJob.getTitle());
    }

    private void validateFindTopNParams(String name, int limit, SimilarityStrategy strategy) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid jobPostingId");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be non-negative!");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("strategy is null!");
        }
    }

    private List<SkillRecommendation> getSkillRecommendationsForCandidate(String candidateEmail, int limit,
                                                                          SimilarityStrategy strategy) {
        validateFindTopNParams(candidateEmail, limit, strategy);
        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }

        Map<Skill, Double> recommendationsMap = getSkillRecommendationsAll(candidateEmail, strategy);

        IterableView<Map.Entry<Skill, Double>, SkillRecommendation> recommendationsView =
            new SkillRecommendationIterable(recommendationsMap);

        Comparator<SkillRecommendation> comparator = new SkillRecommendationByImprovementComparator().thenComparing(
            new SkillRecommendationByNameComparator().reversed()
        );

        return recommendationsView.findTopN(comparator, new SkillRecommendationPredicate(), limit);
    }

    private Map<Skill, Double> getSkillRecommendationsAll(String candidateEmail, SimilarityStrategy strategy) {
        Candidate candidate = candidates.get(candidateEmail);
        Map<Skill, Double> recommendations = new HashMap<>();
        Set<Skill> candidateSkills = new HashSet<>(candidate.getSkills());

        for (JobPosting posting : jobPostings.values()) {
            double currentSimilarity = strategy.calculateSimilarity(candidateSkills, posting.getRequiredSkills());
            for (Skill skill : posting.getRequiredSkills()) {
                if (!candidateSkills.contains(skill)) {

                    candidateSkills.add(skill);
                    double improvement =
                        strategy.calculateSimilarity(candidateSkills, posting.getRequiredSkills()) - currentSimilarity;
                    candidateSkills.remove(skill);

                    recommendations.putIfAbsent(skill, 0.0);
                    recommendations.put(skill, recommendations.get(skill) + improvement);
                }
            }
        }
        return recommendations;
    }

    private void updateMostCommonSkillName(Collection<Skill> newSkills) {
        String nameOld = mostCommonSkillName;
        int freqOld = nameOld == null ? 0 : skillFrequency.getOrDefault(mostCommonSkillName, 0);

        for (Skill skill : newSkills) {
            skillFrequency.putIfAbsent(skill.name(), 0);
            skillFrequency.put(skill.name(), skillFrequency.get(skill.name()) + 1);

            int freqNew = skillFrequency.get(skill.name());
            String nameNew = skill.name();
            if (nameOld == null || freqNew > freqOld || (freqNew == freqOld && nameNew.compareTo(nameOld) < 0)) {
                mostCommonSkillName = nameNew;
            }
        }
    }

    private void updateHighestPaidJob(JobPosting jobPosting) {
        if (highestPaidJob == null || jobPosting.getSalary() > highestPaidJob.getSalary()) {
            highestPaidJob = jobPosting;
        }
    }
}
