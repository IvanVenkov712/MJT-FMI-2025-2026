package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.IterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.generics.TopElementFinderImpl;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByImprovementComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.comparators.SkillRecommendationByNameComparator;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.predicates.SkillRecommendationPredicate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.EntryToSkillRecommendationConvertor;

import java.util.Map;

public class SkillRecommendationIterable extends TopElementFinderImpl<SkillRecommendation> {

    public SkillRecommendationIterable(Iterable<Map.Entry<Skill, Double>> recommendationPairs) {
        super(
            new IterableView<>(recommendationPairs, new EntryToSkillRecommendationConvertor()),
            new SkillRecommendationByImprovementComparator().thenComparing(
                new SkillRecommendationByNameComparator().reversed()
            ), new SkillRecommendationPredicate()
        );
    }
}
