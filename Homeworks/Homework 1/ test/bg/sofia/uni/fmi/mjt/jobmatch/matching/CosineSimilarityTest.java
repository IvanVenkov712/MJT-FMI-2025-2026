package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CosineSimilarityTest {
    @Test
    void testCalculateSimilarityEmpty(){
        assertEquals(0, new CosineSimilarity().calculateSimilarity(Set.of(), Set.of()), MathUtils.EPSILON, "calculateSimilarity should return 0 on empty sets!");
    }

    @Test
    void testCalculateSimilarityZeroVectorFirst(){
        double similarity = new CosineSimilarity().calculateSimilarity(Set.of(new Skill("name", 0)), Set.of(new Skill("name", 2)));
        assertEquals(0, similarity, MathUtils.EPSILON, "When one of the vectors is zero, calculateSimilarity should return 0");
    }

    @Test
    void testCalculateSimilarityNonZeroInput(){
        Set<Skill> candidateSkills = Set.of(
            new Skill("Java", 4), new Skill("Python", 3), new Skill("SQL", 2)
        );
        Set<Skill> jobSkills = Set.of(
            new Skill("Java", 5), new Skill("Python", 2), new Skill("JavaScript", 3)
        );
        double similarity = new CosineSimilarity().calculateSimilarity(candidateSkills, jobSkills);
        assertEquals(0.783, similarity, 0.0005, "Around 0.783 was expected!");
    }
}