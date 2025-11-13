package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.subject.builder.factory.SubjectListBuilderFactoryCS;

public final class ComputerScienceSemesterPlanner extends AbstractSemesterPlanner {
    public ComputerScienceSemesterPlanner() {
        super(new SubjectListBuilderFactoryCS());
    }
    /*
    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
        throws InvalidSubjectRequirementsException {
        validatePlan(semesterPlan);
        if(semesterPlan.minimalAmountOfCredits() <= 0){
            return new UniversitySubject[0];
        }
        UniversitySubject[] sortedSubjs = SubjectHelper.sortSubjectsByRatingDesc(semesterPlan.subjects());
        int totalCredits = 0;
        for(int i = 0; i < sortedSubjs.length; ++i){
            if(totalCredits >= semesterPlan.minimalAmountOfCredits()){
                return Arrays.copyOfRange(sortedSubjs, 0, i + 1);
            }
            totalCredits += sortedSubjs[i].credits();
        }
        throw new CryToStudentsDepartmentException("Too hard subjects!!");
    }
     */
}
