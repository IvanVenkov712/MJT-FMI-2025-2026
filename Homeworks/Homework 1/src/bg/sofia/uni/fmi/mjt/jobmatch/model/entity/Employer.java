package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import bg.sofia.uni.fmi.mjt.jobmatch.model.Validation;

import java.util.Objects;

public record Employer(String companyName, String email) {
    public Employer {
        Validation.validate(companyName, "companyName");
        Validation.validate(email, "email");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Employer employer = (Employer) o;
        return Objects.equals(email, employer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
