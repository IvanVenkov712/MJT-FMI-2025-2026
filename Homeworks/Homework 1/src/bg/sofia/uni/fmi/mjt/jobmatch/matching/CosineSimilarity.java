package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.Set;

public class CosineSimilarity implements SimilarityStrategy {
    @Override
    public double calculateSimilarity(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        return 0;
    }
}
