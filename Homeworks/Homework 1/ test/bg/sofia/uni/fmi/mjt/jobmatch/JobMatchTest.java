package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Education;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
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
}