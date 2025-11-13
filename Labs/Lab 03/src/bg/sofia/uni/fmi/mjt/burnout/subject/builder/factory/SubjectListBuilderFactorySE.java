package bg.sofia.uni.fmi.mjt.burnout.subject.builder.factory;

import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.builder.SubjectListBuilder;
import bg.sofia.uni.fmi.mjt.burnout.subject.builder.SubjectListBuilderSE;

public class SubjectListBuilderFactorySE implements SubjectListBuilderFactory {
    @Override
    public SubjectListBuilder newInstance(SemesterPlan semesterPlan) {
        return new SubjectListBuilderSE(semesterPlan);
    }
}
