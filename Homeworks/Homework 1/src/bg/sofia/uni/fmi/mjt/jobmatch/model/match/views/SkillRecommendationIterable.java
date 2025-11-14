package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.AbstractIterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.EntryToSkillRecommendationConvertor;

import java.util.Map;

public class SkillRecommendationIterable extends AbstractIterableView<Map.Entry<Skill, Double>, SkillRecommendation> {

    public SkillRecommendationIterable(Map<Skill, Double> recommendations) {
        super(recommendations.entrySet(), new EntryToSkillRecommendationConvertor());
    }
}
