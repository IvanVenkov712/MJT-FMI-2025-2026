package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import bg.sofia.uni.fmi.mjt.jobmatch.model.Validation;

import java.util.Set;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public class Candidate {
    private String name;
    private String email;
    private Set<Skill> skills;
    private Education education;
    private int yearsOfExperience;

    public Candidate(String name, String email, Set<Skill> skills, Education education, int yearsOfExperience) {
        validate(name, "name");
        validate(email, "email");
        validate(skills, "skills");
        validate(education, "education");
        validate(yearsOfExperience, "yearsOfExperience");

        this.name = name;
        this.email = email;
        this.skills = skills;
        this.education = education;
        this.yearsOfExperience = yearsOfExperience;
    }
}
