package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;
import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.ByCalloryWorkoutComparator;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.ByDifficultyWorkoutComparator;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.WorkoutFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FitPlanner implements FitPlannerAPI {
    public FitPlanner(Collection<Workout> availableWorkouts) {
        if (availableWorkouts == null) {
            throw new IllegalArgumentException("availableWorkouts was null");
        }
        workouts = new ArrayList<>(availableWorkouts);
    }

    @Override
    public List<Workout> findWorkoutsByFilters(List<WorkoutFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("filters was null");
        }
        List<Workout> res = new ArrayList<>();
        for (Workout workout : workouts) {
            if (matchesAll(workout, filters)) {
                res.add(workout);
            }
        }
        return res;
    }

    @Override
    public List<Workout> generateOptimalWeeklyPlan(int totalMinutes) throws OptimalPlanImpossibleException {
        if (totalMinutes < 0) {
            throw new IllegalArgumentException("totalMinutes is negative");
        }
        if (!optimalPlanPossible(totalMinutes)) {
            throw new OptimalPlanImpossibleException("All workouts exceed the limit");
        }
        return new OptimalPlanGenerator(workouts, totalMinutes).getOptimalPlan();
    }

    @Override
    public Map<WorkoutType, List<Workout>> getWorkoutsGroupedByType() {
        if (groupedByType == null) {
            Map<WorkoutType, List<Workout>> res = new EnumMap<>(WorkoutType.class);
            for (Workout workout : workouts) {
                res.putIfAbsent(workout.getType(), new ArrayList<>());
                res.get(workout.getType()).add(workout);
            }
            groupedByType = Map.copyOf(res);
        }
        return groupedByType;
    }

    @Override
    public List<Workout> getWorkoutsSortedByCalories() {
        if (sortedByCalories == null) {
            sortedByCalories = List.copyOf(sortBy(new ByCalloryWorkoutComparator().reversed()));
        }
        return sortedByCalories;
    }

    @Override
    public List<Workout> getWorkoutsSortedByDifficulty() {
        if (sortedByDifficulty == null) {
            sortedByDifficulty = List.copyOf(sortBy(new ByDifficultyWorkoutComparator()));
        }
        return sortedByDifficulty;
    }

    @Override
    public Set<Workout> getUnmodifiableWorkoutSet() {
        if (workoutsSet == null) {
            workoutsSet = Set.copyOf(workouts);
        }
        return workoutsSet;
    }

    private List<Workout> sortBy(Comparator<Workout> comparator) {
        List<Workout> res = new ArrayList<>(workouts);
        Collections.sort(res, comparator);
        return res;
    }

    private boolean optimalPlanPossible(int totalMinutes) {
        for (Workout workout : workouts) {
            if (workout.getDuration() <= totalMinutes) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesAll(Workout workout, List<WorkoutFilter> filters) {
        for (WorkoutFilter filter : filters) {
            if (!filter.matches(workout)) {
                return false;
            }
        }
        return true;
    }

    private final List<Workout> workouts;
    private Map<WorkoutType, List<Workout>> groupedByType;
    private List<Workout> sortedByCalories;
    private List<Workout> sortedByDifficulty;
    private Set<Workout> workoutsSet;
}
