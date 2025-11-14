package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Education;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JobMatchTest {

    @Test
    void testRegisterCandidateNonExisting(){
        JobMatch jobMatch = new JobMatch();
        Candidate candidate = new Candidate(
            "Ivan", "ivan@test.com", Set.of(new Skill("C++", 4)), Education.BACHELORS, 2
        );
        assertEquals(candidate, jobMatch.registerCandidate(candidate), "Registering a new candidate should return the candidate");
    }

    @Test
    void testRegisterCandidateExisting(){
        JobMatch jobMatch = new JobMatch();
        Candidate candidate = new Candidate("Ivan", "ivan@test.com", Set.of(new Skill("Java", 2)), Education.BACHELORS, 2);
        jobMatch.registerCandidate(candidate);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerCandidate(candidate),
            "Registering the same candidate twice should throw UserAlreadyExistsException!");
    }

    @Test
    void testRegisterCandidateSameEmail(){
        JobMatch jobMatch = new JobMatch();
        Candidate candidate1 = new Candidate("Ivan", "ivan@test.com", Set.of(new Skill("Java", 2)), Education.BACHELORS, 2);
        Candidate candidate2 = new Candidate("Gosho", "ivan@test.com", Set.of(new Skill("Python", 3)), Education.PHD, 20);
        jobMatch.registerCandidate(candidate1);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerCandidate(candidate2),
            "Registering a candidate with the same email twice should throw UserAlreadyExistsException!");
    }

    @Test
    void testRegisterEmployerNonExisting() {
        JobMatch jobMatch = new JobMatch();
        Employer employer = new Employer("IBM", "testemployee@test.com");
        assertEquals(employer, jobMatch.registerEmployer(employer),
            "A non-existing employer should be registered!"
        );
    }

    @Test
    void testRegisterEmployerExisting() {
        JobMatch jobMatch = new JobMatch();
        Employer employer = new Employer("IBM", "testemployee@test.com");
        jobMatch.registerEmployer(employer);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerEmployer(employer),
            "Registering an existing employer should throw UserAlreadyExistsException"
        );
    }

    @Test
    void testRegisterEmployerSameEmail() {
        JobMatch jobMatch = new JobMatch();
        Employer employer1 = new Employer("IBM", "testemployee@test.com");
        Employer employer2 = new Employer("Microsoft", "testemployee@test.com");

        jobMatch.registerEmployer(employer1);
        assertThrows(UserAlreadyExistsException.class, () -> jobMatch.registerEmployer(employer2),
            "Registering an employer with the same email should throw UserAlreadyExistsException"
        );
    }

    @Test
    void testPostJobPostingNonExistingEmployer() {
        JobMatch jobMatch = new JobMatch();
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
        JobMatch jobMatch = new JobMatch();
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
}