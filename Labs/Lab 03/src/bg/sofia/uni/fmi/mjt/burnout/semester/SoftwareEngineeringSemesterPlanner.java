package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.subject.builder.factory.SubjectListBuilderFactorySE;

public final class SoftwareEngineeringSemesterPlanner extends AbstractSemesterPlanner {
    public SoftwareEngineeringSemesterPlanner() {
        super(new SubjectListBuilderFactorySE());
    }
    /*
    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
    throws InvalidSubjectRequirementsException {
        validatePlan(semesterPlan);

        UniversitySubject[] sortedSubjs = SubjectHelper.sortSubjectsByCreditsDesc(semesterPlan.subjects());

        boolean[] included = new boolean[sortedSubjs.length];

        int creditsFromReqs = fillForReqs(sortedSubjs, semesterPlan.subjectRequirements(), included);

        fillForCredits(creditsFromReqs, sortedSubjs, semesterPlan.minimalAmountOfCredits(), included);

       return buildByIncluded(sortedSubjs, included);
    }

    private static UniversitySubject[] buildByIncluded(UniversitySubject[] sortedSubjs, boolean[] included){
        int includedCount = 0;
        for(boolean inc : included){
            if(inc){
                ++includedCount;
            }
        }
        UniversitySubject[] res = new UniversitySubject[includedCount];
        int i = 0;
        for(int j = 0; j < sortedSubjs.length; ++j){
            if(included[j]){
                res[i++] = sortedSubjs[j];
            }
        }
        return res;
    }

    private static int fillForReqs(UniversitySubject[] sortedSubjs, SubjectRequirement[] reqs, boolean[] included){
        int creditsTotal = 0;
        int[] countReq = new int[reqs.length];
        for(int i = 0; i < sortedSubjs.length; ++i){
            for(int j = 0; j < reqs.length; ++j){
                if(sortedSubjs[i].category() == reqs[j].category() && countReq[j] < reqs[j].minAmountEnrolled()){
                    included[i] = true;
                    creditsTotal += sortedSubjs[i].credits();
                    ++countReq[j];
                    break;
                }
            }
        }
        for(int i = 0; i < reqs.length; ++i){
            if(countReq[i] < reqs[i].minAmountEnrolled()){
                throw new CryToStudentsDepartmentException("Not good subjects");
            }
        }
        return creditsTotal;
    }

    private static void fillForCredits(int currCredits, UniversitySubject[] sortedSubjs,
    int minCredits, boolean[] included){
        for(int i = 0; i < sortedSubjs.length; ++i){
            if(currCredits >= minCredits){
                break;
            }
            if(!included[i]){
                included[i] = true;
                currCredits += sortedSubjs[i].credits();
            }
        }
        if(currCredits < minCredits){
            throw new CryToStudentsDepartmentException("Not good subjects");
        }
    }
     */
}
