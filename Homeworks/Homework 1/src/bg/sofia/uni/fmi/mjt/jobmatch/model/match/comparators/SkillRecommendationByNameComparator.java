package bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators;

import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.Comparator;

public class SkillRecommendationByNameComparator implements Comparator<SkillRecommendation> {
    @Override
    public int compare(SkillRecommendation o1, SkillRecommendation o2) {
        return o1.skillName().compareTo(o2.skillName());
    }
}
