package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import bg.sofia.uni.fmi.mjt.jobmatch.model.Validation;

public record Employer(String companyName, String email) {
    public Employer {
        Validation.validate(companyName, "companyName");
        Validation.validate(email, "email");
    }
}
