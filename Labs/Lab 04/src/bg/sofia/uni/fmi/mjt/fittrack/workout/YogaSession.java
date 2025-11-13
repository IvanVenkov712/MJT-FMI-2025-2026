package bg.sofia.uni.fmi.mjt.fittrack.workout;

public final class YogaSession extends AbstractWorkout implements Workout {
    public YogaSession(String name, int duration, int caloriesBurned, int difficulty) {
        super(name, duration, caloriesBurned, difficulty, WorkoutType.YOGA);
    }
}
