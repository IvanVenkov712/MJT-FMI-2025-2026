package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.ElementConverter;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.Map;

public class EntryToSkillRecommendationConvertor implements
    ElementConverter<Map.Entry<Skill, Double>, SkillRecommendation> {

    @Override
    public SkillRecommendation convert(Map.Entry<Skill, Double> arg) {
        return new SkillRecommendation(arg.getKey().name(), arg.getValue());
    }
}
