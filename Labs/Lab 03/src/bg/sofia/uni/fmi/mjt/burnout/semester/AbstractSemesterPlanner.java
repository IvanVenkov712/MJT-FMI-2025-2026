package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.subject.builder.factory.SubjectListBuilderFactory;

import java.text.MessageFormat;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI permits
    ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    private static final int DAYS_FOR_JAR = 5;
    private static final int JAR_MULTIPLIER = 2;

    public AbstractSemesterPlanner(SubjectListBuilderFactory factory) {
        this.factory = factory;
    }

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
        throws InvalidSubjectRequirementsException {
        validatePlan(semesterPlan);
        return factory.newInstance(semesterPlan).build();
    }

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) {
        if (subjects == null || subjects.length < 1) {
            throw new IllegalArgumentException("null or missing subjects");
        }
        if (maximumSlackTime <= 0) {
            throw new IllegalArgumentException("maximumSlackTime is not positive");
        }
        if (semesterDuration <= 0) {
            throw new IllegalArgumentException("semesterDuration is not positive");
        }
        int restTime = getRestTimeTotal(subjects);
        if (restTime > maximumSlackTime) {
            throw new DisappointmentException("Too much rest needed. Vie mladite samo lejite i nishto ne pravite!!");
        }
        int studyTime = getStudyTimeTotal(subjects);
        int jars = studyTime / DAYS_FOR_JAR;
        if (studyTime + restTime > semesterDuration) {
            jars *= JAR_MULTIPLIER;
        }
        return jars;
    }

    protected static void validatePlan(SemesterPlan plan) throws
        InvalidSubjectRequirementsException {
        if (plan == null) {
            throw new IllegalArgumentException("plan is null");
        }
        checkForDubs(plan);
    }

    private static void checkForDubs(SemesterPlan plan) throws InvalidSubjectRequirementsException {
        CatIntPair[] catMap = new CatIntPair[] {
            new CatIntPair(Category.MATH),
            new CatIntPair(Category.PROGRAMMING),
            new CatIntPair(Category.THEORY),
            new CatIntPair(Category.PRACTICAL)
        };
        for (SubjectRequirement req : plan.subjectRequirements()) {
            catMap[req.category().getIndex()].increaseOccurs();
        }
        for (CatIntPair pair : catMap) {
            if (pair.getOccurences() > 1) {
                throw new InvalidSubjectRequirementsException(
                    MessageFormat.format("{0} category is repeated more than once!", pair.getCategory())
                );
            }
        }
    }

    private static int getRestTimeTotal(UniversitySubject[] subjects) {
        int restTime = 0;
        for (UniversitySubject subject : subjects) {
            restTime += subject.getRestTime();
        }
        return restTime;
    }

    private static int getStudyTimeTotal(UniversitySubject[] subjects) {
        int studyTime = 0;
        for (UniversitySubject subject : subjects) {
            studyTime += subject.neededStudyTime();
        }
        return studyTime;
    }

    private final SubjectListBuilderFactory factory;
}
