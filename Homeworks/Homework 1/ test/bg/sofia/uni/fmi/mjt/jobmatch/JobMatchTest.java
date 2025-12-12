package bg.sofia.uni.fmi.mjt.jobmatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.CandidateNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.JobPostingNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.CosineSimilarity;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.JaccardSimilarity;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Education;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

@ExtendWith(MockitoExtension.class)
class JobMatchTest {

    @Mock
    private Map<String, Candidate> candidatesMock;

    @Mock
    private Map<String, Employer> employersMock;

    @Mock
    private Map<String, JobPosting> jobPostingsMock;

    private JobMatch jobMatch; // = new JobMatch(candidatesMock, employersMock, jobPostingsMock);

    private static Candidate candidate;
    private static Employer employer;
    private static List<JobPosting> jobPostingsList1;
    private static List<JobPosting> jobPostingsList2;
    private static List<Candidate> candidatesList1;
    private static List<Candidate> candidatesList2;

    @BeforeAll
    static void setUp() {
        Set<Skill> candidateSkills = Set.of(new Skill("Java", 4), new Skill("Python", 3));
        candidate = new Candidate("Johnny Test", "johnny@test.com", candidateSkills, Education.BACHELORS, 3);
        employer = new Employer("Employer", "employer@email.com");
        jobPostingsList1 = setUpJobPostingsList1();
        jobPostingsList2 = setUpJobPostingsList2();
        candidatesList1 = setUpCandidatesList1();
        candidatesList2 = setUpCandidatesList2();
    }

    static List<Candidate> setUpCandidatesList1() {
        Set<Skill> skills1 =
            Set.of(new Skill("Java", 4), new Skill("Git", 3), new Skill("REST APIs", 2), new Skill("English", 5));
        Candidate c1 = new Candidate("Finn Mertens", "finn@ooo.com", skills1, Education.BACHELORS, 3);

        Set<Skill> skills2 = Set.of(new Skill("Docker", 3), new Skill("Linux", 4));
        Candidate c2 = new Candidate("Jake the Dog", "jake@ooo.com", skills2, Education.HIGH_SCHOOL, 5);

        Set<Skill> skills3 =
            Set.of(
                new Skill("SQL", 5), new Skill("Data Analysis", 4), new Skill("Machine Learning", 3),
                new Skill("Python", 4), new Skill("English", 5));
        Candidate c3 = new Candidate("Princess Bubblegum", "pb@candykingdom.com", skills3, Education.PHD, 8);

        Set<Skill> skills4 =
            Set.of(
                new Skill("Java", 2), new Skill("Cloud Computing", 4), new Skill("Cybersecurity", 3),
                new Skill("DevOps", 2));
        Candidate c4 = new Candidate("Marceline Abadeer", "marcy@nightosphere.com", skills4, Education.BACHELORS, 4);

        Set<Skill> skills5 = Set.of(
            new Skill("C++", 4), new Skill("Operating Systems", 5), new Skill("Scripting", 3),
            new Skill("SQL", 2));
        Candidate c5 = new Candidate("BMO", "bmo@mo.system", skills5, Education.MASTERS, 2);

        Set<Skill> skills6 = Set.of(new Skill("C++", 5), new Skill("SQL", 5), new Skill("PHP", 3));
        Candidate c6 = new Candidate("Ice King", "ice.king@icekingdom.com", skills6, Education.BACHELORS, 7);

        return List.of(c1, c2, c3, c4, c5, c6);
    }

    static List<Candidate> setUpCandidatesList2() {
        Set<Skill> skills1 =
            Set.of(new Skill("Java", 4), new Skill("Spring Boot", 3), new Skill("SQL", 2), new Skill("English", 5));
        Candidate r1 = new Candidate("Mordecai Bluejay", "mordecai@parksdept.com", skills1, Education.BACHELORS, 4);

        Set<Skill> skills2 =
            Set.of(new Skill("Javascript", 5), new Skill("Node.js", 3), new Skill("MongoDB", 2), new Skill("Git", 4));
        Candidate r2 = new Candidate("Rigby Raccoon", "rigby@parksdept.com", skills2, Education.HIGH_SCHOOL, 3);

        Set<Skill> skills3 = Set.of(
            new Skill("Project Management", 5), new Skill("AWS", 4), new Skill("Javascript", 3),
            new Skill("Bash", 2));
        Candidate r3 = new Candidate("Benson", "benson@parksdept.com", skills3, Education.MASTERS, 6);

        return List.of(r1, r2, r3);
    }

    static List<JobPosting> setUpJobPostingsList1() {
        Set<Skill> skills1 =
            Set.of(new Skill("Java", 4), new Skill("Python", 3), new Skill("SQL", 2), new Skill("English", 5));
        JobPosting jobPosting1 =
            new JobPosting("1", "Backend Developer", "employer@email.com", skills1, Education.BACHELORS, 4, 3500);

        Set<Skill> skills2 = Set.of(new Skill("Javascript", 5), new Skill("Node.js", 3), new Skill("Git", 4));
        JobPosting jobPosting2 =
            new JobPosting("2", "Frontend Developer", "employer@email.com", skills2, Education.HIGH_SCHOOL, 3, 3750);

        Set<Skill> skills3 =
            Set.of(
                new Skill("Probability Theory", 4), new Skill("Maths", 5), new Skill("MariaDB", 2),
                new Skill("Databases", 3));
        JobPosting jobPosting3 =
            new JobPosting("3", "Data Scientist", "employer@email.com", skills3, Education.MASTERS, 4, 4500);

        return List.of(jobPosting1, jobPosting2, jobPosting3);
    }

    static List<JobPosting> setUpJobPostingsList2() {
        Set<Skill> job1Skills =
            Set.of(new Skill("Java", 5), new Skill("Python", 4), new Skill("AWS", 3));
        JobPosting jobPosting1 =
            new JobPosting(
                "job1", "Software Engineer I", "employer1@email.com", job1Skills, Education.BACHELORS, 3,
                4000);

        Set<Skill> job2Skills = Set.of(new Skill("Java", 4), new Skill("AWS", 4), new Skill("Docker", 3));
        JobPosting jobPosting2 =
            new JobPosting(
                "job2", "Software Engineer II", "employer2@email.com", job2Skills, Education.BACHELORS, 4,
                4500);

        return List.of(jobPosting1, jobPosting2);
    }

    @BeforeEach
    void setUpTest() {
        jobMatch = new JobMatch(candidatesMock, employersMock, jobPostingsMock);
    }

    @Test
    void testRegisterCandidateNullCandidate() {
        assertThrows(IllegalArgumentException.class, () -> jobMatch.registerCandidate(null), "Candidate is null");
    }

    @Test
    void testRegisterCandidateExistingCandidate() {
        Candidate candidate = candidatesList2.get(1);
        when(candidatesMock.containsKey(candidate.getEmail())).thenReturn(true);

        assertThrows(
            UserAlreadyExistsException.class, () -> jobMatch.registerCandidate(candidate), "Candidate already exists");
    }

    @Test
    void testRegisterEmployerNullEmployer() {
        assertThrows(IllegalArgumentException.class, () -> jobMatch.registerEmployer(null), "Employer null");
    }

    @Test
    void testRegisterEmployerExistingEmployer() {
        when(employersMock.containsKey(employer.email())).thenReturn(true);
        assertThrows(
            UserAlreadyExistsException.class, () -> jobMatch.registerEmployer(employer), "Employer already exists"
        );

        verify(employersMock).containsKey(employer.email());
    }

    @Test
    void postJobPostingNullJobPosting() {
        assertThrows(IllegalArgumentException.class, () -> jobMatch.postJobPosting(null), "Job posting is null");
    }

    @Test
    void postJobPostingNotRegisteredEmployer() {
        when(employersMock.containsKey(jobPostingsList1.get(0).getEmployerEmail())).thenReturn(false);

        assertThrows(
            UserNotFoundException.class, () -> jobMatch.postJobPosting(jobPostingsList1.get(0)),
            "The employer publishing the job posting is not registered");
    }

    @Test
    void testFindTopNCandidatesForJobNullJobPostingId() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNCandidatesForJob(null, 10, new CosineSimilarity()),
            "Job posting ID is null");
    }

    @Test
    void testFindTopNCandidatesForJobBlankJobPostingId() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNCandidatesForJob(" ", 10, new CosineSimilarity()),
            "Job posting ID is null");
    }

    @Test
    void testFindTopNCandidatesForJobNonPositiveLimit() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNCandidatesForJob("1", 0, new JaccardSimilarity()),
            "Limit is non-positive");
    }

    @Test
    void testFindTopNCandidatesForJobNullStrategy() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNCandidatesForJob("1", 4, null),
            "Strategy is null");
    }

    @Test
    void testFindTopNCandidatesForJobInvalidJobPostingID() {
        when(jobPostingsMock.containsKey("1")).thenReturn(false);

        assertThrows(
            JobPostingNotFoundException.class, () -> jobMatch.findTopNCandidatesForJob("1", 10, new CosineSimilarity()),
            "No job posting with this ID exists");
    }

    @Test
    void testFindTopNCandidatesForJobLimitSmallerThanCandidatesCount() {
        when(jobPostingsMock.containsKey("1")).thenReturn(true);
        when(jobPostingsMock.get("1")).thenReturn(jobPostingsList1.get(0));
        when(candidatesMock.values()).thenReturn(candidatesList1);

        List<CandidateJobMatch> expected = List.of(
            new CandidateJobMatch(candidatesList1.get(2), jobPostingsList1.get(0), 1.00 / 2.00),
            new CandidateJobMatch(candidatesList1.get(0), jobPostingsList1.get(0), 1.00 / 3.00),
            new CandidateJobMatch(candidatesList1.get(5), jobPostingsList1.get(0), 1.00 / 6.00),
            new CandidateJobMatch(candidatesList1.get(4), jobPostingsList1.get(0), 1.00 / 7.00));

        List<CandidateJobMatch> result = jobMatch.findTopNCandidatesForJob("1", 4, new JaccardSimilarity());

        assertIterableEquals(expected, result, "The returned candidates don't match the expected candidates");
    }

    @Test
    void testFindTopNCandidatesForJobLimitGreaterOrEqualToCandidatesCount() {
        when(jobPostingsMock.containsKey("2")).thenReturn(true);
        when(jobPostingsMock.get("2")).thenReturn(jobPostingsList1.get(1));
        when(candidatesMock.values()).thenReturn(candidatesList2);

        List<CandidateJobMatch> expected =
            List.of(
                new CandidateJobMatch(candidatesList2.get(1), jobPostingsList1.get(1), 0.9622504486493763),
                new CandidateJobMatch(candidatesList2.get(2), jobPostingsList1.get(1), 0.28867513459481287));

        List<CandidateJobMatch> result = jobMatch.findTopNCandidatesForJob("2", 150, new CosineSimilarity());

        assertIterableEquals(expected, result, "The returned candidates don't match the expected candidates");
    }

    @Test
    void testFindTopNCandidatesForJobEmptyCandidatesList() {
        when(jobPostingsMock.containsKey("1")).thenReturn(true);
        when(jobPostingsMock.get("1")).thenReturn(jobPostingsList1.get(0));
        when(candidatesMock.values()).thenReturn(new ArrayList<>());

        List<CandidateJobMatch> result = jobMatch.findTopNCandidatesForJob("1", 100, new CosineSimilarity());

        assertTrue(result.isEmpty(), "No candidates are registered");
    }

    @Test
    void testFindTopNCandidatesForJobAllSimilaritiesZero() {
        when(jobPostingsMock.containsKey("3")).thenReturn(true);
        when(jobPostingsMock.get("3")).thenReturn(jobPostingsList1.get(2));
        when(candidatesMock.values()).thenReturn(candidatesList1);

        List<CandidateJobMatch> expected = List.of();
        List<CandidateJobMatch> result = jobMatch.findTopNCandidatesForJob("3", 30, new JaccardSimilarity());

        assertTrue(result.isEmpty(), "No candidate applies to the required skills");
    }

    @Test
    void testFindTopNJobsForCandidateNullEmail() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNJobsForCandidate(null, 10, new CosineSimilarity()),
            "Candidate email is null");
    }

    @Test
    void testFindTopNJobsForCandidateBlankEmail() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNJobsForCandidate(" ", 10, new CosineSimilarity()),
            "Candidate email is null");
    }

    @Test
    void testFindTopNJobsForCandidateNonPositiveLimit() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.findTopNJobsForCandidate("email@email.com", 0, new CosineSimilarity()),
            "Limit is non-positive");
    }

    @Test
    void testFindTopNJobsForCandidateNullStrategy() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.findTopNJobsForCandidate("email@email.com", 10, null),
            "Limit is non-positive");
    }

    @Test
    void testFindTopNJobsForCandidateInvalidEmail() {
        when(candidatesMock.containsKey("email@email.com")).thenReturn(false);

        assertThrows(
            CandidateNotFoundException.class,
            () -> jobMatch.findTopNJobsForCandidate("email@email.com", 10, new CosineSimilarity()),
            "No candidate with this email exists");
    }

    @Test
    void testFindTopNJobsForCandidateLimitSmallerThanJobPostingsCount() {
        Candidate candidate = candidatesList1.get(2);
        String candidateEmail = candidate.getEmail();

        when(candidatesMock.containsKey(candidateEmail)).thenReturn(true);
        when(candidatesMock.get(candidateEmail)).thenReturn(candidate);
        when(jobPostingsMock.values()).thenReturn(jobPostingsList1);

        List<CandidateJobMatch> expected =
            List.of(new CandidateJobMatch(candidate, jobPostingsList1.get(0), 0.6704714382670969));
        List<CandidateJobMatch> result = jobMatch.findTopNJobsForCandidate(candidateEmail, 3, new CosineSimilarity());

        assertIterableEquals(expected, result, "The returned candidates don't match the expected candidates");
    }

    @Test
    void testFindTopNJobsForCandidateNoJobPostings() {
        Candidate candidate = candidatesList2.get(0);
        String candidateEmail = candidate.getEmail();

        when(candidatesMock.containsKey(candidateEmail)).thenReturn(true);
        when(candidatesMock.get(candidateEmail)).thenReturn(candidate);
        when(jobPostingsMock.values()).thenReturn(new ArrayList<>());

        List<CandidateJobMatch> result =
            jobMatch.findTopNJobsForCandidate(candidateEmail, 100, new JaccardSimilarity());

        assertTrue(result.isEmpty(), "No job postings are registered");
    }

    @Test
    void testFindSimilarCandidatesNullEmail() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.findSimilarCandidates(null, 5, new JaccardSimilarity()),
            "Candidate email is null"
        );
    }

    @Test
    void testFindSimilarCandidatesBlankEmail() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.findSimilarCandidates(" ", 5, new CosineSimilarity()),
            "Candidate email is blank"
        );
    }

    @Test
    void testFindSimilarCandidatesNonPositiveLimit() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.findSimilarCandidates("candidate@gmail.com", 0, new JaccardSimilarity()),
            "Limit is non-positive"
        );
    }

    @Test
    void testFindSimilarCandidatesNullStrategy() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.findSimilarCandidates("candidate@gmail.com", 5, null),
            "Strategy is null"
        );
    }

    @Test
    void testFindSimilarCandidatesCandidateDoesNotExist() {
        when(candidatesMock.containsKey("candidate@gmail.com")).thenReturn(false);

        assertThrows(
            CandidateNotFoundException.class,
            () -> jobMatch.findSimilarCandidates("candidate@gmail.com", 5, new CosineSimilarity()),
            "No candidate with this email exists"
        );
    }

    @Test
    void testFindSimilarCandidatesEmptyCandidateList() {
        Candidate currentCandidate = candidatesList1.get(0);
        when(candidatesMock.containsKey(currentCandidate.getEmail())).thenReturn(true);
        when(candidatesMock.get(currentCandidate.getEmail())).thenReturn(currentCandidate);
        when(candidatesMock.values()).thenReturn(List.of(currentCandidate));

        List<CandidateSimilarityMatch> result =
            jobMatch.findSimilarCandidates(currentCandidate.getEmail(), 10, new CosineSimilarity());

        assertTrue(result.isEmpty(), "No other candidates exist");
    }

    @Test
    void testFindSimilarCandidatesAllSimilaritiesZero() {
        Candidate target = candidatesList1.get(1);
        when(candidatesMock.containsKey(target.getEmail())).thenReturn(true);
        when(candidatesMock.get(target.getEmail())).thenReturn(target);
        when(candidatesMock.values()).thenReturn(candidatesList2);

        List<CandidateSimilarityMatch> result =
            jobMatch.findSimilarCandidates(target.getEmail(), 10, new JaccardSimilarity());

        assertTrue(result.isEmpty(), "No similar candidates should be returned if all similarities are zero");
    }

    @Test
    void testFindSimilarCandidates() {
        Candidate target = candidatesList1.get(2);
        String email = target.getEmail();

        when(candidatesMock.containsKey(email)).thenReturn(true);
        when(candidatesMock.get(email)).thenReturn(target);
        when(candidatesMock.values()).thenReturn(candidatesList1);

        List<CandidateSimilarityMatch> expected = List.of(
            new CandidateSimilarityMatch(target, candidatesList1.get(5), 0.14285714285714285),
            new CandidateSimilarityMatch(target, candidatesList1.get(4), 0.125)
        );

        List<CandidateSimilarityMatch> result =
            jobMatch.findSimilarCandidates(email, 2, new JaccardSimilarity());

        assertIterableEquals(expected, result, "The returned candidates don't match the expected candidates");
    }

    @Test
    void testGetSkillRecommendationsForCandidateNullEmail() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.getSkillRecommendationsForCandidate(null, 10),
            "Email is null");
    }

    @Test
    void testGetSkillRecommendationsForCandidateBlankEmail() {
        assertThrows(
            IllegalArgumentException.class, () -> jobMatch.getSkillRecommendationsForCandidate(" ", 10),
            "Email is blank");
    }

    @Test
    void testGetSkillRecommendationsForCandidateLimitNonPositive() {
        assertThrows(
            IllegalArgumentException.class,
            () -> jobMatch.getSkillRecommendationsForCandidate("candidate@email.com", 0),
            "Limit is non-positive");
    }

    @Test
    void testGetSkillRecommendationsForCandidateInvalidEmail() {
        when(candidatesMock.containsKey("email@email.com")).thenReturn(false);

        assertThrows(
            CandidateNotFoundException.class,
            () -> jobMatch.getSkillRecommendationsForCandidate("email@email.com", 10),
            "No candidate with this email exists");
    }

    @Test
    void testGetSkillRecommendationsForCandidate() {
        when(candidatesMock.containsKey(candidate.getEmail())).thenReturn(true);
        when(candidatesMock.get(candidate.getEmail())).thenReturn(candidate);
        when(jobPostingsMock.values()).thenReturn(jobPostingsList2);
        jobMatch = new JobMatch(candidatesMock, employersMock, jobPostingsMock);

        List<SkillRecommendation> expected = List.of(
            new SkillRecommendation("AWS", 0.3474325163743781),
            new SkillRecommendation("Docker", 0.04085076216092043)
        );

        List<SkillRecommendation> result = jobMatch.getSkillRecommendationsForCandidate(candidate.getEmail(), 10);

        assertIterableEquals(expected, result, "The returned recommendations don't match the expected recommendations");
    }
}