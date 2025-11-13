package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public record Skill(String name, int level) {
    public Skill {
        validate(name, "name");
        validate(level, "level");
    }
}
