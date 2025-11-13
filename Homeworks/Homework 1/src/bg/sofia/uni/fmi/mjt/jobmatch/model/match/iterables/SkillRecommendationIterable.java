package bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.Iterator;
import java.util.Map;

public class SkillRecommendationIterable implements Iterable<SkillRecommendation> {
    private final Map<Skill, Double> recommendations;

    public SkillRecommendationIterable( Map<Skill, Double> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public Iterator<SkillRecommendation> iterator() {
        return new InternalIterator(recommendations.entrySet().iterator());
    }

    private static class InternalIterator implements Iterator<SkillRecommendation> {

        private final Iterator<Map.Entry<Skill, Double>> iterator;

        public InternalIterator(Iterator<Map.Entry<Skill, Double>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public SkillRecommendation next() {
            Map.Entry<Skill, Double> item = iterator.next();
            return new SkillRecommendation(item.getKey().name(), item.getValue());
        }
    }
}
