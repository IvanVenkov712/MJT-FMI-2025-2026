package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class DurationWorkoutFilter extends BoundaryWorkoutFilter {
    public DurationWorkoutFilter(int min, int max) {
        super(min, max);
    }

    @Override
    public boolean matches(Workout workout) {
        return workout.getDuration() >= getMin() && workout.getDuration() <= getMax();
    }
}
