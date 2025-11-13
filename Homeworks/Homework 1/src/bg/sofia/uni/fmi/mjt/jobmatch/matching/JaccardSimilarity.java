package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.HashSet;
import java.util.Set;

public class JaccardSimilarity extends AbstractSimilarityStrategy {
    @Override
    public double calculateSimilarity(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        validateParameters(candidateSkills, jobSkills);
        if (candidateSkills.isEmpty() && jobSkills.isEmpty()) {
            return 0.0;
        }
        Set<Skill> union = new HashSet<>(candidateSkills);
        union.addAll(jobSkills);
        Set<Skill> intersection = new HashSet<>(candidateSkills);
        intersection.retainAll(jobSkills);
        return (double) intersection.size() / union.size();
    }
}
