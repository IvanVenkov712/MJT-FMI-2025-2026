package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import java.util.Set;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public class JobPosting {
    private String id;
    private String title;
    private String employerEmail;
    private Set<Skill> requiredSkills;
    private Education requiredEducation;
    private int requiredYearsOfExperience;
    private double salary;

    public JobPosting(String id, String title, String employerEmail, Set<Skill> requiredSkills,
                      Education requiredEducation, int requiredYearsOfExperience, double salary) {
        validate(id, "id");
        validate(title, "title");
        validate(employerEmail, "employeeEmail");
        validate(requiredSkills, "requiredSkills");
        validate(requiredEducation, "requiredEducation");
        validate(requiredYearsOfExperience, "requiredYearsOfExperience");
        validate(salary, "salary");

        this.id = id;
        this.title = title;
        this.employerEmail = employerEmail;
        this.requiredSkills = requiredSkills;
        this.requiredEducation = requiredEducation;
        this.requiredYearsOfExperience = requiredYearsOfExperience;
        this.salary = salary;
    }
}
