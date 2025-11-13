package bg.sofia.uni.fmi.mjt.burnout.subject.comparator;

import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public class CreditsComparator implements SubjectComparator {
    @Override
    public int compare(UniversitySubject s1, UniversitySubject s2) {
        return -Integer.compare(s1.credits(), s2.credits());
    }
}
