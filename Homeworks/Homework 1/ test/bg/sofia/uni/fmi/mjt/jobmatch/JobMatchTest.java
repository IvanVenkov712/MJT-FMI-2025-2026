package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.CandidateNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Education;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JobMatchTest {

    private JobMatch jobMatch;

    @BeforeEach
    void initJobMatch() {
        jobMatch = new JobMatch();
    }

    @Test
    void testRegisterCandidateNonExisting(){
        Candidate candidate = new Candidate(
            "Ivan", "ivan@test.com", Set.of(new Skill("C++", 4)), Education.BACHELORS, 2
        );
        assertEquals(candidate, jobMatch.registerCandidate(candidate), "Registering a new candidate should return the candidate");
    }

    @Test
    void testRegisterCandidateExisting(){
        Candidate candidate = new Candidate("Ivan", "ivan@test.com", Set.of(new Skill("Java", 2)), Education.BACHELORS, 2);
        jobMatch.registerCandidate(candidate);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerCandidate(candidate),
            "Registering the same candidate twice should throw UserAlreadyExistsException!");
    }

    @Test
    void testRegisterCandidateSameEmail(){
        Candidate candidate1 = new Candidate("Ivan", "ivan@test.com", Set.of(new Skill("Java", 2)), Education.BACHELORS, 2);
        Candidate candidate2 = new Candidate("Gosho", "ivan@test.com", Set.of(new Skill("Python", 3)), Education.PHD, 20);
        jobMatch.registerCandidate(candidate1);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerCandidate(candidate2),
            "Registering a candidate with the same email twice should throw UserAlreadyExistsException!");
    }

    @Test
    void testRegisterEmployerNonExisting() {
        Employer employer = new Employer("IBM", "testemployee@test.com");
        assertEquals(employer, jobMatch.registerEmployer(employer),
            "A non-existing employer should be registered!"
        );
    }

    @Test
    void testRegisterEmployerExisting() {
        Employer employer = new Employer("IBM", "testemployee@test.com");
        jobMatch.registerEmployer(employer);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerEmployer(employer),
            "Registering an existing employer should throw UserAlreadyExistsException"
        );
    }

    @Test
    void testRegisterEmployerSameEmail() {
        Employer employer1 = new Employer("IBM", "testemployee@test.com");
        Employer employer2 = new Employer("Microsoft", "testemployee@test.com");

        jobMatch.registerEmployer(employer1);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerEmployer(employer2),
            "Registering an employer with the same email should throw UserAlreadyExistsException"
        );
    }

    @Test
    void testPostJobPostingNonExistingEmployer() {
        JobPosting posting = new JobPosting(
            "post1", "Senior Java Developer", "test@test.com",
            Set.of(new Skill("Java", 5)), Education.BACHELORS, 10, 9800
        );
        assertThrows(UserNotFoundException.class, () -> jobMatch.postJobPosting(posting),
            "Posting a jpb with not existing employer should throw UserNotFoundException"
        );
    }

    @Test
    void testPostJobPostingExistingEmployer() {
        Employer employer = new Employer("SAP", "test@test.com");
        jobMatch.registerEmployer(employer);

        JobPosting posting = new JobPosting(
            "post1", "Senior Java Developer", "test@test.com",
            Set.of(new Skill("Java", 5)), Education.BACHELORS, 10, 9800
        );

        assertEquals(posting, jobMatch.postJobPosting(posting),
            "Existing employer should be able to post a job posting."
        );
    }

    @Test
    void testGetSkillRecommendationsForCandidateNonExistingCandidate() {
        assertThrows(
            CandidateNotFoundException.class, () -> jobMatch.getSkillRecommendationsForCandidate("koko2test.com", 2),
            "recommendations for non-existing candidate should throw CandidateNotFoundException"
        );
    }

    @Test
    void testGetSkillRecommendationsForCandidateZeroLimit() {
        Candidate candidate = new Candidate(
            "Ivan", "ivan@test.com", Set.of(new Skill("C++", 4)), Education.BACHELORS, 2
        );
        jobMatch.registerCandidate(candidate);
        assertIterableEquals(List.of(), jobMatch.getSkillRecommendationsForCandidate(candidate.getEmail(), 0),
            "Zero limit should return empty collection"
        );
    }

    @Test
    void testGetSkillRecommendationsForCandidateActualData() {
        Employer employer = new Employer("SAP", "sap@gmail.com");
        jobMatch.registerEmployer(employer);

        Candidate candidate = new Candidate(
            "ivan", "ivan@gmail.com",
            Set.of(new Skill("Java", 4), new Skill("Python", 3)),
            Education.BACHELORS, 2
        );
        jobMatch.registerCandidate(candidate);
        jobMatch.postJobPosting(
            new JobPosting(
                "idPosting1", "Senior", "sap@gmail.com",
                Set.of(new Skill("Java", 5), new Skill("Python", 4), new Skill("AWS", 3)),
                Education.BACHELORS, 2, 6000
            )
        );

        jobMatch.postJobPosting(
            new JobPosting(
                "idPosting2", "Senior", "sap@gmail.com",
                Set.of(new Skill("Java", 4), new Skill("AWS", 4), new Skill("Docker", 3)),
                Education.BACHELORS, 2, 6000
            )
        );

        jobMatch.postJobPosting(
            new JobPosting(
                "idPosting3", "Senior", "sap@gmail.com",
                Set.of(new Skill("Python", 5), new Skill("Docker", 2)),
                Education.BACHELORS, 2, 6000
            )
        );

        assertIterableEquals(
            List.of(
                new SkillRecommendation("AWS", (0.25)), new SkillRecommendation("Docker", 0.20)),
                jobMatch.getSkillRecommendationsForCandidate(candidate.getEmail(), 2),
                "Expected result: [AWS (0.25), Docker (0.20)]"
        );
    }
}