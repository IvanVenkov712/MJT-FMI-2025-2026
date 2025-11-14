package bg.sofia.uni.fmi.mjt.jobmatch.model.match.verifiers;

import bg.sofia.uni.fmi.mjt.jobmatch.ItemVerifier;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

public class SkillRecommendationVerifier implements ItemVerifier<SkillRecommendation> {
    @Override
    public boolean isValid(SkillRecommendation skillRecommendation) {
        return !MathUtils.equalsDouble(skillRecommendation.improvementScore(), 0);
    }
}
