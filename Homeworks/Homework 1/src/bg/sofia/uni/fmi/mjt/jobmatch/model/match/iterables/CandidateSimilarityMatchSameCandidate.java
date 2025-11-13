package bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables;

import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;

import java.util.Collection;
import java.util.Iterator;

public class CandidateSimilarityMatchSameCandidate implements Iterable<CandidateSimilarityMatch> {
    private final Collection<Candidate> candidates;
    private final Candidate target;
    private final SimilarityStrategy strategy;

    public CandidateSimilarityMatchSameCandidate(Collection<Candidate> candidates, Candidate target,
                                                 SimilarityStrategy strategy) {
        this.candidates = candidates;
        this.target = target;
        this.strategy = strategy;
    }

    @Override
    public Iterator<CandidateSimilarityMatch> iterator() {
        return new InternalIterator(candidates.iterator(), target, strategy);
    }

    private static class InternalIterator implements Iterator<CandidateSimilarityMatch> {

        private final Iterator<Candidate> iterator;
        private final Candidate target;
        private final SimilarityStrategy strategy;

        public InternalIterator(Iterator<Candidate> iterator, Candidate target,
                                SimilarityStrategy strategy) {
            this.iterator = iterator;
            this.target = target;
            this.strategy = strategy;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public CandidateSimilarityMatch next() {
            Candidate similar = iterator.next();
            return new CandidateSimilarityMatch(
                target, similar,
                strategy.calculateSimilarity(target.getSkills(), similar.getSkills())
            );
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This iterator cannot be used for element removal!");
        }
    }
}
