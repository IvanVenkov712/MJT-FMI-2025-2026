package bg.sofia.uni.fmi.mjt.burnout.subject.builder;

import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Subjects;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.subject.comparator.SubjectComparator;

import java.util.Arrays;

public abstract class AbstractSubjectListBuilder implements SubjectListBuilder {
    public AbstractSubjectListBuilder(SemesterPlan semesterPlan) {
        this.semesterPlan = semesterPlan;
    }

    protected SemesterPlan getSemesterPlan() {
        return semesterPlan;
    }

    protected UniversitySubject[] getSortedSubjects(SubjectComparator comparator) {
        UniversitySubject[] origSubjs = semesterPlan.subjects();
        UniversitySubject[] res = Arrays.copyOf(origSubjs, origSubjs.length);
        Subjects.sort(res, comparator);
        return res;
    }

    private final SemesterPlan semesterPlan;
}
