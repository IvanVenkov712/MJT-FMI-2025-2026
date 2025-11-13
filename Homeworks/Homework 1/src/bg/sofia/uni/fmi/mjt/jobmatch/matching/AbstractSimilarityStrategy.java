package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.Set;

public abstract class AbstractSimilarityStrategy implements SimilarityStrategy {
    protected void validateParameters(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        if (candidateSkills == null) {
            throw new IllegalArgumentException("candidateSkills was null!");
        }
        if (jobSkills == null) {
            throw new IllegalArgumentException("jobSkills was null!");
        }
    }
}
