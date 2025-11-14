package bg.sofia.uni.fmi.mjt.jobmatch.model.match.views;

import bg.sofia.uni.fmi.mjt.jobmatch.generics.AbstractIterableView;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.views.convertors.CandidateSimilarityMatchSameCandidateConvertor;

import java.util.Collection;

public class CandidateSimilarityMatchSameCandidate extends AbstractIterableView<Candidate, CandidateSimilarityMatch> {

    public CandidateSimilarityMatchSameCandidate(Collection<Candidate> candidates, Candidate target,
                                                 SimilarityStrategy strategy) {
        super(candidates, new CandidateSimilarityMatchSameCandidateConvertor(target, strategy));
    }

}
