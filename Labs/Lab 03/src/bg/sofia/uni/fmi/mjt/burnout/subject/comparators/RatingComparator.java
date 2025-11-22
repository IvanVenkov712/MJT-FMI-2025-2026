package bg.sofia.uni.fmi.mjt.burnout.subject.comparators;

import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public class RatingComparator implements SubjectComparator {
    @Override
    public int compare(UniversitySubject s1, UniversitySubject s2) {
        return -Integer.compare(s1.rating(), s2.rating());
    }
}
