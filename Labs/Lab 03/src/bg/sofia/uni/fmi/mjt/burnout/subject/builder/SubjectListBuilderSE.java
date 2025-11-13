package bg.sofia.uni.fmi.mjt.burnout.subject.builder;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.subject.comparator.CreditsComparator;

public class SubjectListBuilderSE extends AbstractSubjectListBuilder {
    public SubjectListBuilderSE(SemesterPlan semesterPlan) {
        super(semesterPlan);

        sortedSubjs = getSortedSubjects(new CreditsComparator());
        included = new boolean[sortedSubjs.length];
        reqs = semesterPlan.subjectRequirements();
    }

    @Override
    public UniversitySubject[] build() {

        fillForReqs();
        fillForCredits();
        return buildByIncluded();
    }

    private void includeSubject(int i) {

        if (!included[i]) {
            included[i] = true;
            totalCredits += sortedSubjs[i].credits();
            ++includedCount;
        }
    }

    private void fillForReqs() {

        int[] countReq = new int[reqs.length];

        for (int i = 0; i < sortedSubjs.length; ++i) {

            for (int j = 0; j < reqs.length; ++j) {

                if (sortedSubjs[i].category() == reqs[j].category() && countReq[j] < reqs[j].minAmountEnrolled()) {

                    includeSubject(i);
                    ++countReq[j];
                    break;
                }
            }
        }

        for (int i = 0; i < reqs.length; ++i) {
            if (countReq[i] < reqs[i].minAmountEnrolled()) {
                throw new CryToStudentsDepartmentException("Not good subjects");
            }
        }
    }

    private void fillForCredits() {
        for (int i = 0; i < sortedSubjs.length; ++i) {
            if (totalCredits >= getSemesterPlan().minimalAmountOfCredits()) {
                break;
            }
            if (!included[i]) {
                includeSubject(i);
            }
        }
        if (totalCredits < getSemesterPlan().minimalAmountOfCredits()) {
            throw new CryToStudentsDepartmentException("Not good subjects");
        }
    }

    private UniversitySubject[] buildByIncluded() {
        UniversitySubject[] res = new UniversitySubject[includedCount];
        int i = 0;
        for (int j = 0; j < sortedSubjs.length; ++j) {
            if (included[j]) {
                res[i++] = sortedSubjs[j];
            }
        }
        return res;
    }

    private final UniversitySubject[] sortedSubjs;
    private final SubjectRequirement[] reqs;
    private final boolean[] included;
    private int includedCount = 0;
    private int totalCredits = 0;

}
