package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableViewImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByImprovementComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.SkillRecommendationPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.EntryToSkillRecommendationConvertor;

import java.util.Map;

public class SkillRecommendationIterable extends IterableViewImpl<Map.Entry<Skill, Double>, SkillRecommendation> {

    public SkillRecommendationIterable(Iterable<Map.Entry<Skill, Double>> recommendationPairs) {
        super(recommendationPairs, new EntryToSkillRecommendationConvertor(),
            new SkillRecommendationByImprovementComparator().thenComparing(
                new SkillRecommendationByNameComparator().reversed()
            ), new SkillRecommendationPredicate()
        );
    }
}
