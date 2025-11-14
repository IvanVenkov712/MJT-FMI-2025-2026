package bg.sofia.uni.fmi.mjt.jobmatch.model.match.iterables;

import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class CandidateJobMatchSameCandidate implements Iterable<CandidateJobMatch> {
    private final Collection<JobPosting> jobPostings;
    private final Candidate candidate;
    private final SimilarityStrategy strategy;

    public CandidateJobMatchSameCandidate(Collection<JobPosting> jobPostings, Candidate candidate,
                                          SimilarityStrategy strategy) {
        this.jobPostings = jobPostings;
        this.candidate = candidate;
        this.strategy = strategy;
    }

    @Override
    @NotNull
    public Iterator<CandidateJobMatch> iterator() {
        return new InternalIterator(jobPostings.iterator(), candidate, strategy);
    }

    private static class InternalIterator implements Iterator<CandidateJobMatch> {
        private final Iterator<JobPosting> iterator;
        private final Candidate candidate;
        private final SimilarityStrategy strategy;

        public InternalIterator(Iterator<JobPosting> iterator, Candidate candidate,
                                SimilarityStrategy strategy) {
            this.iterator = iterator;
            this.candidate = candidate;
            this.strategy = strategy;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public CandidateJobMatch next() {
            JobPosting posting = iterator.next();
            return new CandidateJobMatch(
                candidate, posting,
                strategy.calculateSimilarity(candidate.getSkills(), posting.getRequiredSkills())
            );
        }
    }
}
