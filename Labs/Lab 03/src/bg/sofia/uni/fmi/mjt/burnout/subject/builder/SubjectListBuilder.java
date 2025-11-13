package bg.sofia.uni.fmi.mjt.burnout.subject.builder;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public interface SubjectListBuilder {
    UniversitySubject[] build();
}
