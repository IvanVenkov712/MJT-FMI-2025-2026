package bg.sofia.uni.fmi.mjt.fittrack.workout;

import bg.sofia.uni.fmi.mjt.fittrack.exception.InvalidWorkoutException;

import java.util.Objects;

public abstract class AbstractWorkout {
    private static final int MIN_DIFFICULTY = 1;
    private static final int MAX_DIFFICULTY = 5;

    public AbstractWorkout(String name, int duration, int caloriesBurned, int difficulty, WorkoutType type) {
        if (name == null || name.isBlank()) {
            throw new InvalidWorkoutException("name was null or empty");
        }
        if (duration <= 0) {
            throw new InvalidWorkoutException("duration is not positive");
        }
        if (caloriesBurned <= 0) {
            throw new InvalidWorkoutException("caloriesBurned is not positive");
        }
        if (difficulty < MIN_DIFFICULTY || difficulty > MAX_DIFFICULTY) {
            throw new InvalidWorkoutException("difficulty is out of bounds!");
        }
        if (type == null) {
            throw new InvalidWorkoutException("type is null");
        }
        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public WorkoutType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractWorkout that = (AbstractWorkout) o;
        return duration == that.duration && caloriesBurned == that.caloriesBurned && difficulty == that.difficulty &&
            Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, caloriesBurned, difficulty, type);
    }

    @Override
    public String toString() {
        return "AbstractWorkout{" +
            "name='" + name + '\'' +
            ", duration=" + duration +
            ", caloriesBurned=" + caloriesBurned +
            ", difficulty=" + difficulty +
            '}';
    }

    private final String name;
    private final int duration;
    private final int caloriesBurned;
    private final int difficulty;
    private final WorkoutType type;
}
