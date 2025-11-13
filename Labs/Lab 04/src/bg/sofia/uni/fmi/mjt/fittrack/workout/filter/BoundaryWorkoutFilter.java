package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

public abstract class BoundaryWorkoutFilter implements WorkoutFilter {
    public BoundaryWorkoutFilter(int min, int max) {
        if (min > max || min < 0 || max < 0) {
            throw new IllegalArgumentException("Invalid bounds");
        }
        this.min = min;
        this.max = max;
    }

    protected int getMin() {
        return min;
    }

    protected int getMax() {
        return max;
    }

    private final int min;
    private final int max;
}
