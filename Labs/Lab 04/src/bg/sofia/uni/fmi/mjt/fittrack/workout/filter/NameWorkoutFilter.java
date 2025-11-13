package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

import java.awt.datatransfer.StringSelection;

public class NameWorkoutFilter implements WorkoutFilter {
    public NameWorkoutFilter(String keyword, boolean caseSensitive) {
        if (keyword == null) {
            throw new IllegalArgumentException("keyword was null");
        }
        this.keyword = keyword;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(Workout workout) {
        if (caseSensitive) {
            return workout.getName().equals(keyword);
        } else {
            return workout.getName().equalsIgnoreCase(keyword);
        }
    }

    private final String keyword;
    private final boolean caseSensitive;
}
