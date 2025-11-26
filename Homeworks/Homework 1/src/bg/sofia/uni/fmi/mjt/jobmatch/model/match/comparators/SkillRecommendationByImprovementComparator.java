package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.math.MathUtils;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.Comparator;

public class SkillRecommendationByImprovementComparator implements Comparator<SkillRecommendation> {

    @Override
    public int compare(SkillRecommendation o1, SkillRecommendation o2) {
        return MathUtils.compareDouble(o1.improvementScore(), o2.improvementScore());
    }
}
