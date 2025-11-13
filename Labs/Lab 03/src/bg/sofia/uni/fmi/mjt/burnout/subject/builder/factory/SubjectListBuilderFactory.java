package bg.sofia.uni.fmi.mjt.burnout.subject.builder.factory;

import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.builder.SubjectListBuilder;

public interface SubjectListBuilderFactory {
    SubjectListBuilder newInstance(SemesterPlan semesterPlan);
}
