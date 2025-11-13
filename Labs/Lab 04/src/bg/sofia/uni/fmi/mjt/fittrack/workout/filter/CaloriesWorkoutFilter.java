package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class CaloriesWorkoutFilter extends BoundaryWorkoutFilter {
    public CaloriesWorkoutFilter(int min, int max) {
        super(min, max);
    }

    @Override
    public boolean matches(Workout workout) {
        return workout.getCaloriesBurned() >= getMin() && workout.getCaloriesBurned() <= getMax();
    }
}
