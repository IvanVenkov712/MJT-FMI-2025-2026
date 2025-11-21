package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import java.util.Objects;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public class Candidate {
    private final String name;
    private final String email;
    private final Set<Skill> skills;
    private final Education education;
    private final int yearsOfExperience;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;
        return Objects.equals(email, candidate.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Education getEducation() {
        return education;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
}
