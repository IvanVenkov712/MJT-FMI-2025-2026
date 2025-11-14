package bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

public class SkillRecommendationPredicate implements MyPredicate<SkillRecommendation> {
    @Override
    public boolean test(SkillRecommendation skillRecommendation) {
        return !MathUtils.equalsDouble(skillRecommendation.improvementScore(), 0);
    }
}
