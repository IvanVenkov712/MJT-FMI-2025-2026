package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import org.junit.jupiter.api.Test;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

class CosineSimilarityTest {

    private static final double DELTA = 1e-6;

    private static final Set<Skill> EMPTY_SET = Set.of();
    private static final Set<Skill> SKILLS_1 =
        Set.of(new Skill("Java", 4), new Skill("Python", 3), new Skill("SQL", 2));
    private static final Set<Skill> SKILLS_2 =
        Set.of(new Skill("Java", 5), new Skill("Python", 2), new Skill("Javascript", 3));
    private static final Set<Skill> SKILLS_3 =
        Set.of(new Skill("Java", 5), new Skill("Golang", 3), new Skill("Bash", 2), new Skill("Docker", 3));
    private static final Set<Skill> SKILLS_4 =
        Set.of(new Skill("C#", 5), new Skill("C++", 4), new Skill("Typescript", 1));

    @Test
    void testCalculateSimilarityNullSet() {
        assertThrows(
            IllegalArgumentException.class, () -> new CosineSimilarity().calculateSimilarity(null, null),
            "Skill sets are null");
    }

    @Test
    void testCalculateSimilarityEmptySets() {
        assertEquals(
            0, new CosineSimilarity().calculateSimilarity(EMPTY_SET, EMPTY_SET), DELTA,
            "Skill sets are empty");
    }

    @Test
    void testCalculateSimilaritySameSizeSetsWithOverlappingSkills() {
        assertEquals(
            0.783217, new CosineSimilarity().calculateSimilarity(SKILLS_1, SKILLS_2), DELTA,
            "Skill sets have similarity rate of 0.783217");
    }

    @Test
    void testCalculateSimilarityDifferentSizeSetsWithOverlappingSkills() {
        assertEquals(
            0.541729, new CosineSimilarity().calculateSimilarity(SKILLS_1, SKILLS_3), DELTA,
            "Skill sets have similarity rate of 0.541729");
    }

    @Test
    void testCalculateSimilaritySameSizeSetsWithNoOverlappingSkills() {
        assertEquals(
            0, new CosineSimilarity().calculateSimilarity(SKILLS_2, SKILLS_4), DELTA,
            "Skill sets with the same size and non-overlapping skills have similarity rate of 0");
    }

    @Test
    void testCalculateSimilarityDifferentSizeSetsWithNoOverlappingSkills() {
        assertEquals(
            0, new CosineSimilarity().calculateSimilarity(SKILLS_3, SKILLS_4), DELTA,
            "Skill sets with different sizes and non-overlapping skills have similarity rate of 0");
    }

    @Test
    void testCalculateSimilarityFilledAndEmptySet() {
        assertEquals(
            0, new CosineSimilarity().calculateSimilarity(EMPTY_SET, SKILLS_4), DELTA,
            "A filled skill set and an empty one have similarity rate of 0");
    }

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