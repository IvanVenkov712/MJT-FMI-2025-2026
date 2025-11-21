package bg.sofia.uni.fmi.mjt.jobmatch.model.match.filters;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.Filter;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

public class SkillRecommendationFilter implements Filter<SkillRecommendation> {
    @Override
    public boolean matches(SkillRecommendation skillRecommendation) {
        return !MathUtils.equalsDouble(skillRecommendation.improvementScore(), 0);
    }
}
