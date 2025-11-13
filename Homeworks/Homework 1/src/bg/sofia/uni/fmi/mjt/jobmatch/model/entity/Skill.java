package bg.sofia.uni.fmi.mjt.jobmatch.model.entity;

import java.util.Objects;

import static bg.sofia.uni.fmi.mjt.jobmatch.model.Validation.validate;

public record Skill(String name, int level) implements Comparable<Skill> {
    private static final int LEVEL_MIN = 0;
    private static final int LEVEL_MAX = 5;

    public Skill {
        validate(name, "name");
        validate(level, "level");
        if (level < LEVEL_MIN || level > LEVEL_MAX) {
            throw new IllegalArgumentException("Level must be between 0 and 5");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(Skill o) {
        return name.compareTo(o.name);
    }
}
