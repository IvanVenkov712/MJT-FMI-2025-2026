package bg.sofia.uni.fmi.mjt.jobmatch.model.match;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;
import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validateImprovementScore;

public record SkillRecommendation(String skillName, double improvementScore) {
    public SkillRecommendation {
        validate(skillName, "skillName");
        validateImprovementScore(improvementScore);
    }
}
