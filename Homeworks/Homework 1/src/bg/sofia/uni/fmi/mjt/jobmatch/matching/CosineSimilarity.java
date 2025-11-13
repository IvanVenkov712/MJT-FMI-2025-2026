package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathVector;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CosineSimilarity extends AbstractSimilarityStrategy {
    @Override
    public double calculateSimilarity(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        validateParameters(candidateSkills, jobSkills);
        if (candidateSkills.isEmpty() && jobSkills.isEmpty()) {
            return 0;
        }
        Pair<Set<Skill>, Set<Skill>> normalized = normalizeSets(candidateSkills, jobSkills);
        Pair<MathVector, MathVector> vectors = transformToVectors(normalized);
        return vectors.first().getSimilarity(vectors.second());
    }

    private record Pair<T1, T2>(T1 first, T2 second) {
        public Pair {
            if (first == null || second == null) {
                throw new IllegalArgumentException("Null elements are not allowed in the pair!");
            }
        }
    }

    private Pair<Set<Skill>, Set<Skill>> normalizeSets(Set<Skill> candidateSkills, Set<Skill> jobSkills) {
        Set<Skill> candidateSkillsNormal = new TreeSet<>(candidateSkills);
        Set<Skill> jobSkillsNormal = new TreeSet<>(jobSkills);
        for (Skill skill : jobSkills) {
            if (!candidateSkillsNormal.contains(skill)) {
                candidateSkillsNormal.add(new Skill(skill.name(), 0));
            }
        }
        for (Skill skill : candidateSkills) {
            if (!jobSkillsNormal.contains(skill)) {
                jobSkillsNormal.add(new Skill(skill.name(), 0));
            }
        }
        return new Pair<>(candidateSkillsNormal, jobSkillsNormal);
    }

    private List<Double> transformToVector(Set<Skill> skills) {
        List<Double> vector = new ArrayList<>();
        for (Skill skill : skills) {
            vector.add((double) skill.level());
        }
        return vector;
    }

    private Pair<MathVector, MathVector> transformToVectors(Pair<Set<Skill>, Set<Skill>> normalizedSets) {
        return new Pair<>(
            new MathVector(transformToVector(normalizedSets.first())),
            new MathVector(transformToVector(normalizedSets.second()))
        );
    }
}
