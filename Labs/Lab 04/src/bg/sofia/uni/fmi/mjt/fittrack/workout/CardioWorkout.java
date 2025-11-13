package bg.sofia.uni.fmi.mjt.fittrack.workout;

public final class CardioWorkout extends AbstractWorkout implements Workout {
    public CardioWorkout(String name, int duration, int caloriesBurned, int difficulty) {
        super(name, duration, caloriesBurned, difficulty, WorkoutType.CARDIO);
    }
}
