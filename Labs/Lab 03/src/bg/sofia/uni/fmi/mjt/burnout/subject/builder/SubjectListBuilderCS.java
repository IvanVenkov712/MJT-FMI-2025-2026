package bg.sofia.uni.fmi.mjt.burnout.subject.builder;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.subject.comparator.RatingComparator;

import java.util.Arrays;

public class SubjectListBuilderCS extends AbstractSubjectListBuilder {
    public SubjectListBuilderCS(SemesterPlan semesterPlan) {
        super(semesterPlan);

    }

    @Override
    public UniversitySubject[] build() {
        if (getSemesterPlan().minimalAmountOfCredits() <= 0) {
            return new UniversitySubject[0];
        }
        UniversitySubject[] sortedSubjs = getSortedSubjects(new RatingComparator());
        int totalCredits = 0;
        for (int i = 0; i < sortedSubjs.length; ++i) {
            if (totalCredits >= getSemesterPlan().minimalAmountOfCredits()) {
                return Arrays.copyOfRange(sortedSubjs, 0, i + 1);
            }
            totalCredits += sortedSubjs[i].credits();
        }
        throw new CryToStudentsDepartmentException("Too hard subjects!!");
    }
}
