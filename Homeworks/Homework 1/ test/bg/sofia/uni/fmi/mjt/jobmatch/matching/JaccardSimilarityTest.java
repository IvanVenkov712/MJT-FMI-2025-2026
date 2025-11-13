package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JaccardSimilarityTest {
    @Test
    void testCalculateSimilarityNonEmptyBoth(){
        Set<Skill> candidateSkills = Set.of(
            new Skill("Java", 1), new Skill("Python", 4), new Skill("SQL", 3)
        );
        Set<Skill> jobSkills = Set.of(
            new Skill("Java", 3), new Skill("SQL", 4), new Skill("AWS", 5)
        );
        double similarity = new JaccardSimilarity().calculateSimilarity(candidateSkills, jobSkills);
        assertEquals(0.5, similarity, MathUtils.EPSILON, "0.5 was expected");
    }

    @Test
    void testCalculateSimilarityNonEmptyFirst(){
        Set<Skill> candidateSkills = Set.of(
            new Skill("Java", 1), new Skill("Python", 4), new Skill("SQL", 3)
        );
        Set<Skill> jobSkills = Set.of();
        double similarity = new JaccardSimilarity().calculateSimilarity(candidateSkills, jobSkills);
        assertEquals(0.0, similarity, MathUtils.EPSILON, "0.5 was expected");
    }

    @Test
    void testCalculateSimilarityNonEmptySecond(){
        Set<Skill> candidateSkills = Set.of();
        Set<Skill> jobSkills = Set.of(
            new Skill("Java", 3), new Skill("SQL", 4), new Skill("AWS", 5)
        );
        double similarity = new JaccardSimilarity().calculateSimilarity(candidateSkills, jobSkills);
        assertEquals(0.0, similarity, MathUtils.EPSILON, "0.5 was expected");
    }

    @Test
    void testCalculateSimilarityBothEmpty(){
        Set<Skill> candidateSkills = Set.of();
        Set<Skill> jobSkills = Set.of();
        double similarity = new JaccardSimilarity().calculateSimilarity(candidateSkills, jobSkills);
        assertEquals(0.0, similarity, MathUtils.EPSILON, "0.5 was expected");
    }
}