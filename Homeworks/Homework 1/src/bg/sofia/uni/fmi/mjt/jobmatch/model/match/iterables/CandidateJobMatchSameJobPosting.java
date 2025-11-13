package bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables;

import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;

import java.util.Collection;
import java.util.Iterator;

public class CandidateJobMatchSameJobPosting implements Iterable<CandidateJobMatch> {
    private final Collection<Candidate> candidates;
    private final JobPosting posting;
    private final SimilarityStrategy strategy;

    public CandidateJobMatchSameJobPosting(Collection<Candidate> candidates, JobPosting posting,
                                           SimilarityStrategy strategy) {
        this.candidates = candidates;
        this.posting = posting;
        this.strategy = strategy;
    }

    @Override
    public Iterator<CandidateJobMatch> iterator() {
        return new InternalIterator(candidates.iterator(), posting, strategy);
    }

    private static class InternalIterator implements Iterator<CandidateJobMatch> {
        private final Iterator<Candidate> iterator;
        JobPosting jobPosting;
        SimilarityStrategy strategy;

        public InternalIterator(Iterator<Candidate> iterator, JobPosting jobPosting,
                                SimilarityStrategy strategy) {
            this.iterator = iterator;
            this.jobPosting = jobPosting;
            this.strategy = strategy;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public CandidateJobMatch next() {
            Candidate candidate = iterator.next();
            return new CandidateJobMatch(
                candidate, jobPosting,
                strategy.calculateSimilarity(candidate.getSkills(), jobPosting.getRequiredSkills())
            );
        }
    }
}

