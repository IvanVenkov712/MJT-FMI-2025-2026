package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.api.JobMatchAPI;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.CandidateNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.JobPostingNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.CollectionConverterImpl;
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
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.JobPostingBySalaryComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.JobPostingByTitleComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.TopJobFinder;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.TopCandidateFinder;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.TopSimilarCandidatesFinder;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.TopSkillRecommendationFinder;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.finders.converters.CandidateJobMatchSameCandidateConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobMatch implements JobMatchAPI {
    private static final Comparator<JobPosting> HIGHEST_PAID_JOB_COMPARATOR =
        new JobPostingBySalaryComparator().reversed().thenComparing(new JobPostingByTitleComparator());

    private static final SimilarityStrategy DEFAULT_STRATEGY = new CosineSimilarity();
    private final Map<String, Candidate> candidates;
    private final Map<String, Employer> employers;
    private final Map<String, JobPosting> jobPostings;

    private final Map<String, Integer> skillFrequency;
    private final Map<String, Integer> allRequiredSkills;
    private String mostCommonSkillName;
    private JobPosting highestPaidJob;

    public JobMatch() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    // To allow Dependency Injection
    JobMatch(Map<String, Candidate> candidates, Map<String, Employer> employers,
             Map<String, JobPosting> jobPostings) {
        if (candidates == null || employers == null || jobPostings == null) {
            throw new IllegalArgumentException("Null initialization if illegal!");
        }
        this.candidates = candidates;
        this.employers = employers;
        this.jobPostings = jobPostings;
        this.skillFrequency = new HashMap<>();
        this.allRequiredSkills = new HashMap<>();
        for (JobPosting posting : jobPostings.values()) {
            updateHighestPaidJob(posting);
            updateAllRequiredSkills(posting.getRequiredSkills());
        }
        for (Candidate candidate : candidates.values()) {
            updateMostCommonSkillName(candidate.getSkills());
        }
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
        if (jobPostings.containsKey(jobPosting.getId())) {
            return jobPosting;
        }
        jobPostings.put(jobPosting.getId(), jobPosting);
        updateHighestPaidJob(jobPosting);
        updateAllRequiredSkills(jobPosting.getRequiredSkills());
        return jobPosting;
    }

    @Override
    public List<CandidateJobMatch> findTopNCandidatesForJob(String jobPostingId, int limit,
                                                            SimilarityStrategy strategy) {
        validateFindTopNParams(jobPostingId, limit, strategy);

        if (!jobPostings.containsKey(jobPostingId)) {
            throw new JobPostingNotFoundException("Job Posting with this id was not found!");
        }

        return new TopCandidateFinder(candidates.values(), jobPostings.get(jobPostingId), strategy)
            .findTopN(limit);
    }

    @Override
    public List<CandidateJobMatch> findTopNJobsForCandidate(String candidateEmail, int limit,
                                                            SimilarityStrategy strategy) {

        validateFindTopNParams(candidateEmail, limit, strategy);

        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }

        return new TopJobFinder(jobPostings.values(), candidates.get(candidateEmail), strategy)
            .findTopN(limit);
    }

    @Override
    public List<CandidateSimilarityMatch> findSimilarCandidates(String candidateEmail, int limit,
                                                                SimilarityStrategy strategy) {
        validateFindTopNParams(candidateEmail, limit, strategy);
        if (!candidates.containsKey(candidateEmail)) {
            throw new CandidateNotFoundException("The candidate was not found!");
        }

        Candidate target = candidates.get(candidateEmail);
        return new TopSimilarCandidatesFinder(candidates.values(), target, strategy)
            .findTopN(limit);
    }

    @Override
    public List<SkillRecommendation> getSkillRecommendationsForCandidate(String candidateEmail, int limit) {
        return getSkillRecommendationsForCandidate(candidateEmail, limit, DEFAULT_STRATEGY);
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
        if (limit <= 0) {
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

        return new TopSkillRecommendationFinder(recommendationsMap.entrySet()).findTopN(limit);

    }

    private Map<Skill, Double> getSkillRecommendationsAll(String candidateEmail, SimilarityStrategy strategy) {
        Candidate candidate = candidates.get(candidateEmail);
        Map<Skill, Double> recommendations = new HashMap<>();
        Set<Skill> candidateSkills = new HashSet<>(candidate.getSkills());
        List<CandidateJobMatch> candidateJobMatches =
            new CollectionConverterImpl<>(new CandidateJobMatchSameCandidateConverter(candidate, strategy)).convert(
                jobPostings.values());

        Set<Skill> allMissing = getMissingSkills(candidate);
        for (Skill skill : allMissing) {

            double totalImprovement = getTotalImprovement(candidateSkills, candidateJobMatches, skill, strategy);

            if (totalImprovement > 0) {
                recommendations.put(skill, totalImprovement);
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
        if (highestPaidJob == null || HIGHEST_PAID_JOB_COMPARATOR.compare(highestPaidJob, jobPosting) < 0) {
            highestPaidJob = jobPosting;
        }
    }

    private void updateAllRequiredSkills(Set<Skill> skills) {
        for (Skill skill : skills) {
            allRequiredSkills.putIfAbsent(skill.name(), skill.level());
            int prevLevel = allRequiredSkills.get(skill.name());
            allRequiredSkills.put(skill.name(), Math.max(skill.level(), prevLevel));
        }
    }

    private Set<Skill> getMissingSkills(Candidate candidate) {
        Set<Skill> res = new HashSet<>();
        for (Map.Entry<String, Integer> entry : allRequiredSkills.entrySet()) {
            Skill currMaxSkill = new Skill(entry.getKey(), entry.getValue());
            if (!candidate.getSkills().contains(currMaxSkill)) {
                res.add(currMaxSkill);
            }
        }
        return res;
    }

    private double getTotalImprovement(Set<Skill> candidateSkills, Iterable<CandidateJobMatch> candidateJobMatches,
                                       Skill skill, SimilarityStrategy strategy) {
        candidateSkills.add(skill);
        double totalImprovement = 0;
        for (CandidateJobMatch candidateJobMatch : candidateJobMatches) {
            double newSimilarity = strategy.calculateSimilarity(candidateSkills, candidateJobMatch.getJobPosting()
                .getRequiredSkills());
            double improvement = newSimilarity - candidateJobMatch.getSimilarityScore();
            totalImprovement += improvement;
        }
        candidateSkills.remove(skill);
        return totalImprovement;
    }
}